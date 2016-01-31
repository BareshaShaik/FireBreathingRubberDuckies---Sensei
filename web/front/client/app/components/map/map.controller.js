class MapController {
    constructor($http, Events, $stateParams, $rootScope) {
        this.events = Events;
        this.$stateParams = $stateParams;
        this.$rootScope = $rootScope;
        this.getEvent();
    };

    getEvent () {
        var that = this;
        var id = this.$stateParams.id;
        this.events.getMap()
            .then( (res) => {
                that.locations = res.data.message;
                if(that.locations)
                    that.drawMap();
            });
    };

    drawMap () {
        var that = this;
        var coord = [this.locations[0].lat, this.locations[0].lng];
        this.$rootScope.map = L.map('map').setView(coord, 17);
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
            maxZoom: 18,
            id: 'danijel911.opo5ap90',
            accessToken: 'pk.eyJ1IjoiZGFuaWplbDkxMSIsImEiOiJjaWp1NHd6c2IwMDEzdjJtNG16NzdqOGVmIn0.zOFQ-k4MlHJS0lJEnRXDdQ'
        }).addTo(this.$rootScope.map);
        this.$rootScope.polyline = L.polyline([]).addTo(this.$rootScope.map);
        this.locations.forEach( (element, index) => {
            that.drawMarker([element.lat, element.lng], element.created);
            coord.push([element.lat, element.lng]);
        });
    };

    drawMarker (coord, date) {
        var marker = L.marker(coord).addTo(this.$rootScope.map);
        marker.bindPopup(date);
        this.$rootScope.polyline.addLatLng(coord);
    };

};
MapController.$inject = ['$http', 'Events', '$stateParams', '$rootScope'];
export {MapController}; 