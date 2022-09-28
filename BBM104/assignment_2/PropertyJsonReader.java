import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;



public class PropertyJsonReader {
     private LinkedList<Square> squares = new LinkedList<Square>();
	 
     public PropertyJsonReader(){
         JSONParser processor = new JSONParser();
         try (Reader file = new FileReader("property.json")){
             JSONObject jsonfile = (JSONObject) processor.parse(file);
             JSONArray Land = (JSONArray) jsonfile.get("1");
             for(Object i:Land){
				 
				 //You can reach items by using statements below:
				 Integer.parseInt((String)((JSONObject)i).get("id"));
				 (String)((JSONObject)i).get("name");
				 Integer.parseInt((String)((JSONObject)i).get("cost"));
				 //And you can add these items to any data structure (e.g. array, linkedlist etc.
				 
				 
                 
             }
             JSONArray RailRoad = (JSONArray) jsonfile.get("2");
             for(Object i:RailRoad){
				 //You can reach items by using statements below:
                Integer.parseInt((String)((JSONObject)i).get("id");
				(String)((JSONObject)i).get("name");
				Integer.parseInt((String)((JSONObject)i).get("cost"));
				//And you can add these items to any data structure (e.g. array, linkedlist etc.
             }
			 
             JSONArray Company = (JSONArray) jsonfile.get("3");
             for(Object i:Company){
				 //You can reach items by using statements below:
                 Integer.parseInt((String)((JSONObject)i).get("id"));
				 (String)((JSONObject)i).get("name");
				 Integer.parseInt((String)((JSONObject)i).get("cost"));
             }
             
         } catch (IOException e){
             e.printStackTrace();
         } catch (ParseException e){
             e.printStackTrace();
         }
     }
     //You can add function(s) if you want
}