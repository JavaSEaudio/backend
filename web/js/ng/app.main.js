var main = angular.module("app.controllers", []);

//Main page
main.controller("main", function($location, $rootScope, $scope, $http) {

    $scope.songs = [];
    var counter = 1;

    $scope.loadMore = function() {
        $http.get("/rest/audio/get?count=5&page=" + counter).success(function(data) {
            $scope.songs = $scope.songs.concat(data.audioDTO);
            console.log($scope.songs);
            console.log("Songs was updated.");
            counter++;
        });
    };

    $scope.loadMore();

    $rootScope.searchQuery = "";

});

main.controller("audio.getbyrate", function($scope, $http) {
    $http.get("/rest/rate/like").success(function(data) {
        $scope.songs = data.audioDTO;
        console.log("Songs was updated.");
    });
});


main.controller("audio.getfree", function($scope, $http) {
    $http.get("/rest/audio/free").success(function(data) {
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

            $http.get("/rest/user/id/?id=" + $scope.song.userid).success(function(data) {
                console.log(data);
                $scope.user = data.userDTO;
            });

            $scope.edit = function(music) {
                $location.path("/audio/edit/" + $scope.song.id);
            };

        });

    $http.get("/rest/comment/get?audio=" + $routeParams.musicId).success(function(data) {
        console.log(data);
        $scope.comments = data.commentsDTO;

        $scope.deleteComment = function(comment) {
            if(confirm("Are you sure?")) {
                $http.get("/rest/comment/delete?comment=" + comment.id)
                    .success(function(data) {
                        console.log(data);
                        location.reload();
                    });
            }
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
    var q = ""+$routeParams.q;
    console.log("Search " + q);
    $.post("/rest/audio/search",{"criterion":q}, function(data) {
        $scope.songs = data.audioDTO;
    });
});



// ----------------------------- Admin --------------------------
main.controller("admin.users", function($http, $scope, $location) {

    $scope.userPage = function(user) {
        $location.path("/profile/user/" + user.login);
    };

    $scope.ban = function(user) {
        if(confirm("Are you sure?")) {
            $http.get("/rest/admin/ban/?userID=" + user.id).success(function(d) {
                console.log("Ban function: ", d);
            });
        }
    };

    $scope.unban = function(user) {
        $http.get("/rest/admin/unban/?userID=" + user.id).success(function(d) {
            console.log("UnBan function: ", d);
        });
    };

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


// --------------------------  Private -----------------------------------
main.controller("private.audio.edit", function($routeParams, $scope, $http) {
    $http.get("rest/private/audio/getbyid?id=" + $routeParams.id)
        .success(function(data) {
            console.log(data);
            $scope.song = data.privateDTO;
        });

    $scope.audioDelete = function(song) {
        $http.get("/rest/private/file/delete?id=" + song.id).success(function(data) {
            alert("Audio was deleted!");
            $location.path("/");
        });
    };
});

main.controller("private.song", function($location, $scope, $http, $routeParams) {
    $http.get("rest/private/audio/getbyid?id=" + $routeParams.musicId)
        .success(function(data) {
            $scope.song = data.privateDTO;
            console.log(data);
        });

    $scope.edit = function(music) {
        $location.path("/private/audio/edit/" + music.id);
    };

});

main.controller("audio.private", function($scope, $http) {
    $http.get("/rest/private/get").success(function(data) {
        console.log(data);
        $scope.songs = data.privateDTO;
        console.log("Songs was updated.");
    });
});