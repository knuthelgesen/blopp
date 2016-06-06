var app = angular.module('blopp');

app.factory('BlogEntry', ['$resource', function ($resource) {
	return $resource("/blopp-api/rest/blog-entries/:id", {id: '@id'});
}]);

app.controller('BlogEntryController', blogEntryController);

blogEntryController.$inject = ['$scope', 'BlogEntry'];

function blogEntryController($scope, BlogEntry) {
	var self = this;
	
	BlogEntry.get({id: $scope.contentInformation.vertexId}, function(data) {
		self.blogEntry = data;
	});
};
