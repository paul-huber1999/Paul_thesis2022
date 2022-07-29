package de.uni_mannheim.informatik.dws.huber.operations;

import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.uni_mannheim.informatik.dws.huber.WebIsA.WebIsA_Relation;
import de.uni_mannheim.informatik.dws.huber.analysis.CSV_ParserAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CreateWnAnalyzer;
import de.uni_mannheim.informatik.dws.huber.analysis.CycleTransitiveRemovalAnalyzer;
import de.uni_mannheim.informatik.dws.huber.input.CSV_Parser;
import de.uni_mannheim.informatik.dws.huber.output.CsvWriter;
import de.uni_mannheim.informatik.dws.huber.util.Constants;

public class CycleTransitiveRemovalWn {

  private static final Logger LOGGER = LoggerFactory.getLogger(CycleTransitiveRemovalWn.class);

  private ArrayList<WebIsA_Relation> cycle_relations;
  private ArrayList<WebIsA_Relation> transitive_relations;
  private ArrayList<WebIsA_Relation> other_relations;

  public CycleTransitiveRemovalAnalyzer cycleTransitiveRemoval(File file) {

    CSV_ParserAnalyzer parser_analyzer = CSV_Parser.loadFile(file);
    if (parser_analyzer != null) {
      ArrayList<WebIsA_Relation> relations = parser_analyzer.getRelations();

      if (relations != null) {

        for (WebIsA_Relation relation : relations) {
          if (relation != null) {

            if (relation.InstanceIsInWordnet() && relation.ClassIsInWordnet()) {
              if (relation.createsCycleInWn()) {
                this.cycle_relations.add(relation);
                LOGGER.debug("cycle " + relation.getInstance() + " " + relation.getClass_());
              } else if (relation.transitiveInWn()) {
                this.transitive_relations.add(relation);
                LOGGER.debug("transitive " + relation.getInstance() + " " + relation.getClass_());
              } else {
                this.other_relations.add(relation);
                // LOGGER.debug(relation.getInstance());
              }
            } else {
              this.other_relations.add(relation);
              // LOGGER.debug(relation.getInstance());
            }
          }
        }
        CsvWriter.overwriteListToFile(this.other_relations, Constants.dirPathInput, file.getName());
        CsvWriter.appendListToFile(this.cycle_relations, Constants.dirPathReflexive,
            file.getName());
        CsvWriter.appendListToFile(this.transitive_relations, Constants.dirPathReflexive,
            file.getName());

        return new CycleTransitiveRemovalAnalyzer(this.cycle_relations.size(),
            this.transitive_relations.size(), this.other_relations.size(), file.getName());
      }
    }
    return null;
  }

  public CycleTransitiveRemovalWn(ArrayList<WebIsA_Relation> cycle_relations,
      ArrayList<WebIsA_Relation> transitive_relations, ArrayList<WebIsA_Relation> other_relations) {
    super();
    this.cycle_relations = cycle_relations;
    this.transitive_relations = transitive_relations;
    this.other_relations = other_relations;
  }

  public CycleTransitiveRemovalWn() {
    super();
    this.cycle_relations = new ArrayList<WebIsA_Relation>();
    this.transitive_relations = new ArrayList<WebIsA_Relation>();
    this.other_relations = new ArrayList<WebIsA_Relation>();
  }

}
