import template from './login.jade';
import {LoginController as controller} from './login.controller';

export const loginDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
