package com.walid.spring.service;

import com.walid.spring.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    private static int todoCount = 3;

    static {
        todos.add(new Todo(1, "Walid", "Learn Spring MVC",
                new Date(), false));
        todos.add(new Todo(2, "Walid", "Learn Struts", new Date(),
                false));
        todos.add(new Todo(3, "Wally", "Learn Hibernate", new Date(),
                false));
    }

    public List<Todo> retrieveTodos(String user) {
        return todos.stream()
                .filter(todo -> todo.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public Todo addTodo(String name, String desc, Date targetDate, boolean isDone) {
        return new Todo(++todoCount, name, desc, targetDate, isDone) {
            {
                todos.add(this);
            }
        };
    }

    public Todo retrieveTodo(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findAny()
                .orElse(null);
    }
}