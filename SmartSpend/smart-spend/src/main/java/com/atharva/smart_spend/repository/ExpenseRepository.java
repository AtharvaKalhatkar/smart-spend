package com.atharva.smart_spend.repository;

import com.atharva.smart_spend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Custom query to find expenses between two dates
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
