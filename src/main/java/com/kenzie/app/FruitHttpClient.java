package com.kenzie.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class FruitHttpClient {
    public static String getDesiredURL(FruitCriteria fc) {
        switch (fc) {
            case ALL:
                // look through the documentation to determine the correct URL
                return "https://www.fruityvice.com/api/fruit/all";
            case LOW_CARB:
                // look through the documentation to determine the correct URL
                return "https://www.fruityvice.com/api/fruit/carbohydrates?min=0&max=5";
            case HIGH_CALORIE:
                // look through the documentation to determine the correct URL
                return "https://www.fruityvice.com/api/fruit/calories?min=100&max=1000";
            default:
                return "https://www.fruityvice.com";
        }
    }

    public String getFruits(String url) {
        // TODO: create a URL object from the desired URL
        // TODO: make the GET request and return the list of FruitDTOs
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }
    public static List<FruitDTO> getFruitsList(String httpResponseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<FruitDTO>> typeReferenceListFruitDTO = new TypeReference<>(){};
        List<FruitDTO> fruitList = mapper.readValue(httpResponseBody, typeReferenceListFruitDTO);

        return fruitList;
    }
    
        public static FruitDTO createFruitDTO(){
        // TODO: create a sample FruitDTO object based on what you want to send in your PUT request
        // instantiate a new FruitDTO object and a new Nutritions DTO object
            FruitDTO fruit = new FruitDTO();
            FruitDTO.Nutritions nutrition = new FruitDTO.Nutritions();
            //FruitDTO.Nutritions nutrition = new FruitDTO.Nutritions();
        // then fill in the properties for each item with the setter methods
            fruit.setGenus("Fragaria");
            fruit.setName("Blackberry");
            fruit.setFamily("Rosaceae");
            fruit.setOrder("Rosales");
            nutrition.setCarbohydrates(5.5);
            nutrition.setProtein(0);
            nutrition.setFat(0.4);
            nutrition.setCalories(29);
            nutrition.setSugar(5.4);
        return fruit;
    }

    public static String fruitToJSON(FruitDTO fruit) throws JsonProcessingException {
        // TODO: convert FruitDTO to JSON using the Jackson library
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String fruitStr = objectMapper.writeValueAsString(fruit);
        return fruitStr;
    }

    public String putFruit(String fruitStr, String url) {
        // TODO: make a PUT request with the data, and return the response
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(fruitStr))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }
}
