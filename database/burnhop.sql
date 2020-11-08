DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS content;

DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE TABLE login ( id INTEGER PRIMARY KEY, email VARCHAR ( 100 ) NOT NULL UNIQUE, password VARCHAR ( 200 ) NOT NULL);

CREATE TABLE users 
	( 
	id INTEGER PRIMARY KEY, 
	name VARCHAR ( 70 ) NOT NULL, 
	username VARCHAR ( 20 ), 
	data_nasc DATE NOT NULL,
	created_on TIMESTAMP NOT NULL, 
	login_id INTEGER NOT NULL,
	FOREIGN KEY(login_id) REFERENCES login(id)
);

CREATE TABLE content(
	id_content INTEGER PRIMARY KEY,
	text VARCHAR(500)
);

CREATE TABLE posts(
	id_post INTEGER PRIMARY KEY,
	user_id	INTEGER NOT NULL,
	posted_on TIMESTAMP NOT NULL,
	content_id INTEGER NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id),
	FOREIGN KEY(content_id) REFERENCES content(id_content)
);

CREATE TABLE comments(
	id_comments INTEGER PRIMARY KEY,
	user_id INTEGER NOT NULL,
	post_id INTEGER NOT NULL,
	comment VARCHAR(500) NOT NULL,
	commented_date TIMESTAMP NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id),
	FOREIGN KEY(post_id) REFERENCES posts(id_post)
);

CREATE SEQUENCE hibernate_sequence START 1;