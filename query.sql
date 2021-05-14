CREATE DATABASE jdbc_sql;

CREATE TABLE team (
    id INT PRIMARY KEY,
    alias TEXT NOT NULL UNIQUE
);

CREATE TABLE team_member (
    id INT PRIMARY KEY,
    name TEXT NOT NULL,
    team INT,
    CONSTRAINT team_member_team_id_fk FOREIGN KEY (team) REFERENCES team(id)
);

INSERT INTO team (id, alias) VALUES (1, 'team1');

INSERT INTO team_member (id, name, team) VALUES (1, 'John Doe', 1);
INSERT INTO team_member (id, name, team) VALUES (2, 'Jane Doe', 1);