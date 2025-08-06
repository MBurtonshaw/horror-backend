BEGIN TRANSACTION;

DROP TABLE IF EXISTS consumer_movie CASCADE;
DROP TABLE IF EXISTS movie_links CASCADE;
DROP TABLE IF EXISTS movie_director CASCADE;
DROP TABLE IF EXISTS movie_writer CASCADE;
DROP TABLE IF EXISTS movie_season CASCADE;
DROP TABLE IF EXISTS movie_decade CASCADE;
DROP TABLE IF EXISTS movie_genre CASCADE;
DROP TABLE IF EXISTS links CASCADE;
DROP TABLE IF EXISTS season CASCADE;
DROP TABLE IF EXISTS director CASCADE;
DROP TABLE IF EXISTS writer CASCADE;
DROP TABLE IF EXISTS decade CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS consumer CASCADE;
DROP TABLE IF EXISTS movie CASCADE;

CREATE TABLE movie(
	movie_id int,
	movie_title varchar(36),
	year_released date,
	movie_decade int,
	movie_url varchar(36),
	CONSTRAINT PK_movie PRIMARY KEY (movie_id)
);

CREATE TABLE consumer(
	consumer_id SERIAL,
	first_name varchar(18),
	last_name varchar(32),
	email varchar(32),
	password varchar(64),
	CONSTRAINT PK_consumer PRIMARY KEY (consumer_id)
);

CREATE TABLE genre(
	genre_name varchar(20),
	CONSTRAINT PK_genre PRIMARY KEY (genre_name)
);

CREATE TABLE decade(
	decade_name int,
	CONSTRAINT PK_decade PRIMARY KEY (decade_name)
);

CREATE TABLE writer(
	writer_name varchar(32),
	CONSTRAINT PK_writer PRIMARY KEY (writer_name)
);

CREATE TABLE director(
	director_name varchar(32),
	CONSTRAINT PK_director PRIMARY KEY (director_name)
);

CREATE TABLE links(
	link_id int,
	youtube_link varchar(100),
	prime_link varchar(100),
	CONSTRAINT PK_links PRIMARY KEY (link_id)
);

CREATE TABLE season(
	season_name varchar(10),
	CONSTRAINT PK_season PRIMARY KEY (season_name)
);

CREATE TABLE movie_genre(
	movie_id int,
	genre_name varchar(20),
	CONSTRAINT PK_movie_genre PRIMARY KEY (movie_id, genre_name),
	CONSTRAINT FK_movie_genre_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_genre_genre FOREIGN KEY (genre_name) REFERENCES genre (genre_name)
);

CREATE TABLE movie_decade(
	movie_id int,
	decade_name int,
	CONSTRAINT PK_movie_decade PRIMARY KEY (movie_id, decade_name),
	CONSTRAINT FK_movie_decade_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_decade_decade FOREIGN KEY (decade_name) REFERENCES decade (decade_name)
);

CREATE TABLE movie_season(
	movie_id int,
	season_name varchar(32),
	CONSTRAINT PK_movie_season PRIMARY KEY (movie_id, season_name),
	CONSTRAINT FK_movie_season_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_season_season FOREIGN KEY (season_name) REFERENCES season (season_name)
);

CREATE TABLE movie_writer(
	movie_id int,
	writer_name varchar(32),
	CONSTRAINT PK_movie_writer PRIMARY KEY (movie_id, writer_name),
	CONSTRAINT FK_movie_writer_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_writer_writer FOREIGN KEY (writer_name) REFERENCES writer (writer_name)
);

CREATE TABLE movie_director(
	movie_id int,
	director_name varchar(32),
	CONSTRAINT PK_movie_director PRIMARY KEY (movie_id, director_name),
	CONSTRAINT FK_movie_director_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_director_director FOREIGN KEY (director_name) REFERENCES director (director_name)
);

CREATE TABLE movie_links(
	movie_id int,
	link_id int,
	CONSTRAINT PK_movie_links PRIMARY KEY (movie_id, link_id),
	CONSTRAINT FK_movie_links_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
	CONSTRAINT FK_movie_links_links FOREIGN KEY (link_id) REFERENCES links (link_id)
);

