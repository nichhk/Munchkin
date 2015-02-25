<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            var i;
            var m;
            $(function() {
                var allOrders = ${orders};
                var allTrips = ${trips};

                for (m = 0; m < allOrders.length; m++) {
                    var items = allOrders[m].items;
                    var trip = allTrips[m];
                    var flatFee = trip.flat;
                    var percentFee = trip.percentage;
                    var sum = 0;
                    var orderWrapper = $('<div class = "well well-lg">');

                    var orderInfoWrapper = $('<div class = "well well-lg">');
                    var orderHeader = $('<h3>').html("Order Information");
                    var location = $('<div style = "font-weight:bold">').html(trip.restaurant);
                    console.log(trip.user);
                    var driverInfo = $('<div class = "row">');
                    var userName = $('<div class = "col-sm-6">').html("Driver: " + trip.user);
                    var useNumber = $('<div class = "col-sm-6">').html("Number: " + trip.phoneNumber);
                    driverInfo.append(userName);
                    driverInfo.append(useNumber);

                    var timeRow = $('<div class = "row">');
                    var eta = $('<div class = "col-sm-6">').html("Estimated time of arrival: " + trip.eta);
                    var lastOrderTime = $('<div class = "col-sm-6">').html("Accepting orders until: " + trip.lastOrder);
                    timeRow.append(eta);
                    timeRow.append(lastOrderTime);


                    var dropOffLoc = $('<div class>').html("Drop Off Location: <b>" + trip.dropOffLocation + "</b>");

                    orderInfoWrapper.append(orderHeader);
                    orderInfoWrapper.append(location);
                    orderInfoWrapper.append(driverInfo);
                    orderInfoWrapper.append(timeRow);
                    orderInfoWrapper.append(dropOffLoc);
                    console.log(items);
                    orderWrapper.append(orderInfoWrapper);
                    console.log(items.length);
                    var k = 0;
                    for (i = 0; i < items.length; i++) {
                        k++;
                        console.log("im here");
                        var itemWrapper = $('<div class = "well well-lg">');
                        var itemInfoRow = $('<div class = "row">');
                        //row for this food item
                        var itemNumber = $('<div class = "col-sm-2" style = "font-weight:bold">').html("Item #" + k);
                        console.log(i);
                        var theItem = $('<div class = "col-sm-6">').html(items[i].foodItem);

                        var theMax = $('<div class = "col-sm-4">').html("$"+items[i].priceMax);
                        sum += parseFloat(items[i].priceMax);
                        console.log(items[i].foodItem);

                        itemInfoRow.append(itemNumber);
                        itemInfoRow.append(theItem);
                        itemInfoRow.append(theMax);

                        itemWrapper.append(itemInfoRow);

                        if (!(items[i].comments === "")) {
                            var commentRow = $('<div>').html(items[i].comments);
                            itemWrapper.append(commentRow);
                        }

                        if (items[i].hasAlt == true) {
                            var alt = items[i].alt;
                            var altItemInfoRow = $('<div class = "row">');
                            $('<br>').appendTo(altItemInfoRow);
                            var altItemNumber = $('<div class ="col-sm-2" style = "font-weight:bold">').html("Alt #" + k);
                            var altFoodItem = $('<div class = "col-sm-6">').html(alt.foodItem);
                            var altPriceMax = $('<div class = "col-sm-4">').html("$"+alt.priceMax);


                            altItemInfoRow.append(altItemNumber);
                            altItemInfoRow.append(altFoodItem);
                            altItemInfoRow.append(altPriceMax);

                            itemWrapper.append(altItemInfoRow);

                            var altComment = alt.comments;

                            if (!(alt.comments === "")) {
                                var altCommentRow = $('<div>').html(alt.comments);
                                itemWrapper.append(altCommentRow);
                            }
                        }
                        orderWrapper.append(itemWrapper);
                    }
                    var priceWrapper = $('<div class = "well well-lg">');
                    $('<h3>').html("Total Price").appendTo(priceWrapper);
                    var subTotalRow = $('<div class = "row">');
                    var sumPart = $('<div class = "col-sm-4">').html("Subtotal: $" + sum.toString());
                    var flatFeePart = $('<div class = "col-sm-4">').html("Flat Fee: $" + flatFee);
                    var percentFee = $('<div class = "col-sm-4">').html("Percent Fee: " + percentFee + "%");
                    subTotalRow.append(sumPart);
                    subTotalRow.append(flatFeePart);
                    subTotalRow.append(percentFee);
                    var total = sum*1.0825 + parseFloat(flatFee) + sum * (parseFloat(percentFee))/100;
                    var stringTotal = total.toFixed(2).toString();
                    var totalRow = $('<div style = "font-weight:bold">').html("Maximum Expected Total (tax inc.): " + stringTotal);

                    priceWrapper.append(subTotalRow);
                    priceWrapper.append(totalRow);

                    orderWrapper.append(priceWrapper);
                    orderWrapper.appendTo('#orders');
                }
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
                            <div id="orders"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>
</t:template>