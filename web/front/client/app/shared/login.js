const login = ($auth, $state, $window) => {
	const loginFacebook = () => {
		$auth.authenticate('facebook').then( (response) => {
                $state.go('home');
            	})
	          .catch(function(response) {
	               console.log("Error");
	               console.log(response);
	          });
	};

	const logOut = () => {
		$window.localStorage.removeItem('satellizer_token');
		$state.go('landing');
	};

	return {loginFacebook, logOut};
};

login.$inject = ['$auth', '$state', '$window'];
export {login}; 