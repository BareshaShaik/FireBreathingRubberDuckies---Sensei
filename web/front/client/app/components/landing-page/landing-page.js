import angular from 'angular';
import {landingDirective} from './landing-page.directive';

export const landing = angular.module('landing', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('landing', {
	    url: '/',
	    template: '<landing></landing>'
	  });
	})
	.directive('landing', landingDirective);