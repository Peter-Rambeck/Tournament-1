use Turnering;
SET FOREIGN_KEY_CHECKS=0; 
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS teamMatches;
DROP TABLE IF EXISTS tournaments;
DROP TABLE IF EXISTS tournamentTeams;
SET FOREIGN_KEY_CHECKS=1;

create table teams (
id int NOT NULL AUTO_INCREMENT,
name VARCHAR(40), 
points int,
position ENUM ('Winner', 'Finalist', 'Semifinalist', 'none'),
PRIMARY KEY (id)
)ENGINE = InnoDB;


INSERT into teams (name, position) values ('De Uovervindelige','none');

update teams SET points = 4 WHERE id=1;