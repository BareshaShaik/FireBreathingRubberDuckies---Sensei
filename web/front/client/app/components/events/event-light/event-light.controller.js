class EventController {
	constructor ($scope, Events, exportData, $stateParams, $timeout, $rootScope) {
        this.events       = Events;
        this.$stateParams = $stateParams;
        this.$timeout     = $timeout;
        this.$rootScope   = $rootScope;
        this.exportData   = exportData;
        this.$scope       = $scope;
        this.lightRealtime();
	};

    lightRealtime () {
        var that = this;
        this.$rootScope.proxy.on('light', (obj) => {
            var data = that.serveDataForLight(obj);
            that.$rootScope.chart.update(data);
        });
    }  

    serveDataForLight (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(element.Created);
            series.push(element.LightLevel);
        });
        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };  
};

EventController.$inject = ['$scope', 'Events', 'exportData', '$stateParams', '$timeout', '$rootScope'];
export {EventController};