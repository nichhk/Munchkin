<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

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
                    <form role="form" action="/create_profile" method = "Post">
                        <div class="form-group">
                            <label for="firstName">First Name:</label>
                            <input type="firstName" class="form-control" name="firstName" id="firstName" placeholder="Enter First Name" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name:</label>
                            <input type="lastName" class="form-control" name="lastName" id="lastName" placeholder="Enter Last Name" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email Address:</label>
                            <input type="email" class="form-control" name="email" id="email" placeholder="Enter Email" required>
                        </div>
                        <div class="form_group">
                            <label for = "college"> College:</label>
                            <input type = "college" class = "form-control" name = "college" id="college" placeholder="Enter College" required>
                        </div>
                        <div class="form_group">
                            <label for = "year"> Year:</label>
                            <input type = "year" class = "form-control" name = "year" id="year" placeholder="Enter Year" required>
                        </div>
                        <div class="form_group">
                            <label for = "phoneNumber"> Phone Number:</label>
                            <input type = "phoneNumber" class = "form-control" name = "phoneNumber" id="phoneNumber" placeholder="Enter Phone Number" required>
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </jsp:attribute>

</t:template>