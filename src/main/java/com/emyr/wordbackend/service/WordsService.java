package com.emyr.wordbackend.service;

import com.emyr.wordbackend.domain.WordIn;
import com.emyr.wordbackend.domain.WordOut;
import java.util.List;

public interface WordsService {

  //TODO: method to update today's word
  void updateTodayWord();

  //TODO: get today's word
  WordOut getTodayWord();

  //TODO: insert a word
  void addWordToStorage(WordIn word);

  //TODO: check if word is real
  Boolean checkIfWordIsReal(String word);

  //Todo: get all words
  List<WordOut> getAllWords();

  void deleteWord(int id);

  int getTotalWordCount();
}
