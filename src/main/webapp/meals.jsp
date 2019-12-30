<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="static ru.javawebinar.topjava.util.TimeUtil.FORMATTER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <thead>
        <tr>
            <th>Описание:</th>
            <th>Время:</th>
            <th>Количество:</th>
        </tr>
    </thead>
    <%
        List<MealTo> meals = (List<MealTo>)request.getAttribute("meals");
    %>

    <c:forEach var="meal" items="${meals}">
        <c:set var="excess">${meal.isExcess()}</c:set>
        <c:if test="${excess == true}">
            <tr style="color : #ff0000;">
        </c:if>
        <c:if test="${excess == false}">
            <tr style="color : green;">
        </c:if>
            <td><c:out value="${meal.getId()}"/></td>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getDateTime()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>
        </tr>
    </c:forEach>
    
    <%
        for (MealTo meal : meals) {
            if (meal.isExcess()) {
                out.println("<tr style=\"color: red;\">");
            } else {
                out.println("<tr style=\"color: green;\">");
            }
            out.println("<td>" + meal.getId() + "</td>" +
                        "<td>" + meal.getDescription() + "</td>" +
                        "<td>" + meal.getDateTime().format(FORMATTER) + "</td>" +
                        "<td>" + meal.getCalories() + "</td>" +
                        "<td><button onclick=\"location.href=\'meals?action=update&id="+ meal.getId()+"\'\">Update</button></td>"+
                        "<td><button onclick=\"location.href=\'meals?action=delete&id="+ meal.getId()+"\'\">Delete</button></td></tr>");
        }

    %>
</table>
<h2>Add Meal</h2>
<div>
    <form method="post" action="meals?action=add">
        <div style="padding: 5px">
            <label>DateTime:<br />
                <input type="datetime-local" name="datetime"><br />
            </label>
        </div>
        <div style="padding: 5px">
            <label>Description:<br />
                <select name="description">
                    <option selected value="Завтрак">Завтрак</option>
                    <option value="Обед">Обед</option>
                    <option value="Ужин">Ужин</option>
                </select>
                <br />
            </label>
        </div>
        <div style="padding: 5px">
            <label>Calories:<br />
                <input type="text" value="500" name="calories"><br />
            </label>
        </div>
        <div style="padding: 5px">
            <button type="submit">Add Meal</button>
        </div>
    </form>
</div>

</body>
</html>