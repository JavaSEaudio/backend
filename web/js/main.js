var musicTpl = "";
var currentPage = 1;

$(function() {
	window.onscroll = function() {
		if($(window).scrollTop() > 180) {
			$(".header__search_wrapper").addClass("top");
		} else {
			$(".header__search_wrapper").removeClass("top");
		}
        addOnScroll();
	};


    // Music loading
    $.get("/music.html", function(tpl) {
        $.getJSON("/rest/audio/get?count=10&page=" + currentPage, function(data) {
            musicTpl = tpl;
            addMusicToPage(data);
        });
    });
    //

    $.get("/rest/test/check", function(data) {
        if(data) {
            //Logined
            logined();
        } else {
            //Not login
        }
    });

    $(".login-anchor").click(function() {
        $(".popup").fadeToggle(500);
        return false;
    });

    $(".popup .login").ajaxForm(function(data) {
        if(data) {
            alert("You are enter! Welcome!");
            $(".popup").fadeOut(500);
            logined();
        } else {
            alert("Password is wrong!");
        }
    });

    $(".search-form").submit(function() {
        searchMusic($("#search-text").val());
        return false;
    });

});

function logined() {
    $(".login-anchor").parent().hide();
}

function addMusicToPage(data) {
    var out = "";
    for(var i = 0; i < data.audioEntity.length; ++i) {
        out += musicTpl.replace("{name}", data.audioEntity[i].name)
            .replace("{artist}", data.audioEntity[i].artist)
            .replace("{audio}", data.audioEntity[i].linkFile)
            .replace("{album-img}", data.audioEntity[i].linkImage);

    }

    $("#content-music").append(out);

    $(".music .img").click(function() {
//        console.log($(this).parent().find("audio")[0]);
        $("audio").each(function() {
            this.pause();
        });
       $(this).parent().find("audio")[0].play();
    });
}

function searchMusic(criterion) {
    $.get("/rest/audio/search?criterion=" + criterion, function(data) {
        $("#content-music").empty();
        addMusicToPage(data);
    });
}

function addOnScroll() {
    console.log($("body").height());
    console.log($(window).scrollTop());
    if($("body").height() - 100 < $(window).scrollTop() + $(window).height() ) {
        console.log("add");
        currentPage++;
        $.getJSON("/rest/audio/get?count=10&page=" + currentPage, function(data) {
            addMusicToPage(data);
        });
    }
}