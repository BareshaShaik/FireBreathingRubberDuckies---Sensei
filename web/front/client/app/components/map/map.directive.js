import template from './map.jade';
import {MapController as controller} from './map.controller.js'; 
export const mapDirective = () => {
	return{
		restrict:'E',
		scope:{},
		replace:true,
		controller:controller,
		controllerAs:'vm',
		template:template
	};
};