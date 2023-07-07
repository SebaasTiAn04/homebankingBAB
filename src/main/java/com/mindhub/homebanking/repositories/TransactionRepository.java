package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value="SELECT * FROM transaction WHERE account_id = :id AND date BETWEEN :start AND :end", nativeQuery = true)
    public List<Transaction> findByDateBetween(@Param("id") Long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
