import org.springframework.boot.web.server.Cookie;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Application {

    private static final String URL="http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        System.out.println("All users: " + response.getBody());

        String session = response.getHeaders().getFirst("Set-Cookie");
        System.out.println("Set-Cookie: " + session);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", session);
        headers.setContentType(MediaType.APPLICATION_JSON);


        User user1 = new User(3, "James", "Brown", 22);
        HttpEntity<User> add = new HttpEntity<>(user1, headers);
        ResponseEntity<String> addResponse = restTemplate.exchange(URL, HttpMethod.POST, add, String.class);
        System.out.println("Add user: " + addResponse.getBody());

        User user2 = new User(3, "Thomas", "Shelby", 32);
        HttpEntity<User> update = new HttpEntity<>(user2, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(URL, HttpMethod.PUT, update, String.class);
        System.out.println("Update user: " + updateResponse.getBody());

        HttpEntity<User> delete = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, delete, String.class);
        System.out.println("Delete user: " + deleteResponse.getBody());

        String concated = Objects.requireNonNull(addResponse.getBody()).concat(Objects.requireNonNull(updateResponse.getBody()).concat(Objects.requireNonNull(deleteResponse.getBody())));
        System.out.println(concated);
    }
}