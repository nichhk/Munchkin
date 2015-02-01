<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<style>
    span:hover{cursor:pointer;}
</style>

<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
    <script>
        $(function(){
            //go through stars up to clicked and change to full
            $('#starRating').on('click', 'span', function(){
                var starID = $(this).attr('id');
                var num = parseInt(starID.split('star'));
                $('#numStars').val(num);
                for (var i = 1; i <= num; i++) {
                    var numString = i.toString();
                    var starString = "star";
                    var idName = starString.concat(numString);
                    if($('#idName').hasClass('glyphicon-star-empty')){
                        $('#idName').removeClass("glyphicon-star-empty").addClass("glyphicon-star");
                    }
                }
                for (var i = num + 1; i <= 5; i++) {
                    var numString = i.toString();
                    var starString = "star";
                    var idName = starString.concat(numString);
                    if($('#idName').hasClass('glyphicon-star')){
                        $('#idName').removeClass("glyphicon-star").addClass("glyphicon-star-empty");
                    }
                }
            });
        });
    </script>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="site-wrapper-inner">
            <h2>Rate the Delivery</h2>
            <form role="form">
                <div class="form-group" id = "starRating">
                    <input type = "number" id = "numStars" name = "numStars" value = 0 style = "display : none">
                    <label for="starRating">How was the delivery experience?</label>
                    <span class="glyphicon glyphicon-star-empty" id = "star1"></span>
                    <span class="glyphicon glyphicon-star-empty" id = "star2"></span>
                    <span class="glyphicon glyphicon-star-empty" id = "star3"></span>
                    <span class="glyphicon glyphicon-star-empty" id = "star4"></span>
                    <span class="glyphicon glyphicon-star-empty" id = "star5"></span>
                </div>
                <div class="form-group">
                    <label for="comment">Additional Comments:</label>
                    <textarea class="form-control" rows="5" id="comment"></textarea>
                </div>
                <div class = "form-group">
                    <button type="submit" class="btn btn-default" style="margin-top:10px">Submit</button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</t:template>