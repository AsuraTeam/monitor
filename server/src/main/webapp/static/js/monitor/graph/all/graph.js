/**
 * 集群画图使用的
 * @param ip
 * @param images_id
 * @param name_datas
 * @param title
 * @param resultType
 * @param imgType
 */
function cluser_graph_all(ip, images_id, name_datas, title, resultType, imgType, clusterName) {

    /**
     * Synchronize zooming through the setExtremes event handler.
     */
    function syncExtremes(e) {
        var thisChart = this.chart;

        if (e.trigger !== 'syncExtremes') { // Prevent feedback loop
            Highcharts.each(Highcharts.charts, function (chart) {
                if (chart !== thisChart) {
                    if (chart.xAxis[0].setExtremes) { // It is null while updating
                        chart.xAxis[0].setExtremes(e.min, e.max, undefined, false, {trigger: 'syncExtremes'});
                    }
                }
            });
        }
    }
    data = get_cluster_datas(ip, name_datas, resultType)
    get_max_min_avg_last_value(data[0]["data"], images_id )

    if (!resultType){
        resultType = ""
    }
    if(!imgType){
        imgType="area"
    }
    colors =  ['#2FCC3F', '#FF841C', '#0074D9', '#E5C400', '#41E500', '#592BCC', '#00E583', '#E50000'];
    startT = $('#startSendTime').val()
    endT = $('#endSendTime').val()
    if (!startT) {
        startT = ""
    }
    if (!endT) {
        endT = ""
    }
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    t = "&startT=" + startT + "&endT=" + endT;
    $('#'+images_id).highcharts({
        chart: {
            type: imgType,
            zoomType: 'x',
        },
        colors: colors,
        title: {
            text:clusterName + title,
        },
        xAxis: {
            crosshair: true,
            type: 'datetime',
            tickPixelInterval: 110,
            gridLineWidth: 1,
            gridLineColor: '#f0f0f1',
            tickmarkPlacement: 'on',
            events: {
                setExtremes: syncExtremes
            },
        },
        yAxis: {
            title: {
                text: ""
            },
        },

        legend: {
            enabled: true,
            verticalAlign: 'bottom',
            //floating: true,
            y: 1,
            align: "left",
            borderWidth: 0
        },

        credits: {
            enabled: false
        },

        tooltip: {
            shared: true,
            crosshairs: true
        },

        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function (e) {
                            hs.htmlExpand(null, {
                                pageOrigin: {
                                    x: e.pageX || e.clientX,
                                    y: e.pageY || e.clientY
                                },
                                headingText: this.series.name,
                                maincontentText: Highcharts.dateFormat('%A, %b %e, %Y', this.x) + ':<br/> ' +
                                this.y + ' visits',
                                width: 200
                            });
                        }
                    }
                },
                marker: {
                    lineWidth: 1
                }
            },
            series: {
                lineWidth : 0.8,
                fillOpacity: 0.15,
            }
        },
        exporting: {
            enabled: false
        },
        series: data
    });
}

/**
 * 集群数据获取
 * @param ip
 * @param name_datas
 * @param resultType
 * @returns {Array}
 */
function get_cluster_datas(ip, name_datas, resultType) {
    datas = new Array();
    for(var i in name_datas){
        names = name_datas[i]
        for(var j = 0; j <names.length ; j ++){
            name = names[j]
            d = {}
            d["name"] = name.replace(/SLASH/g,"/");
            d["data"] = eval(post({}, "/monitor/graph/historyClusterData?ip="+ip+"&name="+names[j]+"&type="+i+"&resultType="+resultType));
            datas.push(d)
        }
    }
    return datas;
}