package model;


import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import util.TeamDataLoader;

public class KnockOutTournament extends Tournament {

    public KnockOutTournament(String title) {
        super(title);

    }
    public void showDescription(){
        System.out.println("I am a knockOut Tournament");

    }
    public void runTest(){
        /** USE CASE 1: Register teams that will play the tournament
         *  Providing team names and player details teams are enrolled in the tournament
         *  and will be added to the match schedule at a later point.
         * UX requirement on request
         * */

         registerTeams("data/teams.csv");



        /** USE CASE 2: See which teams  are currently registered to the tournament
         *  It should be possible to sort the teams in various ways, (natural, points, alphabetical etc.)
         * */
        displayTeams();
        /**  USE CASE 3 : Schedule the matches of the tournament
         *   This is a knock-out type tournament. With 8 registered teams,
         *   we need 7 matches: 4 initial round matches(quarterfinals), 2 semifinals and 1 final.
         *   we want the tournament to be played in no more than 3 hours and 30 minutes.
         *   (If a winner is not found within 30 minutes of a match, it's result will be a 5-5 DRAW)
         *   UX requirement on request
         * */

        int match1 = scheduleMatch(LocalDateTime.of(2020,03,6,12,00));
        int match2 = scheduleMatch(LocalDateTime.of(2020,03,6,12,30));
        int match3 = scheduleMatch(LocalDateTime.of(2020,03,6,13,00));
        int match4 = scheduleMatch(LocalDateTime.of(2020,03,6,13,30));
        int semifinal1 = scheduleMatch(LocalDateTime.of(2020,03,6,14,00));
        int semifinal2 = scheduleMatch(LocalDateTime.of(2020,03,6,14,30));
        int finalmatch = scheduleMatch(LocalDateTime.of(2020,03,6,15,00));

        /** USE CASE 4 : Show all scheduled matches
         *  We want to be able to display information about scheduled matches,
         *      both before and after teams and results have been assigned.
         * */
        displayMatches();  //Before teams and results

        /*
         * USE CASE 5 : Add teams to scheduled matches
         * matches are normally sheduled before we know which teams will be playing them,
         * so we need some way of adding teams to sheduled matches seperately.
         */

        /* updateMatch(match1, teams.get[0],  teams.get[1]);*/
        updateMatch(match1, teams.get(0), teams.get(1));
        updateMatch(match2,  teams.get(2), teams.get(3));
        updateMatch(match3,  teams.get(4), teams.get(5));
        updateMatch(match4,  teams.get(6), teams.get(7));

        displayMatches();//After teams and before results

/** USE CASE 6 : Register the result of a match
 * When a match has played, it is the responsibility of the winner team captain to register the match
 * With a matchID to look up the match, he will be able to input a score for each team of the match.
 *
 * */

        /** Det tal man skriver først i registreringen vil tilhøre kampens hold1.
         Dette må man sikre aldrig kan gå galt ved at man i UIen spørger hvad hold1's målscore blev, derefter hold2's*/

        Team winner1 = registerResult(match1,2,10);
        Team winner2 = registerResult(match2,6,10);
        Team winner3 = registerResult(match3,10,9);
        Team winner4 = registerResult(match4,10,1);

        displayMatches();//After teams and after some results

        updateMatch(semifinal1, winner1, winner2);
        updateMatch(semifinal2, winner3, winner4);

        Team finalist1 = registerResult(semifinal1,8,10);
        Team finalist2 = registerResult(semifinal2,10,1);

        updateMatch(finalmatch, finalist2, finalist1);
        registerResult(finalmatch,10,5);

        displayMatches();//After teams and after all results


    }

    private void registerTeams(String filename) {
        try {

            TeamDataLoader.importTeams(filename, this);

        }catch( FileNotFoundException e) {

            System.out.println(e);
        }
    }
}
