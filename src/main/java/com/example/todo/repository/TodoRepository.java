package com.example.todo.repository;

import java.util.*;
import com.example.todo.model.Todo;

public interface TodoRepository {
    ArrayList<Todo> getAllTodos();

    Todo getTodoById(int id);

    Todo addTodo(Todo todoObj);

    Todo updateTodo(int id, Todo todoObj);

    void deleteTodo(int id);
}