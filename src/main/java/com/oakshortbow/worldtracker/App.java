package com.oakshortbow.worldtracker;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class App
{
    public static void main( String[] args) throws IOException {
        int HEADER_INDEX=0;
        Document doc = Jsoup.connect("http://oldschool.runescape.com/slu?order=WplMA").get();
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        rows.remove(0);
        ArrayList<World> worldList = populateWorlds(rows);
        // for (World x:worldList){
        // System.out.println(x.toString());
        // }

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Frequency check: ");


        int n = reader.nextInt();
        reader.close();

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(n);
            } catch (Exception ex) {

            }
            doc = Jsoup.connect("http://oldschool.runescape.com/slu?order=WplMA").get();
            table = doc.select("table").get(0);
            rows = table.select("tr");
            rows.remove(HEADER_INDEX);
            ArrayList<World> newWorldList = populateWorlds(rows);
            for (int i = 0; i < worldList.size(); ++i) {
                //used as sorting key to filter by least to greatest
                newWorldList.get(i).setPopulationDiff(newWorldList.get(i).getPlayers() - worldList.get(i).getPlayers());
                worldList.get(i).setPopulationDiff(newWorldList.get(i).getPlayers() - worldList.get(i).getPlayers());
            }

            ArrayList<World>sortedNewWorldList = new ArrayList<>(newWorldList);;
            ArrayList<World>sortedWorldList = new ArrayList<>(worldList);;
            Collections.sort(sortedNewWorldList);
            Collections.sort(sortedWorldList);


            for (int i = 0; i < newWorldList.size(); ++i) {
                System.out.println("World " + sortedNewWorldList.get(i).getWorld() + " has gained/lost " + sortedNewWorldList.get(i).getPopulationDiff() + " Players! Bringing Population from " +sortedWorldList.get(i).getPlayers() +  " to " + sortedNewWorldList.get(i).getPlayers() + " Players!");
            }

            worldList = newWorldList;
        }
    }

    public static ArrayList<World> populateWorlds(Elements worlds){
        ArrayList<World>worldList = new ArrayList<>();
        for(Element x:worlds){

            String cleanedString = Jsoup.clean(x.html(), Whitelist.none());
            String worldName = cleanedString.substring(0,14);
            String worldNumString = cleanedString.substring(10,14);
            worldNumString = worldNumString.replaceAll("\\s+","");
            int worldNum = Integer.parseInt(worldNumString)+300;

            cleanedString = cleanedString.substring(14);
            String playerCountString ="";

            int playerCountIdentifier = cleanedString.indexOf("players");
            int playerCount=0;
            if(playerCountIdentifier!=-1) {
                playerCountString = cleanedString.substring(0, playerCountIdentifier);
                playerCountString = playerCountString.replaceAll("\\s+", "");
                playerCount = Integer.parseInt(playerCountString);
                cleanedString = cleanedString.substring(playerCountIdentifier + 7);
            }
            int memberIdentifier = cleanedString.indexOf("Members");
            int freeIdentifier = cleanedString.indexOf("Free");
            String status="";
            String country = "";
            if(freeIdentifier!=-1){
                status ="Free";
                country= cleanedString.substring(0 ,freeIdentifier);
                cleanedString= cleanedString.substring(freeIdentifier+4);
            }

            if(memberIdentifier!=-1){
                status ="Members";
                country= cleanedString.substring(0 ,memberIdentifier);
                cleanedString= cleanedString.substring(memberIdentifier+7);
            }
            country = country.replaceFirst("^ *", "");
            String activity = cleanedString.replaceFirst("^ *", "");

            worldList.add(new World(worldName,worldNum,playerCount,country,status,activity));
        }
        return worldList;
    }

}
