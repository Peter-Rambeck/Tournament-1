# Tournament
bordfodboldturnering is an application that manages teams, matches and results in a tablefootball tournament.
These are the use cases the application supports:


USE CASE 1: Register teams that will play the tournament
Providing team names and player details, teams are enrolled in the tournament and will be added to the match schedule at a later point.
UX requirements on request
 

USE CASE 2: See which teams are currently registered to the tournament. We want to see teamnames, and member names.  Later the user should be able to sort the teams in various ways, (natural, rank, alphabetical etc.)
  

USE CASE 3: Schedule the matches of the tournament
This is a knock-out type tournament. With 8 registered teams, we need 7 matches: 4 initial round matches(quarterfinals), 2 semifinals and 1 final. With 8 teams, we want the tournament to be played in no more than 3 hours and 30 minutes. Each match will be scheduled to last max. 30 minutes. If a winner is not found within 30 minutes of a match – GOLDEN GOAL.
UX requirements on request
  

USE CASE 4: Show all scheduled matches
We want to be able to display information about scheduled matches, both before and after teams and results have been assigned. Who is playing against who? At what time? It the match has been played, we want to see the score(goals on each side), and outcome(who won).
 

 USE CASE 5: Add teams to scheduled matches
 matches are normally scheduled before we know which teams will be playing them, so we need some way of adding teams to scheduled matches separately.
 

USE CASE 6 : Register the result of a match
When a match has played, it is the responsibility of the winner team captain to register the match. With a matchID to look up the match, he/she will be able to input a score for each team of the match.
 
  


 USE CASE 7: what is the next match?
 Mid tournament, we want to be able to see when the next match is played and who will play it. 

  


 USE CASE 8:  Final results and scoreboard
   Post tournament, we want to be able to see
    - who won the tournament
    - who are the runners up where
    - each team's final rank, position and score
 
We will reuse what we built for usecase2

