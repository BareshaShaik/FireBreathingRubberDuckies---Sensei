import template from './landing-page.jade';
import {LandingController as controller} from './landing-page.controller';

export const landingDirective = () => {
	return {
		template,
		controller,
		controllerAs: 'vm',
		restrict: 'E',
		replace: true,
		scope: {},
		link: () => {
			$(() => {
				$('#SVGlogo').css({'visibility': 'hidden'});
				$(document).on('click', () => {
					$('#SVGlogo').css({'visibility': 'visible'}); 
					new Vivus('SVGlogo',  {duration: 200});
				});
			});
		}
	}
};
