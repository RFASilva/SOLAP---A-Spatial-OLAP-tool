package solap.utils;

import org.w3c.dom.Document;
import java.sql.*;

import java.text.Normalizer;

import oracle.xml.sql.query.*;
import oracle.spatial.geometry.*;
import java.util.*;

import pt.uminho.ubicomp.concaveHull.Point;

import solap.clustering.PreProcessing;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;

import solap.clustering.support.IDatabase;
import solap.clustering.support.Instance;

import solap.tablegenerator.ArcGenerator;
import solap.tablegenerator.TwoSpatialGenerator;

public class DBUtils {
    
    public DBUtils() {
    }
    
    //Executes query and returns data in xml format
    public Document executeQuery(String query) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");

            OracleXMLQuery qry = new OracleXMLQuery(conn, query);
            Document xmlData = qry.getXMLDOM();

            conn.close();

            return xmlData;
        } catch (Exception e) {
            System.out.println("Error executing query. " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
     
    public Map<ITriple<String, String, String>, JGeometry> computeArcs(String query, int offSet1, int offSet2, TwoSpatialGenerator tableGen) {
        Map<ITriple<String, String, String>, JGeometry> result = new HashMap<ITriple<String, String, String>, JGeometry>();
        
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
                       
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                //The location of data in the query depends on number of measures
                byte[] image1 = rs.getBytes(offSet1 + 1);
                String description1 = rs.getString(offSet1 + 2);
                byte[] image2 = rs.getBytes(offSet1 + 3);
                String description2 = rs.getString(offSet1 + 4);
                
                // Convert into JGeometry
                JGeometry geometry1 = JGeometry.load(image1);
                JGeometry geometry2 = JGeometry.load(image2);
                
                tableGen.addSpatialObject1(description1, geometry1);
                tableGen.addSpatialObject2(description2, geometry2);
                
                System.out.println("DESCRIPTION 1: " + description1);
                System.out.println("DESCRIPTION 2: " + description2);
                
                JGeometry arc = null;
                if(!description1.equals(description2))
                    arc = ArcGenerator.computeArc(geometry1, geometry2);
                
                ITriple<String, String, String> key = new Triple<String, String, String>(description1, description2, "");
                result.put(key, arc);
            }
            
            rs.close();
            stmt.close();   
            conn.close();
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error computing arcs: " + e.getMessage());
            return null;
        }
    }
    
    public void insertGroupCentroidInSummarizationTable(JGeometry spatialObject, String spatialCombination, String alphaNumeric1, Vector<String> cs, int index, String name) {
        String query = null;
        
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int numberAtt = 7 + cs.size();

            query = "insert into " + name + " values (";
            for ( int i = 0; i < numberAtt; i++) {
                if (i == numberAtt - 1)
                    query += "?)";
                else
                    query +="?,";
            }
            
            PreparedStatement ps = conn.prepareStatement(query);
            int currentPosition=1;
            ps.setInt(currentPosition, index); currentPosition++;
            ps.setString(currentPosition , alphaNumeric1); currentPosition++;
            for(String c: cs){
                ps.setString(currentPosition, c); currentPosition++;
            }
            ps.setString(currentPosition , spatialCombination); currentPosition++;
            Struct obj = JGeometry.store(spatialObject, conn);
            ps.setObject(currentPosition, obj); currentPosition++;
            ps.setString(currentPosition , " "); currentPosition++;
            ps.setString(currentPosition , spatialCombination); currentPosition++;
            ps.setString(currentPosition , spatialCombination); currentPosition++;
            
            //System.out.println("QUERY A EXECUTAR:");
            //System.out.println(ps.toString());
            ps.executeUpdate();
            ps.close();   
            conn.close();
        } catch (Exception e) {
            System.out.println("Error inserting a row");
            e.printStackTrace();
        }
    }

    public void insertSRowOneSpatialObject(JGeometry spatialObject, String spatialCombination, Vector<String> rowValues, int index, String name, String typeNewObjects) {
        String query = null;
        
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int numberAtt = rowValues.size() + 5;;

            query = "insert into " + name + " values (?,";
            for ( int i = 0; i < numberAtt; i++) {
                if (i == numberAtt - 1)
                    query += "?)";
                else
                    query +="?,";
            }
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, index);
            
            for ( int i = 0; i < rowValues.size(); i++) {
                String value = rowValues.get(i);
                 
                if( !isNumber(value) ) {
                    ps.setString(i+2, cleanString(rowValues.get(i)) );
                }
                else if (rowValues.get(i).split("\\.").length == 2) {
                    ps.setDouble(i+2, Double.parseDouble(value) );
                }
                else {
                    try {
                        ps.setInt(i+2, Integer.parseInt(value));
                    } catch(Exception e) {
                        ps.setLong(i+2, Long.parseLong(value));    
                    }
                }
            }
            
            ps.setString(rowValues.size() + 2, spatialCombination);
            
            //Convert JGeometry instante to DB_STRUCT
            Struct obj = JGeometry.store(spatialObject, conn);
            ps.setObject(rowValues.size() + 3, obj);
            
            String groupId = "";
            if(typeNewObjects.equals("group")) {
                 groupId = spatialCombination.substring(5,spatialCombination.length());
                 ps.setObject(rowValues.size() + 4, "G" + groupId);
                 ps.setObject(rowValues.size() + 5, "G" + groupId);
                 ps.setObject(rowValues.size() + 6, " ");                
            }
            else if (typeNewObjects.equals("intersection")) {
                ps.setObject(rowValues.size() + 4, spatialCombination);
                ps.setObject(rowValues.size() + 5, spatialCombination);
                ps.setObject(rowValues.size() + 6, " ");    
            }
            
            ps.executeUpdate();
            ps.close();   
            conn.close();
        } catch (Exception e) {
            System.out.println("Error inserting a row");
            e.printStackTrace();
        }
    }

    public Map<ITriple<String, String, String>, JGeometry> getIntersectedPolygons(String query, int offSet1, int offSet2) {   
        Map<ITriple<String, String, String>, JGeometry> result = new HashMap<ITriple<String, String, String>, JGeometry>();
 
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
                       
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                
                //The location of data in the query depends on number of measures
                byte[] image = rs.getBytes(offSet1 + 2);
                String description1 = rs.getString(offSet1 + 4);
                String description2 = rs.getString(offSet1 + 6);
               
                // Convert into JGeometry
                JGeometry geometry = JGeometry.load(image); 
                ITriple<String, String, String> key = new Triple<String, String, String>(description1, description2, "");
                result.put(key, geometry);
            }
            
            stmt.close();   
            conn.close();
            
            return result;
        } catch (Exception e) {
            System.out.println("Error get objects intersected: " + e.getMessage());
            return null;
        }
    }
    
    public CollectionSpatialObjects getDistinctsSpatialObjects(String query, int numberHigherAttr) {   
        CollectionSpatialObjects result = new CollectionSpatialObjects();

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
                       
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            //Only we want the distinct objects
            Map<String , Object> temp = new HashMap<String , Object>();
                                    
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                String semanticAttr = rs.getString(1);
                if(temp.get(semanticAttr) != null)
                    continue;
                    
                temp.put(semanticAttr, "");
                byte[] image = rs.getBytes(2);
                
                // Convert into JGeometry
                JGeometry geometry = JGeometry.load(image);
                Instance newInstance = new Instance(semanticAttr, geometry);
                
                //Para atributos a um nivel de granularidade superior comparativamente com o atributo espacial
                for(int i = 3; i < (3 + numberHigherAttr); i++)
                   newInstance.addHigherValue(rs.getString(i));
                
                int higherSpatialAttrIndex = 3 + numberHigherAttr;
                String higherSpatialAttrValue = "";
                try {
                    higherSpatialAttrValue = rs.getString(higherSpatialAttrIndex);
                    
                } catch(Exception e) {
                    higherSpatialAttrValue = "";
                }

                newInstance.setAttributeHigherLevel(higherSpatialAttrValue);
                result.addInstance(newInstance);
            }
            
            stmt.close();
            conn.close();
            
            return result;
        } catch (Exception e) {
            System.out.println("Error get spatial objects to apply the spatial clustering: " + e.getMessage());
            return null;
        }
    }
    
    public Map<ITriple<String, String, String>, JGeometry> computeArcsWithClustering(String query, int offSet1,
                                    int offSet2, List<Map<String, JGeometry>> groupsObjects, List<IDatabase> clusteredObjects, TwoSpatialGenerator tableGen) {
        Map<ITriple<String, String, String>, JGeometry> result = new HashMap<ITriple<String, String, String>, JGeometry>();
        
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
                       
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            boolean isGroup1 = false;
            boolean isGroup2 = false;
            
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                isGroup1 = false;
                isGroup2 = false;
                
                //The location of data in the query depends on number of measures
                String description1 = rs.getString(offSet1 + 2);
                String description2 = rs.getString(offSet1 + 4);
                
                ClusterObject obj1 = clusteredObjects.get(0).getClusterBySemanticAttr(description1);
                ClusterObject obj2 = clusteredObjects.get(1).getClusterBySemanticAttr(description2);
                
                JGeometry geometry1 = null;
                JGeometry geometry2 = null;
                
                boolean isNoiseAndUnclassified1 = obj1.getClusterLabel() == ClusterObject.NOISE || obj1.getClusterLabel() == ClusterObject.UNCLASSIFIED;
                boolean isNoiseAndUnclassified2 = obj2.getClusterLabel() == ClusterObject.NOISE || obj2.getClusterLabel() == ClusterObject.UNCLASSIFIED;
                
                if(isNoiseAndUnclassified1 && isNoiseAndUnclassified2) { 
                    byte[] image1 = rs.getBytes(offSet1 + 1);
                    byte[] image2 = rs.getBytes(offSet1 + 3);
                    
                    // Convert into JGeometry
                    geometry1 = JGeometry.load(image1);
                    geometry2 = JGeometry.load(image2);
                    
                }
                
                else if (!isNoiseAndUnclassified1 && isNoiseAndUnclassified2) {
                    geometry1 = groupsObjects.get(0).get(PreProcessing.NAME_GROUP + obj1.getClusterLabel());
                    byte[] image2 = rs.getBytes(offSet1 + 3);
                    geometry2 = JGeometry.load(image2);
                    isGroup1 = true;
                }
                
                else if (!isNoiseAndUnclassified2 && isNoiseAndUnclassified1) {
                    geometry2 = groupsObjects.get(1).get(PreProcessing.NAME_GROUP + obj2.getClusterLabel());
                    byte[] image1 = rs.getBytes(offSet1 + 1);
                    geometry1 = JGeometry.load(image1);
                    isGroup2 = true;
                }
                else {
                    geometry1 = groupsObjects.get(0).get(PreProcessing.NAME_GROUP + obj1.getClusterLabel());
                    geometry2 = groupsObjects.get(1).get(PreProcessing.NAME_GROUP + obj2.getClusterLabel());
                    isGroup1 = true;
                    isGroup2 = true;
                }
                
                System.out.println("DESCRIPTION 1: " + description1 + " POINT: " + geometry1.getJavaPoint());
                System.out.println("DESCRIPTION 2: " + description2 + " POINT: " + geometry2.getJavaPoint());
                
                JGeometry arc = null;
                if(!description1.equals(description2))
                    arc = ArcGenerator.computeArc(geometry1, geometry2);
                
                if(isGroup1)
                    description1 = PreProcessing.NAME_GROUP + obj1.getClusterLabel();
            
                if(isGroup2)
                    description2 = PreProcessing.NAME_GROUP + obj2.getClusterLabel();
                
                tableGen.addSpatialObject1(description1, geometry1);
                tableGen.addSpatialObject2(description2, geometry2);
                
                ITriple<String, String, String> key = new Triple<String, String, String>(description1, description2, "");
                result.put(key, arc);
            }
            
            stmt.close();   
            conn.close();
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error computing arcs with clustering: " + e.getMessage());
            return null;
        }
        
    }

    public void insertSRowTwoSpatialObjectClustering(JGeometry spatialObject,
                                                      String spatialCombination,
                                                      Vector<String> rowValues,
                                                      int index,
                                                      String name,
                                                      JGeometry ob1,
                                                      JGeometry ob2,
                                                      boolean first, boolean second, String id1, String id2, 
                                                      Map<String, String> labels1, Map<String, String> labels2) {
        
        
        String query = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int numberAtt = rowValues.size() + 10;
            query = "insert into " + name + " values (?,";
            for ( int i = 0; i < numberAtt; i++) {
                if (i == numberAtt - 1)
                    query += "?)";
                else
                    query +="?,";
            }
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, index);
            
            for ( int i = 0; i < rowValues.size(); i++) {
                
                if (rowValues.get(i).split(",").length == 1)
                    ps.setDouble(i+2, Double.parseDouble(rowValues.get(i)) );
                else ps.setInt(i+2, Integer.parseInt(rowValues.get(i)) );
            }
            
            ps.setObject(rowValues.size() + 2, "'" + spatialCombination + "'");
            
            //Convert JGeometry instante to DB_STRUCT
            Struct obj = JGeometry.store(spatialObject, conn);
            ps.setObject(rowValues.size() + 3, obj);
            
            Struct temp1 = JGeometry.store(ob1, conn);
            ps.setObject(rowValues.size() + 4, temp1);
            
            Struct temp2 = JGeometry.store(ob2, conn);
            ps.setObject(rowValues.size() + 5, temp2);
         
            boolean insertLegend1 = false;
            boolean insertLegend2 = false;
            
            if(first) {
                if(labels1.containsKey("G" + id1)) ps.setObject(rowValues.size() + 6, "");
                else  {
                    ps.setObject(rowValues.size() + 6, "G" + id1);
                    insertLegend1 = true;
                    labels1.put(("G" + id1), "");
                }
            }
            else ps.setObject(rowValues.size() + 6, "");
            
            if(second) {
                if(labels2.containsKey("G" + id2)) ps.setObject(rowValues.size() + 7, "");
                else {
                    ps.setObject(rowValues.size() + 7, "G" + id2);
                    insertLegend2 = true;
                    labels2.put("G" + id2, "");
                }
            }
            else ps.setObject(rowValues.size() + 7, "");
            
            String semanticDescription[] = spatialCombination.split(",");
            
            if(first) {
                ps.setObject(rowValues.size() + 8, "");
            }
            else {
                if(labels1.containsKey(semanticDescription[0]))
                    ps.setObject(rowValues.size() + 8, "");
                else {
                    ps.setObject(rowValues.size() + 8, semanticDescription[0]);
                    insertLegend1 = true;
                    labels1.put(semanticDescription[0], "");
                }
            }
            
            if(second) {
                ps.setObject(rowValues.size() + 9, "");
            }
            else {
                if(labels2.containsKey(semanticDescription[1]))
                    ps.setObject(rowValues.size() + 9, "");
                else {
                    ps.setObject(rowValues.size() + 9, semanticDescription[1]);
                    insertLegend2 = true;
                    labels2.put(semanticDescription[1], "");
                }
            }
            
            if(insertLegend1) {
                if(first) ps.setObject(rowValues.size() + 10, "G" + id1);
                else ps.setObject(rowValues.size() + 10, semanticDescription[0]);
            }
            else ps.setObject(rowValues.size() + 10, "");
            
            if(insertLegend2) {
                if(second) ps.setObject(rowValues.size() + 11, "G" + id2);
                else ps.setObject(rowValues.size() + 11, semanticDescription[1]);
            }
            else ps.setObject(rowValues.size() + 11, "");
            
            ps.executeUpdate();

            ps.close();   
            conn.close();
        } catch (Exception e) {
            System.out.println("Error inserting row with spatial object: " + e.getMessage());
        }
    }
    
    public void insertTwoSpatialNormal(JGeometry spatialObject, String spatialCombination,
                                       Vector<String> rowValues, int index,
                                       String name, JGeometry ob1,
                                       JGeometry ob2, Map<String, String> labels1, Map<String, String> labels2) {
        String query = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int numberAtt = rowValues.size() + 10;
            query = "insert into " + name + " values (?,";
            for ( int i = 0; i < numberAtt; i++) {
                if (i == numberAtt - 1)
                    query += "?)";
                else
                    query +="?,";
            }
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, index);
            
            for ( int i = 0; i < rowValues.size(); i++) {
                
                if (rowValues.get(i).split(",").length == 1)
                    ps.setDouble(i+2, Double.parseDouble(rowValues.get(i)) );
                else ps.setInt(i+2, Integer.parseInt(rowValues.get(i)) );
            }
            
            ps.setObject(rowValues.size() + 2, "'" + spatialCombination + "'");
            
            //Convert JGeometry instante to DB_STRUCT
            Struct obj = JGeometry.store(spatialObject, conn);
            ps.setObject(rowValues.size() + 3, obj);
            
            Struct temp1 = JGeometry.store(ob1, conn);
            ps.setObject(rowValues.size() + 4, temp1);
            
            Struct temp2 = JGeometry.store(ob2, conn);
            ps.setObject(rowValues.size() + 5, temp2);
            
            String semanticDescription[] = spatialCombination.split(",");
            
            ps.setObject(rowValues.size() + 6, " ");
            ps.setObject(rowValues.size() + 7, " ");
            
            boolean insertLegend1 = insertLegend(labels1, semanticDescription[0], ps, rowValues.size(), 8);
            boolean insertLegend2 = insertLegend(labels2, semanticDescription[1], ps, rowValues.size(), 9);
            
            if(insertLegend1)                        
                ps.setObject(rowValues.size() + 10, semanticDescription[0]);
            else ps.setObject(rowValues.size() + 10, "");
            
            if(insertLegend2)
                ps.setObject(rowValues.size() + 11, semanticDescription[1]);
            else ps.setObject(rowValues.size() + 11, "");
            
            ps.executeUpdate();

            ps.close();   
            conn.close();
        } catch (Exception e) {
            System.out.println("Error inserting row with arc without clustering " + e.getMessage());
        }
    }

