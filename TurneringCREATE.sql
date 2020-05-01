use Turnering;
SET FOREIGN_KEY_CHECKS=0; 
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS teamMatches;
DROP TABLE IF EXISTS tournaments;
DROP TABLE IF EXISTS tournamentTeams;
DROP TABLE IF EXISTS matcheswithteams;
SET FOREIGN_KEY_CHECKS=1;


create table teams (
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(40), 
PRIMARY KEY (id)
)ENGINE = InnoDB;


create table players (
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(40), 
teamID int,
PRIMARY KEY (id),
FOREIGN KEY (teamID) REFERENCES teams(id)
)ENGINE = InnoDB;

create table tournaments (
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(40), 
startDate DATE,
winnerTeamID int,
PRIMARY KEY (id)
)ENGINE = InnoDB;

create table matches (
id int NOT NULL AUTO_INCREMENT,
matchType ENUM ('FINAL','SEMIFINAL','QUARTERFINAL'),
tournamentID int,
date DATE, 
time TIME,
PRIMARY KEY (id),
FOREIGN KEY (tournamentID) REFERENCES tournaments(id)
)ENGINE = InnoDB;


create table matcheswithteams (
id int NOT NULL AUTO_INCREMENT,
matchType ENUM ('FINAL','SEMIFINAL','QUARTERFINAL'),
tournamentID int,
date DATE, 
time TIME,
team1ID int,
team2ID int,
PRIMARY KEY (id),
FOREIGN KEY (tournamentID) REFERENCES tournaments(id),
FOREIGN KEY (team1ID) REFERENCES teams(id),
FOREIGN KEY (team2ID ) REFERENCES teams(id)
)ENGINE = InnoDB;



create table teamMatches(
matchID int NOT NULL,
teamID int NOT NULL,
score int,
PRIMARY KEY (matchID, teamID),
FOREIGN KEY (matchID) REFERENCES matches(id),
FOREIGN KEY (teamID) REFERENCES teams(id)
)ENGINE = InnoDB;


create table tournamentTeams (
tournamentID int NOT NULL,
teamID int NOT NULL, 
position ENUM ('Winner', 'Finalist', 'Semifinalist', 'none') DEFAULT 'none', 
PRIMARY KEY (tournamentID,teamID),
FOREIGN KEY (tournamentID) REFERENCES tournaments(id),
FOREIGN KEY (teamID) REFERENCES teams(id)
)ENGINE = InnoDB;

INSERT into teams (name) values ('De Uovervindelige');
INSERT into teams (name) values ('Golden Eagles');
INSERT into teams (name) values ('Liverpool');
INSERT into teams (name) values ('CPH Allstars');

INSERT into players (name, teamID) VALUES ('Emil',1);
INSERT into players (name, teamID) VALUES ('Gustav', 1);
INSERT into players (name, teamID) VALUES ('Jacob',1);
INSERT into players (name, teamID) VALUES ('Mathias',2);
INSERT into players (name, teamID) VALUES ('Mathias',2);
INSERT into players (name, teamID) VALUES ('Tobias',3);
INSERT into players (name, teamID) VALUES ('Emil',3);
INSERT into players (name, teamID) VALUES ('Mohammed',4);
INSERT into players (name, teamID) VALUES ('Mathias',4);

INSERT into matches ( matchtype, date, time) VALUES ('Semifinal','2020-05-01','8:00:00');
INSERT into matches ( matchtype, date, time) VALUES ('Semifinal','2020-05-01','10:30:00');
INSERT into matches ( matchtype, date, time) VALUES ('final','2020-05-01','13:00:00');


INSERT into matcheswithteams ( matchtype, date, time,team1ID, team2ID ) VALUES ('Semifinal','2020-09-03','12:00:00',1,2);
INSERT into matcheswithteams ( matchtype, date, time,team1ID, team2ID) VALUES ('Semifinal','2020-09-03','12:30:00', 3,4);
INSERT into matcheswithteams ( matchtype, date, time,team1ID, team2ID) VALUES ('final','2020-09-04','12:00:00', 1,4);



INSERT into tournaments ( name, startDate) VALUES ('JuleCup','2019-12-20');
INSERT into tournaments ( name, startDate) VALUES ('PåskeCup','2020-05-01');
INSERT into tournaments ( name, startDate) VALUES ('SemesterStartsCup','2020-09-01');

INSERT into teamMatches ( matchID, teamID, score) VALUES (1,1,10);
INSERT into teamMatches ( matchID, teamID, score) VALUES (1,2,9);
INSERT into teamMatches ( matchID, teamID, score) VALUES (2,3,0);
INSERT into teamMatches ( matchID, teamID, score) VALUES (2,4,10);
INSERT into teamMatches ( matchID, teamID, score) VALUES (3,1,10);
INSERT into teamMatches ( matchID, teamID, score) VALUES (3,4,9);

INSERT into tournamentTeams (tournamentID, teamID) VALUES (1,1);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (1,2);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (1,3);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (2,1);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (2,2);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (2,3);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (2,4);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (3,1);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (3,3);
INSERT into tournamentTeams (tournamentID, teamID) VALUES (3,4);

/*Ændre dato for  påskeCUP*/
UPDATE tournaments SET startDate = '2020-09-21'  WHERE id = 2;

/*registrer holdenes positioner i påskeCUP*/
UPDATE tournamentTeams SET position= "winner" WHERE teamID = 1 AND tournamentID = 2;
UPDATE tournamentTeams SET position= "semifinalist" WHERE teamID = 2 AND tournamentID = 2;
UPDATE tournamentTeams SET position= "semifinalist" WHERE teamID = 3 AND tournamentID = 2;
UPDATE tournamentTeams SET position= "Finalist" WHERE teamID = 4 AND tournamentID = 2;