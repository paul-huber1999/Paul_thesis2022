package de.uni_mannheim.informatik.dws.huber.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.AppendRelationsAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CreateWnAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CycleTransitiveRemovalAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.ReverseRelationsAnalyzer;

public class CsvWriter {

  public static void appendListToFile(ArrayList<WebIsA_Relation> relations, String dirPath,
      String fileName) {

    if (relations.size() != 0) {

      File file = new File(dirPath + fileName);
      boolean fileMissing = false;
      if (!file.exists()) {
        fileMissing = true;
      }

      try (PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, true)));) {
        // if File is not there yet, insert first line of attributes
        if (fileMissing) {
          pw.println("_id,instance,class,frequency,pidspread,pldspread,modifications");
        }
        for (WebIsA_Relation relation : relations) {
          pw.println(relation.toString());
        }
        pw.close();
      } catch (IOException e) {
      }
    }

  }
  
  public static void appendListToFileRandomDS(ArrayList<WebIsA_Relation> relations, String dirPath,
      String fileName) {

    if (relations.size() != 0) {

      try (PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, true)));) {
        // if File is not there yet, insert first line of attributes
        
        for (WebIsA_Relation relation : relations) {
          pw.println(relation.getInstance() + "," + relation.getClass_());
        }
        pw.close();
      } catch (IOException e) {
      }
    }

  }

  public static void overwriteListToFile(ArrayList<WebIsA_Relation> relations, String dirPath,
      String fileName) {

    if (relations.size() != 0) {
      try (PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, false)));) {

        pw.println("_id,instance,class,frequency,pidspread,pldspread,modifications");
        for (WebIsA_Relation relation : relations) {
          pw.println(relation.toString());
        }
        pw.close();
      } catch (IOException e) {
      }
    }
  }

  public static void writeCreateWnAnalyzersToFile(ArrayList<CreateWnAnalyzer> analyzers,
      String dirPath, String fileName) {

    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, false)));) {

      pw.println("Reconstructing WordNet:");
      for (CreateWnAnalyzer analyzer : analyzers) {
        pw.println(analyzer.toString());
      }
      long totalWnHyponomies = 0;
      long totalReflexive = 0;
      long totalOthers = 0;
      long totalNotParsable = 0;
      for (CreateWnAnalyzer analyzer : analyzers) {
        totalWnHyponomies += analyzer.getWordnet_hyponymies();
        totalReflexive += analyzer.getReflexive_relations();
        totalOthers += analyzer.getHyponymies_not_in_wordnet();
        totalNotParsable += analyzer.getNotParsableRelations();
      }
      pw.println("There are " + totalWnHyponomies + " Wn Hyponomies,\n");
      pw.println(totalReflexive + " reflexive relations\n");
      pw.println(totalNotParsable + " not Parsable Relations\n");
      pw.println("and " + totalOthers + "Relations that are not in Wn.");

      pw.close();
    } catch (IOException e) {
    }
  }

  public static void writeCycleTransitiveRemovalToFile(
      ArrayList<CycleTransitiveRemovalAnalyzer> analyzers, String dirPath, String fileName) {

    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, false)));) {

      pw.println("Cycle and Transitive Removal WordNet:");
      for (CycleTransitiveRemovalAnalyzer analyzer : analyzers) {
        pw.println(analyzer.toString());
      }

      long totalCycleRelations = 0;
      long totalTransitiveRelations = 0;
      long totalOthers = 0;
      for (CycleTransitiveRemovalAnalyzer analyzer : analyzers) {
        totalCycleRelations += analyzer.getCycleRelations();
        totalTransitiveRelations += analyzer.getTransitiveRelations();
        totalOthers += analyzer.getOtherRelations();
      }

      pw.println("There are " + totalCycleRelations + " Cycle Relations,\n");
      pw.println(totalTransitiveRelations + " transitive Relations that are to be excluded,");
      pw.println(" and " + totalOthers + " other Relations.");

      pw.close();
    } catch (IOException e) {
    }
  }

  public static void writeAppendRelationsToFile(ArrayList<AppendRelationsAnalyzer> analyzers,
      String dirPath, String fileName) {
    
    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, false)));) {

      pw.println("Append Relations to existing Structure:");
      for (AppendRelationsAnalyzer analyzer : analyzers) {
        pw.println(analyzer.toString());
      }

      long totalAppendedRelations = 0;
      long totalNotPossibleRelations = 0;
      long totalOthers = 0;
      for (AppendRelationsAnalyzer analyzer : analyzers) {
        totalAppendedRelations += analyzer.getAppended_relations();
        totalNotPossibleRelations += analyzer.getNotPossible_relations();
        totalOthers += analyzer.getOther_relations();
      }

      pw.println("There are " + totalAppendedRelations + " appended Relations,\n");
      pw.println(totalNotPossibleRelations + " Relations that are to be excluded,");
      pw.println(" and " + totalOthers + " other Relations.");

      pw.close();
    } catch (IOException e) {
    }    
  }

  public static void writeReverseRelationsToFile(ArrayList<ReverseRelationsAnalyzer> analyzers,
      String dirPath, String fileName) {
    
    try (PrintWriter pw =
        new PrintWriter(new BufferedWriter(new FileWriter(dirPath + fileName, false)));) {

      pw.println("Reversed Relations:");
      for (ReverseRelationsAnalyzer analyzer : analyzers) {
        pw.println(analyzer.toString());
      }

      long totalRelations = 0;
      long totalReversedRelations = 0;
      for (ReverseRelationsAnalyzer analyzer : analyzers) {
        totalRelations += analyzer.getTotalRelations();
        totalReversedRelations += analyzer.getReversedRelations();
      }

      pw.println("There are " + totalRelations + " total Relations,\n");
      pw.println(" and " + totalReversedRelations + " of them were reversed and written to the respective file.");

      pw.close();
    } catch (IOException e) {
    }
  }

}