CREATE TABLE consumer_movie(
	consumer_id int,
	movie_id int,
	CONSTRAINT PK_consumer_movie PRIMARY KEY (consumer_id, movie_id),
	CONSTRAINT FK_consumer_movie_consumer FOREIGN KEY (consumer_id) REFERENCES consumer (consumer_id),
	CONSTRAINT FK_consumer_movie_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id)
);

INSERT INTO movie (movie_id, movie_title, year_released, movie_decade, movie_url) VALUES 
	(1, 'The Sitter', '1977-11-05', 1970, 'the_sitter'), 
	(2, 'It Follows', '2015-03-15', 2010, 'it_follows'), 
	(3, 'Ginger Snaps', '2000-09-07', 2000, 'ginger_snaps'), 
	(4, 'Pontypool', '2008-01-30', 2000, 'pontypool'), 
	(5, 'Pumpkinhead', '1988-10-14', 1980, 'pumpkinhead'), 
	(6, 'Shivers', '1975-10-17', 1970, 'shivers'), 
	(7, 'Prince of Darkness', '1987-10-23', 1980, 'prince_of_darkness'),
	(8, 'The Mist', '2007-11-21', 2000, 'the_mist'),
	(9, 'From Dusk till Dawn', '1996-01-19', 1990, 'dusk_till_dawn'),
	(10, 'Ravenous', '1999-02-07', 1990, 'ravenous'),
	(11, 'The Curse of Frankenstein', '1957-05-02', 1950, 'curse_of_frankenstein'),
	(12, 'Black Christmas', '1974-10-11', 1970, 'black_christmas'),
	(13, 'Funny Games (US)', '2008-03-14', 2000, 'funny_games'),
	(14, 'The VVitch', '2016-02-19', 2010, 'the_vvitch'),
	(15, 'The Lighthouse', '2019-10-18', 2010, 'the_lighthouse'),
	(16, 'Hereditary', '2018-06-08', 2010, 'hereditary'),
	(17, 'Get Out', '2017-02-24', 2010, 'get_out'),
	(18, 'V/H/S', '2012-10-05', 2010, 'vhs'),
	(19, 'Black Phone', '2022-01-24', 2020, 'black_phone'),
	(20, 'Pearl', '2022-09-16', 2020, 'pearl'),
	(21, 'Texas Chainsaw Massacre', '1974-10-01', 1970, 'texas_chainsaw_massacre'),
	(22, 'The Evil Dead', '1983-10-15', 1980, 'evil_dead'),
	(23, 'Evil Dead 2: Dead by Dawn', '1987-03-13', 1980, 'evil_dead_2'),
	(24, 'Creep', '2015-08-14', 2010, 'creep'),
	(25, 'Halloween', '1978-10-25', 1970, 'halloween'),
	(26, 'The Thing', '1982-06-25', 1980, 'the_thing'),
	(27, 'Scream', '1996-12-20', 1990, 'scream'),
	(28, 'Re-Animator', '1985-10-18', 1980, 're_animator'),
	(29, 'Planet Terror', '2007-04-06', 2000, 'planet_terror'),
	(30, 'Gremlins', '1984-06-08', 1980, 'gremlins'),
	(31, 'Hell House LLC', '2015-09-16', 2010, 'hell_house_llc'),
	(32, 'Childs Play', '1988-11-09', 1980, 'childs_play'),
	(33, 'Christine', '1983-10-09', 1980, 'christine'),
	(34, 'Death Proof', '2007-04-06', 2000, 'death_proof'),
	(35, 'Rosemary''s Baby', '1968-06-12', 1960, 'rosemarys_baby'),
	(36, 'Night of the Living Dead', '1968-10-1', 1960, 'night_of_the_living_dead');

INSERT INTO genre (genre_name) VALUES
	('Eerie'), ('Suburban'), ('Slasher'), ('Indie'), ('Short'), ('Surreal'),
	('Creature'), ('Body'), ('Gore'), ('Invasion'), ('Rural'), ('Occult'),
	('Cosmic'), ('Campy'), ('Period'), ('Classic'), ('Analog'), ('Anthology'), ('Urban');

