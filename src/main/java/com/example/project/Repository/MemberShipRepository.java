package com.example.project.Repository;

import java.util.List;

import com.example.project.Entity.MemberShip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

    MemberShip findOneById(Long id);
}
