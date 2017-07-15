package com.hai.epltable;

/**
 * Created by pluto on 6/16/2017.
 */

public class MatchData {
    String date;
    String time;
    String homeNameTeam;
    String awayTeamName;
    String goalsHomeTeam;
    String goalsAwayTeam;

    public MatchData(String date, String time, String homeNameTeam, String awayTeamName, String goalsHomeTeam, String goalsAwayTeam) {
        this.date = date;
        this.time = time;
        this.homeNameTeam = homeNameTeam;
        this.awayTeamName = awayTeamName;
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsAwayTeam = goalsAwayTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHomeNameTeam() {
        return homeNameTeam;
    }

    public void setHomeNameTeam(String homeNameTeam) {
        this.homeNameTeam = homeNameTeam;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }
}
