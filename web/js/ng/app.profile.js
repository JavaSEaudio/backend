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

main.controller("profile.reg", function($scope, $http) {
    console.log("reg");
    $scope.confirmLogin = function(login) {
        $http.get("/rest/test/checkUser", {params: {login:login}})
            .error(function(data, status) {
                $scope.confirmPassStatusColor = "red";
                switch(status) {
                    case 402:
                        $scope.confirmPassStatus = "Login is too small";
                        break;
                    case 403:
                        $scope.confirmPassStatus = "Login occupied";
                        break;
                    case 405:
                        $scope.confirmPassStatus = "Symbols in login is not supports";
                        break;
                    default:
                        $scope.confirmPassStatus = "Login incorrect";
                }

//                console.log(data);
//                console.log("Status", status);
            })
            .success(function(data) {
                $scope.confirmPassStatusColor = "green";
                $scope.confirmPassStatus = "Login correct";
                console.log(data);
            });
    };

    $scope.confirmEmail = function(email) {
        $http.get("/rest/test/checkMail", {params: {mail:email}})
            .error(function(data, status) {
                $scope.confirmEmailStatusColor = "red";
                switch(status) {
                    case 401:
                        $scope.confirmEmailStatus = "E-mail is incorrect";
                        break;
                    case 402:
                        $scope.confirmEmailStatus = "E-mail is too small";
                        break;
                    case 403:
                        $scope.confirmEmailStatus = "Your E-mail is occupied";
                        break;
                    default:
                        $scope.confirmEmailStatus = "E-mail is incorrect";
                }

//                console.log(data);
//                console.log("Status", status);
            })
            .success(function(data) {
                $scope.confirmEmailStatusColor = "green";
                $scope.confirmEmailStatus = "E-mail correct";
                console.log(data);
            });
    };

    $scope.confirmPassword = function(passOne, passTwo) {
        console.log(passOne, passTwo);
        if(passOne.length < 6) {
            $scope.confirmPasswordStatus = "Password is too small. Need minimum 6 symbols";
            $scope.confirmPasswordStatusColor = "red";
            return;
        }

        if(passOne != passTwo) {
            $scope.confirmPasswordStatus = "Passwords do not match.";
            $scope.confirmPasswordStatusColor = "red";
            return;
        }

        $scope.confirmPasswordStatus = "Password is correct.";
        $scope.confirmPasswordStatusColor = "green";
    };

});

main.controller("profile.user",
function($rootScope, $location, $http, $scope, $route, $routeParams) {
    $scope.user = {
        login: $routeParams.user
    };

    $http.get("/rest/user/login/?login=" + $routeParams.user).success(function(data) {
        console.log(data);
        $scope.user = data.userDTO;

        $http.get("/rest/list/my?id=" + $scope.user.id).success(function(data) {
            $scope.musics = data.audioDTO;
        });

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

main.controller("profile.pwd", function($routeParams, $http, $scope) {
    $scope.uniq = $routeParams.uniq;
    $http.get("/rest/password/restore?uniq=" + $routeParams.uniq)
     .error(function() {
        alert("Пшел Вон!!!");
    }).success(function(data) {
        $scope.showPWD = true;
    });
    //uniq
});

main.controller("profile.confirm.email", function($location, $scope, $http, $routeParams) {
    $http.get("/rest/validation?uniq=" + $routeParams.uniq)
        .success(function() {
            $scope.Message = "Congratulations, your super BRO!";
        })
        .error(function() {
            $location.path("/");
        });
});