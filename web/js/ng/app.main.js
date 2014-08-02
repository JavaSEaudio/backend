var main = angular.module("app.controllers", []);

//Main page
main.controller("main", function($location, $rootScope, $scope, $http) {
    $http.get("/rest/audio/get?count=20&page=1").success(function(data) {
        $scope.songs = data.audioDTO;
        console.log("Songs was updated.");
    });

    $scope.edit = function(music) {
        $location.path("/audio/edit/" + music.id);
    };

    $rootScope.searchQuery = "";

});

main.controller("audio.getbyrate", function($scope, $http) {
    $http.get("/rest/rate/like").success(function(data) {
        $scope.songs = data.audioDTO;
        console.log("Songs was updated.");
    });
});

//Song page
main.controller("song", function($location, $scope, $http, $routeParams) {
    $http.get("rest/audio/getbyid?id=" + $routeParams.musicId)
        .success(function(data) {
            $scope.song = data.audioDTO;
            console.log(data);
        });

    $scope.edit = function(music) {
        $location.path("/audio/edit/" + music.id);
    };
});

//Profile
main.controller("profile.exit", function($location, $http) {
    console.log("exit");
    $http.get("/rest/exit");
    $location.path("main/");
    window.location.replace("/");
});

main.controller("profile.signin", function($scope, $http) {
    console.log("signin");
    //$http.get("");
    $scope.login = function() {
        window.location.replace("/");
    }
});

main.controller("profile.reg", function($http) {
    console.log("reg");
    $http.get("");
});

main.controller("profile.user",
function($rootScope, $location, $http, $scope, $route, $routeParams) {
    $scope.user = {
        login: $routeParams.user
    };

    $http.get("/rest/user/login/?login=" + $routeParams.user).success(function(data) {
        console.log(data);
        $scope.user = data.userDTO;

    });

    $scope.edit = function(user) {
        if(user.id == $rootScope.myInfo.id) {
            $location.path("/profile/edit");
        } else if($rootScope.myInfo.access == 2) {
            $location.path("/admin/useredit/" + user.id);
        }
    };


    $scope.audioEdit = function(music) {
        $location.path("/song/" + music.id);
    };

        $http.get("/rest/list/my").success(function(data) {
            $scope.musics = data.audioDTO;
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

main.controller("audio.edit", function($location, $scope, $http, $routeParams) {
    $http.get("rest/audio/getbyid?id=" + $routeParams.id)
        .success(function(data) {
            console.log(data);
            $scope.song = data.audioDTO;
    });

    $scope.audioDelete = function(song) {
        $http.get("/rest/file/delete?id=" + song.id).success(function(data) {
            alert("Audio was deleted!");
            $location.path("/");
        });
    };
});


main.controller("audio.search", function($http, $scope, $routeParams) {
    var q = $routeParams.q;
    console.log("Search " + q);
    $http.get("/rest/audio/search?criterion=" + q).success(function(data) {
        $scope.songs = data.audioDTO;
    });
});



//Admin
main.controller("admin.users", function($http, $scope, $location) {

    $scope.userPage = function(user) {
        $location.path("/profile/user/" + user.login);
    }

    $scope.ban = function(user) {
        $http.get("/rest/admin/ban/?userID=" + user.id).success(function(d) {
            console.log("Ban function: ", d);
        });
    }

    $http.get("/rest/user/all").success(function(data) {
        $scope.users = data.userDTO;
        console.log(data);
    });
});

main.controller("admin.useredit", function($http, $scope, $routeParams) {
    $http.get("/rest/user/id?id=" + $routeParams.id).success(function(data) {
        $scope.user = data.userDTO;
    });
});