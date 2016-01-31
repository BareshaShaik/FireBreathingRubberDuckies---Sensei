class CounterController {
    constructor () {
        this.drawGraph();
    };


    getNumber () {
        return new Array(92); 
    }

    drawGraph () {
        var data = {
          series: [84, 8]
        };

        var sum = function(a, b) { return a + b };

        var chart = new Chartist.Pie('.ct-chart', data, {
          labelInterpolationFnc: function(value) {
            return Math.round(value / data.series.reduce(sum) * 100) + '%';
          },
          donut: true
        });

        chart.on('draw', function (data) {
                if (data.type === 'slice') {
                // Get the total path length in order to use for dash array animation
                var pathLength = data.element._node.getTotalLength();

                // Set a dasharray that matches the path length as prerequisite to animate dashoffset
                data.element.attr({
                    'stroke-dasharray': pathLength + 'px ' + pathLength + 'px'
                });

                // Create animation definition while also assigning an ID to the animation for later sync usage
                var animationDefinition = {
                    'stroke-dashoffset': {
                        id: 'anim' + data.index,
                        dur: 500 * data.value / data.totalDataSum,
                        from: -pathLength + 'px',
                        to: '0px',
                        // We need to use `fill: 'freeze'` otherwise our animation will fall back to initial (not visible)
                        fill: 'freeze'
                    }
                };

                // If this was not the first slice, we need to time the animation so that it uses the end sync event of the previous animation
                if (data.index !== 0) {
                    animationDefinition['stroke-dashoffset'].begin = 'anim' + (data.index - 1) + '.end';
                }

                // We need to set an initial value before the animation starts as we are not in guided mode which would do that for us
                data.element.attr({
                    'stroke-dashoffset': -pathLength + 'px'
                });

                // We can't use guided mode as the animations need to rely on setting begin manually
                // See http://gionkunz.github.io/chartist-js/api-documentation.html#chartistsvg-function-animate
                data.element.animate(animationDefinition, false);

                // add (naive) bounce
                if (data.endAngle === 360) {
                    var index = data.index;
                    var dur = 1000 * data.value / data.totalDataSum / 2;
                    var from = 0;
                    var to = -pathLength / 3;

                    for (var i = 0; i < 4; i++) {
                        data.element.animate({
                            'stroke-dashoffset': {
                                id: 'anim' + (index + 1),
                                dur: dur,
                                from: from + 'px',
                                to: to + 'px',
                                fill: 'freeze',
                                begin: 'anim' + index + '.end'
                            }
                        }, false);

                        index++;
                        dur /= 1.75;

                        var t = from;
                        from = to;
                        to = t / 2.5;
                    }
                }
            }
        });
    };
};

export {CounterController};