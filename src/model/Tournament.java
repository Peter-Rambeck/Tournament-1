package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
        m.setTime(time);
        matches.add(m);
        return m.getId();
    }

    abstract public void runTest();

    protected void displayTeams() {
        for (Team t:teams) {
            System.out.println(t);
        }
    }

    protected void displayMatches() {
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
}