INSERT INTO decade (decade_name) VALUES
	(1950), (1960), (1970), (1980), (1990), (2000), (2010), (2020);

INSERT INTO writer (writer_name) VALUES
	('Fred Walton'), ('Steve Feke'), ('David Robert Mitchell'), ('Karen Walton'), ('Tony Burgess'), 
	('Mark Patrick Carducci'), ('Peter E. Vincent'), ('Gary Gerani'), ('Richard C. Weinman'), ('David Cronenberg'),
	('Prince of Darkness'), ('Stephen King'), ('Frank Darabont'), ('Robert Kurtzman'), ('Quentin Tarantino'),
	('Ted Griffin'), ('Jimmy Sangster'), ('A. Roy Moore'), ('Michael Haneke'), ('Robert Eggers'), ('Ari Aster'),
	('Jordan Peele'), ('Simon Barrett'), ('David Bruckner'), ('Nicholas Tecosky'), ('Ti West'), ('Glenn McQuaid'),
	('Matt Bettinelli-Olpin'), ('Tyler Gillet'), ('Justin Martinez'), ('Chad Villella'), ('Scott Derrickson'),
	('Mia Goth'), ('Tobe Hooper'), ('Sam Raimi'), ('Mark Duplass'), ('Patrick Brice'), ('John Carpenter'),
	('Debra Hill'), ('Bill Lancaster'), ('Kevin Williamson'), ('Stuart Gordon'), ('William J. Norris'),
	('Dennis Paoli'), ('Robert Rodriguez'), ('Chris Columbus'), ('Stephen Cognetti'), ('Don Mancini'), ('John Lafia'),
	('Bill Phillips'), ('Tom Holland'), ('Ira Levin'), ('Roman Polanski'), ('George A. Romero'), ('John Russo');

INSERT INTO director (director_name) VALUES
	('Fred Walton'), ('David Robert Mitchell'), ('John Fawcett'), ('Bruce McDonald'), ('Stan Winston'),
	('David Cronenberg'), ('John Carpenter'), ('Frank Darabont'), ('Robert Rodriguez'), ('Antonia Bird'),
	('Terence Fisher'), ('Bob Clark'), ('Michael Haneke'), ('Robert Eggers'), ('Ari Aster'), ('Jordan Peele'),
	('Adam Wingard'), ('David Bruckner'), ('Ti West'), ('Glenn McQuaid'), ('Joe Swanberg'), ('Radio Silence'),
	('Scott Derrickson'), ('Tobe Hooper'), ('Sam Raimi'), ('Patrick Brice'), ('Wes Craven'),
	('Stuart Gordon'), ('Joe Dante'), ('Stephen Cognetti'), ('Tom Holland'), ('Quentin Tarantino'), ('Roman Polanski'),
	('George A. Romero');

