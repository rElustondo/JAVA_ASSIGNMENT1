DROP TABLE IF EXISTS "USER_ENT";
CREATE TABLE "USER_ENT" (
                        id LONG PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(255),
                        email VARCHAR(255)
);
DROP TABLE IF EXISTS "COMMENT";
CREATE TABLE "COMMENT" (
                            id LONG PRIMARY KEY AUTO_INCREMENT,
                            content VARCHAR(255),
                            timestamp VARCHAR(255)
);