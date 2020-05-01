package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Team {
    private String name;
    private static int nextId = 1;
    private int id;
    private int[] points = new int[10];
    private int matchCount = 0; //used as nextindex in points
    private int score;

    public enum Status{
        SEMIFINALIST, FINALIST, WINNER, NONE
    }
    private Status status = Status.NONE;


    private ArrayList<Player> players = new ArrayList<Player>();

    /*  Team constructor. Used when teamplayer UI flow is CONCURRENT with team creation */
    public Team(String name) {
        this.name = name;
        getPlayersFromUser();
        id = nextId;
        nextId++;
    }

    /*  Team constructor overload. Used when teamplayer UI flow is BEFORE team creation */
    public Team(String teamName, String [] memberNames) {
        this.name = teamName;
        for (String s:memberNames){
            this.addPlayer(s);
        }
        id = nextId;
        nextId++;

    }
    /*
    * Team constructor overload. Converts the ArrayList<String> argument to a ArrayList<Player> instance property
    *
    * */
    public Team(String teamName, ArrayList<String> memberNames) {
        this.name = teamName;
        for (String s:memberNames){
            this.addPlayer(s);
        }
        id = nextId;
        nextId++;
    }
    public ArrayList<String> getPlayersAsStringArray(){
        ArrayList<String> playernames = new ArrayList<String>();
        for (Player p: players) {
            playernames.add(p.toString());
        }
        return playernames;
    }

    @Override
    public String toString() {
        return "Team# " +id+" - " + name  +", players:" + players+
                ", points:" + this.sumPoints()+
                ", score:"+this.score+
                ", position:" +status;
    }

    public String toSQLString() {
        //HUSK SINGLEQUOTES når du lægger string literals ind i en database
        String res = "INSERT INTO teams (name) VALUES ('"+this.name+"');";
        return res;
    }
    public String toSQLString(int matchid) {
        String res = "INSERT INTO teamMatches (matchID,teamID) VALUES ("+this.id+","+matchid+");";
        return res;
    }

    /**
     *
     * SETTERS
     *
     * */
    private void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void addPoint(int i) {
        points[matchCount] = i;
        matchCount++;
    }
    public void addScore(int i) {
        this.score+=i;
    }



    public void setStatus(Status status) {
        this.status = status;
    }
    /**
     *
     * GETTERS
     *
     * */
    private void getPlayersFromUser() {
        System.out.println("add playername (or press q to save and quit) ");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while(!input.equals("q")){
            this.addPlayer(input);
            input = scan.nextLine();
        }

    }
    public int getId() {
        return this.id;
    }

    public int sumPoints() {
        int sum = 0;
        for (int value : points) {
            sum += value;
        }
        return sum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getScore() {
        return this.score;
    }

}
