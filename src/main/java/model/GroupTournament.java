package model;

import java.time.LocalDateTime;



public class GroupTournament extends Tournament {
    private int maxGroupSize;
    private Group[] groups;
    private int currentGroupId;

    public GroupTournament(String title, int maxGroups, int maxGroupSize) {
        super(title);
        this.maxGroupSize = maxGroupSize;
        groups = new Group[maxGroups];
        for(int i = 0; i < maxGroups; i++){
            groups[i]=new Group();
        }

    }

    @Override
    public void runTest() {
        super.runTest();
        System.out.println("Creating groups");
        System.out.println("Adding teams to groups");// either based on their rank or at random

        currentGroupId = 0;
        LocalDateTime startTime = LocalDateTime.of(2020,03,8,10,00);
        for(int i = 0; i < maxGroupSize; i++){
            LocalDateTime time = startTime.plusMinutes(i*30);
            scheduleMatch("2020-03-02", "12:00:00","GROUPPLAY");

        }
        currentGroupId = 1;
        for(int i = 0; i < maxGroupSize; i++){
            LocalDateTime time = startTime.plusMinutes(i*30);
            int matchid = scheduleMatch("2020-03-02", "12:00:00", "GROUPPLAY");
            groups[currentGroupId].addMatch(matchid);
        }

        System.out.println("scheduling group play matches");
        System.out.println("scheduling finals...");
    }

    @Override
    public int scheduleMatch(String d, String t, String matchType) {
        return 0;
    }



    public void shuffleGroups(){
        System.out.println("tournament groups shuffled");
    }


}