//Auxiliary Functions
    
    private boolean insertLegend(Map<String, String> labels, String value,
                              PreparedStatement ps, int i, int pos) throws SQLException {
        if(labels.containsKey(value)) {
            ps.setObject(i + pos, "");
            return false;
        }
        else { 
            ps.setObject(i + pos, value);
            labels.put(value, "");
            return true;
        }
    }        

    public Map<String, JGeometry> getUnionPolygons() {
        Map<String, JGeometry> result = new HashMap<String, JGeometry>();
        
        String query = "select groupid, SDO_AGGR_UNION(MDSYS.SDOAGGRTYPE(clustering_polygons.geom, 0.005)) \n" + 
        "from clustering_polygons\n" + 
        "group by groupid";
        
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
                       
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                        
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                
                String group = rs.getString(1);
                byte[] image = rs.getBytes(2);
                
                // Convert into JGeometry
                JGeometry geometry = JGeometry.load(image); 
                result.put(group, geometry);
            }
            
            stmt.close();   
            conn.close();
            
            return result;
        } catch (Exception e) {
            System.out.println("Error get union polygons objects : " + e.getMessage());
            return null;
        }
       
    }
    
    public void insertPolygons(Map<String, List<Instance>> spatialObjects) {
        String query = null;
        int index = 0;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            PreparedStatement ps = null;
            Iterator<Map.Entry<String, List<Instance>>> it = spatialObjects.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, List<Instance>> next = it.next();
                for(Instance object: next.getValue()) {
                    query = "insert into " + "clustering_polygons" + " values (?,?,?)";
                  
                    ps = conn.prepareStatement(query); 
                    ps.setInt(1, index);
                    ps.setString(2, next.getKey());
                    
                    //Convert JGeometry instante to DB_STRUCT
                    Struct obj = JGeometry.store(object.getSpatialObject(), conn);
                    ps.setObject(3, obj);
            
                    ps.executeUpdate();
                    index++;
                }
            }

            ps.close();   
            conn.close();
        } catch (Exception e) {
            System.out.println("Error inserting row in polygon clustering table: " + e.getMessage());
        }
    }
    
    public static String cleanString(String string) {
        String text = Normalizer.normalize(string, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{L}\\p{N}]", "");
        
        return text;
    }

    public Map<ITriple<String, String, String>, Double> getPreComputedPolygonsDistances(String query) {
        Map<ITriple<String, String, String>, Double> result = new HashMap<ITriple<String, String, String>, Double>();
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            System.out.println("Query: " + query);
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        
            // Process results
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                String semanticAttr1 = rs.getString(2);
                String semanticAttr2 = rs.getString(3);
                double distance = rs.getDouble(4);                            
                result.put(new Triple<String, String, String>(semanticAttr1, semanticAttr2, ""), distance);
            }
            stmt.close();
            conn.close();
            
            return result;
        } catch (Exception e) {
            System.out.println("Error get pre-computed distances between polygons: " + e.getMessage());
            return null;
        }                                        
        
    }
    
    public boolean isNumber(String string) {
          char[] c = string.toCharArray();
          for(int i=0; i < string.length(); i++) {
              
              if(c[i] == '.') {
                  continue;
              }

              if ( !Character.isDigit(c[i])) {
                 return false;
              }
         }
         return true;
    }
    
    public void insertCharacterizedGroupsPolygons(List<Vector<Point>> polygons1, List<String> groupsPolygonsCharacteristics) {
        
        String query = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int counter = polygons1.size();
            query = "insert into polygon_point_group values (?,?,?)";
            
            PreparedStatement ps = conn.prepareStatement(query);
            for(int i = 0; i < counter; i++) {
                double[] temp = toPointsArray(polygons1, i);
                ps.setInt(1, i);
                JGeometry polygonGroup;
                Struct obj = null;
                if(temp != null) {
                    polygonGroup = JGeometry.createLinearPolygon(temp,2,8307);
                    obj = JGeometry.store(polygonGroup, conn);
                    ps.setObject(2, obj);
                }
                else {
                    ps.setNull(2, java.sql.Types.STRUCT, "MDSYS.SDO_GEOMETRY");
                }
                ps.setString(3, groupsPolygonsCharacteristics.get(i));
                ps.executeUpdate();
            }
            
            ps.close();   
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inserting polygon group representation " + e.getMessage());
        }
    }

    public void insertGroupsPolygons(List<Vector<Point>> polygons1, List<Vector<Point>> polygons2, int numberAssoc) {
        
        String query = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            
            int counter = 0;
            query = "insert into polygon_point_group values (?,?";
            
            if(numberAssoc == 2) query += ",?,?";
                
            query += ")";
            
            PreparedStatement ps = conn.prepareStatement(query);
            
            if(polygons1.size() >= polygons2.size())
                counter = polygons1.size();
            else counter = polygons2.size();
            
            //System.out.println("MAU A: " + polygons1.size());
            //System.out.println("MAU B: " + polygons2.size());
            //System.out.println("MAU C: " + counter);
            
            for(int i = 0; i < counter; i++) {
                
                double[] temp = toPointsArray(polygons1, i);
                ps.setInt(1, i);
                
                JGeometry polygonGroup;
                Struct obj = null;
                
                if(temp != null) {
                    polygonGroup = JGeometry.createLinearPolygon(temp,2,8307);
                    //System.out.println("AIAIAIAIAIAI    TEMP1: " + polygonGroup);
                    obj = JGeometry.store(polygonGroup, conn);
                    ps.setObject(2, obj);
                }
                else {
                    ps.setNull(2, java.sql.Types.STRUCT, "MDSYS.SDO_GEOMETRY");
                }
                
                if(numberAssoc == 2) {
                    
                    double[] temp2 = toPointsArray(polygons2, i);
                    ps.setInt(3, i);
                    
                    JGeometry polygonGroup2;
                    Struct obj2 = null;
                    
                    if(temp2 != null) {
                        polygonGroup2 = JGeometry.createLinearPolygon(temp2,2,8307);
                        //System.out.println("AIAIAIAIAIAI    TEMP2: " + polygonGroup2);
                        obj2 = JGeometry.store(polygonGroup2, conn);
                        ps.setObject(4, obj2);
                    }
                    else {
                        //System.out.println("POLIGONOS 3");
                        ps.setNull(4, java.sql.Types.STRUCT, "MDSYS.SDO_GEOMETRY");
                        //System.out.println("POLIGONOS 4");
                    }
                }
                
                ps.executeUpdate();
            }
            
            ps.close();   
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inserting polygon group representation " + e.getMessage());
        }
    }
    
    private double[] toPointsArray(List<Vector<Point>> polygons, int i) {
        System.out.println("polygons size: " + polygons.size() + " i " + i);
        try {
            if(i >= polygons.size())
                return null;
            
            double[] result = new double[(polygons.get(i).size()*2)+2];
            int index = 0;
            
            for(int j = 0; j < result.length-3; j+=2) {
                result[j] = polygons.get(i).get(index).getX();
                result[j+1] = polygons.get(i).get(index).getY();
                index++;
            }
            
            result[result.length-2] = polygons.get(i).get(0).getX();
            result[result.length-1] = polygons.get(i).get(0).getY();
            
            return result;
        }catch (Exception e) {
            return null;
        }
    }
    
    public  HashMap<String, JGeometry> obtainJGeometries(String query){
        HashMap<String, JGeometry> geometries = new HashMap<String, JGeometry>();
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "InformaticA0");
            // Execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Process results
            JGeometry geometry = null;
            while (rs.next()) {
                // Extract JDBC object as raw bytes
                String semanticSpatialAtribute = rs.getString(1);
                byte[] image = rs.getBytes(2);
                geometry = JGeometry.load(image); 
                geometries.put(semanticSpatialAtribute,geometry);
            }
            stmt.close();   
            conn.close();
            return geometries;
        }catch (Exception e) {
            System.out.println("Error no obtainJGeometries: " + e.getMessage());
            return null;
        }
    }
}
