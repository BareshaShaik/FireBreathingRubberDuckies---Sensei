class EventController {
	constructor ($scope, Events, exportData, $stateParams, $timeout, $rootScope) {
        this.events       = Events;
        this.$stateParams = $stateParams;
        this.$timeout     = $timeout;
        this.$rootScope   = $rootScope;
        this.exportData   = exportData;
        this.$scope       = $scope;
        this.checkMovement();
	};

    checkMovement() {
        var that = this;
        this.$rootScope.proxy.on('movement', (obj) => {
            console.log(obj);
            var data = that.serveDataForMovement(obj.Message); 
            that.$rootScope.chart.update(data);
        });
    };

    serveDataForMovement (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(element.hour);
            series.push(element.count);
        });
        series.reverse();
        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };
};

EventController.$inject = ['$scope', 'Events', 'exportData', '$stateParams', '$timeout', '$rootScope'];
export {EventController};