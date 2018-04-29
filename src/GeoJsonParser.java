import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
	 * 		   "properties":
	 * 			{
	 * 				"OBJECTID":12714,
	 * 				"STATUS":null,
	 * 				"SPECIES":null,
	 * 				"DBH":null
	 * 			}
	 * }
	 */
	
	public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        Graph g = new Graph(NUM_OF_TREES);
        
        
        try {

        		//open file and get wrapper json
            Object obj = parser.parse(new FileReader("PPR_StreetTrees.geojson"));
            JSONObject jsonObject = (JSONObject) obj;
            
            //get the list of trees
            JSONArray trees = (JSONArray) jsonObject.get("features");
            Iterator<JSONObject> iterator = (Iterator<JSONObject>) trees.iterator();
            Tree prev = null;
            ArrayList<Tree> treeList = new ArrayList<Tree>();
            
            while (iterator.hasNext()) {
            	    JSONObject tree = iterator.next();
            	    
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
            	    		treeSpecies = Species.randomSpecies();
            	    }
            	    
            	    Tree treeObj = new Tree(id, lat, lng, treeSpecies);
            	    //System.out.println(treeObj);
             
            	    //add edge between prev tree and current tree
            	    if(prev != null) {
            	    		g.addEdge(new Edge(
            	    				 prev.getId(),          
            	    				 prev.getSpecies(),
            	    				 id,
            	    				 treeObj.getSpecies(),
            	    				 prev.distanceTo(treeObj)));
            	    }
            	    
            	    //treeList.add(treeObj);
            	    
            	    prev = treeObj;
          
            }
            //Collections.sort(treeList);
            
            //System.out.print(g);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
