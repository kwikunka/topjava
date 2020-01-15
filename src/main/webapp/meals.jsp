<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt>DateAfter:</dt>
            <dd><input id="1" type="date" name="dateAfter"></dd>
            <dt>DateBefore:</dt>
            <dd><input id="2" type="date" name="dateBefore"></dd>
        </dl>
        <dl>
            <dt>TimeAfter:</dt>
            <dd><input id="3" type="time" name="timeAfter"></dd>
            <dt>TimeBefore:</dt>
            <dd><input id="4" type="time" name="timeBefore"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
</section>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var ids = ["1", "2", "3", "4"];
        for (var id of ids) {
            var input = document.getElementById(id);
            input.value = localStorage.getItem(id);
            (function(id, input) {
                input.addEventListener("change", function() {
                    localStorage.setItem(id, input.value);
                });
            })(id, input);
        }
    });
</script>
</body>
</html>