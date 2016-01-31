import angular from 'angular';
import {loginDirective} from './login.directive';

export const login = angular.module('login', [])
	.config(function($stateProvider, $urlRouterProvider) {
	  $urlRouterProvider.otherwise('/');

	  $stateProvider.state('login', {
	    url: '/login',
	    template: '<login></login>'
	  });
	})
	.directive('login', loginDirective);