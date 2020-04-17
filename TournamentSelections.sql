/*  Træk data ud af en sammenføjningstabel  variation 1 
SELECT
matches.id, matches.matchType, teams.name AS 'winner'
FROM matches, teams, teamMatches
WHERE teams.id = teamMatches.teamID
AND teamMatches.score = 10
AND matches.id = teamMatches.matchID;

SELECT teams.name AS 'vs', matches.id AS 'id', 10-teamMatches.score as difference
FROM teams, teamMatches, matches
WHERE teams.id = teamMatches.teamID
 AND teamMatches.score < 10
 AND matches.id = teamMatches.matchID;*/

 
/* vis mig vinderhold for de tre kampe i turneringen */
SELECT t1.matchType, t1.winner, t2.vs, t2.difference FROM
(SELECT
matches.matchType AS 'matchType', matches.id AS 'id', teams.name AS 'winner'
FROM matches, teams, teamMatches
WHERE teams.id = teamMatches.teamID
AND teamMatches.score = 10
AND matches.id = teamMatches.matchID)t1
INNER JOIN
(SELECT teams.name AS 'vs', matches.id AS 'id', 10-teamMatches.score as difference
FROM teams, teamMatches, matches
WHERE teams.id = teamMatches.teamID
 AND teamMatches.score < 10
 AND matches.id = teamMatches.matchID)t2
ON t1.id = t2.id;




/* Giv mig navne på de spillere der skal have en præmie 
SELECT players.name AS 'player' 
FROM players
WHERE teamID IN
(SELECT
teams.id
FROM matches, teams, teamMatches
WHERE teams.id = teamMatches.teamID
AND teamMatches.score = 10
AND matches.matchType = 'Final'
AND matches.id = teamMatches.matchID);

*/
/*Træk data ud af en sammenføjningstabel  variation 2 
SELECT
matches.id AS 'matchID',
 teams.name AS 'teamName' 
FROM teams
INNER JOIN teamMatches
ON
teamMatches.teamID = teams.id AND teamMatches.score = 10
INNER JOIN matches
ON
matches.id = teamMatches.matchID;
*/

/* Find vinderen af påskecup - scenariet er at vi ved at vinderen er den eneste der har 4 point 
SELECT
 teams.name AS 'winner',
 tournaments.name AS 'tournamentName' 
FROM teams
INNER JOIN tournamentTeams
ON
tournamentTeams.teamID = teams.id 
AND tournamentTeams.points = 4
INNER JOIN tournaments
ON
tournaments.id = 2;*/
 