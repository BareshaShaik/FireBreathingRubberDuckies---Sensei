import template from './app.jade';
import {appController as controller} from './app.controller';

export const appDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {},
		link: () => {
			$(".button-collapse").sideNav();
		}
	};
};
