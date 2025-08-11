package com.katarzyna.novikova.expense.tracker.service;

import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;

import java.util.Map;
import java.util.Optional;

public interface ExpenseService {
    long add(ExpenseDTO expenseDTO);

    Optional<ExpenseDTO> get(long id);

    void delete(long id);

    Map<Long, ExpenseDTO> getAll();

    Optional<ExpenseDTO> update(long id, ExpenseDTO expenseDTO);

}
