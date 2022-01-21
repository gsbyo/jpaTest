<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>

        <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
        
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <link rel="stylesheet" href="/resources/static/css/payment.css">


    </head>
    <body>

  

        <div class="container">  
           <h2>결제</h2>
           <div id="wrap">
           <div class="pay-box">
             <div style="border: 1px solid black; width: 70%; border-radius: 4px;">
             <p style="padding: 10px; margin: 0; background-color: black; color: white;">회원정보</p>
             <div id="info-box" data-id="" style="display: flex; flex-wrap: wrap; justify-content: center;">
             <p> <label>이름 </label><input type="text" id="m-name" readonly> </p>
             <p> <label>성별 </label><input type="text" id="m-gender" readonly> </p>
             <p> <label>나이 </label><input type="number" id="m-age" readonly> </p>
             <p> <label>번호 </label><input type="text" id="m-phone" readonly> </p>
            </div>
           </div>
        </div>
           <div class="pay-box">
            <div style="border: 1px solid black; width: 70%; border-radius: 4px;">
            <p style="padding: 10px; margin: 0; background-color: black; color: white;">멤버쉽</p>
            <p style="margin-left: 10px;">
            <select id="membership" name="membership">
                <c:forEach var="membership" items="${list}">
                <option value="${membership.id}">${membership.name}</option>
            </c:forEach>
            </select>
        </p> 
            <c:forEach var="membership" items="${list}">
              <div class="membership-info" style="display: none;">
                <p style="margin-left: 10px;"><label>가격 : </label><span class="p-price">${membership.price}</span> </p>
                <p style="margin-left: 10px;"> <label>기간 : </label><span class="p-month">${membership.month_len}개월</span></p>
              </div>
            </c:forEach> 
            <p style="margin-left: 10px;"><label>결제일 : </label><input type="text" id="datepicker" name="payDate"> </p>
          
           </div>
        </div>
    </div>
    <button type="button" id="pay-button">결제하기</button>
    </div>       
        
        <div class="container">
            <div style="padding: 10px; ">
            <h2>회원리스트</h2>
            <label>회원 이름 </label><input type="text" id="search-member">
            <button type="button" id="search-button">검색</button>
            </div>
        <div style=" border: 1px solid black;  height: 300px;">
         <table>
             <thead>
             <tr>
                <th>이름</th>
                <th>성별</th>
                <th>나이</th>
                <th>휴대폰</th>
             </tr>
            </thead>
            <tbody id="list-box">
          
        </tbody>
         </table>
         </div>
     </div>

        <div class="container">
           <h2>결제내역</h2>
        <div style="border: 1px solid black;  height: 300px;">
        <table>
            <thead>
            <tr>
               <th>이름</th>
               <th>성별</th>
               <th>나이</th>
               <th>멤버십</th>
               <th>회원 등록일</th>
               <th>회원 만료일</th>
            </tr>
           </thead>
           <tbody id="pay-box">
         
    </tbody>
        </table>
    </div>
    </div>

    <script>

        $(function () {
            $("#datepicker").datepicker({
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
            });
        });

        $('.membership-info').eq(0).show();


        $(document).ready(function () {
            $('#membership').on('change', function () {
                var index = $('#membership option:selected').index();
                $('.membership-info').hide();
                $('.membership-info').eq(index).show();
            })

        });


        

        $('#search-button').on('click', function () {
                $('#list-box').children().remove();
                var name = $('#search-member').val();

                $.ajax({
                    method: "get",
                    url: "/member/" + name,

                    success: function (members) {
                     for(var i = 0; i < members.length; ++i){
                        $('#list-box').append(
                            "<tr class='member-list' style='border-bottom: 1px solid black;' data-id=" + members[i].id + ">"
                            + "<td>" + members[i].name + "</td>"
                            + "<td>" + members[i].gender + "</td>"
                            + "<td>" + members[i].age + "</td>"
                            + "<td>" + members[i].phone_number + "</td>"
                            + "</tr>"
                        )
                     }
                    }
                })
            })

            $(document).on("click", ".member-list", function () {

            

                $('#pay-box').children().remove();
                
                $('#info-box').data('id',$(this).data('id'))
                $('#m-name').val($(this).children('td').eq(0).text());
                $('#m-gender').val($(this).children('td').eq(1).text());
                $('#m-age').val($(this).children('td').eq(2).text());
                $('#m-phone').val($(this).children('td').eq(3).text());

                  $.ajax({
                      method : 'get',
                      url : '/payment/'+ $(this).data('id'),
              
                      success : function(results){
                       $.each(results, function(index, res){
                        $('#pay-box').append(
                            "<tr class='pay-list' style='border-bottom: 1px solid black;' data-id=" +res.id + ">"
                            + "<td>" + res.m.name + "</td>"
                            + "<td>" + res.m.age + "</td>"
                            + "<td>" + res.m.gender + "</td>"
                            + "<td>" + res.ms.name + "</td>"
                            + "<td class='date'>" + res.pay_date.substring(0,10) + "</td>"
                            + "<td class='date'>" + res.expiry_date.substring(0,10) + "</td>"
                            + "<td class='btn'><button id='cancel'>결제취소</button></td>"
                            + "<td class='btn'><button id='refund' data-ms="+ res.ms.id +">결제환불</button></td>"
                            + "</tr>"
                        )
                        });
                    }
                  })
            })



        $('#pay-button').on('click',function(){
            var m_id = $('#info-box').data('id');
            var ms_id = $('#membership option:selected').val();
            var pay_date = $('#datepicker').val();

            console.log(m_id);

            $.ajax({
                method : 'post',
                url : '/payment',
                data : {
                   m_id : m_id,
                   ms_id : ms_id,
                   pay_date : pay_date
                },

                success : function(result){
                    if(result == "success")
                    alert('결제가 완료되었습니다.');
                }
                
            })

        })


        $(document).on("mouseenter", ".pay-list", function() {
           $(this).children('.date').hide();
           $(this).children('.btn').show();
        });


        $(document).on("mouseleave", ".pay-list", function() {
             $(this).children('.date').show();
             $(this).children('.btn').hide();
         });
 

         //해당결제 취소
         $(document).on("click", "#cancel", function () {
             if (confirm("결제취소를 하시겠습니까 ?") == true) {
                      $.ajax({
                          method: "delete",
                          url: "/payment",
                          data: {
                              id: $(this).parents('.pay-list').data('id')
                          },
                          success: function () {
                              alert("삭제 완료");
                          }
  
                      })
                  }

            })

            //해당결제 환불
            $(document).on("click", "#refund", function () {
                   if (confirm("결제환불을 하시겠습니까 ?") == true) {
                        $.ajax({
                            method: "put",
                            url: "/payment",
                            data: {
                                id: $(this).parents('.pay-list').data('id')         
                            },
                            success : function(result){
                                if(result == "success"){
                                    alert("결제가 환불되었습니다")
                                    location.reload();
                                    
                                }else{
                                    alert("결제 날짜를 확인해주세요")
                                    
                                }
                                
                            }
                        })
                    }
            })

        </script>

    </body>
</html>