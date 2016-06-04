var app = angular.module('blopp');

app.factory('ContentInformationResolver', ['$resource', function ($resource) {
	return $resource("/blopp-api/rest/content-information/", {url: '@url'});
}]);
