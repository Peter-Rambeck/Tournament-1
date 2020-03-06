package model;

import java.time.LocalDateTime;

public class Match {
    private int id;
    private Team team1;
    private Team team2;
    private String result;
    private String type;
    private int [] score;
    private LocalDateTime time;
    private static int matchCount=0;


    public Match() {
        matchCount++;
        this.id = matchCount;

    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTeams(Team team1, Team team2){
        this.team1= team1;
        this.team2= team2;
    }

    @Override
    public String toString() {
        String str;
        if(this.team1!=null && this.team2 != null){
           str = "Match#" + id +
                    ", " + team1.getName() +
                    " vs. " + team2.getName() +
                         ", time: " + time;
        }else{
           str = "Match#" + id +
                    ", time: " + time;
        }
        return str;
    }





    public int getId() {
        return id;
    }
}
