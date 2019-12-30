<%@ page import="static ru.javawebinar.topjava.util.TimeUtil.FORMATTER" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update Meal</h2>

<%
    MealTo meal = (MealTo)request.getAttribute("meal");
%>
<%!
    private String isSelected(MealTo meal, String description) {
        return description.equals(meal.getDescription()) ? "selected" : "";
    }
%>
<div>
    <form method="post" action="meals?action=add&id=<%out.print(meal.getId());%>">
        <label hidden>Id:
            <input value="<%out.print(meal.getId());%>"
                   type="hidden">
        </label>
        <label>DateTime:<br />
            <input value="<%out.print(meal.getDateTime());%>"
                   type="datetime-local"
                   name="datetime"><br />
        </label>
        <label>Description:<br />
            <select name="description">
                <option <%out.print(isSelected(meal, "Завтрак"));%> value="Завтрак">Завтрак</option>
                <option <%out.print(isSelected(meal, "Обед"));%> value="Обед">Обед</option>
                <option <%out.print(isSelected(meal, "Ужин"));%> value="Ужин">Ужин</option>
            </select>
            <br />
        </label>
        <label>Calories:<br />
            <input type="text"
                   value="<%out.print(meal.getCalories());%>"
                   name="calories"><br />
        </label>
        <button type="submit">Update Meal</button>
    </form>
</div>

</body>
</html>