import template from './event-map.jade';
import {EventController as controller} from './event-map.controller';

export const eventMapDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
