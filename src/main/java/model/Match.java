package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/** Represents a scheduled Match which may or may not have been played yet
 * @author Tess Gaston
 * @version 1.0
 * @since 1.0
 */
public class Match implements Comparable {
    private Team team1;
    private Team team2;
    private static int nextId = 1;
    private int id;
    private int [] score = new int[2];
    private LocalDate date;
    private LocalTime time;



    /**
     * ENUMS
     */

    public static enum MatchType{
        QUARTERFINAL, SEMIFINAL, FINAL, GROUPPLAY, DEFAULT
    }
    private MatchType matchType;
    /** Represents one of three possible outcome of a match:
     *  DRAW = both teams get 1,
     * TEAM1 = team1 gets 2, team2 gets 0,
     * TEAM2 = team2 gets 2, team1 gets 0,
     * */
    private enum Results{
        DRAW, TEAM1, TEAM2
    }
    private Results result;



    /**
     * CONSTRUCTORS
     */

    public Match(){
        this.id = Match.nextId;
        nextId++;
    }
    /** Overloading - creates a Match with the specified teams
     * @param team1 of type Team
     * @param team2 of type Team
     */
    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.id = Match.nextId;
        nextId++;
    }

    public Match(LocalDate date, LocalTime time, MatchType type ) {

        this.time = time;
        this.date = date;
        this.id = Match.nextId;
        this.setMatchType(type);
        nextId++;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setTeams(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }


    public int setResult(int team1score, int team2score){
        score[0] = team1score;
        score[1] = team2score;

        Team winner = null;
        Team looser = null;
        if(team1score-team2score < 0){
            result = Results.TEAM2;
            winner = team2;
            looser = team1;
            team2.addPoint(2);

        }else if(team1score-team2score > 0){
            result = Results.TEAM1;
            team1.addPoint(2);
            winner = team1;
            looser = team2;

        }else{
            team1.addPoint(1);
            team2.addPoint(1);
            result = Results.DRAW;
        }
        team1.addScore(team1score-team2score);
        team2.addScore(team2score-team1score);

        switch(matchType){

            case QUARTERFINAL:winner.setStatus( Team.Status.SEMIFINALIST);
                              looser.setStatus(Team.Status.NONE);
                              break;
            case SEMIFINAL:winner.setStatus( Team.Status.FINALIST);
                             break;
            case FINAL:winner.setStatus( Team.Status.WINNER);
                             break;
        }
        return winner.getId();
    }

    @Override
    public String toString() {
        String str;
        //DateTimeFormatter formatter = timeFormatter.ofPattern("HH:mm:ss");
        //time.format(formatter)
        //if the match has been assigned teams show them
        if(this.team1!=null && this.team2!=null){
          str =  date+":"+time
                  + " match# " +id+ " - "+this.matchType
                    + ":  "+team1.getName() +
                    " vs. " + team2.getName();
                    //if the match has been played, show score too
                    if(this.result!=null){
                        str +=", result: " +score[0]+" - "+score[1];
                     }
        }else{
            str = date+":"+time
                    + " match# " +id+ " - "+this.matchType;
        }
        return str;
    }
    public String teamMatchSQLString(int i) {
        //Man kan ikke sende mere end een kommando ad gangen
        String res = "INSERT INTO teamMatches (matchID, teamID, score) VALUES ("+this.id+","+team1.getId()+","+score[i]+");";
        return res;
    }


    @Override
    public int compareTo(Object o) {
        Match m = (Match) o;
        return time.compareTo(m.time);
    }

    public LocalTime getTime() {
        return time;
    }

    public int getId() {
        return id;
    }
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }
    public void setMatchType(String matchType) {
        this.matchType = MatchType.valueOf(matchType);
    }
    public MatchType getMatchType() {
        return this.matchType;
    }

    public String[] getTeamNames() {
        String [] teams = new String[2];
        teams[0]= team1.getName();
        teams[1]= team2.getName();
        return teams;
    }
    public int[] getTeamIds() {
       int [] teams = new int[2];
        teams[0]= team1.getId();
        teams[1]= team2.getId();
        return teams;
    }
}
