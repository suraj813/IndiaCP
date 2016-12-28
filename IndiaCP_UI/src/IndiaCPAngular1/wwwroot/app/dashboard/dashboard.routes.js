(function () {
    "use strict";
    angular
        .module("app.dashboard")
        .config(config);
    config.$inject = ["$stateProvider", "$urlRouterProvider"];
    function config($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.dashboard", {
            url: "dashboard",
            templateUrl: "app/dashboard/dashboard.html",
            controller: "app.dashboard.DashboardController"
        })
            .state("index.playgames", {
            url: "playgames",
            templateUrl: "/templates/admin/templates/playgames.html",
            params: {
                seldate: null,
            },
            resolve: {
                games: function ($http, $stateParams) {
                    return $http.post('/api/appmain/GetGamesForDate', $stateParams.seldate).then(function (response) {
                        return response.data;
                    });
                }
            },
            controller: function ($scope, games) {
                $scope.gridOptions.data = games;
                //$scope.items = financials;
            }
        });
    }
    // resolveCPPrograms.$inject = ["app.services.CPProgramService"];
    // function resolveCPPrograms(cpprogramService):
    // {}
})();
//# sourceMappingURL=dashboard.routes.js.map