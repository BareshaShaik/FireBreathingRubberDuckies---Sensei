import angular from 'angular';
import {eventCounterDirective} from './event-counter.directive';

export const eventCounter = angular.module('eventCounter', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-counter', {
	    url: '/event-counter',
	    template: '<event-counter></event-counter>'
	  });
	})
	.directive('eventCounter', eventCounterDirective);