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
            <div class="cover-container">
                <!--
                thanks to : http://bootsnipp.com/snippets/featured/bootstrap-3x-contact-form-layout
                -->
                <div class="page-header">
                    <h1>Welcome to Munchin! The easiest and most convenient way to get a quick bite to eat!</h1>
                </div>
                <p>
                    Make sure to create an account with your @rice.edu email. If you already have an account, then log in
                    to make or join a trip.
                </p>
                <div id="content" class="inner cover" style="text-align: left">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <a class="btn btn-primary" href="${logIn}">Log in</a>
                            <br>

                            <a class="btn btn-primary" href="${createProf}" style="margin-top: 10px">Create an account</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:template>