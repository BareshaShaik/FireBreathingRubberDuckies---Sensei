import template from './event-movements.jade';
import {EventController as controller} from './event-movements.controller';

export const eventMovementsDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
