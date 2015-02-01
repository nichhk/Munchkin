<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            $(function() {
                var theForm = $('<form class="form" role="form" action="/transaction" method="post">');
                var i = 0;
                var totalPrice = 0;
                console.log(${responseJson});
                $.each( ${responseJson}, function(index, order) {
                    if (i = 0) {
                        var nameRow = $('<div class = "row">');
                        var theName = $('<div style = "font-size:160%; font-weight:bold">').html(order.customer);
                        nameRow.append(theName);
                        theForm.append(nameRow);

                        var numberRow = $('<div class = "row">');
                        var theNumber = $('<div style = "font-size:130%; font-weight:bold">').html(order.number);
                        numberRow.append(theNumber);
                        theForm.append(numberRow);

                        var keyStrings = $('<div class> = "row"');
                        var theItem = $('<div class = "col-sm-3">').html("Item");
                        var theMax = $('<div class = "col-sm-3">').html("Max");
                        var actual = $('<div class = "col-sm-3">').html("Actual");
                        keyStrings.append(theItem);
                        keyStrings.append(theMax);
                        keyStrings.append(actual);
                        theForm.append(keyStrings);
                    }
                    if (i > 0) {
                        var data = order.item;
                        var totalRow = $('<div class = "row">');
                        var pItem = $('<div class = "col-sm-3" style = "font-weight:bold">').html(data[0]);
                        var pMaxPrice = $('<div class = "col-sm-3">').html(data[1]);
                        var actualPrice = $('<div class = "form-group col-sm-3">');
                        var inputPrice = $('<input type = "number" class = "form-control" step="0.01" min="0" class="money">');
                        actualPrice.append(inputPrice);
                        var checkBox = $('<input type = "checkbox">');
                        var checkPrice = $('<div class = "form-group col-sm-3">');
                        checkPrice.append(checkBox);
                        totalRow.append(pItem);
                        totalRow.append(pMaxPrice);
                        totalRow.append(actualPrice);
                        totalRow.append(checkPrice);
                        theForm.append(totalRow);
                        $('<input style="display:hidden" type="text" name="number">').val(order.number).appendTo(theForm);
                        if (data[2] != null) {
                            var altItem = $('<div class = "col-sm-3" style = "color : #A0A0A1">').html(data[2]);
                            var altMaxPrice = $('<div class = "col-sm-3" style = "color : #A0A0A1">').html(data[3]);
                            var secondRow = $('<div class = "row">');
                            var altActualPrice = $('<div class = "form-group col-sm-3" style = "color : #A0A0A1">');
                            var altInputPrice = $('<input type = "number" class = "form-control" step="0.01" min="0" class="money">');
                            altActualPrice.append(altInputPrice);
                            var altCheckBox = $('<input type = "checkbox">');
                            var altCheckPrice = $('<div class = "form-group col-sm-3">');
                            altCheckPrice.append(checkBox);
                            secondRow.append(altItem);
                            secondRow.append(altMaxPrice);
                            secondRow.append(altActualPrice);
                            secondRow.append(altCheckPrice);
                            theForm.append(secondRow);
                        }
                    }
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
                $('#total').html((sum*${percentFee} + 100.0 + 8.25)/100.0+${flatFee});
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