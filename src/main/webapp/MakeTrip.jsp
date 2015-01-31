<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">

    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="site-wrapper-inner">
            <h2>Make a Trip</h2>
            <form role="form">
                <div class="form-group">
                    <label for="etd">When will you leave?</label>
                    <input type=time class="form-control" id="etd" placeholder="Enter departure time" required>
                </div>
                <div class="form-group">
                    <label for="restaurantTime">When will you arrive at the restaurant?</label>
                    <input type=time class="form-control" id="restaurantTime" placeholder="Enter arrival time" required>
                </div>
                <div class="form-group">
                    <label for="eta">When will you get back to Rice?</label>
                    <input type=time class="form-control" id="eta" placeholder="Enter arrival time" required>
                </div>
                <div class="form-group">
                    <label for="restaurant">What restaurant are you going to?</label>
                    <input type="text" class="form-control" id="restaurant" placeholder="Enter restaurant name" required>
                </div>
                <div class ="form-group">
                    <label for ="fee">What is your fee?</label>
                    <div class="checkbox">
                        <label for ="percent"><input type="checkbox" id = "percent">Percentage</label>
                        <label for ="flat"><input type="checkbox" id = "flat">Flat Fee</label>
                        <input type="number" class="form-control" id="fee" placeholder="Enter fee (leave out %)" required>
                    </div>
                </div>
                <div class = "form-group">
                    <label for = "maxOrders">Maximum number of orders you will accept?</label>
                    <input type = "number" class = "form-control" id = "maxOrders" placeholder="Enter maximum number" required>
                </div>
                <div class = "form-group">
                    <label for = "acceptUntil">When will you not accept any more orders</label>
                    <input type = "time" class = "form-control" id ="acceptUntil" placeholder="Enter time" required>
                    <button type="submit" class="btn btn-default" style="margin-top:10px">Submit</button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</t:template>