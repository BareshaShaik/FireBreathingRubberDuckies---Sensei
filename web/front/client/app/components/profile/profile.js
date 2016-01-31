import angular from 'angular';
import {profileDirective} from './profile.directive';

export const profile = angular.module('profile', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('profile', {
	    url: '/profile',
	    template: '<profile></profile>'
	  });
	})
	.directive('profile', profileDirective);