package model;
import controller.Controller;
import org.junit.Before;
import org.junit.Test;
import view.UI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Team team1;
    Team team2;
    Tournament kot;
    int matchID;
    Controller ctrl;

    @Before
    public void setUp(){
        kot = new KnockOutTournament("CPH PÅSKE CUP 2020 i bordfodbold");
        ctrl = new Controller(kot);
        ctrl.getData("teams");
        ctrl.getData("matches");
    }



    @Test
    public void test_registerTeam(){
       Team actual_team = kot.registerTeam("B2020",new String[]{"gustav","jack"});
        //tjek at dette hold nu er havnet i turneringes liste af hold.
        Team expected_team = kot.getTeamById(actual_team.getId());
        assertEquals(actual_team,expected_team);
    }

    @Test
    public void test_scheduleMatch(){
        matchID = kot.scheduleMatch("2020-03-02", "12:00:00","QUARTERFINAL");
        Match actual = kot.getMatchById(matchID);
        Match expected = kot.matches.get(kot.matches.size()-1);

        //tjek at senest oprettede kamp i turneringen er denne kamp
        assertEquals(expected,actual);
    }

    /**
     * Tjekker at i kamp nr 3 som er finalen, vinder kampens hold nummer 1.
     */
    @Test
    public void test_registerResult(){
        Team expected =kot.getTeamById(1);
        kot.updateMatch(1, kot.getTeamById(1), kot.getTeamById(2));
        kot.updateMatch(2, kot.getTeamById(3), kot.getTeamById(4));
        kot.updateMatch(3, kot.getTeamById(1), kot.getTeamById(4));
        Team semiFinalist = kot.registerResult(1,10,8);
        Team finalist = kot.registerResult(2,9,10);
        Team winner = kot.registerResult(3,10,5);

        Team actual = winner;
        assertEquals(expected,actual);
    }
    @Test
    public void test_saveTeamToDB(){
        Team actual_team = kot.registerTeam("B2020",new String[]{"gustav","jack"});
        ctrl.saveTeamToDB(actual_team);
        String[] actualArr = Controller.db_connector.runQuery("SELECT * FROM teams ORDER BY id DESC LIMIT 1;").get(0);
        String actual= actualArr[1];
        assertEquals( "B2020", actual);
    }
    /**
     *
     * Tjekker ved stikprøve at Tournament instansens datastruktur for 'teams'. afspejler databasens
     *
     *         1)'De Uovervindelige' [emil, gustav, jacob]
     *         2)'Golden Eagles' [mathias, mathias]
     *         3)'Liverpool'...
     *         4)'CPH Allstars'...
     * */

    @Test
    public void test_generateTeamsFromDB(){
        String expected ="Emil";
        Team team = (Team) kot.getTeamById(1);//'De Uovervindelige'
        ArrayList<Player> players = team.getPlayers();//[emil, gustav, jacob]
        Player  p = (Player) players.get(0);//Emil
        String actual = p.toString();
        assertEquals(expected,actual);
        kot.displayTeams("natural");
    }

    @Test
    public void test_getMatchesFromDB(){
        Match.MatchType expected = Match.MatchType.FINAL;
        Match match = (Match) kot.getMatchById(3);//Finalen var den sidste kamp ud af 3, der blev insertet i databasen
        Match.MatchType actual = match.getMatchType();
        assertEquals(expected,actual);
    }
}
