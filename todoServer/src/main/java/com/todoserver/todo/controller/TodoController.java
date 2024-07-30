package com.todoserver.todo.controller;

import com.todoserver.todo.domain.Todo;
import com.todoserver.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "Todo 저장", description = "Todo를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "성공적으로 저장했습니다."),
            @ApiResponse(responseCode = "400",description = "중복값입니다.")
    })
    @PostMapping
    TodoForm save(@RequestBody TodoForm todoId) {
        todoService.save(todoId.getTodoId());
        return todoId;
    }

    @GetMapping
    @Operation(summary = "모든 Todo 조회", description = "모든 Todo를 조회합니다.")
    @ApiResponse(responseCode = "200",description = "모든 Todo정보를 가져옵니다.")
    List<Todo> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "선택한 Todo 조회", description = "선택한 Todo를 조회합니다.")
    @ApiResponse(responseCode = "200",description = "검색한 Todo를 가져옵니다.")
    Todo findOne(@PathVariable("id") String id) {
        return todoService.findOne(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Todo 삭제", description = "해당 Todo를 삭제합니다.")
    @ApiResponse(responseCode = "200",description = "해당 Todo를 삭제합니다.")
    String delete(@PathVariable("id") String id) {
        return todoService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "모든 Todo 삭제", description = "모든 Todo를 삭제합니다.")
    @ApiResponse(responseCode = "200",description = "모든 Todo를 삭제합니다.")
    void deleteAll() {
        todoService.deleteAll();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Todo 변경", description = "Todo의 완료 정보를 변경합니다.")
    @ApiResponse(responseCode = "200",description = "Todo의 완료 정보를 변경합니다.")
    String toggleTodo(@PathVariable("id") String id) {
        return todoService.toggleTodo(id);
    }
}
