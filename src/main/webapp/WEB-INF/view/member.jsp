<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 

        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <link rel="stylesheet" href="/resources/static/css/list.css">


    </head>
    <body>
            <div class="container">
            <h2>회원등록</h2>
            <div style="border: 1px solid black; width:20%; border-radius: 4px;">
                <p style="padding: 10px; margin: 0; background-color: black; color: white;">회원정보</p>
                <form action="/member" method="post">
                    <div class="input-box" style="display: none;">
                        <label>ID : </label>
                        <input type="text" id="id">
                    </div>
                    <div class="input-box">
                        <label>이름 : </label>
                        <input type="text" name="name" id="name">
                    </div>
                    <div class="input-box">
                        <label>성별 : </label>
                        <select id="gender" name="gender">
                            <option>남</option>
                            <option>여</option>
                        </select>
                    </div>
                    <div class="input-box">
                        <label>나이 : </label>
                        <input type="text" name="age" id="age">
                    </div>
                    <div class="input-box">
                        <label>번호 : </label>
                        <input type="text" name="phone" id="phone">
                    </div>
                </div>
                     
                    <button type="submit" id="sign">등록하기</button>
                </form>
            
        </div>

            <div class="container">
                <h2>회원리스트</h2>
                <div id="button-wrap">
                <button id="edit-btn">수정</button>
                <button id="del-btn">삭제</button>
                <button id="cancel-btn">취소</button>
                <button id="fin-btn">완료</button>
                </div>
             <div style="border:1px solid black; border-bottom: 0;">
             <table>
                 <tr>
                    <th>이름</th>
                    <th>성별</th>
                    <th>나이</th>
                    <th>휴대폰</th>
                 </tr>
             </table>
            </div>
             <div style="height: 300px; overflow-y:scroll; border:1px solid black;">
             <table>
                <tbody id="member-box">
                    <c:forEach var="member" items="${list}">
                        <tr class="member-list" data-id = "${member.id}">
                         <form action="/member" method="put">
                            <td  class="member-name"><span>${member.name}</span><input class="input-name" name="name" value="${member.name}"></td>
                            <td  class="member-gender"><span>${member.gender}</span><select class="input-gender" name="gender" value="${member.gender}">
                                <option>남</option>
                                <option>여</option>
                            </select></td>
                            <td  class="member-age"><span>${member.age}</span><input class="input-age" name="age" value="${member.age}"> </td>
                            <td  class="member-phone"><span>${member.phone_number}</span><input class="input-phone" name="phone" value="${member.phone_number}"> </td>
                         </form>
                        </tr>
                    </c:forEach>
              
         </tbody>
        </table>

         </div>

    

        <script>
           var edit_num = "";

           var edit_ing = false;


           //수정
           $('#edit-btn').on('click',function(){
               if(edit_num == ""){
                   alert('수정 회원을 선택해주세요.')
               }else{
                $('#del-btn, #edit-btn').hide();
                $('#cancel-btn, #fin-btn').show();
                $('.edit-active').children('td').children('input, select').show(); 
                $('.edit-active').children('td').children('span').hide();
            
                
                edit_ing = true;
              }
           })

           //수정취소
           $('#cancel-btn').on('click',function(){
              
              $('.edit-active').children('td').children('input, select').hide(); 
              $('.edit-active').children('td').children('span').show();
              $('#del-btn, #edit-btn').show();
              $('#cancel-btn, #fin-btn').hide();
              $('.member-list').removeClass('edit-active');
          
             // $('.edit-active').children('td').children('input, select').val();
              edit_num = "";
              edit_ing = false;
           })

           //수정완료
           $('#fin-btn').on('click',function(){
            var name =  $('.edit-active').children('td').children('.input-name').val();
            var gender = $('.edit-active').children('td').children('.input-gender').val();
            var age = $('.edit-active').children('td').children('.input-age').val();
            var phone = $('.edit-active').children('td').children('.input-phone').val();

            if (confirm("해당 회원을 수정하시겠습니까 ?") == true) {
                 $.ajax({
                     method : "put",
                     url : "/member/"+edit_num,
                     data : {
                       name : name,
                       gender : gender,
                       age : age,
                       phone : phone  
                     },

                     success : function(result){
                        if(result == "success") alert('수정 완료'); location.reload();
                     }

                 })
                }
           });

           //삭제
           $('#del-btn').on('click',function(){
            if(edit_num == ""){
                   alert('삭제 회원을 선택해주세요.')
               }else{

                if (confirm("해당 회원을 삭제하시겠습니까 ?") == true) {
                 $.ajax({
                     method : "delete",
                     url : "/member",
                     data : {
                         id : edit_num
                     },

                     success : function(){
                         location.reload();
                     }

                 })
                }
              }
            })



           $('.member-list').on('click',function(){
                
            if(edit_ing == false){
                if($(this).hasClass('edit-active')){
                    $(this).removeClass('edit-active');
                     edit_num = "";
                }else{
                    $('.member-list').removeClass('edit-active');
                    edit_num = $(this).data('id');
                    $(this).addClass('edit-active');
                }
            }     
        });
 
    

   
        </script>
    </body>
</html>