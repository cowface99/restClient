package com.persistent.ebaySailpoint.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected String token;
    private  String AuthURL;
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    public BaseController(String AuthURL) {
        this.AuthURL=AuthURL;
    }
    protected String login(String username,String password) throws URISyntaxException, IOException, InterruptedException {

        String formData = "username="+ URLEncoder.encode(username, StandardCharsets.UTF_8)+"&password="+URLEncoder.encode(password,StandardCharsets.UTF_8);
        HttpRequest.BodyPublisher bodyPublisher =
                HttpRequest.BodyPublishers.ofString(formData);

        URI targetUri = new URI(AuthURL+"login");
        HttpRequest httpRequest = HttpRequest
                .newBuilder(targetUri)
                .header("Accept","application/json")
                .header( "Content-type","application/x-www-form-urlencoded")
                .POST(bodyPublisher)
                .build();

        HttpResponse< String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        HashMap<String,Object> result = objectMapper.readValue(response.body(),HashMap.class);
        if(result.get("token")==null){
            System.exit(1);
        }
        return result.get("token").toString();
    }
    protected void logout(String authtoken) throws URISyntaxException, IOException, InterruptedException {
       URI targetUri = new URI(AuthURL + "logout");
       HttpRequest httpRequest = HttpRequest
                .newBuilder(targetUri)
               .header("Accept", "application/json")
               .header("Authorization", authtoken)
               .POST(HttpRequest.BodyPublishers.noBody())
               .build();

       Map<String,Object> result=handleRequest(httpRequest);
        System.out.println("Logged out");
    }

    protected Map HttpGet(String uri,String token) throws URISyntaxException, IOException, InterruptedException {

        URI targetUri;
        targetUri = new URI(uri);

        HttpRequest httpRequest = HttpRequest.newBuilder(targetUri).header("Accept", "application/json").header("Authorization", token).build();

        Map result = handleRequest(httpRequest);

        return result;
    }
    protected Map<String,Object> handleRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpResponse<String> response;
        response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        HashMap<String, Object> result;
        result = objectMapper.readValue(response.body(), HashMap.class);
        return result;

    }
}
