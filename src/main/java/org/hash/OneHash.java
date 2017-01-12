package org.hash;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/7/26.
 */
public class OneHash {

    public static Map<Integer,Integer> map = new HashMap<>();

//    public static void main(String[] args) {
//        Map<String,Map<String,List<String>>> m = new HashMap<>();
//        start(8080);
//        start(8081);
//        start(8082);
//    }

    public static Map<Integer,Integer> start(int port){
        int newport = HashUtil.Hash(port+"");
        for(int i = newport;i<=65535;i=i+1000){
            map.put(i,port);
        }
        for(int i = newport;i>=0;i=i-1000){
            map.put(i,port);
        }
        return map;
    }

}
