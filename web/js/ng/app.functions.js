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

startup.run(function($interval, $rootScope, $http, $location) {

    $rootScope._debug = function(e) {
        console.log(e);
    }

    $http.get("/rest/test/check").success(function(data) {
        $rootScope.logined = "false" !== data;
        if($rootScope.logined) {
            $http.get("/rest/user/mylogin").success(function(data) {
                $rootScope.myInfo = data.userDTO;//Ну и лапша-код)
            });
        }
    });

    // Покупка
    $rootScope.buy = function(music) {
        $http.get("/rest/buy/audio?audioID="+music.id).success(function(data) {
            console.log("Куплено!");
            alert("Куплено!");
            window.location.replace("/");
            $http.get("/rest/user/mylogin").success(function(data) {
                console.log(data);
            });
        });
    };

    $rootScope.like = function(music) {
        $http.get("/rest/like/putlike?id=" + music.id).success(function(data) {
            console.log(data);
            music.like = true;
        });
    };

    $rootScope.audioEdit = function(music) {
        $location.path("/audio/edit/" + music.id);
    };

    //Player
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
        $rootScope.player.setMusic(music);
        $rootScope.player.play();
    };

    // Search
    $rootScope.search = function(q) {
        console.log(q);
        $location.path("/search/" + q);
    };


});