<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            var theForm = $('<form class="form" role="form" action="/transaction" method="post">');
            var totalPrice = 0;
            var i = 0;
            var heading = $('<div class = "row">');
            var headingName = $('<div> class = "col-sm-4" style = "color: #CC0000"').html("Item name");
            var headingMaxPrice = $('<div> class = "col-sm-4" style = "color: #CC0000"').html("Max Price");
            var headingActualPrice = $('<div> class = "col-sm-2" style = "color: #CC0000"').html("Item name");

            heading.append(headingName);
            heading.append(headingMaxPrice);
            heading.append(headingActualPrice);
            theForm.append(heading);

            $(function() {
                $.each(${items}, function (index, item) {
                    var itemWrapper = $('<div class = "well">');
                    i++;
                    //row for this food item
                    var itemNumberRow = $('<div class = "row" style = "font-weight:bold">').html("Item number: " + i);
                    var itemInfoRow = $('<div class = "row">');
                    var theItem = $('<div class = "col-sm-4">').html(item.foodItem);
                    var theMax = $('<div class = "col-sm-4">').html(item.priceMax);
                    var inputHolder = $('<div class = "col-sm-2">');
                    var inputPrice = $('<input type = "number" class = "form-control" step="0.01" min="0" class="money">');
                    var checkBox = $('<input type = "checkbox">');
                    var checkPrice = $('<div class = "form-group col-sm-2">');
                    checkPrice.append(checkBox);

                    itemInfoRow.append(theItem);
                    itemInfoRow.append(theMax);
                    itemInfoRow.append(inputHolder);
                    itemInfoRow.append(checkPrice);

                    itemWrapper.append(itemInfoRow);

                    var comments = item.comments;
                    if (!(comments === "")) {
                        var commentRow = $('<div class = "row">').html(comments);
                        itemWrapper.append(commentRow);
                    }


                    if (item.hasAlt == true) {
                        var altItemNumberRow = $('<div style = "font-weight:bold">').html("Alternate Item number: " + i);
                        var alt = item.alt;
                        var altItemInfoRow = $('<div class = "row">');
                        var altFoodItem = $('<div class = "col-sm-4">').html(alt.foodItem);
                        var altPriceMax = $('<div class = "col-sm-4">').html(alt.priceMax);
                        var altInputPrice = $('<input type = "number" class = "form-control" step="0.01" min="0" class="money">');
                        var altCheckBox = $('<input type = "checkbox">');
                        var altCheckPrice = $('<div class = "form-group col-sm-2">');
                        altCheckPrice.append(checkBox);
                        itemWrapper.append(altItemNumberRow);
                        altItemInfoRow.append(altFoodItem);
                        altItemInfoRow.append(altPriceMax);
                        altItemInfoRow.append(altInputPrice);
                        altItemInfoRow.append(altCheckPrice);
                        itemWrapper.append(altItemInfoRow);

                        var altComment = alt.comments;

                        if (!(altComment === "")) {
                            var altCommentRow = $('<div class = "row">').html(comments);
                            itemWrapper.append(altCommentRow);
                        }
                    }
                    theForm.append(itemWrapper);
                });
                $('<p>').html("Subtotal: $ <span id='subtotal'>0.00</span>").appendTo(theForm);
                $('<p>').html("Total, incl. fee and tax: $ <span id='total'>0.00</span>").appendTo(theForm);
                $('<button type="submit" class="btn btn-primary">').appendTo(theForm);
                theForm.appendTo($('#orders'));
            });

            $(function() {
                var sum = 0;
                $('.money').on('change', function() {
                    sum = 0;
                    $('.money').each(function() {
                        sum += parseFloat(this.value);
                    });
                    $('#subtotal').html(sum);
                    $('#total').html((sum*${percentFee} + 100.0 + 8.25)/100.0+0${flatFee});
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