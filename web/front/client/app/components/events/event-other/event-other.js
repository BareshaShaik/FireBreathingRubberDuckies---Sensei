import angular from 'angular';
import {eventOtherDirective} from './event-other.directive';

export const eventOther = angular.module('eventOther', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-other', {
	    url: '/event-other/:id',
	    template: '<event-other></event-other>'
	  });
	})
	.directive('eventOther', eventOtherDirective);