package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.project.Entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long>  {
   
  Member findOneById(long id);

  List<Member> findByName(String name);

  List<Member> findByNameContaining(String name);

  //결제를 하지않거나 만료가 된 고객이라면
  //결제테이블에 없는 조건과 날짜가 오늘기준으로 만료가 된 고객을 가져와야겟네 ?


  //그리고 검색어와 이름이 왼쪽정렬로 일치하게 검색함.
  



}
