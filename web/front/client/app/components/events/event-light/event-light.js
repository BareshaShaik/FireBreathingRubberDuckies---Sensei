import angular from 'angular';
import {eventLightDirective} from './event-light.directive';

export const eventLight = angular.module('eventLight', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-light', {
	    url: '/event-light/:id',
	    template: '<event-light></event-light>'
	  });
	})
	.directive('eventLight', eventLightDirective);