
var app = angular.module("app", ["ngRoute", "app.controllers", "app.funcs"]);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/main', {
                templateUrl: 'partials/music.html',
                controller: 'main'
            }).
            when('/song/:musicId', {
                templateUrl: 'partials/song.html',
                controller: 'song'
            }).
            otherwise({
                redirectTo: '/main'
            });
    }]);


