/*

 use case 1 - registerTeams()          instantiation, constructors  (later enum, ArrayList)
 use case 2 - displayTeams()           toString method called manually on each team (later ArrayList and foreach)
 use case 3 - scheduleMatch()          instantiation, constructors, instance method invokation (later iterations: enum, ArrayList)
 use case 4 - displaySchedule()        toString method called manually on each team (later ArrayList and foreach)
 use case 5 - setTeams()               instance method invokation, getters and setters
 use case 6 - registerResult()         instance method invokation, setter, datastructure for score, (later enum, searching ArrayList, foreach with condition)
              findTeamById()
 use case 7 - displayNextMatch()        sorting, Comparator interface
            - Match implements Comarable  compare()
 use case 8 - reuse displayTeams (sort by score)        sorting, Comparator interface, compareTo()
            - TeamsSortByRank with score
            - getRank()
*/

import model.Match;
import model.Team;

import javax.management.AttributeList;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<Match> matches= new ArrayList<Match>();

    public static void main(String[] args) {
	/** USE CASE 1: Register teams that will play the tournament
         *  Providing team names and player details teams are enrolled in the tournament
         *  and will be added to the match schedule at a later point.
         * UX requirement on request
         * */

        Team team1 = registerTeam("Kongelunden",new String[]{"Tess","Leif"});
        Team team2 = registerTeam("Islands Brygge",new String[]{"Irma","Storm"});
        Team team3 = registerTeam("Hellerup",new String[]{"Coco","Cleo"});
        Team team4 = registerTeam("Frederiksberg",new String[]{"Sarah","Kasper"});
        Team team5 = registerTeam("Christianshavn",new String[]{"Emma","Indigo"});
        Team team6 = registerTeam("Plumstead",new String[]{"Mary","Martin"});
        Team team7 = registerTeam("Vedersø",new String[]{"Bodil","Svaage"});
        Team team8 = registerTeam("Allerslev",new String[]{"Stina","Torben"});

        displayTeams();


        int match1 = scheduleMatch(LocalDateTime.of(2020,03,6,12,00));
        int match2 = scheduleMatch(LocalDateTime.of(2020,03,6,12,30));
        int match3 = scheduleMatch(LocalDateTime.of(2020,03,6,13,00));
        int match4 = scheduleMatch(LocalDateTime.of(2020,03,6,13,30));
        int semifinal1 = scheduleMatch(LocalDateTime.of(2020,03,6,14,00));
        int semifinal2 = scheduleMatch(LocalDateTime.of(2020,03,6,14,30));
        int finalmatch = scheduleMatch(LocalDateTime.of(2020,03,6,15,00));

        updateMatch(match1, team1, team2);
        updateMatch(match2, team3, team4);
        updateMatch(match3, team5, team6);
        updateMatch(match4, team7, team8);

        //registerResult();


        displayMatches();

      //TODO: displayMatches()//System.out.println(getMatchById(match1));
    }

    public static void updateMatch(int m, Team team1, Team team2){
        Match match = getMatchById(m);
        match.setTeams(team1, team2);
    }

    private static Match getMatchById(int matchId) {
        Match foundMatch = null;
        for (Match m1:matches) {
            if(m1.getId()==matchId){
                foundMatch = m1;
                break;
            }
        }

        return foundMatch;
    }

    private static int scheduleMatch(LocalDateTime time){
        Match m = new Match();
        m.setTime(time);
        matches.add(m);
        return m.getId();
    }



    private static void displayTeams() {
        for (Team t:teams) {
            System.out.println(t);
        }
    }
    private static void displayMatches() {
        for (Match m:matches) {
            System.out.println(m);
        }
    }

    private static Team registerTeam(String teamName, String [] teamMembers) {
        Team team = new Team(teamName, teamMembers);
        teams.add(team);
        return team;
    }


}
