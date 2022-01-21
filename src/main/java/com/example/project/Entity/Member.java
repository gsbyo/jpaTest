package com.example.project.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class Member {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  // 회원 아이디

    @Column ( nullable = false, length = 20 ) // 이름
    private String name; 

    @Column  ( nullable = false, length = 1 ) // 성별
    private String gender;

    @Column  ( nullable = false ) // 나이
    private int age;

    @Column  ( nullable = true, length = 20 ) // 전화번호
    private String phone_number; 

    @OneToMany  // 회원이 결제한 내역은 여러개 있을수도 있는거임.
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private List<Payment> payment; 


    public Member(String name, String gender, int age, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone_number = phoneNumber;
    }

    public void Edit(String name, String gender, int age, String phoneNumber){
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone_number = phoneNumber;
    }

    

   

   
}
