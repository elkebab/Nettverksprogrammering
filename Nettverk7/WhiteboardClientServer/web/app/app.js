var app = angular.module('customerApp', ['ngRoute']);

app.config(function($routeProvider) {
  $routeProvider
          .when('/whiteboard', {
            controller: 'WhiteboardController',
            templateUrl: 'app/partials/whiteboard.html'
          })
          .otherwise({redirectTo: '/whiteboard'});
});
