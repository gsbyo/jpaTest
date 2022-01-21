<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

        <link rel="stylesheet" href="/resources/static/css/membership.css">
    </head>
   


    <body>

        <div class="container" style="margin-bottom: 20px;">
            <h2>회원권 등록</h2>
            <div class="wrap">
                <p style=" margin: 0; background-color: black; width: 100%; padding: 10px; color: white;">회원권 정보</p>
                <form id="ms-form" action="/membership" method="post">
                <div class="index-box">
                    <label>이름</label>
                    <input type="text" id="name" name="name">
                </div>
                <div class="index-box">
                    <label>기간</label>
                    <input type="number" id="month" name="month">
                </div>
                <div class="index-box">
                    <label>가격</label>
                    <input type="number"id="price" name="price">
                </div>
            
                     
                <div style="padding: 10px;">
                <button type="button" id="sign">등록하기</button>
            </div>
                </form>
            </div>
            
        </div>
     

            <div class="container">
            <div style="border:1px solid black; border-bottom: 0;">
                <table>
                    <tr>
                       <th>회원권</th>
                       <th>가격</th>
                       <th>기간</th>
                    </tr>
                </table>
               </div>
                <div style="height: 300px; overflow-y:scroll; border:1px solid black;">
                <table>
                   <tbody id="membership-box">
                       <c:forEach var="ms" items="${list}">
                           <tr class="ms-list" data-id = "${ms.id}">
                               <td  class="ms-name"><span>${ms.name}</span></td>
                               <td  class="ms-price"><span>${ms.price}원</span></td>
                               <td  class="ms-len"><span>${ms.month_len}개월</span></td>
                           </tr>
                       </c:forEach>
                 
            </tbody>
           </table>
   
            </div>
        </div>
    

        <script>
            $('#sign').on('click',function(){
                if($('#name, #month #price').val() == ""){
                    alert("입력이 잘못되었습니다.");
                    return;
                }

                if (confirm("회원권을 등록하시겠습니까 ? ?") == true) {
                    $('#ms-form').submit();
                }
            })
            
         
        </script>
    </body>
</html>