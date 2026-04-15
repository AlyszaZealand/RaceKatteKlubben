DROP TABLE IF EXISTS kat_event;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS kat;
DROP TABLE IF EXISTS medlem;



create table medlem(
                       id INT AUTO_INCREMENT PRIMARY KEY,  -- Rettet fra medlemID til id, så RowMapper kan finde den
                       username varchar(255),
                       password varchar(256),
                       email varchar(255),
                       memberdescription varchar(255)
);

create table kat(
                    catID INT AUTO_INCREMENT PRIMARY KEY, -- Rettet fra katID til catID
                    catrace varChar(255),
                    catname varchar(255),
                    catbirthday datetime,
                    catgender varchar(255),
                    catdescription varchar(255),
                    imageName LONGTEXT,
                    memberID int, -- Rettet fra medlemID til memberID
                    foreign key (memberID) references medlem(id) on DELETE cascade
);

CREATE TABLE event(
                      eventID INT AUTO_INCREMENT PRIMARY KEY,
                      eventName VARCHAR(255),
                      eventDate DATETIME,
                      eventDescription VARCHAR(255)
);

-- Junction table — this is what creates the many-to-many
CREATE TABLE kat_event(
                          katEventID INT AUTO_INCREMENT PRIMARY KEY,
                          catID INT,
                          eventID INT,
                          FOREIGN KEY (catID) REFERENCES kat(catID) ON DELETE CASCADE,
                          FOREIGN KEY (eventID) REFERENCES event(eventID) ON DELETE CASCADE,
                          UNIQUE (catID, eventID)
);




