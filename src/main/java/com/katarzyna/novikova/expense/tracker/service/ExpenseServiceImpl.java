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

    @Override
    public Map<Long, ExpenseDTO> getAll() {
        return expenses;
    }

    @Override
    public void delete(long id) {
        expenses.remove(id);
    }

    @Override
    public Optional<ExpenseDTO> update(long id, ExpenseDTO expenseDTO) {
        if (expenses.containsKey(id)) {
            expenses.put(id, expenseDTO);
            return Optional.ofNullable(expenses.get(id));
        }
        return Optional.empty();
    }
}
