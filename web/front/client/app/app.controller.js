class appController {
	constructor ($scope, $rootScope, $window, $state, login, events) {
		this.$scope            = $scope;
		this.$rootScope        = $rootScope;
		this.$rootScope.$state = $state;
		this.$state 		   = $state;
		this.$window      	   = $window;
		this.events 		   = events;
		this.login 		   = login;
		this.$rootScope.logIn  = false;
		this.init();
	};

	init () {
		var that = this;
		this.$rootScope.proxy = this.events.realTime();
		this.$scope.$on('$locationChangeStart', () => {
			that.$rootScope.logIn = that.$window.localStorage.getItem('satellizer_token') ? 
				true : false;
			if (!that.$rootScope.logIn)
				that.$state.go('login');
		});
	};

	logOut () {
		this.login.logOut();
	}
};

appController.$inject = ['$scope', '$rootScope', '$window', '$state', 'login', 'Events'];
export {appController};