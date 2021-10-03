DROP TABLE IF EXISTS "socks";
DROP TABLE IF EXISTS "colors";
DROP TABLE IF EXISTS "cotton_parts";

CREATE TABLE IF NOT EXISTS "socks" (
  "id" int PRIMARY KEY,
  "quantity" int NOT NULL,
  "colors_id" int NOT NULL,
  "cotton_parts_id" int NOT NULL
);

CREATE TABLE IF NOT EXISTS "colors" (
  "id" int PRIMARY KEY,
  "colors" varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS "cotton_parts" (
  "id" int PRIMARY KEY,
  "cotton_part" int NOT NULL
);


ALTER TABLE "socks" ADD FOREIGN KEY ("colors_id") REFERENCES "colors" ("id");
ALTER TABLE "socks" ADD FOREIGN KEY ("cotton_parts_id") REFERENCES "cotton_parts" ("id");
ALTER TABLE "socks" ADD CONSTRAINT socks_color_cotton_part  UNIQUE ("id", "colors_id", "cotton_parts_id") ;