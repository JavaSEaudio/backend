var main = angular.module("app.controllers", []);

//Main page
main.controller("main", function($rootScope, $scope, $http) {
    $http.get("/rest/audio/get?count=20&page=1").success(function(data) {
        $scope.songs = data.audioEntity;
    });

    $rootScope.searchQuery = "";

});

//Song page
main.controller("song", function($scope, $http, $routeParams) {
    $http.get("rest/audio/getbyid?id=" + $routeParams.musicId)
        .success(function(data) {
            $scope.song = data.audioEntity;
        });
});

//Profile
main.controller("profile.exit", function($location, $http) {
    console.log("exit");
    $http.get("/rest/exit");
    $location.path("main/");
    window.location.replace("/");
});

main.controller("profile.signin", function($http) {
    console.log("signin");
    $http.get("");
});

main.controller("profile.reg", function($http) {
    console.log("reg");
    $http.get("");
});

main.controller("profile.user", function($http, $scope, $route, $routeParams) {
    $scope.user = {
        login: $routeParams.user
    }

    $http.get("/rest/user/login/?login=" + $routeParams.user).success(function(data) {
        console.log(data);
        $scope.user = data.userDTO;
    });

});

main.controller("profile.edit", function($http, $scope, $location) {
    $http.get("/rest/user/mylogin")
        .success(function(data) {
            console.log(data);
            $scope.user = {
              name: data.userDTO.name,
              email: data.userDTO.email,
              information: data.userDTO.information
            };
        })
        .error(function(data, status) {
               if(status === 500) {
                   $location.path("/profile/signin");
               } else {
                   $location.path("/");
               }
        });
});


//Audio
main.controller("audio.upload", function() {
    //Когда нибудь реализуем... В другом году
});

main.controller("audio.edit", function($scope, $http, $routeParams) {
    $http.get("rest/audio/getbyid?id=" + $routeParams.id)
        .success(function(data) {
            console.log(data);
            $scope.song = data.audioEntity;
    });
});


main.controller("audio.search", function($http, $scope, $routeParams) {
    var q = $routeParams.q;
    console.log("Search " + q);
    $http.get("/rest/audio/search?criterion=" + q).success(function(data) {
        $scope.songs = data.audioEntity;
    });
});