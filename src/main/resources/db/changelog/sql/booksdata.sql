INSERT INTO AUTHOR (AUTHORNAME) VALUES ('author1');
INSERT INTO AUTHOR (AUTHORNAME) VALUES ('author2');
INSERT INTO AUTHOR (AUTHORNAME) VALUES ('author3');


INSERT INTO GENRE (GENRETITLE) VALUES ('genre1');
INSERT INTO GENRE (GENRETITLE) VALUES ('genre2');
INSERT INTO GENRE (GENRETITLE) VALUES ('genre3');

INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES ('book1', (SELECT ID
                                                                 FROM AUTHOR
                                                                 WHERE AUTHOR.AUTHORNAME = 'author1'), (SELECT ID
                                                                                                        FROM GENRE
                                                                                                        WHERE
                                                                                                          GENRE.GENRETITLE
                                                                                                          = 'genre1'));
INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES ('book2', (SELECT ID
                                                                 FROM AUTHOR
                                                                 WHERE AUTHOR.AUTHORNAME = 'author2'), (SELECT ID
                                                                                                        FROM GENRE
                                                                                                        WHERE
                                                                                                          GENRE.GENRETITLE
                                                                                                          = 'genre1'));
INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES ('book3', (SELECT ID
                                                                 FROM AUTHOR
                                                                 WHERE AUTHOR.AUTHORNAME = 'author2'), (SELECT ID
                                                                                                        FROM GENRE
                                                                                                        WHERE
                                                                                                          GENRE.GENRETITLE
                                                                                                          = 'genre2'));
INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES ('book4', (SELECT ID
                                                                 FROM AUTHOR
                                                                 WHERE AUTHOR.AUTHORNAME = 'author3'), (SELECT ID
                                                                                                        FROM GENRE
                                                                                                        WHERE
                                                                                                          GENRE.GENRETITLE
                                                                                                          = 'genre3'));

INSERT INTO COMMENT (ENTITY_ID, COMMENTTEXT) VALUES ((SELECT ID
                                                      FROM BOOKS
                                                      WHERE BOOKS.TITLE = 'book2'), 'HAHA THIS BOOK IS LOL!');
INSERT INTO COMMENT (ENTITY_ID, COMMENTTEXT) VALUES ((SELECT ID
                                                      FROM AUTHOR
                                                      WHERE AUTHOR.AUTHORNAME = 'author1'), 'THIS BOOK IS SHIT!');



