<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            $(function() {
                $.each( ${responseJson}, function(index, trip) {
                    var eachCustomer = trip.customer.split(",");
                    var numCustomers = eachCustomer.length;
                    var allCustomers = $('<div id = "expandable">');
                    for (var i = 0; i < numCustomers; i++) {
                        var customer = eachCustomer[i].split(" ");
                        var customerName = customer[0] + customer[1];
                        var phone = customer[2];
                        var newSpan = $('<p>').append($('<a>').html(customerName));
                        allCustomers.append(newSpan);
                    }
                    var myTripID = trip.time;
                    var textForm = $('<form role = "form" action = "send_sms" method = "Get">');
                    var formInput = $('<input type = "submit" value = "Text the Customers" id = "text">');
                    var hiddenText = $('<input type = "hidden" value = myTripID id = "id">');
                    textForm.append(formInput);
                    textForm.append(hiddenText);

                    var timeRow = $('<div class = "row" id = "timeRow">');
                    var timeLeft = $('<div class="col-sm-4" style = "color:red">').html(trip.timeLeft);
                    var timeETA = $('<div class = "col-sm-4">').html(trip.eta);
                    timeRow.append(timeLeft);
                    timeRow.append(timeETA);

                    $('<div id = "toClick">').appendTo('#trips')
                            .append($('<span style = "font-size:160%; font-weight:bold">').html(trip.restaurant))
                            .append(timeRow)
                            .append($('<span>').html("Drop off location: " + trip.dropOffLocation))
                            .append($('<br>'))
                            .append($('<span>').html("Number of Customers: " + numCustomers))
                            .append($('<br>'))
                            .append(textForm)
                            .append($('<br>'))
                            .append(allCustomers.hide())
                            .addClass('trip')
                            .addClass('well well-lg')
                });
            });
            $(function () {
                $('#trips').on('click', "#toClick", function() {
                    $($(this).find("#expandable")).slideDown("fast");
                });
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
                        <div class="col-md-12" >
                            <div id="trips"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>
</t:template>