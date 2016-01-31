import angular from 'angular';
import {eventGraphDirective} from './event-graph.directive';

export const eventGraph = angular.module('eventGraph', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-graph', {
	    url: '/event-graph/:id',
	    template: '<event-graph></event-graph>'
	  });
	})
	.directive('eventGraph', eventGraphDirective);