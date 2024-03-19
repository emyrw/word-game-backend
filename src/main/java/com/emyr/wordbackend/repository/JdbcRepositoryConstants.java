package com.emyr.wordbackend.repository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JdbcRepositoryConstants {

  private static final String COLUMNS = "id, word, is_in_use";

  public static final String FIND_WORD_IN_USE = """
    select %s from words where is_in_use = true;
    """.formatted(COLUMNS);

  public static final String FIND_MAX_ID = """
    select max(id) from words;
    """;

  public static final String FIND_WORD_BY_ID = """
    select %s from words where id = :id;
    """.formatted(COLUMNS);

  public static final String FIND_WORD_BY_WORD = """
    select %s from words where word = :word;
    """.formatted(COLUMNS);

  public static final String FIND_ALL_WORDS = """
    select %s from words;
    """.formatted(COLUMNS);

  public static final String UPSERT_WORD = """
    insert into words (word, is_in_use)
     values (:word, :is_in_use)
     on conflict(word)
     do update set word = :word, is_in_use = :is_in_use;
    """;

  public static final String DELETE_WORD = """
    delete from words where id = :id;
    """;

  public static final String GET_RANDOM_WORD = """
    select %s from words where is_in_use = false order by random() limit 1;
    """.formatted(COLUMNS);
}
