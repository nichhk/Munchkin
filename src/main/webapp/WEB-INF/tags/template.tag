<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="content" fragment="true" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ attribute name="wide" fragment="true" %>
<%@attribute name="isApproved" required="true"%>
<%@attribute name="log" required="true"%>
<%@attribute name="action" required="false"%>
<%@attribute name="page" required="true"%>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Munchin</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../../stylesheets/cover.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    <jsp:invoke fragment="scripts"/>

    <script>
        $(function () {
            $('#<%=page%>').addClass('active');
        });
        $(function () {
            var height = $('.navbar-header').height();
            $body = $('body');
            $body.css("padding-top", height+"px");
        });

    </script>


</head>

<body>

<div class="site-wrapper">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" rel="home" href="/" title="Munchin">
                    Munchin
                </a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <%
                        if (isApproved.compareTo("1") == 0){
                            out.println("<li id=\"trip\"><a href='/trip'>Make a trip</a></li>");
                            out.println("<li id=\"trip_manager\"><a href='/trip_manager'>My trips</a></li>");
                            out.println("<li id=\"order_manager\"><a href='/order_manager'>My orders</a></li>");
                            out.println("<li><a href=\"" + log +  "\">Log out</a></li>");
                        }
                        else{
                            //out.println("<li><a href=\"" + log +  "\">Log in</a></li>");
                        }
                    %>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>
    <jsp:invoke fragment="content"/>
</div>

<div class="modal-loading"></div>

</body>
</html>