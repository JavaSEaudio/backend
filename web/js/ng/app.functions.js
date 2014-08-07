var startup = angular.module("app.startup", []);

startup.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});


//
//startup.directive("scroll", function () {
//    return function(scope, element, attrs) {
//        angular.element(element).bind("scroll", function() {
//            console.log("Scroll")
//            scope[attrs.scroll];
//            scope.$apply();
//        });
//    };
//});


startup.filter("timeFilter", function() {
    return function(time) {
          var m = Math.round(time / 60); // Минуты
          var s = time % 60; // Секунды
          if(s < 10) s = "0" + s;
          return m + ":" + s;
    };
});

startup.filter("userAccessFilter", function() {
    return function(input) {
       switch(input) {
           case 0: return "User";
           case 1: return "Moderator";
           case 2: return "Admin";
           default: return "UFO";
       }
    };
});

startup.run(function($window, $interval, $rootScope, $http, $location) {

    $rootScope._debug = function(e) {
        console.log(e);
    };

    $http.get("/rest/test/check").success(function(data) {
        $rootScope.logined = "false" !== data;
        if($rootScope.logined) {
            $http.get("/rest/user/mylogin").success(function(data) {
                $rootScope.myInfo = data.userDTO;
            });
        }
    });

    // Покупка
    $rootScope.buy = function(music) {
        alertify.confirm("Are you sure?", function(e) {
            if(e) {
                $http.get("/rest/buy/audio?audioID=" + music.id).success(function (data) {
                    console.log("Куплено!");
                    alertify.success("Куплено!");
                    window.location.replace("/");
                });
            }
        });
    };

    $rootScope.like = function(music) {
        $http.get("/rest/like/putlike?id=" + music.id).success(function(data) {
            console.log(data);
            music.countLike++;
            music.like = true;
        });
    };

    $rootScope.audioEdit = function(music) {
        $location.path("/audio/edit/" + music.id);
    };

    // --------------------- Player -----------------------------------------
    $rootScope.player = {
        node: $("#player audio")[0],
        setMusic: function(cm) {
            this.currentMusic = cm;
            console.log($(this.node).attr("src",  this.currentMusic.linkFile));
        },
        play: function() {
            this.node.play();
        },
        pause: function() {
            this.node.pause();
        },
        music: function() {
            return this.currentMusic;
        },

        maxTime: 0,
        time: 0,

        setTime: function(newTime) {
            node.currentTime = newTime;
        },

        active: function() {
            return this.currentMusic === undefined;
        }
    };

    $interval(function() {
        $rootScope.player.time = Math.round($rootScope.player.node.currentTime);

        // TODO:Сделать по человечески, event
        $rootScope.player.maxTime = Math.round($rootScope.player.node.duration);
    }, 1000);


    $rootScope.play = function(music) {
        if($rootScope.player.currentMusic === music) {
            if(!$rootScope.player.node.paused) {
                console.log("PAUSE");
                $rootScope.player.node.pause();
            } else {
                console.log("PLAY");
                $rootScope.player.node.play();
            }
        } else {
            $rootScope.player.setMusic(music);
            $rootScope.player.play();
        }
    };
    // --------------------------------------------------------------------------------------

    // Search
    $rootScope.search = function(q) {
        console.log(q);
        $location.path("/search/" + q);
    };


});