<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            $(function(){
                var me = "https://venmo.com/nicholas-kwon?txn=pay&amount="+${depositAmt}
                $("#id").val(me);
            });

        </script>
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
                                <a class="btn btn-primary" id="id" href="https://venmo.com/nicholas-kwon?txn=pay&amount=${depositAmt}">Pay with Venmo</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>

</t:template>