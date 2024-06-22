package com.example.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.model.TodoRowMapper;

@Service
public class TodoH2Service implements TodoRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Todo> getAllTodos() {
        List<Todo> todosList = db.query("select * from TODOLIST", new TodoRowMapper());
        ArrayList<Todo> allTodos = new ArrayList<>(todosList);
        return allTodos;
    }

    @Override
    public Todo getTodoById(int id) {
        try {
            Todo todoObj = db.queryForObject("select * from TODOLIST where id= ?", new TodoRowMapper(), id);
            return todoObj;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Todo addTodo(Todo todoObj) {
        db.update("insert into TODOLIST(todo, priority, status) values (?, ?, ?)", todoObj.getTodo(),
                todoObj.getPriority(), todoObj.getStatus());
        Todo savedTodo = db.queryForObject("select * from TODOLIST where todo= ? and priority= ? and status= ?", new TodoRowMapper(),
                todoObj.getTodo(), todoObj.getPriority(), todoObj.getStatus());
        return savedTodo;
    }

    @Override
    public Todo updateTodo(int id, Todo todoObj) {
        if (todoObj.getTodo() != null) {
            db.update("update TODOLIST set todo= ? where id= ?", todoObj.getTodo(), id);
        }
        if (todoObj.getPriority() != null) {
            db.update("update TODOLIST set priority= ? where id= ?", todoObj.getPriority(), id);
        }
        if (todoObj.getStatus() != null) {
            db.update("update TODOLIST set status= ? where id= ?", todoObj.getStatus(), id);
        }
        return getTodoById(id);

    }

    @Override
    public void deleteTodo(int id) {
        db.update("delete from TODOLIST where id= ?", id);
        
        
    }

}

