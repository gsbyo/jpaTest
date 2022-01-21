package com.example.project.Entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  

    @Column ( nullable = false) 
    private long m_id;

    @Column ( nullable = false)
    private int amount;

    @Column ( nullable = false)
    private Date refund_date;

    
    public Refund(long m_id, int amount, Date refund_date){
        this.m_id = m_id;
        this.amount = amount;
        this.refund_date = refund_date;
    }


}
