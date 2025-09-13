package com.katarzyna.novikova.expense.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import com.katarzyna.novikova.expense.tracker.service.ExpenseServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpensesController.class)
class ExpensesControllerTest {

    @MockitoBean
    private ExpenseServiceImpl expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Successfully create new expense and add it to expenseService")
    void createExpenseSuccessfullyAddExpenseToExpenseService() throws Exception {
        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");
        String requestBody = objectMapper.writeValueAsString(expenseDTO);

        long id = 1;
        when(expenseService.add(expenseDTO)).thenReturn(id);

        // When Then
        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/expenses/1"));
    }

    @Test
    @DisplayName("Successfully get existing expense by id")
    void getExpenseSuccessfullyForExistingExpense() throws Exception {

        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");

        long id = 1;
        when(expenseService.get(1)).thenReturn(Optional.of(expenseDTO));

        // When Then
        mockMvc.perform(get("/expenses/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expenseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expenseDTO.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(expenseDTO.getCategory()));
    }

    @Test
    @DisplayName("Fail to get non existing expense and return 'not found'")
    void failedToGetNonExistingExpense() throws Exception {
        // Given
        long id = 1;
        when(expenseService.get(1)).thenReturn(Optional.empty());

        // When Then
        mockMvc.perform(get("/expenses/{id}", id)).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successfully get all existing expenses")
    void getAllExpenses() throws Exception {
        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");

        ExpenseDTO expenseDTO2 = new ExpenseDTO();
        expenseDTO2.setName("Pants");
        expenseDTO2.setAmount(299.9);
        expenseDTO2.setCategory("Clothes");

        long id = 1;
        long id2 = 2;
        Map<Long, ExpenseDTO> expenses = new LinkedHashMap<>();
        expenses.put(id, expenseDTO);
        expenses.put(id2, expenseDTO2);
        when(expenseService.getAll()).thenReturn(expenses);

        // When Then
        mockMvc.perform(get("/expenses")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.1").value(expenseDTO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2").value(expenseDTO2));
    }

    @Test
    @DisplayName("Successfully remove existing expense by id")
    void deleteExpenseById() throws Exception {
        // Given

        long id = 1;

        // When Then
        mockMvc.perform(delete("/expenses/{id}", id))
                .andExpect(status().isNoContent());
        verify(expenseService).delete(1);
    }

    @Test
    @DisplayName("Fail to delete non existing expense and return 'no content")
    void failedToDeleteNonExistingExpenseById() throws Exception {
        // Given

        long id = 1;

        // When Then
        mockMvc.perform(delete("/expenses/{id}", id))
                .andExpect(status().isNoContent());
        verify(expenseService).delete(1);
    }

    @Test
    @DisplayName("Successfully update existing expense")
    void updateExistingExpense() throws Exception {
        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");
        String requestBody = objectMapper.writeValueAsString(expenseDTO);   // converts Java object to json written in format String
        long id = 1;


        when(expenseService.update(id, expenseDTO)).thenReturn(Optional.of(expenseDTO));

        // When Then
        mockMvc.perform(put("/expenses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expenseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expenseDTO.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(expenseDTO.getCategory()));
    }

    @Test
    @DisplayName("Attempt to update non existing expense and return 'not found' code")
    void attemptToUpdateNonExistingExpenseReturnsNotFound() throws Exception {
        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");
        String requestBody = objectMapper.writeValueAsString(expenseDTO);   // converts Java object to json written in format String
        long id = 1;


        when(expenseService.update(id, expenseDTO)).thenReturn(Optional.empty());

        // When Then
        mockMvc.perform(put("/expenses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isNotFound());
    }
}
