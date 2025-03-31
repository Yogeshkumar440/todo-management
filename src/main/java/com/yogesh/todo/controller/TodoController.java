package com.yogesh.todo.controller;


import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;

    //Build Add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto){
        logger.info("Creating a new TODO: {} ",todoDto.getTitle());
        TodoDto savedTodo = todoService.addTodo(todoDto);
        logger.info("Todo '{}' created successfully with ID: {}",savedTodo.getTitle(),savedTodo.getId());
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        logger.info("Fetching TODO with ID: {}",todoId);
        TodoDto todoDto = todoService.getTodo(todoId);
        logger.info("Fetched TODO: {} ",todoDto);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    // Build Get All Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        logger.info("Fetching all TODOs");
        List<TodoDto> todos = todoService.getAllTodos();
        logger.info("Total {} TODOs fetched",todos.size());
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updatedTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
        logger.info("Updating TODO with ID: {}",todoId);
        TodoDto updatedTodo = todoService.updateDoto(todoDto, todoId);
        logger.info("TODO with ID {} updated successfully", updatedTodo.getId());
        return ResponseEntity.ok(updatedTodo);
    }

    // Build Delete Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
        logger.warn("Deleting TODO with ID: {} ",todoId);
        todoService.deleteTodo(todoId);
        logger.info("TODO with ID {} deleted successfully",todoId);
        return ResponseEntity.ok("Todo Deleted Successfully!");
    }

    // Build Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
        logger.info("Marking TODO with ID {} as completed",todoId);
        TodoDto updatedTodo = todoService.completeTodo(todoId);
        logger.info("TODO with ID {} marked as complete", updatedTodo.getId());
        return ResponseEntity.ok(updatedTodo);
    }

    // Build inComplete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId){
        logger.info("Marking TODO with ID {} as incomplete",todoId);
        TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
        logger.info("TODO with ID {} marked as incomplete",updatedTodo.getId());
        return ResponseEntity.ok(updatedTodo);

    }

}
