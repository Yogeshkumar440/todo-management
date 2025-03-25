package com.yogesh.todo.controller;


import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //Build Add Todo REST API
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodo = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    // Build Get All Todo REST API
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updatedTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.updateDoto(todoDto, todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    // Build Delete Todo REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo Deleted Successfully!");
    }

    // Build Complete Todo REST API
    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
        TodoDto completedTodo = todoService.completeTodo(todoId);
        return ResponseEntity.ok(completedTodo);
    }

}
