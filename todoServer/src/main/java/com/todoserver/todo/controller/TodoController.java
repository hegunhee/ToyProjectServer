package com.todoserver.todo.controller;

import com.todoserver.todo.domain.Todo;
import com.todoserver.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/todo",produces="application/json")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    TodoForm save(@RequestBody TodoForm todoId) {
        todoService.save(todoId.getTodoId());
        return todoId;
    }

    @GetMapping
    List<Todo> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    Todo findOne(@PathVariable("id") String id) {
        return todoService.findOne(id);
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable("id") String id) {
        return todoService.delete(id);
    }

    @DeleteMapping
    void deleteAll() {
        todoService.deleteAll();
    }

    @PatchMapping("/{id}")
    String toggleTodo(@PathVariable("id") String id) {
        return todoService.toggleTodo(id);
    }
}
