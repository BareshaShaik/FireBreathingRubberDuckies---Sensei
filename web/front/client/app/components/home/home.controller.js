class HomeController {
	constructor (Events, $rootScope, $state) {
		this.events = Events;
		this.getMyChannels();
		// this.getAllEvents();
		this.$rootScope = $rootScope;
		this.$state = $state;
		this.$rootScope.cardQuery = '';
	};

	joinEvent (id, state) {
		this.events.joinEvent({
			'ChannelId': id, 
			'MaxPayedRows': 0
		});
		this.$state.go(state, {'id': id});
	};

	getMyChannels () {
		var that = this;
		this.events.getMyChannels()
			.then( (res) => {
				that.channels = res.data.message;
				console.log(that.channels);
			});
	};

	channelAvailable (id) {
		var available = true;
		if (this.channels) {
			this.channels.forEach( function(element, index) {
				if(element.channelId == id)
					available = false; 
			});
		}
		return available;
	};


	
};

HomeController.$inject = ['Events', '$rootScope', '$state'];
export {HomeController};