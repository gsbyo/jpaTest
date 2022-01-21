package com.example.project.Repository;

import java.util.List;

import javax.transaction.Transactional;
import com.example.project.Entity.Member;
import com.example.project.Entity.Payment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;



@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>  {
    
    Payment findFirstByMOrderByIdDesc(Member m);

    Payment findOneByM(Member m);

    List<Payment> findAllByM(Member m);

    Payment findOneById(Long id);



}
