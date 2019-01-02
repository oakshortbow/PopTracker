package com.oakshortbow.worldtracker;

import java.util.Comparator;

public class World implements Comparable<World>{

    private String worldName;
    private int world;
    private int players;
    private String location;
    private String type;
    private String activity;
    private int populationDiff;

    public World(String worldName,int world,int players,String location,String type,String activity){
        this.worldName=worldName;
        this.world = world;
        this.players=players;
        this.location=location;
        this.type = type;
        this.activity = activity;
        this.populationDiff = 0;
    }


    public World(){
        this.worldName="null";
        this.world = 0;
        this.players=0;
        this.location="null";
        this.type = "null";
        this.activity = "null";
        this.populationDiff = 0;

    }

    public String getWorldName() {
        return worldName;
    }

    public int getWorld() {
        return world;
    }

    public int getPlayers() {
        return players;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getActivity() {
        return activity;
    }

    public String toString(){
        return "World Name Is " + getWorldName() + "\n" + "World Number Is " + getWorld() + "\n" + "Player Count Is " + getPlayers() + "\n" +  "World Location is " + getLocation() + "\n" + "World Type is " + getType() + "\n" + "World Activity is " + getActivity() +"\n";
    }

    public int getPopulationDiff() {
        return populationDiff;
    }

    public void setPopulationDiff(int populationDiff) {
        this.populationDiff = populationDiff;
    }

    public int compareTo(World o){
        return(this.getPopulationDiff() - o.getPopulationDiff());
    }
}
