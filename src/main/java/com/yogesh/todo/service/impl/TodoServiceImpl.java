package com.yogesh.todo.service.impl;

import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.entity.Todo;
import com.yogesh.todo.exception.ResourceNotFoundException;
import com.yogesh.todo.repository.TodoRepository;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));

        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo,TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateDoto(TodoDto todoDto, Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedTodo = todoRepository.save(todo);



        return modelMapper.map(savedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setCompleted(Boolean.TRUE);

        Todo savedTodo = todoRepository.save(todo);

        return modelMapper.map(savedTodo,TodoDto.class);
    }
}
