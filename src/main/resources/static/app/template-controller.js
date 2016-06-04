var app = angular.module('blopp');

app.controller('template-controller', templateController);
templateController.$inject = ['$scope', '$route'];
function templateController($scope, $route) {
	var self = this;
	
	$scope.contentInformation = $route.current.locals.contentInformation;
};
