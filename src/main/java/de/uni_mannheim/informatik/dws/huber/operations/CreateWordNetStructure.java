package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CreateWnAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.output.CsvWriter;
import de.uni_mannheim.informatik.dws.huber.util.Constants;

public class CreateWordNetStructure {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateWordNetStructure.class);

  private ArrayList<WebIsA_Relation> wordnet_hyponymies;
  private ArrayList<WebIsA_Relation> reflexive_relations;
  private ArrayList<WebIsA_Relation> hyponymies_not_in_wordnet;

  public CreateWnAnalyzer createWordNetStructure(File file) {

    CSV_ParserAnalyzer parserAnalyzer = CSV_Parser.loadFile(file);
      
    if (parserAnalyzer != null) {
      ArrayList<WebIsA_Relation> relations = parserAnalyzer.getRelations();
      
      for (WebIsA_Relation relation : relations) {
        if (relation != null) {

          if (relation.InstanceIsInWordnet()) {
            if (relation.IsInWordNet()) {
              LOGGER.debug("true\n" + relation.getInstance());

              this.wordnet_hyponymies.add(relation);

            } else {
              // Reflexivität prüfen, nur wenn Instanz und Klasse nicht gleich sind
              if(!relation.getInstance().equals(relation.getClass_())) {
               
                WebIsA_Relation fake_reflexive =
                    new WebIsA_Relation(relation.getClass_(), relation.getInstance());
                if (fake_reflexive.InstanceIsInWordnet() && fake_reflexive.IsInWordNet()) {                               
                  this.reflexive_relations.add(relation);               
                
                } else {
                  this.hyponymies_not_in_wordnet.add(relation);  
                } 
              } else {
                this.hyponymies_not_in_wordnet.add(relation);           
              }
            }          
          } else {
            this.hyponymies_not_in_wordnet.add(relation);
          }

        }

      }
      // Writing ArrayLists of relations to respective files
      CsvWriter.appendListToFile(this.wordnet_hyponymies, Constants.dirPathOutput, file.getName());
      CsvWriter.appendListToFile(this.reflexive_relations, Constants.dirPathReflexive,
          file.getName());
      CsvWriter.overwriteListToFile(this.hyponymies_not_in_wordnet, Constants.dirPathInput,
          file.getName());

      return new CreateWnAnalyzer(this.wordnet_hyponymies.size(), this.reflexive_relations.size(),
          this.hyponymies_not_in_wordnet.size(), parserAnalyzer.getNotParseableRelations(), file.getName());

    }

    return null;

  }

  public CreateWordNetStructure() {
    this.wordnet_hyponymies = new ArrayList<WebIsA_Relation>();
    this.reflexive_relations = new ArrayList<WebIsA_Relation>();
    this.hyponymies_not_in_wordnet = new ArrayList<WebIsA_Relation>();
  }

  public CreateWordNetStructure(ArrayList<WebIsA_Relation> wordnet_hyponymies,
      ArrayList<WebIsA_Relation> reflexive_relations,
      ArrayList<WebIsA_Relation> hyponymies_not_in_wordnet) {
    super();
    this.wordnet_hyponymies = wordnet_hyponymies;
    this.reflexive_relations = reflexive_relations;
    this.hyponymies_not_in_wordnet = hyponymies_not_in_wordnet;
  }
}
