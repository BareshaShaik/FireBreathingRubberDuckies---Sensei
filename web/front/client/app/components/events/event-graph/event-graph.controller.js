class EventController {
	constructor ($scope, Events, exportData, $stateParams, $timeout, $rootScope) {
        this.events       = Events;
        this.$stateParams = $stateParams;
        this.$rootScope   = $rootScope;
        this.$scope       = $scope;
        this.realtime();
	};

    realtime () {
        console.log("Hooolo");
        var that = this;
        this.$rootScope.proxy.on('heartrate', (obj) => {
            console.log(obj);
            var data = that.serveDataForGraph(obj.Message);
            that.$rootScope.chart.update(data);
        });
    };

    serveDataForGraph (data) {
        console.log(data);
        var labels     = [],
            series     = [],
            seriesFull = [];
        for (var i = 0; i < data.length; i++) {
            labels.push(new Date(data[i].Created));
            series.push(data[i].Rate);
        }
        seriesFull.push(series);
        console.log(seriesFull);
        return {
            'series': seriesFull,
            'labels': labels
        }
    };
};

EventController.$inject = ['$scope', 'Events', 'exportData', '$stateParams', '$timeout', '$rootScope'];
export {EventController};