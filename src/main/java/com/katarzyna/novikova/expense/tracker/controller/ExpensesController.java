package com.katarzyna.novikova.expense.tracker.controller;

import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import com.katarzyna.novikova.expense.tracker.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    // responsible for HTTP requests

    private final ExpenseService expenseService;

    @Autowired
    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseDTO expense) {
        log.info("expense details: {}", expense);   // wyświetla informacje w log file (na serwerze lub w konsoli lokalnie)
        long id = expenseService.add(expense);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable long id) {
        Optional<ExpenseDTO> expense = expenseService.get(id);
        return ResponseEntity.of(expense);
    }

}
