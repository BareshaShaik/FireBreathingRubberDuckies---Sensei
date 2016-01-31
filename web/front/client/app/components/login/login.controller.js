class LoginController {
	constructor (login) {
		this.login = login;
	};

	loginFacebook() {
        this.login.loginFacebook();
     };
};

LoginController.$inject = ['login'];
export {LoginController};