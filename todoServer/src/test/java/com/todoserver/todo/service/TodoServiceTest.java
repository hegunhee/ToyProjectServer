package com.todoserver.todo.service;

import com.todoserver.todo.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Test
    public void TODO가_저장되어야함() throws Exception {
        //given
        Todo studyTodo = Todo.createTodo("공부하기");
        todoService.save(studyTodo.getText());
        //when
        Todo result = todoService.findOne(studyTodo.getText());
        //then
        assertThat(studyTodo.getText()).isEqualTo(result.getText());
    }

    @Test
    public void 여러개의_TODO_조회() throws Exception {
        //given
        Todo studyTodo = Todo.createTodo("공부하기");
        Todo englishTodo = Todo.createTodo("영어하기");
        todoService.save(studyTodo.getText());
        todoService.save(englishTodo.getText());
        //when

        Todo result1 = todoService.findOne(studyTodo.getText());
        Todo result2 = todoService.findOne(englishTodo.getText());
        List<Todo> todoList = todoService.findAll();
        //then
        assertThat(2).isEqualTo(todoList.size());
        assertThat(studyTodo.getText()).isEqualTo(result1.getText());
        assertThat(englishTodo.getText()).isEqualTo(result2.getText());
    }

    @Test
    public void TODO_삭제() throws Exception {
        //given
        Todo studyTodo = Todo.createTodo("공부하기");
        Todo englishTodo = Todo.createTodo("영어하기");
        todoService.save(studyTodo.getText());
        todoService.save(englishTodo.getText());
        //when
        todoService.delete(studyTodo.getText());
        List<Todo> todoList = todoService.findAll();
        //then
        assertThat(1).isEqualTo(todoList.size());
    }

    @Test
    public void 전체_삭제() throws Exception {
        //given
        Todo studyTodo = Todo.createTodo("공부하기");
        Todo englishTodo = Todo.createTodo("영어하기");
        todoService.save(studyTodo.getText());
        todoService.save(englishTodo.getText());
        //when
        todoService.deleteAll();

        //then
        List<Todo> result = todoService.findAll();
        assertThat(0).isEqualTo(result.size());

    }

    /**
     *
     * Todo.createTodo의 초기값은 false
     */
    @Test
    public void 투두_토글시_isClear_true값으로변경() throws Exception {
        //given
        Todo studyTodo = Todo.createTodo("공부하기");
        todoService.save(studyTodo.getText());
        //when
        todoService.toggleTodo(studyTodo.getText());

        Todo result = todoService.findOne(studyTodo.getText());
        //then
        assertThat(true).isEqualTo(result.getIsCheck());
    }
}