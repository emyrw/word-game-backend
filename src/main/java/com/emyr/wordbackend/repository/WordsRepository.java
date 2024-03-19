package com.emyr.wordbackend.repository;

import com.emyr.wordbackend.domain.WordIn;
import com.emyr.wordbackend.domain.WordOut;
import java.util.List;
import java.util.Optional;

public interface WordsRepository {

  Optional<WordOut> getWordInUse();

  int getMaxWordId();

  Optional<WordOut> getWordById(int id);

  Long upsertWord(WordIn word);

  Optional<WordOut> findByWord(String word);

  List<WordOut> listWords();

  void deleteWord(int id);

  Optional<WordOut> getRandomWord();

}
