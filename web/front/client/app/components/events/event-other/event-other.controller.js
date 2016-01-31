class EventController {
	constructor ($scope, Events, exportData, $stateParams, $timeout, $rootScope) {
        this.events       = Events;
        this.$stateParams = $stateParams;
        this.$timeout     = $timeout;
        this.$rootScope   = $rootScope;
        this.exportData   = exportData;
        this.$scope       = $scope;
        this.fridgeAlert  = false;
        this.checkFridgeStatus();
	};

    generateCSV () {
        var data = this.exportData.convertToCSV(this.eventData);
        var data = this.exportData.generateCSV(data);
        window.open(data);
    };

    checkFridgeStatus() {
        var that = this;
        this.$rootScope.proxy.on('fridge', (obj) => {
            toastr.warning('YOUR FRIDGE IS OPEN!');
            var data = that.serveDataForFridgeGraph(obj.Message); 
            that.$rootScope.chart.update(data);
        });
    }

    serveDataForFridgeGraph (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(element.date);
            series.push(element.count);
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