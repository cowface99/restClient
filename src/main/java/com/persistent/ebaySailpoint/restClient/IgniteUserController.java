package com.persistent.ebaySailpoint.restClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class IgniteUserController extends BaseController{
    private final String BASE_URL;
    public IgniteUserController(
            @Value("${authBaseURL}") String AuthURL,
            @Value("${userBaseURL}") String BASE_URL
    ) {
        super(AuthURL);
        this.BASE_URL=BASE_URL;
    }

    @GetMapping
    public Map getUsers(@RequestHeader Map<String,String> headers) throws URISyntaxException, IOException, InterruptedException {

        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL + "?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

    @GetMapping(path = "/{uuid}")
    public Map GetUserById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL +uuid+ "?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

    @GetMapping(path = "/{uuid}/accounts")
    public Map GetUserAccountsById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL +uuid+ "/accounts?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

    @GetMapping(path = "/{uuid}/adminRoles")
    public Map GetUserAdminRolesById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL +uuid+ "/adminRoles?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

    @GetMapping(path = "/{uuid}/applications")
    public Map GetUserApplicationsById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL +uuid+ "/applications?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

    @GetMapping(path = "/{uuid}/approvals")
    public Map GetUserApprovalsById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL +uuid+ "/approvals?";
        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);
        return result;
    }

//    @GetMapping(path = "/{uuid}/avatar",produces = "image/png")
//    public Byte[] GetUserAvatarById(@RequestHeader Map<String,String>headers, @PathVariable("uuid") String uuid) throws URISyntaxException, IOException, InterruptedException {
//        String username = headers.get("username");
//        String password = headers.get("password");
//        if(username==null||password==null){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//
//        String authToken = login(username,password);
//        String targetUrl = BASE_URL +uuid+ "/avatar?";
//
//        URI targetUri = new URI(targetUrl);
//
//        HttpRequest httpRequest = HttpRequest
//                .newBuilder(targetUri)
//                .header("Accept", "image/png")
//                .header("Authorization", authToken)
//                .build();
//
//        HttpResponse response;
//        HttpClient httpClient = HttpClient.newHttpClient();
//        response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
//        Byte[] result = objectMapper.readValue(response.body(),);
//        return result;
//
//
//        HashMap<String, Object> result;
////        result = objectMapper.readValue(response.body(), HashMap.class);
//        logout(authToken);
//        return result;
//    }
}