INSERT INTO links (link_id, youtube_link, prime_link) VALUES
	(1, 'https://www.youtube.com/watch?v=--BSM6J6tGI', NULL),
	(2, 'https://www.youtube.com/watch?v=emhePra9Zm4', 'https://www.amazon.com/gp/video/detail/B011KKFPGW/ref=atv_dp_share_cu_r'),
	(3, 'https://www.amazon.com/gp/video/detail/B008X1O7KM/ref=atv_dp_share_cu_r', 'https://www.youtube.com/watch?v=PaVuuiA0fO0'),
	(4, 'https://www.youtube.com/watch?v=jQ4DmindUIA', 'https://www.amazon.com/gp/video/detail/B0CH5YZVT9/ref=atv_dp_share_cu_r'),
	(5, 'https://www.youtube.com/watch?v=qbl_E1rgbC8', 'https://www.amazon.com/gp/video/detail/B00HH3WIBW/ref=atv_dp_share_cu_r'),
	(6, 'https://www.youtube.com/watch?v=CQ9iL41uXug', 'https://www.amazon.com/gp/video/detail/B00O8NPCQW/ref=atv_dp_share_cu_r'),
	(7, 'https://www.youtube.com/watch?si=WcE3Nr3MSOzhamQa&v=SbxxUe9C0n4&feature=youtu.be', 'https://www.amazon.com/gp/video/detail/B000I9WWEA/ref=atv_dp_share_cu_r'),
	(8, 'https://www.youtube.com/watch?v=ph1umRRFTNU', 'https://www.amazon.com/gp/video/detail/B003TNQEF0/ref=atv_dp_share_cu_r'),
	(9, 'https://www.youtube.com/watch?v=H5Vk7qiAfNk', 'https://www.amazon.com/gp/video/detail/B009JZUJIK/ref=atv_dp_share_cu_r'),
	(10, 'https://www.youtube.com/watch?v=GQMYF4yKnyU', 'https://www.amazon.com/gp/video/detail/B009EEVOP8/ref=atv_dp_share_cu_r'),
	(11, 'https://www.youtube.com/watch?v=FCNrxjaVf2M', 'https://www.amazon.com/gp/video/detail/B00DKN7XHC/ref=atv_dp_share_cu_r'),
	(12, 'https://www.youtube.com/watch?v=BawUDjjgAsc', 'https://www.amazon.com/gp/video/detail/B07X7KZ98B/ref=atv_dp_share_cu_r'),
	(13, 'https://www.youtube.com/watch?v=QKh5xTo_I14', 'https://www.amazon.com/gp/video/detail/B0091WEFZO/ref=atv_dp_share_cu_r'),
	(14, 'https://www.youtube.com/watch?v=0otnn7hgnek', 'https://www.amazon.com/gp/video/detail/B01BT3SDQO/ref=atv_dp_share_cu_r'),
	(15, 'https://www.youtube.com/watch?v=1TZXfVEdXyA', 'https://www.amazon.com/gp/video/detail/B07Z4236MR/ref=atv_dp_share_cu_r'),
	(16, 'https://www.youtube.com/watch?v=W1WgJB4EkGU', 'https://www.amazon.com/gp/video/detail/B07GRB9NF3/ref=atv_dp_share_cu_r'),
	(17, NULL, 'https://www.amazon.com/gp/video/detail/B06Y1H48K7/ref=atv_dp_share_cu_r'),
	(18, 'https://www.youtube.com/watch?v=YHvBw3zCebk', 'https://www.amazon.com/gp/video/detail/B00944CJRK/ref=atv_dp_share_cu_r'),
	(19, 'https://www.youtube.com/watch?v=0OFW1jZznac', 'https://www.amazon.com/gp/video/detail/B0B5W5R4X3/ref=atv_dp_share_cu_r'),
	(20, 'https://www.youtube.com/watch?v=JXqgN-a1N7g', 'https://www.amazon.com/gp/video/detail/B0B8MY88QP/ref=atv_dp_share_cu_r'),
	(21, 'https://www.youtube.com/watch?v=2ujekuaOPv0', 'https://www.amazon.com/gp/video/detail/B077T2DG12/ref=atv_dp_share_cu_r'),
	(22, NULL, 'https://www.amazon.com/gp/video/detail/B0BQSC8DCK/ref=atv_dp_share_cu_r'),
	(23, 'https://www.youtube.com/watch?v=uzb6fMc5Z8w', 'https://www.amazon.com/gp/video/detail/B007WN5S8M/ref=atv_dp_share_cu_r'),
	(24, 'https://www.youtube.com/watch?v=XmMbhCa3ikc', 'https://www.amazon.com/gp/video/detail/B011KKCH0O/ref=atv_dp_share_cu_r'),
	(25, 'https://www.youtube.com/watch?v=-_8KR4U5Yhk', 'https://www.amazon.com/gp/video/detail/B09L2NZ5GK/ref=atv_dp_share_cu_r'),
	(26, 'https://www.youtube.com/watch?v=osB4KmgfIDM', 'https://www.amazon.com/gp/video/detail/B0CMR2Y5CN/ref=atv_dp_share_cu_r'),
	(27, 'https://www.youtube.com/watch?v=IQO7FAeV_lM', 'https://www.amazon.com/gp/video/detail/B00AYB1BIK/ref=atv_dp_share_cu_r'),
	(28, 'https://www.youtube.com/watch?v=Ih6QRaKLryQ', 'https://www.amazon.com/gp/video/detail/B075F41LJF/ref=atv_dp_share_cu_r'),
	(29, 'https://www.youtube.com/watch?v=gUkJvulfu6k', 'https://www.amazon.com/gp/video/detail/B002I7IC58/ref=atv_dp_share_cu_r'),
	(30, 'https://www.youtube.com/watch?v=WB9hY4tmV3w', 'https://www.amazon.com/gp/video/detail/B00KQ9Z1SQ/ref=atv_dp_share_cu_r'),
	(31, 'https://www.youtube.com/watch?v=SlIbSDNSTao', 'https://www.amazon.com/gp/video/detail/B07X3NDRZ7/ref=atv_dp_share_cu_r'),
	(32, 'https://www.youtube.com/watch?v=qHIxyDin-og', 'https://www.amazon.com/gp/video/detail/B000IZ21BS/ref=atv_dp_share_cu_r'),
	(33, NULL, 'https://www.amazon.com/Christine-Keith-Gordon/dp/B000TS5CME'),
	(34, 'https://www.youtube.com/watch?v=UJYVe5TNXhc', 'https://www.amazon.com/Grindhouse-Death-Proof-Kurt-Russell/dp/B0CM1M26V5'),
	(35, 'https://www.youtube.com/watch?v=GejaojGjjF0', 'https://www.amazon.com/gp/video/detail/B002O2IPP4/ref=atv_dp_share_cu_r'),
	(36, 'https://www.youtube.com/watch?v=H91BxkBXttE', 'https://www.amazon.com/gp/video/detail/B075DRS2Y4/ref=atv_dp_share_cu_r');

