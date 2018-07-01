package com.walid.spring.view;

import com.walid.spring.SpringBootMicroserviceApplication;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerIT {

    private static final String LOCAL_HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new TestRestTemplate(username, password);
    }

    @Test
    public void welcome() {
        ResponseEntity<String> response = template.getForEntity(createURL("/welcome/name/Walid"), String.class);
        assertThat(response.getBody(), equalTo("Welcome Walid!"));
    }

    @Test
    public void welcomeWithBean() throws JSONException {
        ResponseEntity<String> response = template.getForEntity(createURL("/welcome/object/name/Walid"), String.class);

        String expected = "{\"message\": \"Welcome Walid!\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURL(String uri) {
        return LOCAL_HOST + port + uri;
    }
}