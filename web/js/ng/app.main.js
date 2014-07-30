var main = angular.module("app.controllers", []);

//Main page
main.controller("main", function($rootScope, $scope, $http) {

    $rootScope.player = {
        node: $("#player audio")[0],
        music: function(music) {
            this.node.attr("src", music.linkFile);
        },
        play: function() {
            this.node.play();
        },
        pause: function() {
            this.node.pause();
        }
    };

    $http.get("/rest/audio/get?count=20&page=1").success(function(data) {
        $scope.songs = data.audioEntity;
    });

    $scope.play = function(music) {
        $rootScope.player.music(music);
        $rootScope.player.play();
    }
});

//Song page
main.controller("song", function($scope, $http, $routeParams) {
    $http.get("rest/audio/getbyid?id=" + $routeParams.musicId)
        .success(function(data) {
            $scope.song = data.audioEntity;
        });
});