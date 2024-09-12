package com.example.practice;

import org.apache.logging.log4j.util.Strings;

public class State {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String weather;
    private String time_zone;
    private Boolean visited;
    private Boolean want_to_visit;

    public State () {}

    public State(int id, String weather, String time_zone, Boolean visited, Boolean want_to_visit){
        this.id= id;
        this.weather= weather;
        this.time_zone = time_zone;
        this.visited =visited;
        this.want_to_visit = want_to_visit;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone() {
        this.time_zone = time_zone;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean getWant_to_visit() {
        return want_to_visit;
    }

    public void setWant_to_visit(Boolean want_to_visit) {
        this.want_to_visit = want_to_visit;
    }




}
