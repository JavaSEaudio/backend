$(function() {
    window.onscroll = function() {
        if($(window).scrollTop() > 180) {
            $(".header__search_wrapper").addClass("top");
        } else {
            $(".header__search_wrapper").removeClass("top");
        }
//        addOnScroll();
    };
});


var app = angular.module("app", ["ngRoute", "app.controllers", "app.startup"]);

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
            when("/profile/user/:user", {
                templateUrl : "partials/profile.html",
                controller: "profile.user"
            }).
            //Profile
            when("/profile/exit", {
                templateUrl : "partials/profile.html",
                controller: "profile.exit"
            }).
            when("/profile/signin", {
                templateUrl : "partials/signin.html",
                controller: "profile.signin"
            }).
            when("/profile/reg", {
                templateUrl : "partials/reg.html",
                controller: "profile.reg"
            }).
            when("/profile/edit", {
                templateUrl : "partials/profile.edit.html",
                controller: "profile.edit"
            }).
            //Audio
            when("/audio/upload", {
                templateUrl : "partials/audio.upload.html",
                controller: "audio.upload"
            }).
            when("/audio/edit/:id", {
                templateUrl : "partials/audio.edit.html",
                controller: "audio.edit"
            }).
            //Search
            when("/search/:q", {
                templateUrl : "partials/music.html",
                controller: "audio.search"
            }).
            //Admin
            when("/admin/users", {
               templateUrl: "partials/admin.users.html",
               controller: "admin.users"
            }).
            otherwise({
                redirectTo: '/main'
            });
    }]);


