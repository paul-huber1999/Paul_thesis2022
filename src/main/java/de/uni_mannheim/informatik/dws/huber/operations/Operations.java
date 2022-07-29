package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.SimpleMain;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.AppendRelationsAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CreateWnAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CycleTransitiveRemovalAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.RandomDataSetAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.ReverseRelationsAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.output.CsvWriter;
import de.uni_mannheim.informatik.dws.huber.util.Constants;
import de.uni_mannheim.informatik.dws.huber.util.UtilityMethods;
import net.sf.extjwnl.dictionary.Dictionary;

public class Operations {

  private static final Logger LOGGER = LoggerFactory.getLogger(Operations.class);
  // this WordNet dictionary is used during the whole time the program is running
  public static final Dictionary WORDNET_DICTIONARY = SimpleMain.initializeDictionary();

  /*
   * the following methods are structurally similar, thus, only the first one is commented
   */
  
  /**
   * starts process of running through CSV files and re-building WordNet's tree structure
   * 
   * @param input represents directory path where files are located, the full WebIsA data set
   */
  public static void runThroughWebIsA_Data1(String inputPath) {

    ArrayList<File> files = UtilityMethods.returnAllWebIsA_FilePaths(inputPath);    // since the relations are stored in 703 CSV files, the according filepaths are generated
    ArrayList<CreateWnAnalyzer> analyzers = new ArrayList<>();                      

    for (File file : files) {                                                       // every file of the 703 is processed in the same way

      CreateWordNetStructure cwns = new CreateWordNetStructure();           
      CreateWnAnalyzer analyzer = cwns.createWordNetStructure(file);                // after the file was processed an analyzer is returned by the method processing the file that contains information about the number of relations that were assigned to which group

      if (analyzer != null) {
        analyzers.add(analyzer);
      } else {
        LOGGER.info("\n No such file as " + file.getName() + "\n");
      }
    }
    // the information of all analyzers is written to a separate file
    CsvWriter.writeCreateWnAnalyzersToFile(analyzers, Constants.dirPathAnalysis, "WNanalyzer1.txt");

  }

  public static void runThroughWebIsA_Data_Removal(String inputPath) {

    ArrayList<File> files = UtilityMethods.returnAllWebIsA_FilePaths(inputPath);
    ArrayList<CycleTransitiveRemovalAnalyzer> analyzers = new ArrayList<>();

    for (File file : files) {

      CycleTransitiveRemovalWn ctr = new CycleTransitiveRemovalWn();
      CycleTransitiveRemovalAnalyzer analyzer = ctr.cycleTransitiveRemoval(file);

      if (analyzer != null) {
        analyzers.add(analyzer);
      } else {
        LOGGER.info("\n No such file as " + file.getName() + "\n");
      }
    }

    CsvWriter.writeCycleTransitiveRemovalToFile(analyzers, Constants.dirPathAnalysis,
        "CTR_analyzer1806.txt");

  }


  public static void reverseWebIsA_Relations(String inputPath) {

    ArrayList<File> files = UtilityMethods.returnAllWebIsA_FilePaths(inputPath);
    ArrayList<ReverseRelationsAnalyzer> analyzers = new ArrayList<>();

    for (File file : files) {

      ReverseRelations rRel = new ReverseRelations();
      ReverseRelationsAnalyzer analyzer = rRel.reverseRelations(file);

      if (analyzer != null) {
        analyzers.add(analyzer);
      } else {
        LOGGER.info("\n No such file as " + file.getName() + "\n");
      }
    }

     CsvWriter.writeReverseRelationsToFile (analyzers, Constants.dirPathAnalysis,
     "ReverseRelations_analyzer.txt");

  }

  /*
   * 
   */
  public static void appendRelations(String inputPath) {
   
    ArrayList<File> files = UtilityMethods.returnAllWebIsA_FilePaths(inputPath);
    ArrayList<AppendRelationsAnalyzer> analyzers = new ArrayList<>();

    for (File file : files) {

      AppendRelations appendRel = new AppendRelations();
      AppendRelationsAnalyzer analyzer = appendRel.appendRelations(file);

      if (analyzer != null) {
        analyzers.add(analyzer);
      } else {
        LOGGER.info("\n No such file as " + file.getName() + "\n");
      }
    }

    CsvWriter.writeAppendRelationsToFile(analyzers, Constants.dirPathAnalysis, "AppendRelationsAnalyzer4_>0.txt");

  }
  
  /**
   * used for the creation of the evaluation data sets
   * @param inputPath path from which the random set be generated from
   */
  public static void createRandomDataSet(String inputPath) {
    
    ArrayList<File> files = UtilityMethods.returnAllWebIsA_FilePaths(inputPath);

    int counter = 0;
    
    for (File file : files) {

      RandomDataSet randomDS = new RandomDataSet();
      RandomDataSetAnalyzer analyzer = randomDS.selectRelations(file, counter);
      
      if(analyzer != null) {
        counter = analyzer.getCounter();
        
        if (!analyzer.getSelectedRelations().isEmpty()) {
          CsvWriter.appendListToFileRandomDS(analyzer.getSelectedRelations(), Constants.dirPathAnalysis, "evalSet1.csv");
        }
      }   
    }

  }



}
