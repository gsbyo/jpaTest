package com.example.project.Controller;

import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.project.Dto.StatDto;
import com.example.project.Entity.Member;
import com.example.project.Entity.MemberShip;
import com.example.project.Entity.Payment;
import com.example.project.Entity.Refund;
import com.example.project.Repository.MemberRepository;
import com.example.project.Repository.MemberShipRepository;
import com.example.project.Repository.PaymentRepository;
import com.example.project.Repository.RefundRepository;
import com.example.project.Service.PaymentService;
import com.example.project.Service.RefundService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberShipRepository memberShipRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    private final PaymentService paymentService;
    private final RefundService refundService;
    


    // ???????????? - ??????
    @GetMapping("member")
    public ModelAndView member() {
        ModelAndView mv = new ModelAndView();

        List<Member> member = memberRepository.findAll();

        if(member != null){
            mv.addObject("list", member); 
        }

        return mv;
    }


    @PostMapping("member")
    public void sign(HttpServletRequest req, HttpServletResponse res) throws IOException, ParseException {

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        String age = req.getParameter("age");

         final Member member = Member.builder()
        .name(name)
        .age(Integer.parseInt(age))
        .gender(gender)
        .phone_number(phone)
        .build();

         memberRepository.save(member);

         res.sendRedirect("/member");
    }


    //?????????????????? ???????????? ????????? ????????? ?????????.
    @GetMapping("member/{name}")
    public List<Member> Search(@PathVariable("name") String name){
        
        List<Member> members = memberRepository.findByNameContaining(name);
        
        return members;      
    }

    //??????
    @PutMapping("member/{id}")
    public String Edit(@PathVariable("id") long id, HttpServletRequest req){
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        int age = Integer.parseInt(req.getParameter("age"));

       

       Member member = memberRepository.findOneById(id);

        if(member != null){
            member.Edit(name, gender, age, phone);

            memberRepository.save(member);
        
            return "success";      
        }

      

  
       return "fail";
       
    }


    @DeleteMapping
    public void Del(HttpServletRequest req){
        long id = Integer.parseInt(req.getParameter("id"));
        
        memberRepository.deleteById(id);
    } 



    //????????????
    @GetMapping("payment")
    public ModelAndView payment_mg(){
        ModelAndView mv = new ModelAndView();

        List<MemberShip> memberShip = memberShipRepository.findAll();
       
    
            mv.addObject("list", memberShip);

        return mv;
    }


    //??????
    @PostMapping("payment")
    public String payment(HttpServletRequest req) throws ParseException, IOException{
       String member_id = req.getParameter("m_id");
       String membership_id = req.getParameter("ms_id");
       String pay_date = req.getParameter("pay_date");


       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       Date date = format.parse(pay_date);

    
       Member member = memberRepository.findOneById(Long.parseLong(member_id));
       MemberShip ms = memberShipRepository.findOneById(Long.parseLong(membership_id));

       //?????? ??????
       Payment pay = paymentRepository.findFirstByMOrderByIdDesc(member);
       
        //?????? ????????? ???????????? ???????????? ?????? ???????????? ?????? ????????????.
       if(pay != null){
         if(date.compareTo(pay.getExpiry_date()) < 0){
           return "fail";
         }
       }

     
       
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.MONTH, ms.getMonth_len());

       Date expiry_Date = cal.getTime();

       Payment payment = Payment.builder()
       .pay_date(date)
       .expiry_date(expiry_Date)
       .m(member)
       .ms(ms)
       .build();

       paymentRepository.save(payment);

       return "success";

    }


    //??????
    @PutMapping("payment")
    public String payment_refund(HttpServletRequest req, HttpServletResponse res) throws ParseException, IOException{
      long id = Long.parseLong(req.getParameter("id")); // ???????????? ????????? id

      Payment payment = paymentRepository.findOneById(id);

      //?????? ?????? ??????
      MemberShip ms = memberShipRepository.findOneById((long) 1);  
     
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     
      Date now = new Date();
      String now_day = simpleDateFormat.format(now);
   
      System.out.println(now_day);
   

      Date expiry = payment.getExpiry_date();
      String expiry_date = simpleDateFormat.format(expiry);

      //?????? ???????????? now?????? ????????? ??????
      //???????????? ?????? ???????????? ????????? ??????????????? ?????????.
      if(payment.getPay_date().compareTo(now) > 0) return "fail";
      
      int have_year = Integer.parseInt(expiry_date.split("-")[0]) - Integer.parseInt(now_day.split("-")[0]); 
      int have_month = Integer.parseInt(expiry_date.split("-")[1]) - Integer.parseInt(now_day.split("-")[1]); 
      int have_day= Integer.parseInt(expiry_date.split("-")[2]) - Integer.parseInt(now_day.split("-")[2]);

      
      //????????? ????????? ?????? ?????? ?????? ?????? ???????????? ????????? ?????? ?????? 
      if(have_year == 0 && have_month < 0 || have_year < 0) return "fail";
      //????????? ?????? ??? have_month + (have_year * 12)  
      // have_year > 0 ??????

      // day < 30 ??? ?????? month??? -
      if(have_day < 0) --have_month;  


                                 //????????????                      ???????????????                             ????????????                    ??????????????????
      int refund_price =  payment.getMs().getPrice() - ((payment.getMs().getMonth_len() - (have_month + (have_year * 12)) )  * ms.getPrice());

      if(refund_price < 0){ 
          refund_price = 0; 
      }


      //???????????? ????????? ???????????? ????????? ??????
      //refund entity save
      payment.Now_DaySet();

      final Refund refund = Refund.builder()
      .m_id(payment.getM().getId())
      .amount(refund_price)
      .refund_date(payment.getExpiry_date())
      .build();

      refundRepository.save(refund);

      return "success";


        
    }

    //????????????
    @DeleteMapping("payment")
    public String payment_del(HttpServletRequest req, HttpServletResponse res) throws ParseException, IOException{
       long id = Long.parseLong(req.getParameter("id"));

       paymentRepository.deleteById(id);
   
       return "??????";
    }



    @GetMapping("payment/{member}")
    public List<Payment> payment_find(@PathVariable("member") long m_id){
       
        Member member = memberRepository.findOneById(m_id);

        List<Payment> payment = paymentRepository.findAllByM(member);

        return payment;
    }


    //???????????? ???????????? ???????????? ?????????.
    @PutMapping("payment/all")
    public void paymentAll_edit(){
        //?????? ???????????? ????????? ???????????? ???????????? ?????????
        //???????????? ???????????? ????????? ??????
    }


    @GetMapping("membership")
    public ModelAndView membership(){
        ModelAndView mv = new ModelAndView();

        List<MemberShip> ms = memberShipRepository.findAll();

        mv.addObject("list", ms);

        return mv;
    }




    //???????????????
    @PostMapping("membership")
    public void membership_add(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String name = req.getParameter("name");
        String month = req.getParameter("month");
        String price = req.getParameter("price");

        final MemberShip memberShip = MemberShip.builder() 
        .name(name)
        .month_len(Integer.parseInt(month))
        .price(Integer.parseInt(price))
        .build();

         memberShipRepository.save(memberShip);

         res.sendRedirect("/member/payment");

    }


    //??????
    @GetMapping("stat")
    public ModelAndView account() throws JsonProcessingException{
        ModelAndView mv = new ModelAndView();
 
     
       List<StatDto> stat =  paymentService.StatGet(2022);
       List<StatDto> refund = refundService.RefundGet(2022);
       
       for(var i = 0; i <  stat.size(); ++i){
            stat.get(i).setTotal( ( stat.get(i).getTotal().subtract(refund.get(i).getTotal())) );
       }

       String json = new ObjectMapper().writeValueAsString(stat);
       
       mv.addObject("Stat", json);
       
   
        return mv;
    }

    //?????? ??????
    @GetMapping(" stat/{year}")
    public void  stat_get(){
        
     
    }
    



    
}
