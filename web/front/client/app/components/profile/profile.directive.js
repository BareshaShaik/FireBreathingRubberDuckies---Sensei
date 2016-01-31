import template from './profile.jade';
import {ProfileController as controller} from './profile.controller';

export const profileDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {},
		link: () => {
			$('.collapsible').collapsible();
		}
	}
};
