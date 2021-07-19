<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="css/app.css" rel="stylesheet" type="text/css"/>
        <link href="css/form.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="panel">
            <div class="container">
                <div class="form-container">
                    <div class="form-title">
                        <div class="col-3">
                            <label><img src="${images}/${calendarIcon}" alt="date-time" class="tt-icon"/><span class="form-header">Add Timetable</span></label>
                        </div>
                    </div>
                    <form action="${timeTable.id != null? 'update' : 'insert'}" method="post">
                        <c:if test="${timeTable.id != null}">
                            <input type="hidden" name="id" id="id" value="<c:out value='${timeTable.id}' />" />
                        </c:if>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label">Date</span></label>
                            </div>
                            <div class="col-6">
                                <input class="datetime" type="date" name="date" required value="<c:out value='${timeTable.date}' />">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label">Slot</span></label>
                            </div>
                            <div class="col-6">
                                <select class="form-control" name="slot">
                                    <option>Select</option>
                                    <c:forEach items="${slots}" var="slot">
                                        <option value="${slot.id}" ${timeTable.slot == slot.id ? 'selected="selected"' : ''}>${slot.name} </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label">Room</span></label>
                            </div>
                            <div class="col-6">
                                <select class="form-control" name="room">
                                    <option>Select</option>
                                    <c:forEach items="${rooms}" var="room">
                                        <option value="${room.id}" ${timeTable.roomId == room.id ? 'selected="selected"' : ''}>${room.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label col-3">Teacher</span></label>
                            </div>
                            <div class="col-6">
                                <select class="form-control" name="teacher">
                                    <option>Select</option>
                                    <c:forEach items="${teachers}" var="teacher">
                                        <option value="${teacher.id}" ${timeTable.teacherId == teacher.id ? 'selected="selected"' : ''}>${teacher.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label">Class/Course</span></label>
                            </div>
                            <div class="col-6">
                                <select class="form-control" name="courseClass">
                                    <option>Select</option>
                                    <c:forEach items="${courseClasses}" var="courseClass">
                                        <option value="${courseClass.id}" ${timeTable.classCourseId == courseClass.id ? 'selected="selected"' : ''}>${courseClass.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-3">
                                <label><span class="form-label col-3"></span></label>
                            </div>
                            <div class="col-6">
                                <button type="submit" class="btn">Save</button><label class="error-msg pl-5">${existedError}</label>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
