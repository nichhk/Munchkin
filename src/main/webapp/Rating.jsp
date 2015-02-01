<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<style>
    span:hover{cursor:pointer;}
</style>
*****

NOTE TO SELF: MAKE SURE TO PASS IN USERID TO THIS PAGE

*****
<t:template isApproved="${isApproved}" log="${log}" page="${page}">
    <jsp:attribute name="scripts">
    <script>
        $(function(){
            //go through stars up to clicked and change to full
            $('#starRating').on('click', 'span', function(){
                var starID = $(this).attr("id");
                console.log(starID);
                var num = parseInt(starID.split('star')[1]);
                console.log(num);
                $('#numStars').val(num);
                for (var i = 1; i <= num; i++) {
                    var numString = i.toString();
                    console.log(numString);
                    var starString = "star";
                    var idName = starString.concat(numString);
                    if($('#'+idName).hasClass('glyphicon-star-empty')){
                        $('#'+idName).removeClass("glyphicon-star-empty").addClass("glyphicon-star");
                    }
                }
                for (var i = num + 1; i <= 5; i++) {
                    var numString = i.toString();
                    var starString = "star";
                    var idName = starString.concat(numString);
                    if($('#'+idName).hasClass('glyphicon-star')){
                        $('#'+idName).removeClass("glyphicon-star").addClass("glyphicon-star-empty");
                    }
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
                        <div class="col-md-12">
                        <h2>Rate the Delivery</h2>
                        <form role="form" id="ratingForm">
                            <input type="text" name="userId" value="${userId}" style="display:none">
                            <div class="form-group" id = "starRating">
                                <input type = "number" id = "numStars" name = "numStars" value = 0 style = "display : none">
                                <label for="starRating">How was the delivery experience?</label>
                                <span class="glyphicon glyphicon-star-empty" id = "star1" style="color:gold"></span>
                                <span class="glyphicon glyphicon-star-empty" id = "star2" style="color:gold"></span>
                                <span class="glyphicon glyphicon-star-empty" id = "star3" style="color:gold"></span>
                                <span class="glyphicon glyphicon-star-empty" id = "star4" style="color:gold"></span>
                                <span class="glyphicon glyphicon-star-empty" id = "star5" style="color:gold"></span>
                            </div>
                            <div class="form-group">
                                <label for="comments">Comments:</label>
                                <textarea class="form-control" rows="5" name="comment" id="comments" form="ratingForm"></textarea>
                            </div>
                            <div class = "form-group">
                                <button type="submit" class="btn btn-default" style="margin-top:10px">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </jsp:attribute>
</t:template>