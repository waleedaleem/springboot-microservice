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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerIT {

    @LocalServerPort
    private int port;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    private TestRestTemplate template;
    //private MultiValueMap<String, String> headers;

    @Before
    public void setUp() {
        // set Authorization header manually
        String auth = username + ":" + password;
        /*byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", authHeader);
        headers.add("Content-Type", "application/json");*/
        //Let Spring set Authorization header
        template = new TestRestTemplate(username, password);
        template.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Test
    public void retrieveTodos() throws JSONException {

        String expected = "[" + "{id:1,user:Walid,desc:\"Learn Spring MVC\",done:false}" + ","
                + "{id:2,user:Walid,desc:\"Learn Struts\",done:false}" + "]";

        String uri = "/users/Walid/todos";

        ResponseEntity<String> response = template.getForEntity(createUrl(uri), String.class);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void retrieveTodo() throws Exception {
        String expected = "{id:1,user:Walid,desc:\"Learn Spring MVC\",done:false}";

        String uri = "/users/Walid/todos/1";

        ResponseEntity<String> response = template.getForEntity(createUrl(uri), String.class);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createUrl(String uri) {
        return "http://localhost:" + port + uri;
    }
}