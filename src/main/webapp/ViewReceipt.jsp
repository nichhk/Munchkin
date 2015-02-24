<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            var theForm = $('<form class="form" role="form" action="/transaction" method="post">');
            var totalPrice = 0;
            var i = 0;
            var headingWell = $('<div class = "well">');
            var heading = $('<div class = "row">');
            var itemNumber = $('<div class = "col-sm-2" style = "color:#CC0000">').html("Item #");
            var headingName = $('<div class = "col-sm-4" style = "color: #CC0000">').html("Item name");
            var headingMaxPrice = $('<div class = "col-sm-3" style = "color: #CC0000">').html("Max Price");
            var headingActualPrice = $('<div class = "col-sm-2" style = "color: #CC0000">').html("Actual Price");
            var commentHeader = $('<div style = "color: #CC0000">').html("Comments:");

            heading.append(itemNumber);
            heading.append(headingName);
            heading.append(headingMaxPrice);
            heading.append(headingActualPrice);
            headingWell.append(heading);
            headingWell.append(commentHeader);

            theForm.append(headingWell);

            $(function() {
                var i;
                var k = 0;
                $.each(${items}, function (index, item) {
                    console.log(item.length);
                    for (i = 0; i < item.length; i++) {
                        k++;
                        var itemWrapper = $('<div class = "well well-lg">');
                        var itemInfoRow = $('<div class = "row">');
                        //row for this food item
                        var itemNumber = $('<div class = "col-sm-2" style = "font-weight:bold">').html("Item #" + k);
                        console.log(i);
                        var theItem = $('<div class = "col-sm-4">').html(item[i].foodItem);

                        var theMax = $('<div class = "col-sm-3">').html("$"+item[i].priceMax);
                        console.log(item[i].foodItem);
                        var inputHolder = $('<div class = "col-sm-2">');
                        var inputPrice = $('<input type = "number" class = "form-control money" step="0.01" min="0">');
                        inputHolder.append(inputPrice);
                        var checkBox = $('<input type = "checkbox">');
                        var checkPrice = $('<div class = "form-group col-sm-1">');
                        checkPrice.append(checkBox);

                        itemInfoRow.append(itemNumber);
                        itemInfoRow.append(theItem);
                        itemInfoRow.append(theMax);
                        itemInfoRow.append(inputHolder);
                        itemInfoRow.append(checkPrice);

                        itemWrapper.append(itemInfoRow);

                        if (!(item[i].comments === "")) {
                            var commentRow = $('<div>').html(item[i].comments);
                            itemWrapper.append(commentRow);
                        }


                        if (item[i].hasAlt == true) {
                            itemWrapper.append('<br>');
                            var alt = item[i].alt;
                            var altItemInfoRow = $('<div class = "row">');
                            var altItemNumber = $('<div class ="col-sm-2" style = "font-weight:bold">').html("Alt #" + k);
                            var altFoodItem = $('<div class = "col-sm-4">').html(alt.foodItem);
                            var altPriceMax = $('<div class = "col-sm-3">').html("$"+alt.priceMax);
                            var altInputHolder = $('<div class = "col-sm-2">');
                            var altInputPrice = $('<input type = "number" step="0.01" min="0" class="form-control money">');
                            altInputHolder.append(altInputPrice);
                            var altCheckBox = $('<input type = "checkbox">');
                            var altCheckPrice = $('<div class = "form-group col-sm-1">');
                            altCheckPrice.append(altCheckBox);

                            altItemInfoRow.append(altItemNumber);
                            altItemInfoRow.append(altFoodItem);
                            altItemInfoRow.append(altPriceMax);
                            altItemInfoRow.append(altInputHolder);
                            altItemInfoRow.append(altCheckPrice);

                            itemWrapper.append(altItemInfoRow);

                            var altComment = alt.comments;

                            if (!(alt.comments === "")) {
                                var altCommentRow = $('<div>').html(alt.comments);
                                itemWrapper.append(altCommentRow);
                            }
                        }
                        theForm.append(itemWrapper);
                    }
                });
                var totalWrapper = $('<div class = "well well-lg">');
                $('<h3 style = "font-weight:bold; color:#CC0000">').html("Total").appendTo(totalWrapper);
                $('<p>').html("Subtotal: $ <span id='subtotal'>0.00</span>").appendTo(totalWrapper);
                $('<p>').html("Total, incl. fee and tax: $ <span id='total'>0.00</span>").appendTo(totalWrapper);

                $('<button type="submit" class="btn btn-primary">').appendTo(totalWrapper);
                theForm.append(totalWrapper);
                theForm.appendTo($('#orders'));
            });

            $(function() {
                var sum = 0;
                $('.money').on('change', function() {
                    sum = 0.0;
                    console.log("yay I am changing");
                    $('.money').each(function() {
                        if ($.isNumeric(parseFloat(this.value))) {
                            sum += parseFloat(this.value);
                        }
                        console.log(sum);
                    });
                    $('#subtotal').html(sum);
                    var total = sum*${percentFee} + sum*(.0825) + sum + ${flatFee};
                    console.log(total.toFixed(2));
                    var stringTotal = total.toFixed(2).toString();
                    console.log(stringTotal);
                    $('#total').html(stringTotal);
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
                            <div id="orders"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>
</t:template>