package com.yogesh.todo.service.impl;

import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.entity.Todo;
import com.yogesh.todo.repository.TodoRepository;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        // Convert TodoDto into Todo Jpa Entity
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedDto = todoRepository.save(todo);

        // Convert savedDto Todo Jpa entity object into TodoDto Object
        TodoDto savedTodoDto = new TodoDto();
        savedTodoDto.setId(savedDto.getId());
        savedTodoDto.setTitle(savedDto.getTitle());
        savedTodoDto.setDescription(savedDto.getDescription());
        savedTodoDto.setCompleted(savedDto.isCompleted());

        return savedTodoDto;
    }
}
