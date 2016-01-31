import angular from 'angular';
import {eventMovementsDirective} from './event-movements.directive';

export const eventMovements = angular.module('eventMovement', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('event-movements', {
	    url: '/event-movements/:id',
	    template: '<event-movements></event-movements>'
	  });
	})
	.directive('eventMovements', eventMovementsDirective);