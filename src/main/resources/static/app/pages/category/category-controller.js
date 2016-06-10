var app = angular.module('blopp');

app.factory('Category', ['$resource', function ($resource) {
	return $resource("/blopp-api/rest/categories/:id", {id: '@id'});
}]);

app.controller('CategoryController', categoryController);

categoryController.$inject = ['$scope', 'Category'];

function categoryController($scope, Category) {
	var self = this;
	
	Category.get({id: $scope.contentInformation.vertexId}, function(data) {
		self.category = data;
	});
};
