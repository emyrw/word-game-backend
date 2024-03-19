package com.emyr.wordbackend.repository;

import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.DELETE_WORD;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.FIND_ALL_WORDS;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.FIND_MAX_ID;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.FIND_WORD_BY_ID;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.FIND_WORD_BY_WORD;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.FIND_WORD_IN_USE;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.GET_RANDOM_WORD;
import static com.emyr.wordbackend.repository.JdbcRepositoryConstants.UPSERT_WORD;

import com.emyr.wordbackend.domain.WordIn;
import com.emyr.wordbackend.domain.WordOut;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WordsRepositoryImpl implements WordsRepository {

  private final NamedParameterJdbcTemplate namedJdbcTemplate;


  @Override
  public Optional<WordOut> getWordInUse() {
    var foundEntity = namedJdbcTemplate.query(FIND_WORD_IN_USE,
      new MapSqlParameterSource(), (rs, rowNum) -> mapToEntity(rs));
    return foundEntity.isEmpty()
      ? Optional.empty()
      : Optional.of(foundEntity.get(0));
  }

  @Override
  public int getMaxWordId() {
    return namedJdbcTemplate.queryForObject(FIND_MAX_ID, Map.of(), Integer.class);
  }

  @Override
  public Optional<WordOut> getWordById(int id) {
    var foundEntity = namedJdbcTemplate.query(FIND_WORD_BY_ID,
      new MapSqlParameterSource("id", id), (rs, rowNum) -> mapToEntity(rs));
    return foundEntity.isEmpty()
      ? Optional.empty()
      : Optional.of(foundEntity.get(0));
  }

  @Override
  public Long upsertWord(WordIn word) {
    var param = getMapSqlParameterSource(word);
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    namedJdbcTemplate.update(UPSERT_WORD, param, generatedKeyHolder, new String[] { "id" });
    return Optional.ofNullable(generatedKeyHolder.getKey()).map(Number::longValue).orElse(null);
  }

  @Override
  public Optional<WordOut> findByWord(String word) {
    var foundEntity = namedJdbcTemplate.query(FIND_WORD_BY_WORD,
      new MapSqlParameterSource("word", word), (rs, rowNum) -> mapToEntity(rs));
    return foundEntity.isEmpty()
      ? Optional.empty()
      : Optional.of(foundEntity.get(0));
  }

  @Override
  public void deleteWord(int id) {
    namedJdbcTemplate.update(DELETE_WORD, new MapSqlParameterSource("id", id));
  }

  @Override
  public List<WordOut> listWords() {
    return namedJdbcTemplate.query(FIND_ALL_WORDS, new MapSqlParameterSource(), (rs, rowNum) -> mapToEntity(rs));
  }

  @Override
  public Optional<WordOut> getRandomWord() {
    var foundEntity = namedJdbcTemplate.query(GET_RANDOM_WORD,
      new MapSqlParameterSource(), (rs, rowNum) -> mapToEntity(rs));
    return foundEntity.isEmpty()
      ? Optional.empty()
      : Optional.of(foundEntity.get(0));
  }

  private WordOut mapToEntity(ResultSet rs) throws SQLException {
    return new WordOut(rs.getInt("id"),
      rs.getString("word"),
      rs.getBoolean("is_in_use")
    );
  }

  private static MapSqlParameterSource getMapSqlParameterSource(WordIn wordIn) {
    var param = new MapSqlParameterSource();
    param.addValue("word", wordIn.word());
    param.addValue("is_in_use", wordIn.isInUse());
    return param;
  }
}
