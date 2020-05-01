/*

 use case 1 - registerTeams()          instantiation, constructors  (later enum, ArrayList)
 use case 2 - displayTeams()           toString method called manually on each team (later ArrayList and foreach)
 use case 3 - scheduleMatch()          instantiation, constructors, instance method invokation (later iterations: enum, ArrayList)
 use case 4 - displaySchedule()        toString method called manually on each team (later ArrayList and foreach)
 use case 5 - setTeams()               instance method invokation, getters and setters
 use case 6 - registerResult()         instance method invokation, setter, datastructure for score, (later enum, searching ArrayList, foreach with condition)
              findTeamById()
 use case 7 - displayNextMatch()        sorting, Comparator interface
            - Match implements Comarable  compare()
 use case 8 - reuse displayTeams (sort by score)        sorting, Comparator interface, compareTo()
            - TeamsSortByRank with score
            - getRank()


TODO
 -  Automatisering af timeslots
    Med start tidspunkt for turnering, samt antal hold,
    skal en metode kunne generere de nødvendige time objekter, der skal bruges i sheduleMatch metoden
*/


import controller.Controller;
import model.*;
import view.UI;


public class Main {


    public static void main(String[] args) {
        /**
         * USE CASE 9
         * The application should be able to handle more than one tournament at a given time.
         * There may fx. be one for each department or each year(årgang)
         * */

        Tournament knockOutTournament = new KnockOutTournament("CPH PÅSKE CUP 2020 i bordfodbold");
       // knockOutTournament.runTest();

      //I stedet for at det hele kører som en lang test, laver vi nu en controller med et UI som vi kan teste tingene i...
       Controller ctrl = new Controller(new UI(),knockOutTournament);
        ctrl.showMainMenu();
    }

}