INSERT INTO season (season_name) VALUES
	('Fall'), ('Winter'), ('Spring'), ('Summer');

INSERT INTO movie_genre VALUES
	(1, 'Eerie'), (1, 'Suburban'), (1, 'Slasher'), (1, 'Indie'), (1, 'Short'),
	(2, 'Eerie'), (2, 'Suburban'), (2, 'Surreal'), (2, 'Indie'),
	(3, 'Creature'), (3, 'Suburban'), (3, 'Indie'), (3, 'Body'), (3, 'Gore'),
	(4, 'Eerie'), (4, 'Indie'), (4, 'Invasion'), (4, 'Rural'),
	(5, 'Creature'), (5, 'Occult'), (5, 'Rural'),
	(6, 'Invasion'), (6, 'Indie'), (6, 'Body'), (6, 'Gore'),
	(7, 'Occult'), (7, 'Invasion'), (7, 'Urban'), (7, 'Cosmic'), (7, 'Eerie'),
	(8, 'Creature'), (8, 'Cosmic'), (8, 'Rural'), (8, 'Invasion'),
	(9, 'Creature'), (9, 'Campy'), (9, 'Body'), (9, 'Gore'),
	(10, 'Invasion'), (10, 'Gore'), (10, 'Period'), (10, 'Campy'),
	(11, 'Classic'), (11, 'Creature'), (11, 'Period'),
	(12, 'Suburban'), (12, 'Indie'), (12, 'Slasher'),
	(13, 'Suburban'), (13, 'Invasion'), (13, 'Surreal'),
	(14, 'Occult'), (14, 'Period'), (14, 'Rural'), (14, 'Eerie'),
	(15, 'Eerie'), (15, 'Period'), (15, 'Surreal'), (15, 'Cosmic'),
	(16, 'Occult'), (16, 'Suburban'), (16, 'Surreal'), (16, 'Gore'),
	(17, 'Eerie'), (17, 'Suburban'), (17, 'Body'),
	(18, 'Analog'), (18, 'Slasher'), (18, 'Gore'), (18, 'Occult'), (18, 'Surreal'), (18, 'Anthology'),
	(19, 'Eerie'), (19, 'Cosmic'), (19, 'Suburban'), (19, 'Period'),
	(20, 'Rural'), (20, 'Campy'), (20, 'Period'),
	(21, 'Rural'), (21, 'Slasher'), (21, 'Gore'), (21, 'Indie'),
	(22, 'Rural'), (22, 'Surreal'), (22, 'Occult'), (22, 'Campy'), (22, 'Gore'), (22, 'Indie'),
	(23, 'Rural'), (23, 'Surreal'), (23, 'Occult'), (23, 'Campy'), (23, 'Gore'),
	(24, 'Eerie'), (24, 'Analog'),
	(25, 'Eerie'), (25, 'Slasher'), (25, 'Suburban'),
	(26, 'Eerie'), (26, 'Surreal'), (26, 'Cosmic'), (26, 'Invasion'), (26, 'Gore'), (26, 'Body'),
	(27, 'Slasher'), (27, 'Gore'), (27, 'Suburban'),
	(28, 'Campy'), (28, 'Gore'), (28, 'Body'),
	(29, 'Surreal'), (29, 'Campy'), (29, 'Gore'), (29, 'Invasion'), (29, 'Rural'),
	(30, 'Surreal'), (30, 'Campy'), (30, 'Creature'), (30, 'Invasion'), (30, 'Suburban'),
	(31, 'Analog'), (31, 'Eerie'), (31, 'Occult'),
	(32, 'Occult'), (32, 'Campy'), (32, 'Surreal'), (32, 'Body'), (32, 'Slasher'), (32, 'Urban'),
	(33, 'Surreal'), (33, 'Suburban'), (33, 'Period'),
	(34, 'Eerie'), (34, 'Gore'), (34, 'Campy'),
	(35, 'Eerie'), (35, 'Surreal'), (35, 'Urban'), (35, 'Body'), (35, 'Occult'), (35, 'Classic'),
	(36, 'Invasion'), (36, 'Eerie'), (36, 'Rural'), (36, 'Indie'), (36, 'Classic');

