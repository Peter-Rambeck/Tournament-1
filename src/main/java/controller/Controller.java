package controller;

import model.Tournament;

import java.util.ArrayList;

public class Controller {

    public static Tournament tournament;
    public static DBConnector db_connecter = new DBConnector();
    private static Boolean connectedToDB;
    public Controller(Tournament tournament){

        this.tournament = tournament;
        getData("teams");
        getData("matches");
    }

    public static void getData(String entity){
        connectedToDB= db_connecter.connect();
        switch (entity){
            case "teams": //getTeamsFromDB();
                            break;
            case "matches": getMatchesFromDB();
                            break;

        }
    }

    private static void getMatchesFromDB() {
        if(connectedToDB){
           String sql= "select * from matcheswithteams";
           ArrayList<String[]> data = db_connecter.runQuery(sql);
           tournament.generateMatchesFromDB(data);
           tournament.displayMatches();

        }
    }
}
