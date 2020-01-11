package apiStuff;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {

        //Hashmap that relates a championID to the String name of the chamion
        HashMap<Integer, String> champMap = new HashMap<Integer, String>();
        System.out.println("Hello World!");
        //initialize the apiCall class
        ApiCall apiCall = new ApiCall();

        //Load the champion data from the file into the hashmap
        champMap = apiCall.parseChampFile();

        //Scraped hash map for storing all the data in one line. REMOVE IF UNNEEDED AT THE ENd
        HashMap<HashMap<Integer, Integer>, Boolean> ourMap = new HashMap<HashMap<Integer, Integer>, Boolean>();


        Scanner in = new Scanner(System.in);
        //delimits the username read in by the scanner so user name == username
        in.useDelimiter(" ");
        while(true){
            System.out.print("Enter a username: ");
            String s = in.nextLine();
            //removes any extra spaces in the string before or after the name being entered
            s = s.replace(" ", "");
            s = s.toLowerCase();
            if(s.equals("quit")){
                System.out.println("QUITTING");
                break;
            }
            else{
                System.out.println("");
                apiCall.getAccountID(s);
                apiCall.doTheStuff();
            }

        }

        apiCall.winOrLoss("2689365069");
        //System.out.println(champMap.get(350));
       // System.out.println(champMap.get(266));
       // System.out.println(champMap.get(103));
       // System.out.println(champMap.get(245));
       // System.out.println(champMap.size());
    }
}