INSERT INTO movie_decade VALUES
	(1, 1970), (2, 2010), (3, 2000), (4, 2000), (5, 1980), (6, 1970), (7, 1980), (8, 2000), (9, 1990), (10, 1990),
	(11, 1950), (12, 1970), (13, 2000), (14, 2010), (15, 2010), (16, 2010), (17, 2010), (18, 2010), (19, 2020),
	(20, 2020), (21, 1970), (22, 1980), (23, 1980), (24, 2010), (25, 1970), (26, 1980), (27, 1990), (28, 1980),
	(29, 2000), (30, 1980), (31, 2010), (32, 1980), (33, 1980), (34, 2000), (35, 1960), (36, 1960);

INSERT INTO movie_season VALUES
	(25, 'Fall'), (3, 'Fall'), (11, 'Fall'), (2, 'Fall'), (14, 'Fall'), (28, 'Fall'),
	(12, 'Winter'), (10, 'Winter'), (30, 'Winter'), (26, 'Winter'), (4, 'Winter'), (31, 'Winter'),
	(8, 'Spring'), (22, 'Spring'), (15, 'Spring'), (16, 'Spring'), (20, 'Spring'), (19, 'Spring'),
	(1, 'Summer'), (9, 'Summer'), (5, 'Summer'), (27, 'Summer'), (29, 'Summer'), (21, 'Summer');
	
