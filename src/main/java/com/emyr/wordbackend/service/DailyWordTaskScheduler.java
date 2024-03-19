package com.emyr.wordbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class DailyWordTaskScheduler {

  @Autowired
  private WordsService wordsService;

  //at midnight every day
  @Scheduled(cron = "0 0 0 * * *")
  public void changeTodayWord() {
    wordsService.updateTodayWord();
  }

}
