package de.uni_mannheim.informatik.dws.huber.analysis;

import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;

public class CSV_ParserAnalyzer {

  private ArrayList<WebIsA_Relation> relations;
  private int notParseableRelations;
  public ArrayList<WebIsA_Relation> getRelations() {
    return relations;
  }
  public void setRelations(ArrayList<WebIsA_Relation> relations) {
    this.relations = relations;
  }
  public int getNotParseableRelations() {
    return notParseableRelations;
  }
  public void setNotParseableRelations(int notParseableRelations) {
    this.notParseableRelations = notParseableRelations;
  }
  public CSV_ParserAnalyzer(ArrayList<WebIsA_Relation> relations, int notParseableRelations) {
    super();
    this.relations = relations;
    this.notParseableRelations = notParseableRelations;
  }
  public CSV_ParserAnalyzer() {
    super();
    this.relations = new ArrayList<WebIsA_Relation>();
    this.notParseableRelations = 0;
    // TODO Auto-generated constructor stub
  }
  
  
}
