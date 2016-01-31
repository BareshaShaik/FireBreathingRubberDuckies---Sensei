import '../styles/main.scss';


import angular from 'angular';
import uiRouter from 'angular-ui-router';
// import ngAnimate from 'angular-animate';
import satellizer from 'satellizer';
import ngStorage from 'ngstorage'; 

//components
import {home} from './components/home/home';
import {landing} from './components/landing-page/landing-page';
import {login} from './components/login/login';
import {profile} from './components/profile/profile';
import {eventMap} from './components/events/event-map/event-map';
import {eventGraph} from './components/events/event-graph/event-graph';
import {eventOther} from './components/events/event-other/event-other';
import {eventCounter} from './components/events/event-counter/event-counter';
import {eventMovements} from './components/events/event-movements/event-movements';
import {eventLight} from './components/events/event-light/event-light';

//directives
import {appDirective} from './app.directive';
import {mapDirective} from './components/map/map.directive';
import {graphDirective} from './components/graph/graph.directive';

import $ from 'jquery';
global.jQuery = global.$ = $;
import {shared} from './shared/shared';

var config = ($authProvider) => {
	$authProvider.baseUrl = 'http://cambridgetest.azurewebsites.net/';
	$authProvider.facebook ({
		name: 'facebook',
		clientId: '967327336654590',
		redirectUri: 'http://localhost:15583/',
		url: '/auth/facebook',
		authorizationEndpoint: 'https://www.facebook.com/v2.5/dialog/oauth'
	});
};
config.$inject = ['$authProvider'];

var interceptor = ($q, $location, $localStorage, window) => {
    return {
        'request': function(config) {
            config.headers = config.headers || {};
            if (window.localStorage('satellizer_token')) {
                config.headers.Authorization = 'Bearer ' + window.localStorage('satellizer_token');
            }
            return config;
        },
        'responseError': function(response) {
            console.log(response);
        }
    };
};

interceptor.$inject = ['$q', '$location', '$localStorage', '$window'];

var app = angular.module ('app', [
	uiRouter, 
	// ngAnimate, 
	satellizer,
	ngStorage.name,
	home.name,
	login.name,
	shared.name,
	profile.name,
	eventMap.name,
	eventGraph.name,
	eventOther.name,
	landing.name,
	eventCounter.name,
	eventMovements.name,
	eventLight.name
])
.config(config)
.directive('app', appDirective)
.directive('map', mapDirective)
.directive('graph', graphDirective);

app.value('$', $);