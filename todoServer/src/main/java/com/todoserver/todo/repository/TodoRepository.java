package com.todoserver.todo.repository;

import com.todoserver.todo.domain.Todo;

import java.util.List;

public interface TodoRepository {

    public List<Todo> findAll();

    public Todo findOne(String id);

    public void save(Todo todo);

    public void delete(String id);

    public void deleteAll();
}
