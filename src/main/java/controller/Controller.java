package controller;

import java.util.*;

import model.Match;
import model.Player;
import model.Team;
import model.Tournament;
import util.FileLoader;
import view.UI;

/**
 *
 * @author tess
 */
public class Controller {

    public static Tournament tournament;
    public static DBConnector db_connector = new DBConnector();

    private static UI ui;
    private static Boolean connectedToDB;

    //OBS! team objects are used in the generation of matches, so get teams first!
    public Controller(UI ui, Tournament tournament) {
        this.ui = ui;
        this.tournament = tournament;
        getData("teams");
        getData("matches");
    }

    public Controller(Tournament tournament) {
        this.tournament = tournament;
    }

    public static void getData(String entity){
        connectedToDB = db_connector.connect();
        switch (entity) {
            case "teams":getTeamsData();
                break;
            case "matches":getMatchesData();
                break;
        }

    }

    public static void showMainMenu() {
        //Vent på brugerens valg af spillere eller at brugeren afslutter
        int choice = 0;
        while (choice != 8) {
            ui.println("---------------------- Bordfodboldturnering ----------------------");
            ui.println("1) Se hold");
            ui.println("2) Registrer et Hold");
            ui.println("3) Se kampe");
            ui.println("4) Skemalæg en kamp");
            ui.println("5) Registrer et resultat");
            ui.println("6) sorter hold efter rang");
            ui.println("7) Vis næste kamp");
            ui.println("8) Exit");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 8) {
                    throw new NumberFormatException();
                }
                switch (choice) {
                    case 1:
                        showTournamentTeams("natural");
                        break;
                    case 2:
                        startRegisterTeamFlow();
                        break;
                    case 3:
                        showTournamentMatches();
                        break;
                    case 4:
                        startSheduleMatchFlow();
                        break;
                    case 5:
                        startRegisterResultFlow();
                        break;
                    case 6:  showTournamentTeams("ranked");
                        break;
                    case 7:    showNextMatch();
                        break;


                }
            }catch(NumberFormatException e){
                ui.println("Vælg mellem menupunkt 1 - 5");
            }
        }

    }

    private static void showNextMatch() {
      /*  Match m = tournament.getNextMatch();
        ui.println( "NEXT MATCH");
        ui.println(m.toString());*
    }
    private static void showLastMatch() {
     /*   Match m = tournament.getLastMatch();
        ui.println( "LastMATCH");
        ui.println(m.toString());*/
    }
    private static void startRegisterResultFlow() {
        //todo:create UIflow and make sure the state is saved to the database, teamMatches (teamID, matchID, score)
        ui.println("Skriv kampens id ");
        int ui_matchid=Integer.parseInt(ui.getInput());
        String [] matchteams=tournament.getMatchById(ui_matchid).getTeamNames();
        ui.println("Skriv målscore for "+ matchteams[0]);
        int ui_team1score=Integer.parseInt(ui.getInput());
        ui.println("Skriv målscore for "+matchteams[1]);
        int ui_team2score=Integer.parseInt(ui.getInput());
        Team winner = tournament.registerResult( ui_matchid,ui_team1score,ui_team2score);
        ui.println("The winner is "+winner.getName());
        // skal måske ligge i registerResult
        saveResultToDB(ui_matchid);

        showMainMenu();
    }

    private static void startSheduleMatchFlow() {
        //todo:finish this user flow - advanced because of teams
        ui.println("Skriv dato kampen skal spilles yyyy-mm-dd");
        String ui_date = ui.getInput();
        ui.println("Skriv klokkeslæt  HH:mm:ss");
        String ui_time = ui.getInput();
        ui.println("Hvilken slags kamp er det?"+ Match.MatchType.values());
        String ui_matchType = ui.getInput();
        int id = tournament.scheduleMatch(ui_date, ui_time,ui_matchType );
        ui.println("Skriv ID på hold1 eller q ");
        int ui_team1ID=Integer.parseInt(ui.getInput());
        ui.println("Skriv ID på hold2");
        int ui_team2ID=Integer.parseInt(ui.getInput());
        Match m = tournament.getMatchById(id);
        m.setTeams(tournament.getTeamById(ui_team1ID), tournament.getTeamById(ui_team2ID));
        saveMatchToDB(m);
        showMainMenu();
    }

    private static void startRegisterTeamFlow() {
        String userinput = ui.registerTeam();
        ArrayList<String> playerlist = ui.addPlayers(); // starter sub-flow
        Team team = tournament.registerTeam(userinput, playerlist);
        saveTeamToDB(team);

        //Denne linie sikrer at hold der bliver tilføjet manuelt gennem programmet, bliver lagt i ind dataHashMappet så det kan blive gemt i databasen når programmet slutter (se saveTeamsToDB)
        tournament.updateTeamsdata(userinput, playerlist);
        showMainMenu();
    }


    private static void showTournamentTeams(String method){
        /** USE CASE 2: See which teams are currently registered to the tournament
         *  It should be possible to sort the teams in various ways, (natural, points, alphabetical etc.)
         *  todo offer some sorting filters
         * */

        tournament.displayTeams(method);
    }

    /** USE CASE 2: See which teams are currently registered to the tournament
     *  It should be possible to sort the teams in various ways, (natural, points, alphabetical etc.)
     *  todo offer some sorting filters
     * */

    private static void showTournamentMatches(){

        tournament.displaySchedule();
    }



    private static void getMatchesData(){
        if(connectedToDB) {
            /* Henter teamdata fra databasen, sender den til tournament*/
            //  String sql = "SELECT * FROM matches";
            String sql = "SELECT matches.id AS 'matchID',"+
                    " teams.id AS 'teamID'," +
                    " teamMatches.score" +
                    " FROM teams" +
                    " INNER JOIN teamMatches" +
                    " ON" +
                    " teamMatches.teamID = teams.id" +
                    " INNER JOIN matches" +
                    " ON" +
                    " matches.id = teamMatches.matchID;";

            LinkedHashMap<Integer, int[][]>results = db_connector.runMatchQuery(sql,"matchID", "teamID", "score");
            ArrayList<String[]>  matchDetails = db_connector.runQuery("SELECT matchType, tournamentID,  date, time  FROM matches");

            if (matchDetails != null) {
                tournament.generateMatchesFromDB(matchDetails, results);
            }

        }else{
            System.out.println();
            ui.println(" No connection to the database. Matches not available");

        }
    }
    /*
     * Henter teams fra databasen, hvis der ikke er forbindelse får brugeren valget mellem at hente fra fil, eller indtaste nogle hold
     * */
    private static void getTeamsData() {
        if(connectedToDB) {
            /* Henter teamdata fra databasen, sender den til tournament*/
            String sql= "SELECT teams.id AS id, teams.name AS team, players.name AS player FROM players, teams WHERE players.teamId = teams.id;";

            LinkedHashMap <String, ArrayList<String>> data = db_connector.runTeamsQuery(sql,"team", "player");
            //teamname, playerarraylist
            if (data != null) {
                //rå data laves om til objekter
                tournament.generateTeamsFromDB((LinkedHashMap)data);
            }
        }else{

            System.out.println();
            ui.println(" No connection to the database");
            Scanner data = FileLoader.loadTeams("teams.csv");
            tournament.generateTeamsFromFile(data);

        }
    }

    public static void saveData(){
        if(db_connector.mysql!=null) {
            saveTeamsToDB();
        }else {
            FileLoader.saveTeamsToFile(tournament.toString());
        }
    }

    private static void saveMatchToDB(Match match){
        String sql = "";
        sql = match.teamMatchSQLString(0);
        db_connector.runInsertQuery(sql);
        sql = match.teamMatchSQLString(1);
        db_connector.runInsertQuery(sql);
        Team team1 = tournament.getTeamById(match.getTeamIds()[1]);
        Team team2 = tournament.getTeamById(match.getTeamIds()[2]);
        sql = team1.toSQLString(match.getId());//en metode der returnerer en insert i teamMatches (teamId, matchId)
        db_connector.runInsertQuery(sql);
        sql = team2.toSQLString(match.getId());//en metode der returnerer en insert i teamMatches (teamId, matchId)
        db_connector.runInsertQuery(sql);
    }

    public static void saveTeamToDB(Team team){
        String sql = "";
        sql = team.toSQLString();
        db_connector.runInsertQuery(sql);
        for (Player p:team.getPlayers()) {
            sql = p.toSQLString(team.getId());
            db_connector.runInsertQuery(sql);
        }

    }

    private static void saveResultToDB(int matchid){
        String sql = "";
        sql = tournament.getMatchById(matchid).teamMatchSQLString(0);
        db_connector.runInsertQuery(sql);
        sql = tournament.getMatchById(matchid).teamMatchSQLString(1);
        db_connector.runInsertQuery(sql);
    }

    /*
     * For saving all at once.
     * */
    private static void saveTeamsToDB(){
        String sql = "";

        for (Team t : tournament.getTeams()) {

            sql = t.toSQLString();
            saveTeamToDB(t);
        }
    }

}
