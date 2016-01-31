class ProfileController {
	constructor (User, Events, $scope) {
		this.user = User;
		this.events = Events;
		this.getUserInfo();
		this.getUserDevices();
		this.getDeviceTypes();
		this.getTemplates();
		this.getEvents();

	};

	getUserInfo () {
		var that = this;
		this.user.getUserInfo()
			.then( (res) => {
				that.userInfo = res.data.message[0];
				console.log(res.data);
			});
	};

	getUserDevices () {
		var that = this;
		this.user.getUserDevices()
			.then( (res) => {
				that.userDevices = res.data.message[0];
			});
	};

	getDeviceTypes () {
		var that = this;
		this.user.getDeviceTypes()
			.then( (res) => {
				that.deviceType = res.data.message;
			});
	};

	addDevice (device) {
		this.userDevices.push(device);
		this.user.addDevice(device);
		this.$scope.$apply();
	};

	addEvent (event) {
		var tags = [];
		var tagsArray = event.Tags.split(', ');
		event.IsPrivate = event.IsPrivate == undefined ? false : true;
		event.Tags = [];
		tagsArray.forEach( (element, index) => {
			event.Tags.push({
				'name': '#' + element
			});
		});
		console.log(event);
		this.user.addEvent(event);
	};

	getEvents () {
		var that = this;
		this.events.getMyEvents()
			.then( (res) => {
				that.myEvents = res.data.message;
				console.log(that.myEvents);
			});
	};

	getTemplates () {
		var that = this;
		this.user.getTemplates()
			.then( (res) => {
				that.templates = res.data.message;
				console.log(that.templates);
			});
	}
};

ProfileController.$inject = ['User', 'Events', '$scope'];
export {ProfileController};