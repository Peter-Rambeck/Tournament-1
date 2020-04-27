package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Match {
    private int id;
    private Team team1;
    private Team team2;
    private String result;
    private String type;
    private int [] score = new int[2];
    private LocalDate date;

    private LocalTime time;
    private static int matchCount=0;
    private MatchType matchType;
    public enum MatchType{
      FINAL, SEMIFINAL, QUARTERFINAL, GROUPPLAY, DEFAULT
    }

    public Match(MatchType matchType) {
        matchCount++;
        this.id = matchCount;
    }

    public Match(LocalDate date, LocalTime time, MatchType matchType) {
          this.time = time;
          this.date = date;
          this.matchType = matchType;
          matchCount++;
          this.id = matchCount;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTeams(Team team1, Team team2){
        this.team1= team1;
        this.team2= team2;
    }

    @Override
    public String toString() {
        String str;
        DateTimeFormatter shortformat = DateTimeFormatter.ofPattern("HH:mm");

        if(this.team1!=null && this.team2 != null){
           str =  time.format(shortformat)+" Match#" + id +
                    ", " + team1.getName() +
                    " vs. " + team2.getName();
           if(this.result!=null) {
                 str += " "+this.score[0] + " - " + this.score[1];
           }
        }else{
           str = time.format(shortformat)+" Match#" + id;
        }
        return str;
    }


    public int getId() {
        return id;
    }

    public Team setResult(int teamscore1, int teamscore2) {
        Team winnerTeam;

        score[0]=teamscore1;
        score[1]=teamscore2;

        if(teamscore1 > teamscore2){
            winnerTeam = team1;
            this.result = "TEAM1";
            team1.addPoints(3);
        }else{
            winnerTeam = team2;
            this.result = "TEAM2";
            team2.addPoints(3);
        }

        team1.addScore(teamscore1-teamscore2);
        team2.addScore(teamscore2-teamscore1);

        return winnerTeam;
    }
}
