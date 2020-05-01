package model;

import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

abstract public class Tournament {
    protected String title;
    protected int id;

    //dataobjects
    protected ArrayList<Team> teams = new ArrayList<Team>();
    protected ArrayList<Match> matches = new ArrayList<Match>();
    protected List sortedTeams = teams;
    protected List sortedMatches = matches;


    //serialized dataobjects
    private HashMap <String, ArrayList<String>> teamsdata;
    private ArrayList<String> matchdata;

    public Tournament(String title) {
        this.title = title;
    }

    public void runTest(){
        System.out.println("Testing Tournament");
    }

    @Override
    public String toString() {
        return "Tournament "+title;
    }

   abstract public int scheduleMatch(String d, String t, String matchType);

    /**
     * Registrering af hold når datastrukturen er String [] - ofte fra csv fil, eller fra en test case.
     */

    public Team registerTeam(String teamName, String [] teamMembers) {//REFACTOR; remove method after converting player list read from csv data to arraylist
        Team team = new Team(teamName, teamMembers);
        teams.add(team);
        return team;
    }
    /**
     * Registrering af hold når datastrukturen er ArrayListe [] - fra database eller UI.
     */
    public Team registerTeam(String teamName, ArrayList<String> teamMembers) {
        Team team = new Team(teamName, teamMembers);
        teams.add(team);
        return team;
    }


    public void generateTeamsFromFile( Scanner data) {
        String line = "";
        while(data.hasNextLine()){
            line = data.nextLine();
            String [] lineArr = line.split(",");
            String [] playerArray = Arrays.copyOfRange(lineArr, 1, 2);
            //For hver linie i filen
            registerTeam(lineArr[0],playerArray);
        }

    }
    /**
     * Vi har holddata fra databasen, men vi har brug for at omforme dem til dataobjekter,
     * så vi nemt kan ændre tilstanden mens programmet kører.
     *
     * */
    public void generateTeamsFromDB(LinkedHashMap <String, ArrayList<String>> teamsdata ){
        this.teamsdata = teamsdata;
        Iterator it = teamsdata.entrySet().iterator();

        while(it.hasNext()){
           Map.Entry pair = (Map.Entry)it.next();//Jeg skal bruge nøglens pålydende ikke dens værdi, derfor har jeg brug for denne variabel
            //TODO, make it so that registerTeam() takes arrayList instead of string[]
            Team team = new Team((String) pair.getKey(),(ArrayList<String>)pair.getValue());
            teams.add(team);
            System.out.println(pair.getKey()+" : "+pair.getValue());
        }

    }

    /**
     * Vi har kampdata fra databasen, men vi har brug for at omforme dem til dataobjekter,
     * så vi nemt kan ændre tilstanden mens programmet kører.
     * */
    public void generateMatchesFromDB(ArrayList<String []> matchdetails,  LinkedHashMap<Integer, int[][]> results ){
        for(String[] row: matchdetails) {
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(row[2], dateformatter);
            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(row[3], timeformatter);
            Match.MatchType type = Match.MatchType.valueOf(row[0]);
            Match match = new Match(date, time, type);
            matches.add(match);
            int [][] matchresult= results.get(match.getId());

            int team1id = matchresult[0][0];
            int team2id =  matchresult[1][0];
            int scoreteam1 = matchresult[0][1];
            int scoreteam2 = matchresult[1][1];
            Team team1 =  getTeamById(team1id);
            Team team2 =  getTeamById(team2id);
            match.setTeams(team1,team2);
            registerResult(match.getId(), scoreteam1 , scoreteam2);

        }
     }


    public void updateMatch(int matchId, Team winner1, Team winner2) {
        Match m = getMatchById(matchId);
        m.setTeams(winner1, winner2);
    }

    public Team registerResult(int matchId, int team1_score, int team2_score) {
        Match m = getMatchById(matchId);
        int winner = m.setResult(team1_score,team2_score);
        return getTeamById(winner);
    }



    public void displayTeams(String order){

        switch(order){
            case "natural": System.out.println("************ TEAMS *****************");
                            for (Team t:teams) {
                                System.out.println(t);
                            }
                break;
            case "ranked":   System.out.println("************ TEAMS RANKED *****************");
            Comparator comp= new TeamSortByRank();
                Collections.sort(sortedTeams, comp);

                for (Object st: sortedTeams) {
                    Team t = (Team) st;
                    int rank = getTeamRank(t.getId());
                    System.out.println(rank+". "+t);
                }
                break;
            case "score":   System.out.println("************ TEAMS RANKED *****************");
               // Comparator comp2= new TeamSortByScore();
            //    Collections.sort(sortedTeams, comp2);

                for (Object st: sortedTeams) {
                    Team t = (Team) st;
                    int rank = getTeamRank(t.getId());
                    System.out.println(rank+". "+t);
                }
                break;
        }
    }


    public void displaySchedule(){
        System.out.println("************ SHEDULE *****************");
        for (Match m:matches) {
            System.out.println(m);
        }
    }
    /**
     * @return the match with the earliest (but in the future) scheduled time slot
     * uses Collection.sort
     */
    protected Match getNextMatch(){

        Collections.sort(sortedMatches);

        Match nextMatch = null;
        for (Object o: sortedMatches) {
            nextMatch = (Match) o;
            if(nextMatch.getTime().isAfter(LocalTime.now())){
                break;
            }
        }
        return nextMatch;
    }
    private int getTeamRank(int teamid){
        Team team = getTeamById(teamid);
        return sortedTeams.indexOf(team)+1;
    }
    /**
     *  METHODS FOR RETRIEVING EXISTING MATCH AND TEAM OBJECTS THAT WE WANT TO OPERATE ON
     * */
   public Match getMatchById(int id){

        for (Match m : matches) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
    public Team getTeamById(int id){
        for (Team t : teams) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    public ArrayList<Team> getTeams() {
        return teams;
    }

    public HashMap<String, ArrayList<String>> getTeamsdata() {
        return teamsdata;
    }

    public void updateTeamsdata(String userinput, ArrayList<String> playerlist){
        teamsdata.put(userinput,playerlist);

    }
    public String toSQLString(int teamid) {
        String res = "INSERT INTO tournamentTeams (tournamentID,teamID) VALUES ("+this.id+","+teamid+");";
        return res;
    }




}

