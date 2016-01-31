import {api} from './api';
import {user} from './user';
import {events} from './events';
import {exportData} from './exportData';
import {login} from './login';

export const shared = angular.module('shared', [])
	.constant('API', api)
	.factory('User', user)
	.factory('exportData', exportData)
	.factory('login', login)
	.factory('Events', events);