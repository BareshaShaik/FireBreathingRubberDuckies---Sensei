import template from './event-counter.jade';
import {CounterController as controller} from './event-counter.controller';

export const eventCounterDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {}
	}
};
