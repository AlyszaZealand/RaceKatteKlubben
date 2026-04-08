
DROP TABLE IF EXISTS cat;
DROP TABLE IF EXISTS member;


CREATE TABLE member (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(100) NOT NULL,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        member_description TEXT
);
CREATE TABLE cat (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(100) NOT NULL,
                     breed VARCHAR(100),
                     birth_date DATE,
                     gender VARCHAR(50),
                     registration_number VARCHAR(100),

    -- Dette er linjen der forbinder katten til dens ejer
                     member_id INT NOT NULL,

    -- Foreign Key Constraint (Sikrer at man ikke kan tilføje en kat til et id, der ikke findes)
    -- ON DELETE CASCADE betyder, at hvis en ejer slettes, slettes deres katte også automatisk
                     FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);