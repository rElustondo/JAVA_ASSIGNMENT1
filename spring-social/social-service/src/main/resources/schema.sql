DROP TABLE IF EXISTS "USER_ENT" CASCADE;
CREATE TABLE "USER_ENT" (
                        id LONG PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(255),
                        email VARCHAR(255),
                        password VARCHAR(255)

);
DROP TABLE IF EXISTS "COMMENT";
CREATE TABLE "COMMENT" (
                        id LONG PRIMARY KEY AUTO_INCREMENT,
                        content VARCHAR(255),
                        timestamp VARCHAR(255),
                        user_id LONG,
                        FOREIGN KEY (user_id) REFERENCES USER_ENT(id)
);