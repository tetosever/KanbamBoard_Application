CREATE DATABASE IF NOT EXISTS ProgettoSD;

USE ProgettoSD;

CREATE TABLE IF NOT EXISTS columns(
	title VARCHAR(60) NOT NULL,
    state ENUM("in corso", "archiviato") NOT NULL,
    PRIMARY KEY (title)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tile(
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(60) NOT NULL,
    author VARCHAR(16) NOT NULL,
    messageType ENUM("organizzativo", "informativo") NOT NULL,
    content MEDIUMTEXT,
    columns VARCHAR(60) NOT NULL,
    CONSTRAINT belongs
		FOREIGN KEY (columns)
        REFERENCES ProgettoSD.columns(title)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

