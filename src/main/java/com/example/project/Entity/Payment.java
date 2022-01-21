package com.example.project.Entity;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



/*@SqlResultSetMapping(
        name="ExpiryDateMapping",
        classes = @ConstructorResult(
                targetClass = 
                columns = {
                   
                })
)
@NamedNativeQuery(
        name="GroupByDate",
        query="SELECT " 
        +"",           
        resultSetMapping="")*/



@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "payment")
public class Payment {
    //회원 결제테이블
  
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column ( nullable = false  )
    Date pay_date; //결제일


    @Column ( nullable = false )
    Date expiry_date; //만료일

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    Member m;

    @OneToOne
    @JoinColumn(name = "membership_id")
    MemberShip ms;  //멤버쉽 종류


    public Payment(Date pay_date, Date expiry_date, Member member,   MemberShip membership){
        this.pay_date = pay_date;
        this.expiry_date = expiry_date;
        this.m = member;
        this.ms = membership;
    }

    public void Add_expiryDate(int Add_Date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.expiry_date);
        cal.add(Calendar.DATE, Add_Date);

        this.expiry_date = cal.getTime();
    }

    public void Now_DaySet(){
        Date now = new Date();
        this.expiry_date = now;        
    }

    
}
