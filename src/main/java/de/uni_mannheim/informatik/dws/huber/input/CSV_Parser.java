package de.uni_mannheim.informatik.dws.huber.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;
import com.sun.media.jfxmedia.logging.Logger;
import de.uni_mannheim.informatik.dws.huber.SimpleMain;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;

public class CSV_Parser {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CSV_Parser.class);

  /*
   * loads input file which has to be a CSV File returns ArrayList of the relations from the CSV
   * file
   */
  public static CSV_ParserAnalyzer loadFile(File file) {

    
    BufferedReader in = null;
    String line = null;
    final String delimiter = ",";
    Pattern pattern =
        Pattern.compile("(\\d+),([-a-zA-Z0-9]+),([-a-zA-Z0-9]+),(\\d+),(\\d+),(\\d+),(.{1,10})");

    if (file.exists()) {
      CSV_ParserAnalyzer parserAnalyzer = new CSV_ParserAnalyzer();

      try {
        in = new BufferedReader(new FileReader(file));
        boolean firstLine = true;

        while ((line = in.readLine()) != null) {
          // leave out first line, which is the header line
          if (firstLine) {
            firstLine = false;
            continue;
          }
          try {
            if (line.charAt(0) == '"') {
              line = line.substring(1); // remove first char because it is can be a quote (")
            }
          } catch (StringIndexOutOfBoundsException ex) {
            LOGGER.error(ex.toString());
            continue;
          }
          Matcher matcher = pattern.matcher(line);
          try {
//            while (matcher.find()) {
//              parserAnalyzer.getRelations().add(new WebIsA_relation(Integer.parseInt(matcher.group(1)),
//                  matcher.group(2), matcher.group(3), Integer.parseInt(matcher.group(4)),
//                  Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)),
//                  matcher.group(7)));
//            }
             String[] token = line.split(delimiter, 7);
             parserAnalyzer.getRelations().add(WebIsA_Relation.stringArrToRelInstance(token));
          } catch (NumberFormatException nfe) {
            LOGGER.info(nfe.getMessage());
            parserAnalyzer.setNotParseableRelations(parserAnalyzer.getNotParseableRelations() + 1);
          }
        }

      } catch (FileNotFoundException e) {
        LOGGER.error(e.toString());
      } catch (IOException e) {
        LOGGER.error(e.toString());
      }
      // LOGGER.debug(relations.toString());

      return parserAnalyzer;
    }
    return null;
  }
}
