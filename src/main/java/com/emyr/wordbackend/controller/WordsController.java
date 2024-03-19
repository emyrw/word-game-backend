package com.emyr.wordbackend.controller;

import com.emyr.wordbackend.domain.WordIn;
import com.emyr.wordbackend.domain.WordOut;
import com.emyr.wordbackend.service.WordsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("word-game/v1/words")
public class WordsController {

  private final WordsService wordsService;

  @GetMapping("/today")
  public WordOut getTodayWord() {
    return wordsService.getTodayWord();
  }

  @PostMapping
  public void addWord(@RequestBody WordIn word) {
    wordsService.addWordToStorage(word);
  }

  @GetMapping("/check/{word}")
  public Boolean checkWordIsReal(@PathVariable String word) {
    return wordsService.checkIfWordIsReal(word);
  }

  @GetMapping("/all")
  public List<WordOut> getAllWords() { return wordsService.getAllWords(); }

  @PostMapping("/generate")
  public void manuallyUpdateWord() { wordsService.updateTodayWord();}

  @DeleteMapping("/{id}")
  public void deleteWordWord(@PathVariable String id) { wordsService.deleteWord(Integer.parseInt(id));}

  @GetMapping("/count")
  public int getWordCount() { return wordsService.getTotalWordCount(); }


}
