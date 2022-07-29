package de.uni_mannheim.informatik.dws.huber;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.operations.Operations;
import de.uni_mannheim.informatik.dws.huber.util.Constants;


public class SimpleMain {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMain.class);

  public static void main(String[] args) {
    Options options = new Options();

    options.addOption(Option.builder("i").longOpt("input").hasArg().required()
        .desc("file or folder to process as input.").build());

    options.addOption(Option.builder("h").longOpt("help").desc("Showing this help.").build());

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("java -jar ", options);
      System.exit(1);
    }

    if (cmd.hasOption("help")) {
      formatter.printHelp("java -jar ", options);
      System.exit(1);
    }

//    File inputFile = new File(cmd.getOptionValue("input"));
//    if (inputFile.exists() == false) {
//      LOGGER.error("input does not exist: {} - stopping", inputFile);
//      System.exit(1);
//    }

    String input = cmd.getOptionValue("input");
    try {
      process(input);
    } catch (JWNLException ex) {
      LOGGER.error("JWNLException...", ex);
    }
  }



  /**
   * Wordnet Dictionary
   */
  // private static final Dictionary WORDNET_DICTIONARY = initializeDictionary();

  public static Dictionary initializeDictionary() {
    try {
      return Dictionary.getDefaultResourceInstance();
    } catch (JWNLException ex) {
      LOGGER.error("Could not load WordNet...", ex);
      return null;
    }
  }
  
  /**
   * invoked by main method, determines following course of program
   * different operations can be invoked from here: 
   * re-build of WordNet, removal of cycle and transitive relations, reversing relations and appending relations
   * 
   * @param input if input file is used from command line then 
   * @throws JWNLException
   */
  private static void process(String input) throws JWNLException {
    LOGGER.info("Process now file {}", input);

    Operations.runThroughWebIsA_Data1(Constants.dirPathInput);
    Operations.runThroughWebIsA_Data_Removal(Constants.dirPathInput);
    Operations.reverseWebIsA_Relations(Constants.dirPathInput);
    Operations.appendRelations(Constants.dirPathInputReversed); // this operation can be repeated as often as desired
  }

}
