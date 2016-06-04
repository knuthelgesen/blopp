var app = angular.module('blopp');

app.factory('Breadcrumbs', ['$resource', function ($resource) {
	return $resource("/blopp-api/rest/breadcrumbs/", {url: '@url'}, {
		get: {isArray: true}
	});
}]);

app.directive('breadcrumbs', ['$route', function($route) {
    return {
        restrict: "E",
        replace: true,
        templateUrl: "/app/common/breadcrumbs/breadcrumbs.html",
        controller: function () {
        	var self = this;

        	self.breadcrumbs = $route.current.locals.breadcrumbs;
        },
        controllerAs: "breadcrumbs"
    };

}]);

