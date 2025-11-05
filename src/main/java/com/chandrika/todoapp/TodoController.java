package com.chandrika.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // Display list of todos
    @GetMapping("/")
    public String home(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "index"; // this will look for templates/index.html   
    }
    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        Todo todo = new Todo(title, false);
        todoRepository.save(todo);
        return "redirect:/";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }
    
    @GetMapping("/complete/{id}")
    public String completeTodo(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setCompleted(true);
            todoRepository.save(todo);
        }
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editTodo(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElse(null);
        model.addAttribute("todo", todo);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @RequestParam String title) {
        Todo todo = todoRepository.findById(id).orElse(null);
        todo.setTitle(title);
        todoRepository.save(todo);
        return "redirect:/";
    }


}
