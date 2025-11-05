package com.chandrika.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        Todo todo = new Todo(title, false);
        todoRepository.save(todo);
        return "redirect:/";
    }

    // ✅ FIXED DELETE
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }

    // Toggle completed
    @PostMapping("/toggle")
    public String toggleTodo(@RequestParam Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return "redirect:/";
    }

    // ✅ FIXED EDIT
    @GetMapping("/edit")
    public String editTodo(@RequestParam Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElse(null);
        model.addAttribute("todo", todo);
        return "edit";
    }

    // ✅ FIXED UPDATE
    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title) {
        Todo todo = todoRepository.findById(id).orElse(null);
        todo.setTitle(title);
        todoRepository.save(todo);
        return "redirect:/";
    }
}
