package com.katarzyna.novikova.expense.tracker.controller;

import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import com.katarzyna.novikova.expense.tracker.service.ExpenseServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensesControllerTest {

    @MockitoBean
    private ExpenseServiceImpl expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createExpense() {
    }

    @Test
    @DisplayName("Successfully get existing expense by id")
    void getExpenseSuccessfullyForExistingExpense() throws Exception {

        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");

        long id = 1;
        when(expenseService.get(1)).thenReturn(Optional.of(expenseDTO));

        mockMvc.perform(get("/expenses/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").exists());
    }

    @Test
    void getAllExpenses() {
    }

    @Test
    void deleteExpense() {
    }

    @Test
    void updateExpense() {
    }
}