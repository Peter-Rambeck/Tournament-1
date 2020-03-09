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
*/

import model.Tournament;


public class Main {


    public static void main(String[] args) {
	    Tournament tournament = new Tournament("CPH PÅSKE CUP 2020");
        tournament.runTest();
    }





}
