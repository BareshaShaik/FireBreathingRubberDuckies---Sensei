import template from './event-light.jade';
import {EventController as controller} from './event-light.controller';

export const eventLightDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
