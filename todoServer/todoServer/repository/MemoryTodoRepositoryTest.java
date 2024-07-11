package com.example.SpringPlayground.todoServer.repository;

import com.example.SpringPlayground.todoServer.domain.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryTodoRepositoryTest {

    MemoryTodoRepository repository = new MemoryTodoRepository();

    @AfterEach
    public void afterEach() { repository.deleteAll();}

    @Test
    public void 투두_추가() throws Exception{
        // given
        Todo todo = Todo.createTodo("hello");
        //when
        repository.save(todo);
        Todo result = repository.findOne(todo.getText());
        //then
        System.out.println("result.getText() = " + result.getText());
        assertThat(todo).isEqualTo(result);
    }

    @Test
    public void 모든_투두_조회_크기2() throws Exception {
        //given
        Todo todo1 = Todo.createTodo("hello1");
        Todo todo2 = Todo.createTodo("hello2");
        //when
        repository.save(todo1);
        repository.save(todo2);
        //then
        List<Todo> todoList = repository.findAll();
        assertThat(todoList.size()).isEqualTo(2);
    }

    @Test
    public void 투두_삭제() throws Exception {
        //given
        Todo todo1 = Todo.createTodo("hello1");
        Todo todo2 = Todo.createTodo("hello2");
        //when
        repository.save(todo1);
        repository.save(todo2);
        repository.delete(todo1.getText());
        //then
        List<Todo> todoList = repository.findAll();
        Todo remainTodo = todoList.get(0);
        System.out.println("remainTodo.getText() = " + remainTodo.getText());
        assertThat(todoList.size()).isEqualTo(1);
        assertThat(remainTodo).isEqualTo(todo2);
    }

    @Test
    public void 투두_상태변경() throws Exception {
        //given
        Todo todo1 = Todo.createTodo("hello1"); // 기본값은 false
        //when
        repository.save(todo1);
        todo1.toggleTodo();
        repository.save(todo1);
        Todo toggledTodo = repository.findOne(todo1.getText());
        System.out.println("toggledTodo.getIsCheck() = " + toggledTodo.getIsCheck());
        //then
        assertThat(toggledTodo.getIsCheck()).isEqualTo(true);
    }
}