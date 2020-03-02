;
CREATE USER IF NOT EXISTS "SA" SALT 'f2df1d06462bd048' HASH '2049fe487e66336f7be36d02fd1caef83a9199e221152c4a6927cf75c0412f0d' ADMIN;
CREATE CACHED TABLE "PUBLIC"."MINISTER"(
                                           "ID" BINARY NOT NULL,
                                           "NAME" VARCHAR(255)
);
ALTER TABLE "PUBLIC"."MINISTER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.MINISTER;
CREATE CACHED TABLE "PUBLIC"."PASSWORD_RESET_TOKEN"(
                                                       "ID" BINARY NOT NULL,
                                                       "EXPIRY_DATE" TIMESTAMP,
                                                       "TOKEN" VARCHAR(255),
                                                       "USER_ID" BINARY NOT NULL
);
ALTER TABLE "PUBLIC"."PASSWORD_RESET_TOKEN" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PASSWORD_RESET_TOKEN;
CREATE CACHED TABLE "PUBLIC"."TERRITORY"(
                                            "ID" BINARY NOT NULL,
                                            "NAME" VARCHAR(255),
                                            "NUMBER" VARCHAR(255),
                                            "URL_FOR_GOOGLE_MAP" VARCHAR(255),
                                            "URL_FOR_JPEG_MAP" VARCHAR(255)
);
ALTER TABLE "PUBLIC"."TERRITORY" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TERRITORY;
CREATE CACHED TABLE "PUBLIC"."TERRITORY_ENTRY"(
                                                  "ID" BINARY NOT NULL,
                                                  "ARCHIVED" BOOLEAN,
                                                  "CONGREGATION_POOL" BOOLEAN,
                                                  "MINISTERID" BINARY,
                                                  "MINISTER_NAME" VARCHAR(255),
                                                  "REGISTERED" TIMESTAMP,
                                                  "TERRITORYID" BINARY,
                                                  "TERRITORY_NAME" VARCHAR(255),
                                                  "TERRITORY_NUMBER" VARCHAR(255)
);
ALTER TABLE "PUBLIC"."TERRITORY_ENTRY" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TERRITORY_ENTRY;
CREATE CACHED TABLE "PUBLIC"."USER"(
                                       "ID" BINARY NOT NULL,
                                       "EMAIL" VARCHAR(255),
                                       "FIRST_NAME" VARCHAR(255),
                                       "LAST_NAME" VARCHAR(255),
                                       "PASSWORD" VARCHAR(255),
                                       "REGISTRATION_DATE" TIMESTAMP,
                                       "ROLES" VARCHAR(255),
                                       "USER_NAME" VARCHAR(255)
);
ALTER TABLE "PUBLIC"."USER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_27" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.USER;
CREATE CACHED TABLE "PUBLIC"."VERIFICATION_TOKEN"(
                                                     "ID" BINARY NOT NULL,
                                                     "EXPIRY_DATE" TIMESTAMP,
                                                     "TOKEN" VARCHAR(255),
                                                     "USER_ID" BINARY NOT NULL
);
ALTER TABLE "PUBLIC"."VERIFICATION_TOKEN" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.VERIFICATION_TOKEN;
ALTER TABLE "PUBLIC"."TERRITORY" ADD CONSTRAINT "PUBLIC"."UK_4KWBVUVEJHLXG2BRW6BEFWIHH" UNIQUE("NUMBER");
ALTER TABLE "PUBLIC"."USER" ADD CONSTRAINT "PUBLIC"."UK_OB8KQYQQGMEFL0ACO34AKDTPE" UNIQUE("EMAIL");
ALTER TABLE "PUBLIC"."MINISTER" ADD CONSTRAINT "PUBLIC"."UK_EVDNHK2WXQ3BCICVY693RYWOF" UNIQUE("NAME");
ALTER TABLE "PUBLIC"."USER" ADD CONSTRAINT "PUBLIC"."UK_LQJRCOBRH9JC8WPCAR64Q1BFH" UNIQUE("USER_NAME");
ALTER TABLE "PUBLIC"."PASSWORD_RESET_TOKEN" ADD CONSTRAINT "PUBLIC"."FK5LWTBNCUG84D4ERO33V3CFXVL" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USER"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."VERIFICATION_TOKEN" ADD CONSTRAINT "PUBLIC"."FK_VERIFY_USER" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USER"("ID") NOCHECK;
