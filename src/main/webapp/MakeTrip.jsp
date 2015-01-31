<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>

            $(function() {
                $("#percentFee").prop('disabled', true); //http://stackoverflow.com/questions/6982692/html5-input-type-date-default-value-to-today
                $("#flatFee").prop('disabled', true);
                var now = new Date();
                var month = (now.getMonth() + 1);
                var day = now.getDate();
                if(month < 10)
                    month = "0" + month;
                if(day < 10)
                    day = "0" + day;
                var today = now.getFullYear() + '-' + month + '-' + day;
                $("#etaDate").val(today);
                $("#lastOrderDate").val(today);
            });
            $(function () {

                $('#percent').click(function () {

                    if ($("#percent").prop("checked")==true) {
                        //alert("Able");
                        $("#percentFee").prop('disabled', false);
                    }
                    else {
                        //alert("Disable");
                        $("#percentFee").prop('disabled', true);
                    }

                });
                $('#flat').click(function () {
                    if ($("#flat").prop("checked")==true) {
                        $("#flatFee").prop('disabled', false);
                    }
                    else {
                        $("#flatFee").prop('disabled', true);
                    }
                });
            });

        </script>

    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="site-wrapper-inner">
            <h2>Make a Trip</h2>
            <form role="form">
                <div class="form-group">
                    <label for="lastOrderTime">When will you stop taking orders?</label>
                    <input type=time class="form-control" id="lastOrderTime" name = "lastOrderTime" placeholder="Enter latest order time" required>
                    <input type = date class = "form-control" id = "lastOrderDate" name = "lastOrderDate">
                </div>

                <div class="form-group">

                    <label for="etaTime">When will you be able to drop off the food?</label>
                    <input type=time class="form-control" id="etaTime" name = "etaTime" placeholder="Enter delivery time" required>
                    <input type=date class="form-control" id="etaDate" name = "etaDate" required>
                </div>
                <div class="form-group">
                    <label for="restaurant">What restaurant are you going to?</label>
                    <input type="text" class="form-control" id="restaurant" name = "restaurant" placeholder="Enter restaurant name" required>
                </div>
                <div class ="form-group">

                    <div class="checkbox">
                        <label for ="percent"><input type="checkbox" id = "percent" name = "percentage">Percentage</label>

                        <label for ="flat"><input type="checkbox" id = "flat" name = "flat">Flat Fee</label>

                        <input type="number" class="form-control" id="percentFee" name = "percentFee" placeholder="10% -> ex: 10" required>

                        <input type="number" class="form-control" id="flatFee" name = "flatFee" placeholder="$3.50 -> ex: 3.5" required>
                    </div>
                </div>
                <div class = "form-group">
                    <label for = "maxOrders">Maximum number of orders you will accept?</label>
                    <input type = "number" class = "form-control" id = "maxOrders" name = maxOrders" placeholder="Enter maximum number" required>
                </div>
                <div class = "form-group">

                    <button type="submit" class="btn btn-default" style="margin-top:10px">Submit</button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</t:template>