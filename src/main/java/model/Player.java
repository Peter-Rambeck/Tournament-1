package model;

public class Player {
    private String name;
    public Player(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return name ;
    }
    public String toSQLString(int teamID) {
        //HUSK single quotes rundt om string literals!!
        String res = "INSERT INTO players (name, teamID) VALUES ('"+name+"',"+teamID+");";
        return res;
    }
}
