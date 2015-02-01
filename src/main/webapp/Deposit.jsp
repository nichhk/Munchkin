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
                <div id="content" class="inner cover" style="text-align: left">
                    <div class="row">
                        <div class="col-md-12">
                            <p>The max amount of money you're willing to pay, including the fees
                                and tax, is $${depositAmt}. Clicking the button below will take you
                                to Venmo to place your order.
                            </p>
                            <form action="https://api.venmo.com/v1/payments" method="POST">
                                <div style="display:none">
                                    <input type="text" name="access_token" id="access_token"
                                           value="4MX7yxwTu97RjLktV4TvkHmcRNawPwsG">
                                    <input type="text" name="number" id="number" value="8183035160">
                                    <input type="number" name="amount" id="amount" value="${depositAmt}">
                                    <input type="text" name="note" id="note" value="${note}">
                                </div>
                                <button type="submit" class="btn btn-primary">Pay with Venmo</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>

</t:template>