
CREATE TABLE color(
    id serial PRIMARY KEY,
    code varchar(8)
);

CREATE TABLE set(
    id serial PRIMARY KEY,
    name varchar(30),
    color_id int default 1,
    CONSTRAINT fk_color
      FOREIGN KEY(color_id)
	  REFERENCES color(id)
);

CREATE TABLE score(
    id serial PRIMARY KEY,
    set_id int,
    score_first int,
    score_second int,
    CONSTRAINT fk_set
      FOREIGN KEY(set_id)
	  REFERENCES set(id)
);

CREATE TABLE flashcard(
    id serial PRIMARY KEY,
    set_id int,
    first_sentence varchar(60),
    second_sentence varchar(60),
    first_correct int DEFAULT 0,
    second_correct int DEFAULT 0,
    CONSTRAINT fk_set
      FOREIGN KEY(set_id)
	  REFERENCES set(id)
);

create function create_score_row() returns trigger
    language plpgsql
as
$$
BEGIN
    Insert into score( score_first, score_second, set_id)
    VALUES (0,0,NEW.id);

    Return NEW;
end;
$$;

create function update_first_score() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE score
        SET score_first=(
            select sum(first_correct) as first_correct  from flashcard
            where set_id=NEW.set_id
            )
        WHERE set_id=NEW.set_id;

    Return NEW;
end;
$$;

create function update_second_score() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE score
    SET score_second=(
        select sum(second_correct) as second_correct  from flashcard
        where set_id=NEW.set_id
    )
    WHERE set_id=NEW.set_id;

    Return NEW;
end;
$$;

create trigger update_first_score_trigger
    after update
        of first_correct
    on flashcard
    for each row
execute procedure update_first_score();

create trigger update_second_score_trigger
    after update
        of second_correct
    on flashcard
    for each row
execute procedure update_second_score();

create trigger create_score_row_trigger
    after insert
    on set
    for each row
execute procedure create_score_row();

INSERT INTO color(code) values ('#F0B4F8');
INSERT INTO color(code) values ('#44a9e8');
INSERT INTO color(code) values ('#7DDBD3');
INSERT INTO color(code) values ('#F15412');
INSERT INTO color(code) values ('#7C3E66');
INSERT INTO color(code) values ('#A5BECC');

INSERT INTO set(name, color_id) values ('test set',1);

insert into flashcard(set_id, first_sentence, second_sentence)
values(1,'testFirstSentence','testSecondSentence');