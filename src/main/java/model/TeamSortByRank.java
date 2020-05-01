package model;

import java.util.Comparator;

public class TeamSortByRank implements Comparator<Team> {
    @Override
    public int compare(Team t, Team t1) {
        int difference = t1.sumPoints()- t.sumPoints();

        return difference;
    }
}
