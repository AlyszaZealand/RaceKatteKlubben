-- Password 123456 hashed with BCrypt
INSERT INTO medlem (username, password, email, memberdescription) VALUES
                                                                      ('abc', '$2a$10$3dZegT8u3HVQgSQDcb9fXeBAUXLqvsNhTtWBa4f51u7gQnmN9vaX6', 'abc@abc.dk', 'Stor katte entusiast fra Odense'),
                                                                      ('Bob', '$2a$10$3dZegT8u3HVQgSQDcb9fXeBAUXLqvsNhTtWBa4f51u7gQnmN9vaX6', 'bob@bob.dk', 'Racerkat opdrætter fra København');

-- Cats for Anders (memberID = 1)
INSERT INTO kat (catrace, catname, catbirthday, catgender, catdescription, memberID) VALUES
                                                                                         ('Maine Coon',   'Simba',  '2021-03-15', 'Han', 'Stor og flot Maine Coon med brun pels',      1),
                                                                                         ('Siamese',      'Luna',   '2022-07-22', 'Hun', 'Elegant Siamese med blå øjne',               1),
                                                                                         ('Bengal',       'Tiger',  '2020-11-05', 'Han', 'Vild Bengal kat med flot plettet mønster',   1);

-- Cats for Bob (memberID = 2)
INSERT INTO kat (catrace, catname, catbirthday, catgender, catdescription, memberID) VALUES
                                                                                         ('Persian',      'Snehvid', '2021-06-10', 'Hun', 'Blød og rolig Persisk kat med hvid pels',  2),
                                                                                         ('Scottish Fold','Øre',     '2023-01-18', 'Han', 'Sød Scottish Fold med foldede ører',       2),
                                                                                         ('Ragdoll',      'Bamse',   '2022-09-30', 'Hun', 'Blød Ragdoll der elsker at blive båret',   2);

INSERT INTO event (eventname, eventdate, eventdescription) VALUES
                                                               ('Fynsk Forårsudstilling', '2027-05-12 10:00:00', 'Årets hyggeligste udstilling for alle racekatte. Kom og mød andre entusiaster!'),
                                                               ('København Grand Prix', '2027-06-22 09:00:00', 'Kæmpe international udstilling i hjertet af København. Der er flotte rosetter til vinderne.'),
                                                               ('Jysk Katteshow 2027', '2027-08-15 11:30:00', 'Et fantastisk show i Aarhus med stort fokus på pelspleje og agility for katte.');

INSERT INTO kat_event (catID, eventID) VALUES
                                           (1, 1), -- Simba deltager i Fynsk Forårsudstilling (Event 1)
                                           (2, 1), -- Luna deltager også i Fynsk Forårsudstilling (Event 1)
                                           (4, 2), -- Snehvid deltager i København Grand Prix (Event 2)
                                           (3, 2), -- Tiger deltager i København Grand Prix (Event 2)
                                           (6, 3), -- Bamse deltager i Jysk Katteshow (Event 3)
                                           (5, 3); -- Øre deltager i Jysk Katteshow (Event 3)