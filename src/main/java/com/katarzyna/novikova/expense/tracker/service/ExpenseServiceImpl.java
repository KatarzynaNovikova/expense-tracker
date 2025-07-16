package com.katarzyna.novikova.expense.tracker.service;

import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private Map<Long, ExpenseDTO> expenses = new HashMap<>();
    private long id;

    @Override
    public long add(ExpenseDTO expenseDTO) {
        expenses.put(++id, expenseDTO);
        return id;
    }

    @Override
    public Optional<ExpenseDTO> get(long id) {
        if (expenses.containsKey(id)) {
            return Optional.of(expenses.get(id));
        }
        return Optional.empty();
    }
}
