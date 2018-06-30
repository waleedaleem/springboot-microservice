package com.walid.spring.view;

import com.walid.spring.model.Todo;
import com.walid.spring.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "TODO API", description = "TODO CRUD Endpoints API")
@RestController
@RequestMapping("/users")
public class TodoController {

    @Autowired
    TodoService todoService;

    @ApiOperation(value = "Retrieves TODOs for a specific user", response = List.class)
    @GetMapping(value = "/{name}/todos", produces = "application/json")
    public List<Todo> retrieveTodos(@PathVariable String name) {
        return todoService.retrieveTodos(name);
    }

    @ApiOperation(value = "Retrieves a specific TODO", response = Todo.class)
    @GetMapping(path = "/{name}/todos/{id}")
    public Todo retrieveTodo(@PathVariable String name, @PathVariable int id) {
        return todoService.retrieveTodo(id);
    }

    @ApiOperation(value = "Adds a new TODO", response = Todo.class)
    @PostMapping("/{name}/todos")
    ResponseEntity add(@PathVariable String name, @Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.addTodo(name, todo.getDesc(), todo.getTargetDate(), todo.isDone());
        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTodo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
