package com.example.project.Dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
public class StatDto {
   private String date;
   private BigDecimal total;
 
   public  StatDto(String date, BigDecimal total) {
       this.date = date;
       this.total = total;
   
   }
}
