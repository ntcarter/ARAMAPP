package apiStuff;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {

        HashMap<Integer, String> champMap = new HashMap<Integer, String>();
        System.out.println("Hello World!");
        ApiCall apiCall = new ApiCall();

        apiCall.doTheStuff();
        champMap = apiCall.parseChampFile();

      /*  for(Map.Entry<Integer, String> ent : champMap.entrySet()){
            Integer k = ent.getKey();
            String s = ent.getValue();
            System.out.println("ChampID: "+k+" Name: "+s);

        }*/

        HashMap<Integer, HashMap<Integer, Boolean>> ourMap = new HashMap<Integer, HashMap<Integer, Boolean>>();

        //System.out.println(champMap.get(350));
       // System.out.println(champMap.get(266));
       // System.out.println(champMap.get(103));
       // System.out.println(champMap.get(245));
       // System.out.println(champMap.size());
    }
}
