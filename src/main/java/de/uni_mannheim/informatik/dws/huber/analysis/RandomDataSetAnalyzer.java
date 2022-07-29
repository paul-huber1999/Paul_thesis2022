package de.uni_mannheim.informatik.dws.huber.analysis;

import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;

public class RandomDataSetAnalyzer {
  
  private int counter;
  private ArrayList<WebIsA_Relation> selectedRelations;
  
  public int getCounter() {
    return counter;
  }
  public void setCounter(int counter) {
    this.counter = counter;
  }
  public ArrayList<WebIsA_Relation> getSelectedRelations() {
    return selectedRelations;
  }
  public void setSelectedRelations(ArrayList<WebIsA_Relation> selectedRelations) {
    this.selectedRelations = selectedRelations;
  }
  
  public RandomDataSetAnalyzer(int counter, ArrayList<WebIsA_Relation> selectedRelations) {
    super();
    this.counter = counter;
    this.selectedRelations = selectedRelations;
  }
  
  public RandomDataSetAnalyzer() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  
  
}
