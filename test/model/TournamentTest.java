package model;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Team team1;
    Team team2;
    Tournament kot;
    int matchID;

    @Before
    public void setUp(){
        kot = new KnockOutTournament("CPH PÅSKE CUP 2020 i bordfodbold");
    }


    @Test
    public void test_registerTeam(){
        team1 = kot.registerTeam("Kongelunden",new String[]{"Tess","Leif"});
        team2 = kot.registerTeam("Islands Brygge",new String[]{"Irma","Storm"});
        assertEquals(team2, kot.teams.get(1));
    }

    @Test
    public void test_scheduleMatch(){
        matchID = kot.scheduleMatch(LocalDateTime.of(2020,03,8,10,00), Match.MatchType.QuarterFinal);
        assertEquals(matchID,1);
    }


    @Test
    public void test_registerResult(){
        team1 = kot.registerTeam("Kongelunden",new String[]{"Tess","Leif"});
        team2 = kot.registerTeam("Islands Brygge",new String[]{"Irma","Storm"});
        matchID = kot.scheduleMatch(LocalDateTime.of(2020,03,8,10,00), Match.MatchType.QuarterFinal);
        kot.updateMatch(1, team1, team2);
        Team winner = kot.registerResult(1,10,2);
        assertEquals(winner,team1);
    }
}
