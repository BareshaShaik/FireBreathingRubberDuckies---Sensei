const user = ($http, API, $q) => {
	const getUserInfo = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/users/profile')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getUserDevices = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/devices/me')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getDeviceTypes = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/types')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise;
	}

	const addDevice = (device) => {
		$http.post(API.deafaultUrl + '/api/1/devices/pair', device);
	};

	const getTemplates = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/templates')
			.then((res) => {
					deferred.resolve(res)
				});
			return deferred.promise;
	};

	const addEvent = (event) => {
		$http.post(API.deafaultUrl + '/api/1/events', event);
	};

	return {getUserInfo, getUserDevices, getDeviceTypes, addDevice, addEvent, getTemplates};
};

user.$inject = ['$http', 'API', '$q'];
export {user};