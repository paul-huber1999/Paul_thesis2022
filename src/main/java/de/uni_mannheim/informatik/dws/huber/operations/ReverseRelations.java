package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.ReverseRelationsAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.output.CsvWriter;
import de.uni_mannheim.informatik.dws.huber.util.Constants;
import de.uni_mannheim.informatik.dws.huber.util.UtilityMethods;

public class ReverseRelations {

  public ReverseRelations() {
    super();
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(ReverseRelations.class);

  public ReverseRelationsAnalyzer reverseRelations(File file) {

    ReverseRelationsAnalyzer analyzer = new ReverseRelationsAnalyzer(0, 0, file.getName());

    CSV_ParserAnalyzer parser_analyzer = CSV_Parser.loadFile(file);
    if (parser_analyzer != null) {
      ArrayList<WebIsA_Relation> relations = parser_analyzer.getRelations();

      if (relations != null) {

        for (WebIsA_Relation relation : relations) {
          if (relation != null) {

            analyzer.setTotalRelations(analyzer.getTotalRelations() + 1);

            String fileName;
            String class_ = UtilityMethods.removeAllNonAlphaNumeric(relation.getClass_());
            
            switch (class_.length()) {
              case 0:
                fileName = UtilityMethods.createCsvFileName("00");
                analyzer.setReversedRelations(analyzer.getReversedRelations() + 1);
                break;
              case 1:
                fileName = UtilityMethods.createCsvFileName("0" + class_);
                analyzer.setReversedRelations(analyzer.getReversedRelations() + 1);
                break;
              default:
                fileName = UtilityMethods.createCsvFileName(class_.substring(0, 2));
                analyzer.setReversedRelations(analyzer.getReversedRelations() + 1);
                break;
            }
            
            ArrayList<WebIsA_Relation> one_relation = new ArrayList<>();
            one_relation.add(relation.returnReflexiveRelation());
            CsvWriter.appendListToFile(one_relation, Constants.dirPathInputReversed, fileName);
          }
        }
        return analyzer;
      }
    }
    return null;
  }
}
