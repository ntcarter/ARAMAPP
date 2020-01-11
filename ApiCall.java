package apiStuff;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ApiCall {
    //URL url = new URL("https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/P5Gxyl5vUXanXwY7oWRmmGxkMwKE_N_NvlVvAqEpQ5N_KTY?queue=450&endIndex=100&beginIndex=0&api_key=RGAPI-09a930b4-f60b-42bd-8a63-b32e9ec05634");
    String key = "RGAPI-8c0da7d7-0e70-4ec8-b4f3-53ad90068181";
    String acctID = "";

    public ApiCall() throws IOException {
    }


    //based off of an entered username this function gets all the ARAM game data for the user. Uses acctID global variable and makeURL function to work.
    public void doTheStuff() throws IOException, InterruptedException {
        JSONObject resultobj = new JSONObject();
        //first iteration of the calls is done separately. games 0-99
        URL beginningURL = makeURL(0,100);
        BufferedReader in = new BufferedReader(new InputStreamReader(beginningURL.openStream()));
        rateLimitSleep();
        //Creates a string based on the JSON returned by RIOT API.
        String tmp = in.readLine();
        //create a jsonobj based on the line read in.
        JSONObject obj = new JSONObject(tmp);
        //convert into JSON array so we can get one of the JSON values
        JSONArray arr = obj.getJSONArray("matches");

        //keeps track of the total games returned
        int totalGames = (int) obj.get("totalGames");
        System.out.println(totalGames);


        JSONArray arr3 = obj.getJSONArray("matches");
        //Counter for print tests can remove later
        int counter = 0;
        for(int i= 0; i< arr.length();i++){
            System.out.println(counter +": "+arr.get(i));
            JSONObject tmpObj = arr3.getJSONObject(i);
            System.out.println(tmpObj.get("champion"));
            System.out.println(tmpObj.get("gameId"));
            JSONArray sw = new JSONArray();
            sw.put(tmpObj.get("champion"));
            sw.put("PLACEHOLDERWIN/LOSS");
            resultobj.put(""+tmpObj.get("gameId"), sw);
            counter++;
        }

        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEe: "+resultobj);
        System.out.println(resultobj.get("3147744420"));
        //keeps track of the current game analyzed. starts at 100 since we have already looked at the first 100 (or all games if the user has less than 100).
        int tmpTotalGames = 100;
        //Not sure what this is doing I think its a safety thing so at most I will make 10 calls and not get black listed on accident.
        int check =10;

        URL tmpURL;

        while(tmpTotalGames <= totalGames && check >=0){

            System.out.println("REQUEST ----------------------------------------------------------------------------------");
            System.out.println("GAMES LEFT "+ tmpTotalGames);
                tmpURL = makeURL(tmpTotalGames, tmpTotalGames + 100);
                BufferedReader in2 = new BufferedReader(new InputStreamReader(tmpURL.openStream()));
                String tmp2 = in2.readLine();
                JSONObject obj2 = new JSONObject(tmp2);

                //Theres a bug with riot API sometimes the total games is incorrect this checks for the bug and corrects it.
                //The bug is an incorrect total games value. Checking at least 1 past the initial totalGames value corrects the bug if it exists.
                //The bug might exists for a small amount of people with an even multiple of 100 games
                int checkTotalGames = (int) obj2.get("totalGames");
                if(checkTotalGames  > totalGames){
                    totalGames = checkTotalGames;
                }
                JSONArray arr2 = obj2.getJSONArray("matches");
                for(int i= 0; i< arr2.length();i++){
                        System.out.println(counter+": "+arr2.get(i));
                        JSONObject tmpObj = arr2.getJSONObject(i);
                         //System.out.println(tmpObj.get("champion"));
                         //System.out.println(tmpObj.get("gameId"));
                        counter++;
                }
                //we get 100 games at a time from riot API so check in intervals of 100.
                tmpTotalGames +=100;


            //RIOT api limits 100 requests every 2 min. Thats about 1 request every 1.4 seconds being on the safe side.
            rateLimitSleep();
            check--;
        }
        in.close();
    }


    public HashMap<Integer, String> parseChampFile() throws FileNotFoundException {
        //Hash map to return
        HashMap<Integer, String> champMap = new HashMap<Integer, String>();
        //need to change the file path to be dynamic
        File file = new File("C:\\Users\\Nate\\IdeaProjects\\RIOTAPITESTER\\src\\apiStuff\\champList.txt");
        Scanner scan = new Scanner(file);
        //-1 is a temp value
        int champID = -1;

        scan.useDelimiter(",|:");
        int i = 0;
        while(scan.hasNext()){
            String nxt = scan.next();
            String newNxt = nxt.replaceAll("\"","");
            //i alternates between 0 and 1 grabbing champion ID's
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

    //Creates and returns a new URL for the Match Information JSON. RIOT api only allows 100 pieces of data at a time.
    //beginningI is the index to begin the RIOT API request at
    //endI is the index to end the RIOT aPI request at. (MAX 100 greater than beginningI)
    public URL makeURL(int beginningI, int endI) throws MalformedURLException {
        String s = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/"+acctID+"?queue=450&endIndex=" + endI + "&beginIndex=" + beginningI + "&api_key=" +key;
        return new URL(s);
    }


    //Gets the encrypted accountID based on the entered username
    public String getAccountID(String acctName) throws IOException {
        String tmp2 = "";
        String s = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+acctName+"?api_key="+key;
           // System.out.println("getting account ID for:" + acctName);

            URL call = new URL(s);
            try{
                BufferedReader in2 = new BufferedReader(new InputStreamReader(call.openStream()));
                 tmp2 = in2.readLine();
            }
            catch (Exception e){
                System.out.println("ERROR ACCOUNT NOT FOUND");
                return "ERROR NO ACCOUNT FOUND";
            }
        //System.out.println("INFO: "+tmp2);
        JSONObject obj2 = new JSONObject(tmp2);
        System.out.println(obj2.get("accountId"));
        acctID = (String) obj2.get("accountId");
        return (String) obj2.get("accountId");
    }

    /**
     * After every API call we sleep for 1.4 second to comply with RIOT's rate limit of 100 requests every 2 min. 200ms added extra to be safe
     * @throws InterruptedException
     */
    public void rateLimitSleep() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1400);
        return;
    }

    /**
     * take a game id and returns true for a win false for a loss.
     * @param gameID
     * @return
     */
    public Boolean winOrLoss(String gameID) throws InterruptedException, IOException {

        String s = "https://na1.api.riotgames.com/lol/match/v4/matches/"+gameID+"?api_key="+key;
        URL u = new URL(s);
        BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
        rateLimitSleep();
        //Creates a string based on the JSON returned by RIOT API.
        String tmp = in.readLine();
        //create a jsonobj based on the line read in.
        JSONObject obj = new JSONObject(tmp);
        //convert into JSON array so we can get one of the JSON values
        JSONArray arr = obj.getJSONArray("participantIdentities");

        for(int i = 0; i< 10; i++){
            System.out.println("ARRJSON: "+arr.get(i));
        }


        System.out.println(s);
        return false;
    }
}
