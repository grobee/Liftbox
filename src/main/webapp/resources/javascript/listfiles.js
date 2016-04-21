$(document).ready(function(){


    $("#check_all").click(function() {
        if($('#check_all').is(':checked')){
            $(".check_field").prop('checked', true);
            $(".field").css("background-color", "#d3ffa9");
            $(".properties").show();
        }else{
            $(".check_field").prop('checked', false);
            $(".field").css("background-color", "#fff");
            $(".properties").hide();
        }
    });

});

