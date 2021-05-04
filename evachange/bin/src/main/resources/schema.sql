DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS SHARE;
DROP TABLE IF EXISTS SHAREQUANTITY;

CREATE TABLE USER (
        id INT PRIMARY KEY,
        name VARCHAR(250) NOT NULL,
        budget DOUBLE NOT NULL
);

CREATE TABLE SHARE (
        symbol VARCHAR(250) PRIMARY KEY,
        rate DOUBLE NOT NULL
);

CREATE TABLE SHAREQUANTITY (
        symbol VARCHAR(250) NOT NULL,
        id int NOT NULL,
        quantity int NOT NULL
);

ALTER TABLE SHARE
    ADD FOREIGN KEY (symbol)
        REFERENCES SHARE(symbol);
ALTER TABLE USER
    ADD FOREIGN KEY (id)
        REFERENCES USER(id);