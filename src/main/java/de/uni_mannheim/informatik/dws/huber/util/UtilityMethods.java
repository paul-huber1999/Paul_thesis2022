package de.uni_mannheim.informatik.dws.huber.util;

import java.io.File;
import java.util.ArrayList;

public interface UtilityMethods {

  /*
   * returns a list of File objects with the file names (including paths) in the directory
   */
  static ArrayList<File> returnAllWebIsA_FilePaths(String path) {

    String firstPart = path + "tuplesdb.i";
    String endPart = ".csv";
    ArrayList<File> list = new ArrayList<>();

    list.add(new File(firstPart + "00" + endPart));

    for (int i = 97; i < 123; i++) {
      list.add(new File(firstPart + "0" + (char) i + endPart));
    }

    for (int i = 97; i < 123; i++) {
      for (int j = 97; j < 123; j++) {

        list.add(new File(firstPart + (char) i + (char) j + endPart));

      }
    }
    return list;
  }

  /**
   * returns String which is the name of the csv file with the the two letters in the @param
   * beginningWith e.g., for "ab", "tuplesdb.iab.csv" will returned
   */
  public static String createCsvFileName(String beginningWith) {
    return "tuplesdb.i" + beginningWith + ".csv";
  }

  /**
   * removes all chars that are not letters from a String
   */
  public static String removeAllNonAlphaNumeric(String s) {
    if (s == null) {
      return null;
    }
    return s.replaceAll("[^A-Za-z]", "");
  }
}
