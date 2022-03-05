  
///*首页-banner*/
//$(".fullSlide").hover(function(){
//    $(this).find(".prev,.next").start(true, true).fadeTo("show", 0.5)
//	},
//	function(){
//		$(this).find(".prev,.next").fadeOut()
//	});
//    $(".fullSlide").slide({
//        titCell: ".hd ul",
//        mainCell: ".bd ul",
//        effect: "fold",
//        autoPlay: true,
//        autoPage: true,
//        trigger: "click",
//        startFun: function(i) {
//            var curLi = jQuery(".fullSlide .bd li").eq(i);
//            if ( !! curLi.attr("_src")) {
//                curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
//            }
//        }
//    });


///*服务项目-网络推广*/
//$('.panel-title').on('click', function(){
//		$(this).removeClass('glyphicon-plus').addClass('glyphicon-minus').parent().css("background","linear-gradient(120deg,#6dc6e4,#dbbefc)");
//		$('.panel-title').not($(this)).removeClass('glyphicon-minus').addClass('glyphicon-plus').parent().css("background","#ededed");

//	 if($(this).parent().parent().find(".collapse").hasClass("in")){
//		$(this).removeClass('glyphicon-minus').addClass('glyphicon-plus').parent().css("background","#ededed");
//	 }

//});

function setseasonimg(season) {
    cleanseason();
    var in_bg = '../Content/web/img/' + season + "/in-bg.png";
    var in_barr = '../Content/web/img/' + season + "/in-banner.jpg";
    $(".in-foot").css("background-image", "url(" + in_bg + ")");
    $(".in-banner").css("background-image", "url(" + in_barr + ")");
    $("#li_" + season).addClass("active-s");
}
function cleanseason() {
    $("#li_spring").removeClass("active-s");
    $("#li_summer").removeClass("active-s");
    $("#li_autumn").removeClass("active-s");
    $("#li_winter").removeClass("active-s");
}