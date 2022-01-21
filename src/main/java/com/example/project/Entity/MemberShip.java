package com.example.project.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "membership")
public class MemberShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 회원권 종류

    @Column (nullable = false)
    private String name;


    @Column (nullable = false)
    private int month_len;


    @Column (nullable = false)
    private int price;

    public MemberShip(String membership_name, int month_len, int price){
        this.name = membership_name;
        this.month_len = month_len;
        this.price = price;
    }



}
