package com.emyr.wordbackend.service;

import com.emyr.wordbackend.client.DictionaryClient;
import com.emyr.wordbackend.domain.WordIn;
import com.emyr.wordbackend.domain.WordOut;
import com.emyr.wordbackend.repository.WordsRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WordsServiceImpl implements WordsService {

  private final WordsRepository wordsRepository;
  private final DictionaryClient dictionaryClient;

  @Override
  public void updateTodayWord() {
    WordOut currentWord = getTodayWord();
    WordOut randomWord = wordsRepository.getRandomWord().get();
    wordsRepository.upsertWord(new WordIn(currentWord.word(), false));
    wordsRepository.upsertWord(new WordIn(randomWord.word(), true));

  }

  @Override
  public WordOut getTodayWord() {
    return wordsRepository.getWordInUse().get();
  }

  @Override
  public void addWordToStorage(WordIn word) {
    wordsRepository.upsertWord(word);
  }

  @Override
  public Boolean checkIfWordIsReal(String word) {
    Optional<WordOut> maybeRetrievedWord = wordsRepository.findByWord(word);
    if (maybeRetrievedWord.isPresent()) {
        return true;
    } else {
        if (dictionaryClient.isRealWord(word)) {
          addWordToStorage(new WordIn(word,false));
          return true;
        } else {
          return false;
        }
    }
  }

  @Override
  public void deleteWord(int id) {
    wordsRepository.deleteWord(id);
  }

  @Override
  public List<WordOut> getAllWords() {
    return wordsRepository.listWords();
  }

  @Override
  public int getTotalWordCount() { return wordsRepository.getMaxWordId(); }
}
