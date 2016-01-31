class EventController {
	constructor ($scope, Events, exportData, $stateParams, $rootScope) {
        this.events       = Events;
        this.$stateParams = $stateParams;
        this.$rootScope   = $rootScope;
        this.exportData   = exportData;
        this.$scope       = $scope;
        this.mapRealTime();
	};

	mapRealTime () {
   		var that = this;
        	this.$rootScope.proxy.on('location', (obj) => {
	       	that.drawMarker([obj.Lat, obj.Lng], obj.Created);
	     });
	};

	drawMarker (coord, date) {
     	var marker = L.marker(coord).addTo(this.$rootScope.map);
     	marker.bindPopup(date);
     	this.$rootScope.polyline.addLatLng(coord);
     };
};

EventController.$inject = ['$scope', 'Events', 'exportData', '$stateParams', '$rootScope'];
export {EventController};

