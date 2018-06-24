package com.walid.spring.view;

import com.walid.spring.SpringBootMicroserviceApplication;
import com.walid.spring.model.Todo;
import com.walid.spring.service.TodoService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

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

    @Test
    public void addTodo() throws Exception {
        Todo todo = new Todo(TodoService.getTodoCount() + 1, "Wally", "Learn Hibernate", new Date(), false);
        URI location = template.postForLocation(createUrl("/users/Wally/todos"), todo);
        assertThat(location.getPath(), containsString("/users/Wally/todos/" + TodoService.getTodoCount()));
    }

    private String createUrl(String uri) {
        return "http://localhost:" + port + uri;
    }
}