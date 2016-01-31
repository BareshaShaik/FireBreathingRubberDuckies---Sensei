import template from './event-graph.jade';
import {EventController as controller} from './event-graph.controller';

export const eventGraphDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
