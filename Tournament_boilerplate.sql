use Turnering;

/* slet eksisterende tabeller */
SET FOREIGN_KEY_CHECKS=0; 
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS teamMatches;
DROP TABLE IF EXISTS tournaments;
DROP TABLE IF EXISTS tournamentTeams;
SET FOREIGN_KEY_CHECKS=1;


/* Lav en tabel for hold */
create table teams (
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(40), 
PRIMARY KEY (id)
)ENGINE = InnoDB;


/* Lav en sammenføjningstabel for hold og kampe */
create table teamMatches(
matchID int NOT NULL,
teamID int NOT NULL,
PRIMARY KEY (matchID, teamID),
FOREIGN KEY (matchID) REFERENCES matches(id),
FOREIGN KEY (teamID) REFERENCES teams(id),
score int
)ENGINE = InnoDB;

/* sæt data ind i hold tabellen */
INSERT into teams (name, position) values ('De Uovervindelige','none');

/* opdater eksisterende post i hold tabellen*/
update teams SET points = 4 WHERE id=1;