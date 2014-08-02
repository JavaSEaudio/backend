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

startup.run(function($rootScope, $http, $location) {

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
    }

    //Player
    $rootScope.player = {
        node: $("#player audio")[0],
        setMusic: function() {
            console.log($(this.node).attr("src",  $rootScope.currentMusic.linkFile));
        },
        play: function() {
            this.node.play();
        },
        pause: function() {
            this.node.pause();
        }
    };

    $rootScope.play = function(music) {
        $rootScope.currentMusic = music;
        $rootScope.player.setMusic();
        $rootScope.player.play();
    };

    // Search
    $rootScope.search = function(q) {
        console.log(q);
        $location.path("/search/" + q);
    };


});