package com.example.project.Service;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.example.project.Dto.StatDto;
import com.example.project.Entity.Payment;
import com.example.project.Repository.PaymentRepository;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    private final EntityManager em;



    //steam을 이용 ?
    @Transactional
    public List<Payment> Stat_yearList(int year){
       List<Payment> account = em.createQuery(
           "SELECT p from payment p where data-format(pay_date, '%Y') = "+year+")")
           .getResultList();
           
       return account;
    }

    @Transactional
    public List<StatDto> StatGet(int year){

        String jpql = "select date, total "+
                       "from ( "+
                       "select "+
                       "date_format(cc.day, '%Y-%m') date, "+
                       "ifnull(sum(price),0) as total "+
                       "from calendar cc "+
                       "left join "+
                        "(select pay_date, price from payment p "+
                        "join membership ms on (p.membership_id = ms.id)) pm "+
                        "on (pm.pay_date = cc.day) "+
                        "group by date"+
                        ") a "+
                        "where "+
                        "date like concat ('%' , '2022', '%')";




        Query nativeQuery = em.createNativeQuery(jpql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
      
        List<StatDto> stat = jpaResultMapper.list(nativeQuery, StatDto.class);

        return stat;
   
    }

        

}
