<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

        <link rel="stylesheet" href="/resources/static/css/">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
       
    </head>
   


    <body>
        <h2>2022년 매출</h2>
        <div style="width: 800px;">
        <canvas id="bar-chart"></canvas>
    </div>
           



        <script>

            var data = JSON.parse('${Stat}');
            var graph_date = [];

            for(var i = 0; i < data.length; ++i){
                 graph_date.push(data[i].total);
            }

     

            new Chart(document.getElementById("bar-chart"), {
                type: 'bar',
                data: {
                    labels: ["1월", "2월", "3월", "4월", "5월","6월","7월","8월", "9월","10월","11월","12월"],
                    datasets: [
                        {
                            label: "",
                            backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850","#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850","#3e95cd", "#8e5ea2"],
                            data: graph_date
                        }
                    ]
                },
                options: {
                    legend: { display: false },
                    title: {
                        display: true,
                        text: ''
                    }
                }
            });
        </script>
    </body>
</html>