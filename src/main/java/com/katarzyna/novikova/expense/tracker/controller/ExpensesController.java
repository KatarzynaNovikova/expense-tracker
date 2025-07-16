package com.katarzyna.novikova.expense.tracker.controller;

import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import com.katarzyna.novikova.expense.tracker.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("expense details: {}", expense);
        expenseService.add(expense);
        return ResponseEntity.status(200).build();
    }

}
