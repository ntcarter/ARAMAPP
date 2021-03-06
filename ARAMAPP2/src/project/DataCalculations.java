package project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class DataCalculations {

    private JSONObject data;

    public HashMap<Integer, String> champMap;
    public int totalGames = 0;

    public DataCalculations(JSONObject data, HashMap<Integer, String> champMap){
        this.champMap = champMap;
        this.data = data;
    }


    public void calcWinLoss(){

        JSONArray arr1 = new JSONArray();

        if(data.length() < 1){
            return;
        }
        System.out.println("DATA: "+data);

        System.out.println("KEYSET: "+data.keySet());
        Set<String> objKeys = data.keySet();


        Hashtable<Integer,Integer> numGames = new Hashtable<Integer, Integer>();
        Hashtable<Integer,Integer> numWins = new Hashtable<Integer, Integer>();

        //Creates the HashTable which "counts" how many times the user has played each champion
        //total games will be different then the number of entries in num games and num wins due to a champion being played more than once
        int counter = 0;
        for(String key : objKeys){
             arr1 = data.getJSONArray(key);
             System.out.println(champMap.get(arr1.get(0)));
            System.out.println(arr1.get(1));

            if(numGames.get(arr1.get(0)) == null){
                numGames.put((int) arr1.get(0) , 1);
            }
            else{
                int gamecount =  numGames.get(arr1.get(0));
                gamecount++;

                numGames.put((int) arr1.get(0), gamecount);
            }

            if(numWins.get(arr1.get(0)) == null){
                numWins.put((int) arr1.get(0) , 0);
            }
            if((boolean) arr1.get(1) == true){
                int winCount =  numWins.get(arr1.get(0));
                winCount++;

                numWins.put((int) arr1.get(0), winCount);
            }
            totalGames++;
        }

        System.out.println("TOTALGAMES: "+totalGames);
        System.out.println("1: "+numGames.entrySet().size());
        System.out.println("2: "+numWins.entrySet().size());


        Set<Integer> champKeys = numGames.keySet();


        for(int key : champKeys){
            System.out.println("");
            System.out.println("KEY: " + key);
            System.out.println("Champion: " + champMap.get(key));
            System.out.println("WINS: "+numWins.get(key));
            System.out.println("GAMES: "+ numGames.get(key));
            System.out.println("");
            double winR = ((double) numWins.get(key) / (double) numGames.get(key)) * 100.0;
            double playR = (double) numGames.get(key) / (double) totalGames;
            DecimalFormat df = new DecimalFormat("#.###");
            System.out.println(champMap.get(key) + "'s Winrate  is: "+ df.format(winR) + "%" );
            System.out.println(champMap.get(key) + "'s playrate is: "+ df.format(playR) + "%" );
        }



    }


    public void changeData(JSONObject newdata){
        data = newdata;
    }
}
