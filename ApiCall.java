package apiStuff;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ApiCall {
    //URL url = new URL("https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/P5Gxyl5vUXanXwY7oWRmmGxkMwKE_N_NvlVvAqEpQ5N_KTY?queue=450&endIndex=100&beginIndex=0&api_key=RGAPI-09a930b4-f60b-42bd-8a63-b32e9ec05634");

    String key = "RGAPI-09a930b4-f60b-42bd-8a63-b32e9ec05634";

    public ApiCall() throws IOException {
    }

    public void doTheStuff() throws IOException, InterruptedException {
        URL beginningURL = makeURL(0,100);
        BufferedReader in = new BufferedReader(new InputStreamReader(beginningURL.openStream()));
        String tmp = in.readLine();
        JSONObject obj = new JSONObject(tmp);
        JSONArray arr = obj.getJSONArray("matches");
        //System.out.println(obj.get("totalGames"));

        int totalGames = (int) obj.get("totalGames");
        System.out.println(totalGames);


        int counter = 0;
        for(int i= 0; i< arr.length();i++){
            System.out.println(counter +": "+arr.get(i));
            //JSONObject tmpObj = arr.getJSONObject(i);
            //System.out.println(tmpObj.get("champion"));
           // System.out.println(tmpObj.get("gameId"));
            counter++;
        }

        int tmpTotalGames = 100;
        int check =10;

        URL tmpURL;

        while(tmpTotalGames <= totalGames && check >=0){

            System.out.println("REQUEST ----------------------------------------------------------------------------------");
            System.out.println("GAMES LEFT "+ tmpTotalGames);
                tmpURL = makeURL(tmpTotalGames, tmpTotalGames + 100);

                BufferedReader in2 = new BufferedReader(new InputStreamReader(tmpURL.openStream()));
                String tmp2 = in2.readLine();
                JSONObject obj2 = new JSONObject(tmp2);
                JSONArray arr2 = obj2.getJSONArray("matches");
                for(int i= 0; i< arr2.length();i++){
                    System.out.println(counter+": "+arr2.get(i));
                    JSONObject tmpObj = arr2.getJSONObject(i);
                    //System.out.println(tmpObj.get("champion"));
                    //System.out.println(tmpObj.get("gameId"));
                    counter++;
                }
                tmpTotalGames +=100;


           // System.out.println("TOTOTOTOTOTO");
            TimeUnit.SECONDS.sleep(1);
            check--;
        }
        in.close();
    }


    public HashMap<Integer, String> parseChampFile() throws FileNotFoundException {
        HashMap<Integer, String> champMap = new HashMap<Integer, String>();
        File file = new File("C:\\Users\\Nate\\IdeaProjects\\RIOTAPITESTER\\src\\apiStuff\\champList.txt");
        Scanner scan = new Scanner(file);
        int champID = -1;

        scan.useDelimiter(",|:");
        int i = 0;
        while(scan.hasNext()){
            String nxt = scan.next();
            String newNxt = nxt.replaceAll("\"","");
            //System.out.println(newNxt);
            if(i == 0){
                champID = Integer.parseInt(newNxt);
                i = 1;
            }
            else if(i ==1){
                if(champID != -1){
                    champMap.put(champID, newNxt);
                }
                i = 0;
            }
        }
        scan.close();
        return champMap;
    }

    public URL makeURL(int beginningI, int endI) throws MalformedURLException {
        String s = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/P5Gxyl5vUXanXwY7oWRmmGxkMwKE_N_NvlVvAqEpQ5N_KTY?queue=450&endIndex=" + endI + "&beginIndex=" + beginningI + "&api_key=" +key;
        return new URL(s);
    }
}
