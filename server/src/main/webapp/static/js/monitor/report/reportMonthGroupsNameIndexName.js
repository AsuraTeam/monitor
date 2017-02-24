/**
 * Created by admin on 2016/11/11.
 */
/**
 * 获取多个
 */
function get_monitor_report_all(image_id, groupsName, names, type, startT, endT) {
    if (!startT) {
        startT = ""
    }
    if (!endT) {
        endT = ""
    }


    data = []
    if (!names){
        url = "/monitor/reports/getIndexNames"
        names_data = post({groupsName: groupsName, startT:startT, endT:endT, indexName:names}, url);
        names = eval(names_data)
        for (j = 0; j < names.length; j++) {
            name = names[j]
            if (!name) {
                continue;
            }
            url = "/monitor/reports/indexDataHistory?indexName=" + name.replace(/\//,"SLASH") + "&groupsName=" + groupsName + "&startT=" + startT + "&endT=" + endT;
            get_data = eval(post({}, url));
            data_dict = {}
            data_dict["name"] =  name;
            data_dict["data"] =  get_data;
            data.push(data_dict)
        }
    }else{
        url = "/monitor/reports/indexDataHistory?indexName=" + names.replace(/\//,"SLASH") + "&groupsName=" + groupsName + "&startT=" + startT + "&endT=" + endT;
        get_data = eval(post({}, url));
        data_dict = {}
        data_dict["name"] =  name;
        data_dict["data"] =  get_data;
        data.push(data_dict)
    }

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    $('#' + image_id).highcharts({
        chart: {
            type: type,
            zoomType: 'x',
        },

        colors: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9',
            '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 110,

        },
        yAxis: {
            title: {
                text: ""
            },
        },
        plotOptions: {
            line: {
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 3
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 3600000, // one hour
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: true
        },
        exporting: {
            enabled: false
        },
        series: data
    });
}
function getPie(title, name, groupsName, pieId) {

    // Radialize the colors
    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: {
                cx: 0.5,
                cy: 0.3,
                r: 0.7
            },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
            ]
        };
    });

    // Build the chart
    $('#' + pieId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        colors: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9',
            '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
//            colors:["#FFB94F", "#1cc09f", "#3c763d", "#99389f", "#017ebc"],
        title: {
            text: "",
        },
        tooltip: {
            pointFormat: '报警 <b>{point.y}次 {point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                showInLegend: true,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },
                    connectorColor: 'silver'
                }
            }
        },
        series: [{
            name: name,
            data: post({groupsName: groupsName}, '/monitor/reports/reportIndexName')
        }]
    });

}
function loadDataIndex(groupsName, start, end, indexName) {
    $("#listTableIndex" + groupsName).dataTable({
        "filter": true,//去掉搜索框
        "ordering": false, // 是否允许排序
        "paginationType": "full_numbers", // 页码类型
        "destroy": true,
        "processing": true,
        "serverSide": true,
        "displayLength": 6 , // 默认长度
        "ajax": { // 请求地址
            "url": "/monitor/reports/indexNameDetail?groupsName=" + groupsName + "&startT=" + start + "&endT=" + end + "&indexName=" + indexName,
            "type": 'post'
        },
        "bLengthChange": false,
        "bPaginate": true,
        "columns": [ // 数据映射
            {"data": "alarmTime"},
            {"data": "ipAddress"},
            {
                "data": "indexName", "mRender": function (data) {
                return data.replace(/SLASH/g, "/")
            }
            },
        ],
        "fnRowCallback": function (row, data) { // 每行创建完毕的回调
            $(row).data('recordId', data.recordId);
        }
    });
}


