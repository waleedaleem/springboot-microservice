package com.walid.spring.view;

import com.walid.spring.model.Todo;
import com.walid.spring.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TodoService service;

    @Test
    public void retrieveTodos() throws Exception {

        List<Todo> mockList = Arrays.asList(new Todo(1, "Walid",
                "Learn Spring MVC", new Date(), false), new Todo(2, "Walid",
                "Learn Struts", new Date(), false));

        when(service.retrieveTodos(anyString())).thenReturn(mockList);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/Walid/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "[" + "{id:1,user:Walid,desc:\"Learn Spring MVC\",done:false}" + ","
                + "{id:2,user:Walid,desc:\"Learn Struts\",done:false}" + "]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createTodo() throws Exception {
        final int CREATED_TODO_ID = 9;
        Todo mockTodo = new Todo(CREATED_TODO_ID, "Walid", "Learn Spring MVC", new Date(), false);
        String todo = "{\"user\":\"Walid\",\"desc\":\"Learn Spring MVC\", \"done\":false}";

        when(service.addTodo(anyString(), anyString(), isNull(), anyBoolean())).thenReturn(mockTodo);

        mvc.perform(
                MockMvcRequestBuilders.post("/users/Walid/todos")
                        .content(todo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/users/Walid/todos/" + CREATED_TODO_ID)));
    }

    @Test
    public void createTodo_withValidationError() throws Exception {
        final int CREATED_TODO_ID = 9;
        Todo mockTodo = new Todo(CREATED_TODO_ID, "Walid", "Learn Spring MVC", new Date(), false);
        String todo = "{\"user\":\"Walid\",\"desc\":\"Learn\", \"done\":false}";

        when(service.addTodo(anyString(), anyString(), isNull(), anyBoolean())).thenReturn(mockTodo);

        mvc.perform(
                MockMvcRequestBuilders.post("/users/Walid/todos")
                        .content(todo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}