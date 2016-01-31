import angular from 'angular';
import {eventMapDirective} from './event-map.directive';

export const eventMap = angular.module('eventMap', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-map', {
	    url: '/event-map',
	    template: '<event-map></event-map>'
	  });
	})
	.directive('eventMap', eventMapDirective);