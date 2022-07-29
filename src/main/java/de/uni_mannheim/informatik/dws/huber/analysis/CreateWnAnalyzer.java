package de.uni_mannheim.informatik.dws.huber.analysis;

import java.util.ArrayList;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;

public class CreateWnAnalyzer {
  
  private int wordnet_hyponymies;
  private int reflexive_relations;
  private int hyponymies_not_in_wordnet;
  private int notParsableRelations;
  private String filename;
 
  
  public int getWordnet_hyponymies() {
    return wordnet_hyponymies;
  }
  public void setWordnet_hyponymies(int wordnet_hyponymies) {
    this.wordnet_hyponymies = wordnet_hyponymies;
  }
  public int getReflexive_relations() {
    return reflexive_relations;
  }
  public void setReflexive_relations(int reflexive_relations) {
    this.reflexive_relations = reflexive_relations;
  }
  public int getHyponymies_not_in_wordnet() {
    return hyponymies_not_in_wordnet;
  }
  public void setHyponymies_not_in_wordnet(int hyponymies_not_in_wordnet) {
    this.hyponymies_not_in_wordnet = hyponymies_not_in_wordnet;
  }
  public String getFilename() {
    return filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }

  public CreateWnAnalyzer(int wordnet_hyponymies, int reflexive_relations,
      int hyponymies_not_in_wordnet, int notParsableRelations, String filename) {
    super();
    this.wordnet_hyponymies = wordnet_hyponymies;
    this.reflexive_relations = reflexive_relations;
    this.hyponymies_not_in_wordnet = hyponymies_not_in_wordnet;
    this.notParsableRelations = notParsableRelations;
    this.filename = filename;
  }
  @Override
  public String toString() {
   StringBuilder sb = new StringBuilder("File" + this.filename + "contains: ");
   sb.append(this.wordnet_hyponymies + " Wn Hyponomies, ");
   sb.append(this.reflexive_relations + " reflexive relations, and  ");
   sb.append(this.notParsableRelations + " Relations that couldnt be parsed, and  ");
   sb.append(this.hyponymies_not_in_wordnet + " Hyponomies that are not in Wn\n");
   
   return sb.toString();
  }
  public int getNotParsableRelations() {
    return notParsableRelations;
  }
  public void setNotParsableRelations(int notParsableRelations) {
    this.notParsableRelations = notParsableRelations;
  }

  
}
