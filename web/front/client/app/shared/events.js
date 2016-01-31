const events = ($http, API, $q, $) => {
	const getEventById = (id) => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/fetch/' + id)
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getFridge = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/fridge/bydate')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getMap = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/map')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getMovements = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/movement/bydate')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getLight = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/lights')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getAllEvents = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/events')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const joinEvent = (data) => {
		$http.post(API.deafaultUrl + '/api/1/channels/join', data);
	};

	const getMyChannels = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/channels/me')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const getMyEvents = () => {
		let deferred = $q.defer();
		$http.get(API.deafaultUrl + '/api/1/events/mine')
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 
	};

	const unlockEvent = (id) => {
		$http.get(API.deafaultUrl + '/api/1/events/unlock/' + id);
	};

	const realTime = () => {	
		var connection = $.hubConnection('http://cambridgetest.azurewebsites.net');
		var proxy = connection.createHubProxy('NotificationHub');
		connection.start().done( () => {
          	console.log('Realtime Started');
          });
		return proxy;
	};

	const buyData = (data) => {
		console.log(data);
		let deferred = $q.defer();
		$http.post(API.deafaultUrl + '/api/1/channels/join', data)
			.then((res) => {
				deferred.resolve(res)
			});
		return deferred.promise; 

	};

	return {getEventById, getFridge, buyData, getMap, getMovements, getLight, getMyChannels, joinEvent, getAllEvents, getMyEvents, unlockEvent, realTime};
};

events.$inject = ['$http', 'API', '$q', '$'];
export {events};