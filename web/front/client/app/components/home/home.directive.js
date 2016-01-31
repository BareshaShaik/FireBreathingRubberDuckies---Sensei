import template from './home.jade';
import {HomeController as controller} from './home.controller';

export const homeDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
