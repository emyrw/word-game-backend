package com.emyr.wordbackend.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestDictionaryClient implements DictionaryClient {


  @Override
  public boolean isRealWord(String word) {
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + word))
      .method("GET", HttpRequest.BodyPublishers.noBody())
      .build();
    HttpResponse<String> response = null;
    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() >= 200 &&  response.statusCode() < 300;
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return false;
    }
  }
}
