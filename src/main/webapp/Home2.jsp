<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="site-wrapper-inner">
            <a class="btn btn-primary" href="${logIn}">Log in</a>
            <a class="btn btn-primary" href="${signIn}">Create an account</a>
        </div>

    </jsp:attribute>

</t:template>