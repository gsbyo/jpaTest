<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

        <link rel="stylesheet" href="/resources/static/css/main.css">
    </head>
   


    <body>
     
        <div class="container">
            <div style="width: 100%; color: black; text-align: center;">
                <h2>회원 관리 시스템</h2>
            </div>
            <div class="box" style="background-color: cadetblue;" onclick="location.href='/member/'">
               <p>회원 등록 / 회원 조회</p>
            </div>

            <div class="box" style="background-color:darkseagreen" onclick="location.href='/payment'">
                <p>결제 / 환불</p>
             </div>
         
             <div class="box" style="background-color:darkred" onclick="location.href='/membership'">
                <p>회원권 관리</p>
             </div>
         
             <div class="box" style="background-color:darkkhaki" onclick="location.href='/stat'" >
                <p>정산</p>
             </div>
          
        </div>

        <script>
         
        </script>
    </body>
</html>