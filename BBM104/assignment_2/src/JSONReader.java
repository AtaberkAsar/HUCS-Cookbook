import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONReader {

    public static void ListJsonReader(ChanceCards[] chanceCards, CommunityChestCards[] communityChestCards){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("list.json")){
            JSONObject jsonFile = (JSONObject) processor.parse(file);

            int j = 0;
            JSONArray chanceList = (JSONArray) jsonFile.get("chanceList");
            for(Object i:chanceList){
                chanceCards[j++] = new ChanceCards((String)((JSONObject)i).get("item"));
            }

            j = 0;
            JSONArray communityChestList = (JSONArray) jsonFile.get("communityChestList");
            for(Object i:communityChestList){
                communityChestCards[j++] = new CommunityChestCards((String)((JSONObject)i).get("item"));
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public static void PropertyJsonReader(Squares[] squares){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("property.json")){
            JSONObject jsonFile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonFile.get("1");
            for(Object i:Land){
                int id = Integer.parseInt((String)((JSONObject)i).get("id"));
                String name = (String)((JSONObject)i).get("name");
                int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                squares[id - 1] = new Land(id - 1 , name, cost);
            }

            JSONArray RailRoad = (JSONArray) jsonFile.get("2");
            for(Object i:RailRoad){
                int id = Integer.parseInt((String)((JSONObject)i).get("id"));
                String name = (String)((JSONObject)i).get("name");
                int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                squares[id - 1] = new RailRoads(id - 1, name, cost);
            }

            JSONArray Company = (JSONArray) jsonFile.get("3");
            for(Object i:Company){
                int id = Integer.parseInt((String)((JSONObject)i).get("id"));
                String name = (String)((JSONObject)i).get("name");
                int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                squares[id - 1] = new Company(id - 1, name, cost);
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }

        squares[2] = new Squares(2, "Community Chest");
        squares[17] = new Squares(17, "Community Chest");
        squares[33] = new Squares(33, "Community Chest");

        squares[4] = new TaxSquares(4, "Income Tax");
        squares[38] = new TaxSquares(38, "Super Tax");

        squares[7] = new Squares(1, "Chance");
        squares[22] = new Squares(22, "Chance");
        squares[36] = new Squares(36, "Chance");

        squares[0] = new Squares(0, "GO");
        squares[10] = new Squares(10, "Jail");
        squares[20] = new Squares(20, "Free Parking");
        squares[30] = new Squares(30, "Go to Jail");

    }
}
