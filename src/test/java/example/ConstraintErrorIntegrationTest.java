package example;

import java.net.URI;

import errorhandler.api.ApiError;
import errorhandler.api.ApiErrorResponse;
import example.api.SimpleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConstraintErrorIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testNumberTooHigh() {

        SimpleRequest request = new SimpleRequest(11);

        RequestEntity<SimpleRequest> req = new RequestEntity<>(request, new HttpHeaders(), HttpMethod.POST, URI.create("/example/simple"));
        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(req, ApiErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());

        ApiError error = response.getBody().getErrors().get(0);
        assertEquals("The number must not be more than 10", error.getErrorMessage());
    }
}
