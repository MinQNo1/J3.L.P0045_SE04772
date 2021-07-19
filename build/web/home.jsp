<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="css/app.css" rel="stylesheet" type="text/css" />
    </head>
    <body> 
        <div class="panel">
            <div class="container">
                <div class="form-container">
                    <div class="form-title">
                        <div class="col-3">
                            <label><img src="${images}/${calendarIcon}" alt="date-time" class="tt-icon"/><span class="form-header">Timetable</span></label>
                        </div>
                    </div>
                    <form action="home" method="post" class="home-form">
                        <div class="form-group search-group">
                            <div class="col-2">
                                <label>
                                    <span class="form-label text-bold">From Date</span>
                                </label>
                                <input class="form-control datetime" type="date" name="startDate" required value="<c:out value='${startDate}' />">
                            </div>
                            <div class="col-2">
                                <label><span class="form-label text-bold">To Date</span></label>
                                <input class="form-control datetime" type="date" name="endDate" required value="<c:out value='${endDate}' />">
                            </div>
                            <div class="search-col">
                                <label><span class="form-label">&nbsp;</span></label>
                                <button class="btn btn-search" type="submit">Search</button>
                            </div>
                            <div class="form-group2">
                                <label><span class="form-label">&nbsp;</span></label>
                                <a href="new" class="link btn-add"><img src="${imagePath}/${calendarIcon}" alt="date-time" class="add-icon"/>Add timetable</a>
                            </div>
                        </div>
                    </form>
                    <table class="table home-table">
                        <tr>
                            <th><img src="${images}/${calendarIcon}" alt="date-time" class="table-icon"/>Date</th>
                            <th><img src="${images}/${timeIcon}" alt="date-time" class="table-icon"/>Slot</th>
                            <th><img src="${images}/${timeIcon}" alt="date-time" class="table-icon"/>Time</th>
                            <th>Class</th>
                            <th>Teacher</th>
                            <th>Room</th>
                            <th>Action</th>
                        </tr>
                        <c:forEach items="${list}" var="item" >
                            <tr>
                                <td class="w-20 mr-5">${item.date}</td>
                                <td class="w-10">${item.slot}</td>
                                <td class="w-20">${item.startTime}-${item.endTime}</td>
                                <td class="text-blue w-15">${item.classCourse}</td>
                                <td class="w-10">${item.teacher}</td>
                                <td class="w-10">${item.roomId}</td>
                                <td class="w-15">
                                    <a href="edit?id=${item.id}">Edit</a>
                                    &nbsp;
                                    <a href="delete?id=${item.id}" onclick="showConfirmBox()">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="paging">
                        <c:if test="${maxPage<1}">
                            <h3>Not Found !!</h3>
                        </c:if>
                        <c:if test="${maxPage>1}">
                            <c:forEach begin="1" end="${maxPage}" var="i">
                                <a class="${i==pageIndex?"active":""}" href="home?page=${i}">${i}</a>
                            </c:forEach>
                        </c:if>
                        <div class="about-me">
                            <div class="large-title">${about.title}</div>
                            <div class="description">${about.description}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="js/form.js"></script>
</html>
