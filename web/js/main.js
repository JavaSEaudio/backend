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


var app = angular.module("app", ["ngRoute",'ngAnimate', "app.controllers", "app.startup"]);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/main', {
                templateUrl: 'partials/music.html',
                controller: 'main'
            }).
            when('/audio/private', {
                    templateUrl: 'partials/private.music.html',
                    controller: 'audio.private'
            }).
            when('/audio/buylist', {
                templateUrl: 'partials/music.html',
                controller: 'audio.buylist'
            }).
            when('/audio/getbyrate', {
                templateUrl: 'partials/music.html',
                controller: 'audio.getbyrate'
            }).
            when('/audio/getfree', {
                templateUrl: 'partials/music.html',
                controller: 'audio.getfree'
            }).
            when('/song/:musicId', {
                templateUrl: 'partials/song.html',
                controller: 'song'
            }).
            when('/private/song/:musicId', {
                templateUrl: 'partials/private.song.html',
                controller: 'private.song'
            }).
            when("/profile/user/:user", {
                templateUrl : "partials/profile.html",
                controller: "profile.user"
            }).
            when("/profile/pwd/:uniq", {
                templateUrl : "partials/pwd.html",
                controller: "profile.pwd"
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
            when("/profile/confirm/email/:uniq", {
                templateUrl : "partials/confirm-email.html",
                controller: "profile.confirm.email"
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
            when("/private/audio/edit/:id", {
                templateUrl : "partials/private.audio.edit.html",
                controller: "private.audio.edit"
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
            when("/admin/useredit/:id", {
                templateUrl: "partials/admin.useredit.html",
                controller: "admin.useredit"
            }).
            otherwise({
                redirectTo: '/main'
            });
    }]);


