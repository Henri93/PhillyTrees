import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJsonParser {
	
	public static long FIRST_ID = 1;
	public static int NUM_OF_TREES = 111994;
	
	
	/* Tree Json Structure
	 * 
	 * {
	 * "geometry":{
	 * 				"coordinates":[-75.15990846155516,40.04461169263879],
	 * 				"type":"Point"
	 * 		      },
	 * 
	 * "type": "Feature",
	 * "properties":
	 * 			{
	 * 				"OBJECTID":12714,
	 * 				"STATUS":null,
	 * 				"SPECIES":null,
	 * 				"DBH":null
	 * 			}
	 * }
	 */
	
	public static Tree[] parseTreeData() {
		

        JSONParser parser = new JSONParser();
        
        ArrayList<Tree> treeList = new ArrayList<Tree>();
        
        JSONObject jsonObj = new JSONObject();
		
        JSONObject jsonObject;
        int index = 0;
        
        try {

        		//open file and get wrapper json
            //Object obj = parser.parse(new FileReader("PPR_StreetTrees.geojson"));
            Object obj = parser.parse(new FileReader("PhillyTree.geojson"));
            jsonObject = (JSONObject) obj;
            
            //get the list of trees
            JSONArray trees = (JSONArray) jsonObject.get("features");
            Iterator<JSONObject> iterator = (Iterator<JSONObject>) trees.iterator();
            Tree prev = null;
            ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();
            
            
            
            while (iterator.hasNext()) {
            	JSONObject tree = iterator.next();
	            	for (int i = 0; i < 20; i++) {
	            	    if (iterator.hasNext()) tree = iterator.next();
	            	}
            	    
            	    //get location in terms of latitude and longitude
            	    JSONObject geometry = (JSONObject) tree.get("geometry");
            	    String loc = geometry.get("coordinates").toString();
            	    Double lng = Double.parseDouble(loc.substring(loc.indexOf("[") + 1, loc.indexOf(",")));
            	    Double lat = Double.parseDouble(loc.substring(loc.indexOf(",") + 1, loc.indexOf("]")));
            	   
            	    //get species information and id
            	    JSONObject properties = (JSONObject) tree.get("properties");
            	   
            	    int id = Integer.valueOf(properties.get("OBJECTID").toString());
            	    
            	    String speciesString = (String) properties.get("SPECIES");
            	  
            	    Species treeSpecies = Species.GINKGO;
            	    
            	    if(speciesString == null) {
            	    		//generate random species
            	    		properties.remove("SPECIES");
            	    		treeSpecies = Species.randomSpecies();
            	    		properties.put("SPECIES", "\""+treeSpecies+"\"");
            	    }
            	    
            	    Tree treeObj = new Tree(id, lat, lng, treeSpecies);
            	    
            	    treeList.add(treeObj);
            	    
            	    index++;
            	    //System.out.println(treeObj);
            	    
            	    //used to make the dataset
            	    /*JSONArray geometryJson = new JSONArray();
            	    geometryJson.add("coordinates: [" + lng + "," + lat + "]");
            	    geometryJson.add("type: Point");
            		
            		JSONArray prop = new JSONArray();
            		prop.add("OBJECTID: " + id);
            		prop.add("SPECIES: " + treeSpecies);
            		
            		jsonObj.put("properties", prop);
            		jsonObj.put("geometry", geometryJson);
            		
            		jsons.add(jsonObj);*/
            		
             
            	    //add edge between prev tree and current tree
            	    /*if(prev != null) {
            	    		g.addEdge(new Edge(
            	    				 prev.getId(),          
            	    				 prev.getSpecies(),
            	    				 id,
            	    				 treeObj.getSpecies(),
            	    				 prev.distanceTo(treeObj)));
            	    }*/
            	    
            	   
            	    //jsons.add(tree);
            	    prev = treeObj;
          
            }
            
          
            
            //used to make the better dataset
            /*try (FileWriter file = new FileWriter("PhillyTree.geojson")) {
            		file.write(jsonObject.toJSONString());
            		//file.write("{\"type\":\"FeatureCollection\",\"features\":[");
            		//for(JSONObject json : jsons) {
            		//	file.write(json.toJSONString());
                //}
            		//file.write("]}");
    				System.out.println("Successfully Copied JSON Object to File...");
  
    			}catch(Exception e) {
    			 	e.printStackTrace();
    			}*/
           
           
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Tree[] stockArr = new Tree[treeList.size()];
        stockArr = treeList.toArray(stockArr);
        return stockArr; 

    }
}
