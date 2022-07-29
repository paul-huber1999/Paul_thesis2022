package de.uni_mannheim.informatik.dws.huber.WebIsA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.SimpleMain;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.operations.Operations;
import de.uni_mannheim.informatik.dws.huber.util.Constants;
import de.uni_mannheim.informatik.dws.huber.util.UtilityMethods;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.dictionary.Dictionary;

/**
 * @author Paul Huber
 * representing a relation from WebIsA
 */
public class WebIsA_Relation {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebIsA_Relation.class);
  
  // attributes are identical to the one from relations in WebIsA
  private int id;
  private String instance;  // hyponym
  private String class_;    // hypernym
  private int frequency;
  private int pidspread;
  private int pldspread;
  private String modifications;

  public WebIsA_Relation(int id, String instance, String class_, int frequency, int pidspread,
      int pldspread, String modifications) {
    super();
    this.id = id;
    this.instance = instance;
    this.class_ = class_;
    this.frequency = frequency;
    this.pidspread = pidspread;
    this.pldspread = pldspread;
    this.modifications = modifications;
  }

  public WebIsA_Relation(String instance, String class_) {
    super();
    this.instance = instance;
    this.class_ = class_;
  }


  /**
   * gets String Array with attributes, then creates and returns WebIsA instance
   * @param token   attributes of the relation
   * @return WebIsA_Relation instance
   * @throws NumberFormatException
   */
  public static WebIsA_Relation stringArrToRelInstance(String[] token)
      throws NumberFormatException {

    return new WebIsA_Relation(Integer.parseInt(token[0]), token[1], token[2],
        Integer.parseInt(token[3]), Integer.parseInt(token[4]), Integer.parseInt(token[5]),
        token[6]);
  }

  /**
   * checks whether first part (hyponym) of the WebIsA_Relation instance is in WordNet
   * 
   * @return boolean true if hyponym of relation is in WordNet
   */
  public boolean InstanceIsInWordnet() {
    try {
      IndexWord instance_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.instance);

      if (instance_word != null) {
        return true;
      }
    } catch (JWNLException e) {
      LOGGER.error("JWNLException...", e);
    }
    return false;
  }

  /**
   * checks whether second part (hypernym) of the WebIsA_Relation instance is in WordNet
   * 
   * @return boolean true if hypernym of relation is in WordNet
   */
  public boolean ClassIsInWordnet() {
    try {
      IndexWord class_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.class_);

      if (class_word != null) {
        return true;
      }
    } catch (JWNLException e) {
      LOGGER.error("JWNLException...", e);
    }
    return false;
  }

  /**
   * checks whether WebIsA_Relation instance is also in WordNet
   * 
   * @return boolean true if relation is also an existing hypernymy relation in WordNet
   */
  public boolean IsInWordNet() {
    try {
      IndexWord instance_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.instance);

      if (instance_word != null) {
        List<Synset> instance_synsets = instance_word.getSenses(); // a word can appear in several WordNet synsets

        for (Synset synset : instance_synsets) {
          PointerTargetNodeList hypernyms;
          hypernyms = PointerUtils.getDirectHypernyms(synset);

          for (PointerTargetNode hypernym : hypernyms) {

            if (hypernym.getSynset().containsWord(this.class_)) {
              return true;
            }
          }
        }
      }
    } catch (JWNLException e) {
      LOGGER.error("JWNLException...", e);
    }
    return false;
  }

  /**
   * checks whether hyponym or hypernym attribute of a WebIsA_relation instance is in WordNet
   * 
   * @return boolean true if hyponym or hypernym is in WordNet
   */
  public boolean IsPartlyInWordNet() {
    try {
      IndexWord instance_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.instance);
      IndexWord class_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.class_);

      if (instance_word != null || class_word != null) {
        return true;
      }
    } catch (JWNLException e) {
      LOGGER.error("JWNLException...", e);
    }
    return false;
  }


  /**
   * 
   * @return WebIsA_relation instance that is reflexive, so hyponym and hypernym are switched 
   * 
   */
  public WebIsA_Relation returnReflexiveRelation() {
    return new WebIsA_Relation(this.getId(), this.getClass_(), this.getInstance(), this.getFrequency(), this.getPidspread(), this.getPldspread(), this.getModifications()) ;
  }
 
  /**
  * 
  * @return true if the WebIsA_relation instance creates a cycle in WordNet
  */
  public boolean createsCycleInWn() {

    try {
      IndexWord instance_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.instance);

      List<Synset> instance_synsets = instance_word.getSenses();
      
      for (Synset synset : instance_synsets) {
       
        PointerTargetTree hyponymTree = PointerUtils.getHyponymTree(synset);
        List<PointerTargetNodeList> hyponymList = hyponymTree.toList();
        for (PointerTargetNodeList list : hyponymList) {
          for (PointerTargetNode hyponym : list) {

            if (hyponym.getSynset().containsWord(this.class_)) {
              return true;
            }
          }
        }
      }
    } catch (JWNLException e) {
      LOGGER.error(e.toString());
    }
    return false;
  }
  
  /**
   * 
   * @return true if  the WebIsA_relation instance skips more than one layer of generalization in WordNet
   */
  public boolean transitiveInWn() {

    try {
      IndexWord instance_word = Operations.WORDNET_DICTIONARY.getIndexWord(POS.NOUN, this.instance);

      List<Synset> instance_synsets = instance_word.getSenses();
      
      for (Synset synset : instance_synsets) {
       
        PointerTargetTree hypernymTree = PointerUtils.getHypernymTree(synset);
        List<PointerTargetNodeList> hypernymList = hypernymTree.toList();
        for (PointerTargetNodeList list : hypernymList) {
          for (PointerTargetNode hypernym : list) {

            if (hypernym.getSynset().containsWord(this.class_)) {
              return true;
            }
          }
        }
      }
    } catch (JWNLException e) {
      LOGGER.error(e.toString());
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public String getClass_() {
    return class_;
  }

  public void setClass_(String class_) {
    this.class_ = class_;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  public int getPidspread() {
    return pidspread;
  }

  public void setPidspread(int pidspread) {
    this.pidspread = pidspread;
  }

  public int getPldspread() {
    return pldspread;
  }

  public void setPldspread(int pldspread) {
    this.pldspread = pldspread;
  }

  public String getModifications() {
    return modifications;
  }

  public void setModifications(String modifications) {
    this.modifications = modifications;
  }

  public boolean equals(WebIsA_Relation relation) {

    if (this.getInstance().equals(relation.getInstance())
        & this.getClass_().equals(relation.getClass_())) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder("");
    sb.append(Integer.toString(this.id) + ",");
    sb.append(this.instance + ",");
    sb.append(this.class_ + ",");
    sb.append(Integer.toString(this.frequency) + ",");
    sb.append(Integer.toString(this.pidspread) + ",");
    sb.append(Integer.toString(this.pldspread) + ",");
    sb.append(this.modifications);

    return sb.toString();
  }
  

}
