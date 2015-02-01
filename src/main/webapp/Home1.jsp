<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
        <script>
        $(function() {
            $.each( ${responseJson}, function(index, trip) {
                $('<div>').appendTo('#trips')
                        .append($('<span>').html(trip.name))
                        .append($('<span>').html(trip.rating))
                        .append($('<span style = "font-size:160%">').html(trip.location))
                        .append($('<span>').html(trip.eta))
                        .append($('<p>').html("thanks for clicking").hide())
                        .addClass('trip')
            });
        });
        $(function () {
            $('#trips').on('click', 'div', function() {
                $($(this).find('p')[0]).slideDown("slow");
            });
        });
    </script>
    </jsp:attribute>