INSERT INTO movie_writer VALUES
	(1, 'Fred Walton'), (1, 'Steve Feke'),
	(2, 'David Robert Mitchell'),
	(3, 'Karen Walton'),
	(4, 'Tony Burgess'),
	(5, 'Mark Patrick Carducci'), (5, 'Peter E. Vincent'), (5, 'Gary Gerani'), (5, 'Richard C. Weinman'),
	(6, 'David Cronenberg'),
	(7, 'John Carpenter'),
	(8, 'Stephen King'), (8, 'Frank Darabont'),
	(9, 'Robert Kurtzman'), (9, 'Quentin Tarantino'),
	(10, 'Ted Griffin'),
	(11, 'Jimmy Sangster'),
	(12, 'A. Roy Moore'),
	(13, 'Michael Haneke'),
	(14, 'Robert Eggers'),
	(15, 'Robert Eggers'),
	(16, 'Ari Aster'),
	(17, 'Jordan Peele'),
	(18, 'Simon Barrett'), (18, 'David Bruckner'), (18, 'Nicholas Tecosky'), (18, 'Ti West'), (18, 'Glenn McQuaid'), (18, 'Matt Bettinelli-Olpin'), (18, 'Tyler Gillet'), (18, 'Justin Martinez'), (18, 'Chad Villella'),
	(19, 'Scott Derrickson'),
	(20, 'Mia Goth'), (20, 'Ti West'),
	(21, 'Tobe Hooper'),
	(22, 'Sam Raimi'),
	(23, 'Sam Raimi'),
	(24, 'Mark Duplass'), (24, 'Patrick Brice'),
	(25, 'John Carpenter'), (25, 'Debra Hill'),
	(26, 'Bill Lancaster'), (26, 'John Carpenter'),
	(27, 'Kevin Williamson'), 
	(28, 'Stuart Gordon'), (28, 'William J. Norris'), (28, 'Dennis Paoli'),
	(29, 'Robert Rodriguez'),
	(30, 'Chris Columbus'),
	(31, 'Stephen Cognetti'),
	(32, 'Don Mancini'), (32, 'John Lafia'), (32, 'Tom Holland'),
	(33, 'Bill Phillips'), (33, 'Stephen King'),
	(34, 'Quentin Tarantino'),
	(35, 'Ira Levin'), (35, 'Roman Polanski'),
	(36, 'George A. Romero'), (36, 'John Russo');
	
INSERT INTO movie_director VALUES 
	(1, 'Fred Walton'),
	(2, 'David Robert Mitchell'),
	(3, 'John Fawcett'),
	(4, 'Bruce McDonald'),
	(5, 'Stan Winston'), 
	(6, 'David Cronenberg'),
	(7, 'John Carpenter'),
	(8, 'Frank Darabont'),
	(9, 'Robert Rodriguez'),
	(10, 'Antonia Bird'), 
	(11, 'Terence Fisher'),
	(12, 'Bob Clark'),
	(13, 'Michael Haneke'),
	(14, 'Robert Eggers'),
	(15, 'Robert Eggers'), 
	(16, 'Ari Aster'),
	(17, 'Jordan Peele'),
	(18, 'Adam Wingard'), (18, 'David Bruckner'), (18, 'Ti West'), (18, 'Glenn McQuaid'), (18, 'Joe Swanberg'), (18, 'Radio Silence'),
	(19, 'Scott Derrickson'),
	(20, 'Ti West'), 
	(21, 'Tobe Hooper'),
	(22, 'Sam Raimi'),
	(23, 'Sam Raimi'),
	(24, 'Patrick Brice'),
	(25, 'John Carpenter'), 
	(26, 'John Carpenter'),
	(27, 'Wes Craven'),
	(28, 'Stuart Gordon'),
	(29, 'Robert Rodriguez'),
	(30, 'Joe Dante'), 
	(31, 'Stephen Cognetti'),
	(32, 'Tom Holland'),
	(33, 'John Carpenter'),
	(34, 'Quentin Tarantino'),
	(35, 'Roman Polanski'),
	(36, 'George A. Romero');

INSERT INTO movie_links (movie_id, link_id) VALUES
	(1, 1), (2, 2), (3, 3), (4, 4), 
	(5, 5), (6, 6), (7, 7), (8, 8), 
	(9, 9), (10, 10), (11, 11), (12, 12), 
	(13, 13), (14, 14), (15, 15), (16, 16), 
	(17, 17), (18, 18), (19, 19), (20, 20), 
	(21, 21), (22, 22), (23, 23), (24, 24), 
	(25, 25), (26, 26), (27, 27), (28, 28), 
	(29, 29), (30, 30), (31, 31), (32, 32), 
	(33, 33), (34, 34), (35, 35), (36, 36);

INSERT INTO consumer (consumer_id, first_name, last_name, email, password)
VALUES (DEFAULT, 'Matthew', 'Burtonshaw', 'MBurtonshaw@gmail.com', 'password');

INSERT INTO consumer_movie (consumer_id, movie_id)
VALUES (1, 3), (1, 4), (1, 8), (1, 19), (1, 25);

COMMIT TRANSACTION;