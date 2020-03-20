package model;

public class GroupTournament extends Tournament {
    private int maxGroups;
    private int maxGroupSize;

    public GroupTournament(String s, int maxGroups, int maxGroupSize) {
        super(s);
        this.maxGroupSize = maxGroupSize;
        this.maxGroups = maxGroups;
    }

    public void runTest(){
        System.out.println("registrere teams ");
        System.out.println("set tournament period ");
        System.out.println("create groups ");
    }

}
