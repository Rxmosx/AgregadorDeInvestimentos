package tech.buildrun.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BillingAddressService  {


    public JsonNode getResponseCep (String cep) {

        String url = "https://viacep.com.br/ws/";

        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + cep + "/json/")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.body());


            return root;

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }





}
