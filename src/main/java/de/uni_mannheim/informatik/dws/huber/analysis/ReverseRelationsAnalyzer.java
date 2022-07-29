package de.uni_mannheim.informatik.dws.huber.analysis;

public class ReverseRelationsAnalyzer {

  private int totalRelations;
  private int reversedRelations;
  private String fileName;
  
  public ReverseRelationsAnalyzer() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ReverseRelationsAnalyzer(int totalRelations, int reversedRelations, String fileName) {
    super();
    this.totalRelations = totalRelations;
    this.reversedRelations = reversedRelations;
    this.fileName = fileName;
  }

  @Override
  public String toString() {
   StringBuilder sb = new StringBuilder("File " + this.fileName + " contains: ");
   sb.append(this.totalRelations + " total Relations, of which ");
   sb.append(this.reversedRelations + " were reversed and put into another file.\n");
   
   return sb.toString();
  }

  public int getTotalRelations() {
    return totalRelations;
  }

  public void setTotalRelations(int totalRelations) {
    this.totalRelations = totalRelations;
  }

  public int getReversedRelations() {
    return reversedRelations;
  }

  public void setReversedRelations(int reversedRelations) {
    this.reversedRelations = reversedRelations;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
