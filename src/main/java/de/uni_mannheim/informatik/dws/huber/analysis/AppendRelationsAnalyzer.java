package de.uni_mannheim.informatik.dws.huber.analysis;

import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;

public class AppendRelationsAnalyzer {

  private int appended_relations;
  private int notPossible_relations;
  private int other_relations;
  private String fileName;
  
  
  
  public AppendRelationsAnalyzer(int appended_relations, int notPossible_relations,
      int other_relations, String fileName) {
    super();
    this.appended_relations = appended_relations;
    this.notPossible_relations = notPossible_relations;
    this.other_relations = other_relations;
    this.fileName = fileName;
  }
  
  @Override
  public String toString() {
   StringBuilder sb = new StringBuilder("File " + this.fileName + " contains: ");
   sb.append(this.appended_relations + " appended Relations, ");
   sb.append(this.notPossible_relations + " Relations that are not possible, and ");
   sb.append(this.other_relations + " other Relations.\n");

   return sb.toString();
  }
  
  public AppendRelationsAnalyzer() {
    super();
    // TODO Auto-generated constructor stub
  }

  public int getAppended_relations() {
    return appended_relations;
  }
  public void setAppended_relations(int appended_relations) {
    this.appended_relations = appended_relations;
  }
  public int getNotPossible_relations() {
    return notPossible_relations;
  }
  public void setNotPossible_relations(int notPossible_relations) {
    this.notPossible_relations = notPossible_relations;
  }
  public int getOther_relations() {
    return other_relations;
  }
  public void setOther_relations(int other_relations) {
    this.other_relations = other_relations;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  
 
}
