package de.uni_mannheim.informatik.dws.huber.analysis;

public class CycleTransitiveRemovalAnalyzer {

  private long cycleRelations;
  private long transitiveRelations;
  private long otherRelations;
  private String filename;


  public CycleTransitiveRemovalAnalyzer(long cycleRelations, long transitiveRelations,
      long otherRelations, String filename) {
    super();
    this.cycleRelations = cycleRelations;
    this.transitiveRelations = transitiveRelations;
    this.otherRelations = otherRelations;
    this.filename = filename;
  }

  public long getCycleRelations() {
    return cycleRelations;
  }

  public void setCycleRelations(long cycleRelations) {
    this.cycleRelations = cycleRelations;
  }

  public long getTransitiveRelations() {
    return transitiveRelations;
  }

  public void setTransitiveRelations(long transitiveRelations) {
    this.transitiveRelations = transitiveRelations;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public long getOtherRelations() {
    return otherRelations;
  }

  public void setOtherRelations(long otherRelations) {
    this.otherRelations = otherRelations;
  }

  @Override
  public String toString() {
   
    StringBuilder sb = new StringBuilder("File " + this.filename + " contains: ");
   sb.append(this.cycleRelations + " Cycle relations, ");
   sb.append(this.transitiveRelations + " transitive relations, and  ");
   sb.append(this.otherRelations + " other relations\n");
   
   return sb.toString();
  }

}
