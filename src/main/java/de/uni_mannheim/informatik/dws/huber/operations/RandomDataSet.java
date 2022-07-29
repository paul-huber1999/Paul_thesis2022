package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.RandomDataSetAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;

public class RandomDataSet {

  public RandomDataSetAnalyzer selectRelations(File file, int counter) {

    CSV_ParserAnalyzer parserAnalyzer = CSV_Parser.loadFile(file);

    if (parserAnalyzer != null) {
      ArrayList<WebIsA_Relation> allRelations = parserAnalyzer.getRelations();
      ArrayList<WebIsA_Relation> selectedRelations = new ArrayList<>();
      
      for (WebIsA_Relation relation : allRelations) {
        if (relation != null) {
          if (counter % 242422 == 0) { // 8661130(no. of relations) /300 (size of dataset) = 28870
           // 72726867/300 = 242422
            selectedRelations.add(relation);            
          }
          counter++;
        }
      }
      return new RandomDataSetAnalyzer(counter, selectedRelations);
    }
    return null;
  }
}
