import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoJsonParser {
	
	public static long FIRST_ID = 1;
	
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

        try {

            Object obj = parser.parse(new FileReader("PPR_StreetTrees.geojson"));
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONArray msg = (JSONArray) jsonObject.get("features");
            Iterator<JSONObject> iterator = (Iterator<JSONObject>) msg.iterator();
            long smallest = Long.MAX_VALUE;
            while (iterator.hasNext()) {
            	    JSONObject tree = iterator.next();
            	    
            	    //get location in terms of latitude and longitude
            	    JSONObject geometry = (JSONObject) tree.get("geometry");
            	    String loc = geometry.get("coordinates").toString();
            	    Double lng = Double.parseDouble(loc.substring(loc.indexOf("[") + 1, loc.indexOf(",")));
            	    Double lat = Double.parseDouble(loc.substring(loc.indexOf(",") + 1, loc.indexOf("]")));
            	   
            	    //get species information and id
            	    JSONObject properties = (JSONObject) tree.get("properties");
            	    long id = (long) properties.get("OBJECTID");
            	    String species = (String) properties.get("SPECIES");
            	    
            	    //System.out.println(id-FIRST_ID + " at (" + lat + " , " + lng + ")");
            	    
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
