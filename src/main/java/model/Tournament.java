package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

abstract public class Tournament {
    private String title;
    protected static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<Match> matches = new ArrayList<Match>();

    public Tournament(String title) {
        this.title = title;
    }

    protected Team registerResult(int matchID, int teamScore1, int teamScore2){
        Match match = getMatchById(matchID);
        Team winnerTeam = match.setResult(teamScore1,teamScore2);
        return winnerTeam;
    }
    public void updateMatch(int m, Team team1, Team team2){
        Match match = getMatchById(m);
        match.setTeams(team1, team2);
    }

    private Match getMatchById(int matchId) {
        Match foundMatch = null;
        for (Match m1:matches) {
            if(m1.getId()==matchId){
                foundMatch = m1;
                break;
            }
        }
        return foundMatch;
    }

    protected int scheduleMatch(LocalDateTime time, Match.MatchType type){
        Match m = new Match(type);
        m.setTime(time.toLocalTime());
        m.setDate(time.toLocalDate());
        matches.add(m);
        return m.getId();
    }

    abstract public void runTest();

    public void displayTeams() {
        for (Team t:teams) {
            System.out.println(t);
        }
    }

    public void displayMatches() {
        System.out.println("*****************  TOURNAMENT SCHEDULE  *****************");

        for (Match m:matches) {
            System.out.println(m);
        }
    }

    public Team registerTeam(String teamName, String[] teamMembers) {
        Team team = new Team(teamName, teamMembers);
        teams.add(team);
        return team;
    }

    public void generateMatchesFromDB(ArrayList<String[]> matchesdata) {
        Iterator it = matchesdata.iterator();
        while(it.hasNext()){
            String[] matchdata = (String[]) it.next();
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(matchdata[3], dateformatter);
            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(matchdata[4], timeformatter);
            Match.MatchType type = Match.MatchType.valueOf(matchdata[1]);
            Match match = new Match(date, time, type);
            matches.add(match);
        }

    }
}
