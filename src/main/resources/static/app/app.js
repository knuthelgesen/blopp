var app = angular.module('blopp', ['ngRoute', 'ngResource']);

app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	  $locationProvider.html5Mode(true);
	  
	  $routeProvider
	  .when('/', {
          templateUrl: '/app/template.html',
          controller: 'template-controller',
          resolve: {
        	  contentInformation: resolveContentInformation(),
        	  breadcrumbs: resolveBreadcrumbs()
          }
	  })
	  .when('/:url*', {
          templateUrl: '/app/template.html',
          controller: 'template-controller',
          resolve: {
        	  contentInformation: resolveContentInformation(),
        	  breadcrumbs: resolveBreadcrumbs()
          }
	  });
}]);

/*
 * Resolve information about content located at current URL
 */
var resolveContentInformation = function() {
	return ['$route', 'ContentInformationResolver', function($route, ContentInformationResolver) {
    	if (!$route.current.params.url) {
    		$route.current.params.url = '';
    	}
		
		return ContentInformationResolver.get({url: '/' + $route.current.params.url}).$promise;
	}];
};

/*
 * Resolve breadcrumbs for current URL
 */
var resolveBreadcrumbs = function() {
	return ['$route', 'Breadcrumbs', function($route, Breadcrumbs) {
    	if (!$route.current.params.url) {
    		$route.current.params.url = '';
    	}
		
		return Breadcrumbs.get({url: '/' + $route.current.params.url}).$promise;
	}];
};


/*
 * Root controller. Will set up WS client and connection.
 */
app.controller('blopp-controller', bloppController);
bloppController.$inject = [];
function bloppController() {
	var self = this;
};
