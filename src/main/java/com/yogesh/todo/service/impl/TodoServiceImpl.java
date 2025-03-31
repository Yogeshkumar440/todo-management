package com.yogesh.todo.service.impl;

import com.yogesh.todo.dto.TodoDto;
import com.yogesh.todo.entity.Todo;
import com.yogesh.todo.exception.ResourceNotFoundException;
import com.yogesh.todo.repository.TodoRepository;
import com.yogesh.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        logger.info("Adding a new todo: {} ",todoDto.getTitle());

        // Convert TodoDto into Todo Jpa Entity
        Todo todo = modelMapper.map(todoDto,Todo.class);

        Todo savedDto = todoRepository.save(todo);

        // Convert savedDto Todo Jpa entity object into TodoDto Object
        TodoDto savedTodoDto = modelMapper.map(savedDto,TodoDto.class);

        logger.info("Todo added successfully with ID: {}", savedDto.getId());
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        logger.info("Fetching todo with ID: {}",id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()->{
                    logger.error("Todo not found with ID: {} ",id);
                    return new ResourceNotFoundException("Todo not found with id: "+id);
                });

        logger.info("Todo fetched successfully: {}", todo.getTitle());
        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        logger.info("Fetching all todos");
        List<Todo> todos = todoRepository.findAll();
        List<TodoDto> todoDtos = todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
                .toList();
        logger.info("Total todos fetched: {}",todoDtos.size());
        return todoDtos;
    }

    @Override
    public TodoDto updateDoto(TodoDto todoDto, Long id) {

        logger.info("Updating todo with ID: {}",id);

        Todo todo = todoRepository.findById(id).orElseThrow(() -> {
                    logger.info("Todo not found with ID: {}",id);
                    return new ResourceNotFoundException("Todo not found with id: " + id);
                });

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedTodo = todoRepository.save(todo);

        logger.info("Todo updated successfully: {}",savedTodo.getTitle());

        return modelMapper.map(savedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        logger.info("Deleting todo with id: {}", id);

        Todo todo = todoRepository.findById(id).orElseThrow(() -> {
            logger.error("Todo not found with ID: {}",id);
            return new ResourceNotFoundException("Todo not found with id: " + id);
        });
        todoRepository.deleteById(id);
        logger.info("Todo with ID: {} deleted successfully",id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        logger.info("Marking todo as complete with ID: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Todo not found with ID: {}", id);
                    return new ResourceNotFoundException("Todo not found with id: " + id);
                });

        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo = todoRepository.save(todo);
        logger.info("Todo with ID: {} marked as completed", id);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        logger.info("Marking todo as incomplete with ID: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Todo not found with ID: {}", id);
                    return new ResourceNotFoundException("Todo not found with id: " + id);
                });

        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);
        logger.info("Todo with ID: {} marked as incomplete", id);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }
}
