package com.yogesh.todo.service.impl;

import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.entity.Todo;
import com.yogesh.todo.repository.TodoRepository;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        // Convert TodoDto into Todo Jpa Entity
        Todo todo = modelMapper.map(todoDto,Todo.class);

        Todo savedDto = todoRepository.save(todo);

        // Convert savedDto Todo Jpa entity object into TodoDto Object
        TodoDto savedTodoDto = modelMapper.map(savedDto,TodoDto.class);

        return savedTodoDto;
    }
}
