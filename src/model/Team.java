package model;

import java.util.ArrayList;

public class Team {

        private String name;
        private int id;
        private static int nextId= 1;
        private int [] points = new int[10];
        private int matchCount = 0;
        private int score;


        public enum Position{
          Winner, Finalist, Semifinalist, none
        }
        private Position position = Position.none;

        private ArrayList<Player> players = new ArrayList<Player>();

        String attacker;
        String defender;


        public Team(String name, String [] memberNames){
            this.name = name;
            attacker = memberNames[0];
            defender = memberNames[1];

            this.id = nextId;
            nextId++;
            for (String s:memberNames){
              this.addPlayer(s);
             }
        }

        protected void addPlayer(String name) {
            Player p = new Player(name);
            players.add(p);
        }


    @Override
    public String toString(){
            String str = this.name+ ", "+ this.players;
            return str;
    }


        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static int getNextId() {
            return nextId;
        }

        public int[] getPoints() {
            return points;
        }

        public int getScore() {
            return score;
        }

        public Position getPosition() {
            return position;
        }

    public void addPoints(int i) {
        this.points[matchCount]=i;
        matchCount++;
    }
    public void addScore(int score){

            this.score += score;
    }
}
