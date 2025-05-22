DELETE FROM member_interest;
DELETE FROM interest;

ALTER TABLE interest DROP CHECK interest_chk_1;

INSERT INTO interest (interest_type) VALUES ('MARKETING');
INSERT INTO interest (interest_type) VALUES ('SELF_DEVELOPMENT');
INSERT INTO interest (interest_type) VALUES ('VOLUNTEERING');
INSERT INTO interest (interest_type) VALUES ('CAREER_ROADMAP');
INSERT INTO interest (interest_type) VALUES ('STOCK_INVESTING');
INSERT INTO interest (interest_type) VALUES ('STARTUP');
INSERT INTO interest (interest_type) VALUES ('IT');
INSERT INTO interest (interest_type) VALUES ('CONTEST');
INSERT INTO interest (interest_type) VALUES ('SELF_UNDERSTANDING');
INSERT INTO interest (interest_type) VALUES ('DESIGN');
INSERT INTO interest (interest_type) VALUES ('TRAVEL');
INSERT INTO interest (interest_type) VALUES ('HUMANITIES_AND_ARTS');
INSERT INTO interest (interest_type) VALUES ('COUNSELING');
INSERT INTO interest (interest_type) VALUES ('BACKEND');
INSERT INTO interest (interest_type) VALUES ('SPORTS');
INSERT INTO interest (interest_type) VALUES ('WRITING');
INSERT INTO interest (interest_type) VALUES ('CAREER_DECISION');