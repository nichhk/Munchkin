<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
            $(function() {
                console.log("here");
                $.each( ${responseJson}, function(index, order) {
                    for( i = 0; i<order.primaryItems.length;i++){
                        order.primaryItems[i];
                        order.altItems[i];
                        order.primaryMax[i];
                        order.altMax[i];
                        order.primaryComments[i];
                        order.altComments[i];
                        $('<div id = "toClick">').appendTo("#orders")
                                .append("Food Item: "+order.primaryItems[i])
                                .append(" \n Max price:" + order.primaryMax[i])
                                .append("\n Comments:" + order.primaryComments[i]);






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