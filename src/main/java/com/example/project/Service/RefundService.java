package com.example.project.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.example.project.Dto.StatDto;
import com.example.project.Repository.RefundRepository;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefundService {
    
    private final RefundRepository refundRepository;

    private final EntityManager em;

    @Transactional
    public List<StatDto> RefundGet(int year){
        

        String jpql = "select date, total "+
                       "from ( "+
                       "select "+
                       "date_format(cc.day, '%Y-%m') date, "+
                       "ifnull(sum(amount),0) as total "+
                       "from calendar cc "+
                       "left join "+
                       "refund r on r.refund_date = cc.day "+
                       "group by date "+
                       ") a "+
                       "where "+
                       "date like concat ('%' , '2022', '%')";
                       


        Query nativeQuery = em.createNativeQuery(jpql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
      
        List<StatDto> stat = jpaResultMapper.list(nativeQuery, StatDto.class);

        return stat;

    }
}
