package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.AppendRelationsAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CreateWnAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.output.CsvWriter;
import de.uni_mannheim.informatik.dws.huber.util.Constants;
import de.uni_mannheim.informatik.dws.huber.util.UtilityMethods;

public class AppendRelations {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppendRelations.class);

  private int appended_relations;
  private int notPossible_relations;
  private ArrayList<WebIsA_Relation> other_relations;
  
  public AppendRelations() {
    super();
    this.appended_relations = 0;
    this.notPossible_relations = 0;
    this.other_relations = new ArrayList<WebIsA_Relation>();
  }
  
  public AppendRelationsAnalyzer appendRelations(File file) {

    CSV_ParserAnalyzer parserAnalyzer = CSV_Parser.loadFile(file);
    CSV_ParserAnalyzer outputAnalyzer =
        CSV_Parser.loadFile(new File(Constants.dirPathOutput + file.getName()));

    if (parserAnalyzer != null && outputAnalyzer != null) {
      ArrayList<WebIsA_Relation> relations = parserAnalyzer.getRelations();
      ArrayList<WebIsA_Relation> outputRelations = outputAnalyzer.getRelations();

      Set<String> outputInstances = new HashSet<String>();
      
      for (WebIsA_Relation outputRelation : outputRelations) {
        outputInstances.add(outputRelation.getInstance());
      }

      for (WebIsA_Relation relation : relations) {
        if (relation != null) {
          
          String fileName;
          String class_ = UtilityMethods.removeAllNonAlphaNumeric(relation.getClass_());
          ArrayList<WebIsA_Relation> one_relation = new ArrayList<>();     
          
          if (relation.InstanceIsInWordnet() && relation.ClassIsInWordnet()) {
            
            switch (class_.length()) {
              case 0:
                fileName = UtilityMethods.createCsvFileName("00");
                break;
              case 1:
                fileName = UtilityMethods.createCsvFileName("0" + class_);
                break;
              default:
                fileName = UtilityMethods.createCsvFileName(class_.substring(0, 2));
                break;
            }
            one_relation.add(relation.returnReflexiveRelation());
            CsvWriter.appendListToFile(one_relation, Constants.dirPathReflexive, fileName);                  
            this.notPossible_relations++;

          } else if (outputInstances.contains(relation.getInstance())) {
            // relation.getPidspread() >= 2 && relation.getPldspread() >= 2 &&
  
            switch (class_.length()) {
              case 0:
                fileName = UtilityMethods.createCsvFileName("00");
                break;
              case 1:
                fileName = UtilityMethods.createCsvFileName("0" + class_);
                break;
              default:
                fileName = UtilityMethods.createCsvFileName(class_.substring(0, 2));
                break;
            }           
            one_relation.add(relation.returnReflexiveRelation());
            CsvWriter.appendListToFile(one_relation, Constants.dirPathOutput, fileName);
            
            this.appended_relations++;

          } else {
            this.other_relations.add(relation);
          }

        }
      }
      // Writing ArrayLists of relations to respective files
       CsvWriter.overwriteListToFile(this.other_relations, Constants.dirPathInputReversed,
       file.getName());
      
       return new AppendRelationsAnalyzer(this.appended_relations, this.notPossible_relations, this.other_relations.size(), file.getName());

    }

    return null;

  }

  
}
