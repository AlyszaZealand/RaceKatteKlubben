
create table medlem(
                       medlemID INT AUTO_INCREMENT PRIMARY KEY,
                       username varchar(255),
                       password varchar(256),
                       email varchar(255),
                       memberdescription varchar(255)
);

create table kat(
                    katID INT AUTO_INCREMENT PRIMARY KEY,
                    catrace varChar(255),
                    catname varchar(255),
                    catbirthday datetime,
                    catgender varchar(255),
                    catdescription varchar(255),
                    medlemID int,
                    foreign key (medlemID) references medlem(medlemid) on DELETE cascade
);