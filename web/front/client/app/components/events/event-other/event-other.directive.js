import template from './event-other.jade';
import {EventController as controller} from './event-other.controller';

export const eventOtherDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
