<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            $(function() {
                console.log("here");
                $.each( ${responseJson}, function(index, order) {
                    for( i = 0; i<order.primaryItems.length;i++){
                        var pPrice = "$" + order.primaryMax[i].toString();
                        var primaryRow = $('<div class = "row" id = "primaryRow">');
                        var k = i+1;
                        var primaryFood = $('<div>').html("Food Item #" + k + ": "+order.primaryItems[i] + " (" + pPrice + ")");
                        primaryRow.append(primaryFood);
                        if (order.primaryComments[i] != null && order.primaryComments[i].localeCompare("") != 0) {
                            var comments = $('<div>').html("Comments: " + order.primaryComments[i]);
                            primaryRow.append(comments);
                        }
                        var altRow = $('<div class = "row" id = "altRow">');
                        if (order.altItems[i] == null) {
                            console.log("hello" + i);
                        }
                        if (order.altItems[i]==order.primaryItems[i]) {
                            console.log("yello" + i);
                        }
                        if (order.altItems[i] != null && (!(order.altItems[i]===order.primaryItems[i]))) {
                            console.log("hey");
                            var altPrice = "$" + order.altMax[i].toString();
                            var altFood = $('<div style = "color : #A0A0A1">').html("Alternate Food Item #" + k  + ": "+order.altItems[i] + " (" + altPrice + ")");
                            altRow.append(altFood);
                            if (order.altComments[i] != null && order.altComments[i].localeCompare("") != 0) {
                                var comments = $('<div style = "color : #A0A0A1">').html("Comments: " + order.altComments[i]);
                                altRow.append(comments);
                            }
                        }
                        $('<div id = "toClick">').appendTo("#orders")
                                .append(primaryRow)
                                .append(altRow)
                                .addClass('well well-lg')
                    }
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