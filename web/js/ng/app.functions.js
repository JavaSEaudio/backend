var funcs = angular.module("app.funcs", []);

funcs.run(function($rootScope, $http) {
    $http.get("/rest/test/check").success(function(data) {
        $rootScope.logined = !!data;
    });
});