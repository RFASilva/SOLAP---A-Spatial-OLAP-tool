package solap.tablegenerator;

import org.w3c.dom.Document;

public interface TableGenerator {

    //Creates the table in the database with the data inserted
    public void generateTable();
    
    //Return the spatial query of the table generated
    public String getQuery();
    
    //Return the data with the necessary transformations (aggregation data belongs to the some group)
    public Document getClusteringData();
    
    public boolean didClustering();
}
