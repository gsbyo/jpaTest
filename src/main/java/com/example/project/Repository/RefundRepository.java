package com.example.project.Repository;

import com.example.project.Entity.Refund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long>{
    
}
