class GraphController {
    constructor($http, Events, $stateParams, $timeout, $rootScope, exportData) {
        this.events     = Events;
        this.$timeout   = $timeout;
        this.$rootScope = $rootScope;
        this.exportData = exportData;
        this.id         = $stateParams.id;
        this.buy        = true;
        if(this.id == 2)
            this.getFridge();
        else if (this.id == 3)
            this.getMovements();
        else if (this.id == 5)
            this.getLight();
        else
            this.getEvent();
    };

    getEvent () {
        var that = this;
        this.events.getEventById(this.id)
            .then( (res) => {
                that.eventData = res.data.message;
                console.log(that.eventData);
                that.drawGraph();
            });
    };

    getFridge () {
        var that = this;
        this.events.getFridge()
            .then( (res) => {
                that.eventData = res.data.message;
                that.drawGraph();
                console.log(that.eventData);
            });
    };

    getMovements () {
        var that = this;
        this.events.getMovements()
            .then( (res) => {
                that.eventData = res.data.message;
                console.log(that.eventData);
                that.drawGraph();
            });
    }

    getLight () {
        var that = this;
        this.events.getLight()
            .then( (res) => {
                that.eventData = res.data.message;
                console.log(that.eventData);
                that.drawGraph();
            });
    }

    drawGraph () {
        var that = this;
        var data;
        var options = {
            low: 0,
            showArea: true
        };

        if (this.id == 1){
            data = this.serveDataForGraph(this.eventData);
            this.$rootScope.chart = new Chartist.Line('.ct-chart', data, options);
        }

        if (this.id == 2){
            data = this.serveDataForFridgeGraph(this.eventData);
            this.$rootScope.chart = new Chartist.Line('.ct-chart', data, options);
            this.$rootScope.chart.on('draw', function(data) {
                if(data.type === 'line' || data.type === 'area') {
                    data.element.animate({
                    d: {
                        begin: 2000 * data.index,
                        dur: 2000,
                        from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                        to: data.path.clone().stringify(),
                        easing: Chartist.Svg.Easing.easeOutQuint
                        }
                    });
                }
            });
        }

        if (this.id == 5) {
            data = this.serveDataForLight(this.eventData);
            this.$rootScope.chart = new Chartist.Line('.ct-chart', data, options);
        }

        if (this.id == 3) {
            data = this.serveDataForMovement(this.eventData);
            this.$rootScope.chart = new Chartist.Line('.ct-chart', data, options);
        }
    };

    serveDataForGraph (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];

        data.forEach( (element, index) => {
            labels.push(new Date(element.created));
            series.push(element.rate);
        });
        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };

    serveDataForFridgeGraph (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(new Date(element.date));
            series.push(element.count);
        });

        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };

    serveDataForMovement (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(element.hour + "h");
            series.push(element.count);
        });
        series.reverse();
        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };

    serveDataForLight (data) {
        var labels     = [],
            series     = [],
            seriesFull = [];
        data.forEach( (element, index) => {
            labels.push(new Date(element.created));
            series.push(element.lightLevel);
        });
        seriesFull.push(series);
        
        return {
            'series': seriesFull,
            'labels': labels
        }
    };

    generateCSV () {
        var data = this.exportData.convertToCSV(this.eventData);
        var data = this.exportData.generateCSV(data);
        window.open(data);
    };

    buyData () {
        console.log(this.id);
        var that = this;
        this.events.buyData({ 
                'ChannelId': this.id,
                'MaxPayedRows': 0,
                'Price': 10 
        }).then(function(res){
            that.buy = false;
            that.generateCSV();
            if (that.id == 2)
                that.getFridge();
            else if (that.id == 3)
                that.getMovements();
            else if (that.id == 5)
                that.getLight();
            else
                that.getEvent();
        });
    };

};
GraphController.$inject = ['$http', 'Events', '$stateParams', '$timeout', '$rootScope', 'exportData'];
export {GraphController};