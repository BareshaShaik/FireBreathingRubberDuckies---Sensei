import template from './graph.jade';
import {GraphController as controller} from './graph.controller.js'; 
export const graphDirective = () => {
	return{
		restrict:'E',
		scope:{},
		replace:true,
		controller:controller,
		controllerAs:'vm',
		template:template
	};
};