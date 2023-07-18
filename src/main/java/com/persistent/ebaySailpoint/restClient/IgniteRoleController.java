package com.persistent.ebaySailpoint.restClient;

import com.persistent.ebaySailpoint.restClient.Role.Role;
import com.persistent.ebaySailpoint.restClient.Role.RoleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/roles")
public class IgniteRoleController extends BaseController{
    private String BASE_URL;

    @Autowired
    public IgniteRoleController(
            @Value("${authBaseURL}")String AuthURL,
            @Value("${roleBaseURL}") String baseUrl
    ) throws IOException, InterruptedException, URISyntaxException {
        super(AuthURL);
        BASE_URL = baseUrl;
    }

    @GetMapping()
    public Map GetRoles(
            @RequestParam(name = "sortBy", required = false) String SortBy,
            @RequestParam(name = "attributes", required = false) String attributes,
            @RequestParam(name = "cursor", required = false) String cursor,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "sortOrder", required = false) String sortOrder,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken
    ) throws URISyntaxException, IOException, InterruptedException {


        if(authToken!=null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String base64decoded = authToken.split(" ")[1];
        System.out.println(base64decoded);

//        String authToken = login(username,password);
        String targetUrl = BASE_URL + "?";

        targetUrl = limit != null ? (targetUrl + "limit=" + limit) : targetUrl;
        targetUrl = cursor != null ? (targetUrl + "cursor=" + cursor) : targetUrl;
        targetUrl = filter != null ? (targetUrl + "filter=" + filter) : targetUrl;
        targetUrl = attributes != null ? (targetUrl + "attributes=" + attributes) : targetUrl;
        targetUrl = sortOrder != null ? (targetUrl + "sortOrder=" + sortOrder) : targetUrl;
        targetUrl = SortBy != null ? (targetUrl + "SortBy=" + SortBy) : targetUrl;

        Map result = HttpGet(targetUrl,authToken);
        logout(authToken);

        return result;
    }

    @GetMapping(path = "{roleId}")
    public Map<String, Object> GetRoleByRoleId(
            @PathVariable("roleId") String roleId,
            @RequestParam(name = "attributes", required = false) String attributes,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String targetUrl = BASE_URL + roleId + "?";

        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        targetUrl = attributes != null ? (targetUrl + "attributes=" + attributes) : targetUrl;


        Map<String, Object> result = HttpGet(targetUrl,authToken);
        logout(authToken);

        return result;

    }

    @GetMapping(path = "{roleId}/users")
    public Map<String, Object> GetUsersByRoleId(
            @PathVariable("roleId") String roleId,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "cursor", required = false) String cursor,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sortOrder", required = false) String sortOrder,
            @RequestParam(name = "SortBy", required = false) String SortBy,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL + roleId + "/users?";

        targetUrl = limit != null ? (targetUrl + "limit=" + limit) : targetUrl;
        targetUrl = cursor != null ? (targetUrl + "cursor=" + cursor) : targetUrl;
        targetUrl = filter != null ? (targetUrl + "filter=" + filter) : targetUrl;
        targetUrl = sortOrder != null ? (targetUrl + "sortOrder=" + sortOrder) : targetUrl;
        targetUrl = SortBy != null ? (targetUrl + "SortBy=" + SortBy) : targetUrl;

        Map<String, Object> result = HttpGet(targetUrl,authToken);
        logout(authToken);

        return result;
    }

    @GetMapping(path = "{roleId}/entitlements")
    public Map<String, Object> GetEntitlementsByRoleId(
            @PathVariable("roleId") String roleId,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        String targetUrl = BASE_URL + roleId + "/entitlements";

        Map<String, Object> result = HttpGet(targetUrl,authToken);
        logout(authToken);

        return result;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Map<String, Object> PostRole(
            @RequestBody RoleWrapper roles,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        for (Role role : roles.getRoles()) {
            if (role.getRoleUniqueName() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Role Unique Name");
            }
        }
        URI targetUri = new URI(BASE_URL);
        String serialisedPersonWrapper = objectMapper.writeValueAsString(roles);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(serialisedPersonWrapper);
        HttpRequest httpRequest = HttpRequest
                .newBuilder(targetUri)
                .header("Accept", "application/json")
                .headers("Content-type", "application/json")
                .header("Authorization", authToken)
                .POST(bodyPublisher)
                .build();

        Map result = handleRequest(httpRequest);
        logout(authToken);
        return result;
    }

    @PutMapping
    public Map<String, Object> EditRole(
            @RequestBody RoleWrapper roles,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);

        for (Role role : roles.getRoles()) {
            if (role.getRoleKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Role key");
            }
        }
        URI targetUri = new URI(BASE_URL);
        String serialisedPersonWrapper = objectMapper.writeValueAsString(roles);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(serialisedPersonWrapper);
        HttpRequest httpRequest = HttpRequest
                .newBuilder(targetUri)
                .header("Accept", "application/json")
                .headers("Content-type", "application/json")
                .header("Authorization", authToken)
                .POST(bodyPublisher)
                .build();

        Map result = handleRequest(httpRequest);
        logout(authToken);
        return result;
    }

    @DeleteMapping(path = "{roleId}")
    public Map<String, Object> DeleteRoleById(
            @PathVariable(name = "roleId") String roleId,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        System.out.println("Delete method");
        String targetUri = BASE_URL + roleId;
        URI uri = new URI(targetUri);

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .headers("Content-type", "application/json")
                .header("Authorization", authToken)
                .DELETE()
                .build();

        Map result =handleRequest(httpRequest);
        logout(authToken);
        return result;
    }

    @DeleteMapping
    public Map<String, Object> DeleteRoles(
            @RequestBody RoleWrapper roles,
            @RequestHeader()Map<String ,String > headers
    ) throws URISyntaxException, IOException, InterruptedException {
        String username = headers.get("username");
        String password = headers.get("password");
        if(username==null||password==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String authToken = login(username,password);
        for (Role role : roles.getRoles()) {
            if (role.getRoleKey() == null || role.getRoleUniqueName() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Role key or role unique name");
            }
        }
        URI targetUri = new URI(BASE_URL);
        String serialisedPersonWrapper = objectMapper.writeValueAsString(roles);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(serialisedPersonWrapper);
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(targetUri)
                .header("Accept", "application/json")
                .headers("Content-type", "application/json")
                .header("Authorization", authToken)
                .method("DELETE", bodyPublisher)
                .build();

        Map result=handleRequest(httpRequest);
        logout(authToken);
        return result;
    }
}
