/*
 * 
 *  url = "/devops/?page=user.user"
 *  paramter = {id:id,user:user}
 *  
 */
function post(paramter , url){
    var result = "";
    $.ajax({
        type:"POST",
        url:url,
        data: paramter,
        async:false,
        success:function(data){
            result = data;
        }
    });
    return result;
}

function post_get(url) {
    xml = GenXMLData(tableName, fieldID, "", "");
    contentTD.innerHTML = content;
    var XmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    XmlHttp.onreadystatechange = function () {
        if (XmlHttp.readyState == 4) {
            if (XmlHttp.status == 200) {
                firstPost = true;
            }
        }
    }
    XmlHttp.open("post", url, true);
    XmlHttp.send(xml);
}

//获取from数据
function get_form_data(){
    var result = {}
    forch = ["input","textarea","select"]
    for(i=0;i<forch.length;i++){
        $.each($("form "+forch[i]),
            function(name,object){
                result[$(object).attr("name")] = $(object).val()
            }
        );
    }
    return result;
}

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') {
        options = options || {};
        if (value === null) {
            value = '';
            options = $.extend({}, options);
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString();
        }
        options
        var path = options.path ? '; path=' + (options.path) : '';
        var  path = ";path=/";
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else {
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};


function sleep(n) {
    var start = new Date().getTime();
    while(true)  if(new Date().getTime()-start > n) break;
}

function  setWidth(id,all) {
    console.log(id)
    width = $('#'+id+"BorderWidth").val()
    console.log(width)
    if(width==100){
        widths=49
        if(all){
            widths=48
        }
    }else{
        widths=100
    }
    $('#'+id+"BorderWidth").val(widths)
    $('#'+id+"Border").css("width",widths+"%")
}


function  setWidthMin(id,all) {
    console.log(id)
    width = $('#'+id+"BorderWidth").val()
    console.log(width)
    if(width==100){
        widths=31
        if(all){
            widths=31
        }
    }else{
        widths=100
    }
    $('#'+id+"BorderWidth").val(widths)
    $('#'+id+"Border").css("width",widths+"%")
}

var colors = ["#1ab394",
    "#f8ac59", "#CC66CC",
    "#1f90d8", "#FFB94F", "#1cc09f", "#3c763d", "#99389f", "#017ebc"];



// 默认画图公用的
function graph(color, id, title, ytitle, url, chartype,lstartT,lendT) {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    startT= $('#startSendTime').val()
    endT = $('#endSendTime').val()
    if(!startT){
        startT = ""
    }
    if(!endT){
        endT = ""
    }

    if(lstartT){
        startT=lstartT;
        endT = lendT;
    }
    lineWidth = 3
    lineWidthO = 5
    if(startT){
        lineWidth = 1
        lineWidthO= 1
    }


    if(!chartype){
        chartype = "spline"
    }

    $('#' + id).highcharts({
        chart: {
            type: chartype,
            zoomType: 'x'

        },

        colors: ["#" + color],
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 110
        },
        yAxis: {
            title: {
                text: title
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#1ab394',
                marker: {
                    enabled: false
                },
                shadow: false,
            }
            ]
        },
        plotOptions: {
            spline: {
                lineWidth: 2,
                states: {
                    hover: {
                        lineWidth: lineWidthO
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 3600000, // one hour
                pointStart: Date.UTC(2015, 4, 31, 0, 0, 0)
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
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: ytitle,
            data: eval(post({}, url+"&startT="+startT+"&endT="+endT)),
        }]
    });
}

function timePulush(loadimg) {

    /** 时间插件
     *
     */
    var startSendTime = {
        elem: '#startSendTime',
        choose: function (data) {
            endSendTime.min = data;
            endSendTime.start = data;
            if($('#endSendTime').val()){
                loadimg()
            }
        }
    }

    var endSendTime = {
        elem: '#endSendTime',
        choose: function (data) {
            startSendTime.max = data;
            if($('#startSendTime').val()){
                loadimg()
            }

        }
    }
    laydate(startSendTime);
    laydate(endSendTime);

}

/**
 * 画图获取时间
 */
function getStartEndTime() {
    start =    $('#startSendTime').val()
    end =   $('#endSendTime').val()
    return start,end;
}

/**
 * json格式转换
 * @param json
 * @returns {string|XML}
 */
function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match.replace(/\\n/g,"<br>") + '<br></span>';
    });
}

function get_max_min_avg_last_value(data, id ){
    value = 0
    new_data = new Array();
    for(i=0;i<data.length;i++){
        new_data.push(data[i][1]);
        value += data[i][1]
    }
    last = new_data[new_data.length-1];
    new_data.sort(function (a,b) {
        return a - b;
    })
    min = new_data[0]
    avg = value / new_data.length
    max = new_data[new_data.length-1]
    $("#"+id+"last").html(last)
    $("#"+id+"max").html(max)
    $("#"+id+"min").html(min)
    $("#"+id+"avg").html(avg.toFixed(2))

}


// 默认画图公用的
function graph_min(color, id, title, ytitle, url, chartype,lstartT,lendT) {

    $('#show_image_data_'+id).show()

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    startT= $('#startSendTime').val()
    endT = $('#endSendTime').val()
    if(!startT){
        startT = ""
    }
    if(!endT){
        endT = ""
    }

    if(lstartT){
        startT=lstartT;
        endT = lendT;
    }
    lineWidth = 3
    lineWidthO = 5
    if(startT){
        lineWidth = 1
        lineWidthO= 1
    }

    data= eval(post({}, url+"&startT="+startT+"&endT="+endT))
    get_max_min_avg_last_value(data, id );

    $('#'+id).bind('mousemove touchmove touchstart', function (e) {
        var chart,
            point,
            i,
            event;

        for (i = 0; i < Highcharts.charts.length; i = i + 1) {
            chart = Highcharts.charts[i];
            event = chart.pointer.normalize(e.originalEvent); // Find coordinates within the chart
            point = chart.series[0].searchPoint(event, true); // Get the hovered point

            if (point) {
                point.highlight(e);
            }
        }
    });

    Highcharts.Point.prototype.highlight = function (event) {
        this.onMouseOver(); // Show the hover marker
        this.series.chart.tooltip.refresh(this); // Show the tooltip
        this.series.chart.xAxis[0].drawCrosshair(event, this); // Show the crosshair
    };
    if(!chartype){
        chartype = "spline"
    }
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
    $('#' + id).highcharts({
        chart: {
            type: chartype,
            zoomType: 'x'

        },

        colors: ["#" + color],
        title: {
            text: ''
        },
        xAxis: {
            crosshair: true,
            type: 'datetime',
            tickPixelInterval: 110,
            events: {
                setExtremes: syncExtremes
            },
        },
        yAxis: {
            title: {
                text: ""
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#1ab394',
                marker: {
                    enabled: false
                },
                shadow: false,
            }
            ]
        },
        plotOptions: {
            spline: {
                lineWidth: 2,
                states: {
                    hover: {
                        lineWidth: lineWidthO
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 3600000, // one hour
                pointStart: Date.UTC(2015, 4, 31, 0, 0, 0),

            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        series: [{
            name: ytitle ,
            data: data,
            //data: eval(post({}, url+"&startT="+startT+"&endT="+endT)),
        }]
    });
}


function checkInput(min,max,def,id,value) {

    if(min){
        if(value<min){
            parent.layer.msg("该项最小值是:"+min,  {icon:2})
            $('#'+id).val(def)
            return
        }
    }
    if(max){
        if(value>max){
            $('#'+id).val(def)
            parent.layer.msg("该项最大值是:"+max,  {icon:2})
            return
        }
    }
}

/**
 * 获取多个
 */
function get_graph_all(image_id, ips,title, groups, names, type,     startT, endT) {
    if(!startT){
        startT = ""
    }
    if(!endT){
        endT = ""
    }
    ips = ips.split(",")
    names = names.split(",")
    data = []
    for (i=0;i<ips.length;i++){
        if(!ips[i]){
            continue
        }
        for (j=0;j<names.length;j++) {

            name = names[j]
            if(!name){
                continue;
            }
            url = "/monitor/graph/historyData?ip=" + ips[i] + "&name=" + name + "&type=" + groups+"&startT="+startT+"&endT="+endT;
            get_data = eval(post({}, url));
            data_dict = {}
            data_dict["name"] = ips[i]+" "+name;
            data_dict["data"] = get_data;
            data.push(data_dict)
        }
    }

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    $('#'+image_id).highcharts({
        chart: {
            type: type,
            zoomType: 'x',
        },

        colors: ["#80CD98","#BEA046","#65C1FB","#1ab394","#02FF70","#787C80",
            "#7FDCFE","#0074D9","#FF821B","#FEFBF8","#83144B","#FEF6FF","#FCFDFC","#FDEA74","#F9F7F9",
            "#16FD7E", "#77D6CE", "#3493E1","#f8ac59","#1f90d8",
        ],
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
        series:  data
    });
}
function getRandomData(len){
    data = new Array();
    for (i =0 ;i<len; i++){
        x = (new Date()).getTime(), // current time
            y = Math.random();
        sleep(4)
        data.push([x,y])
    }
    return data;
}

function getRealHistory(ip, name, type) {
    url = "/monitor/graph/historyData?ip="+ip+"&name="+name+"&type="+type;
    datas = eval(post({},url));
    if(!datas){
        return getRandomData(100);
    }
    if (datas.length > 100 ){
        return datas.slice(datas.length-100, datas.length);
    }
    return datas;
}

/**
 *
 * @param server
 * @param groups
 * @param name
 */
function realtime_graph(id, server, groups, name) {
    $('#show_image_data_'+id).hide()

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
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
    var datas_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))
    $('#'+id).highcharts({
        chart: {
            type: 'area',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            zoomType: 'x',
            events: {
                load: function () {
                    var timer;
                    // set up the updating of the chart each second
                    var series = this.series[0];
                    timer = setInterval(function () {
                        realtime = $.cookie("is_realtime");
                        if(realtime=="0" || !realtime || realtime==""){
                            if (timer){
                                clearInterval(timer)
                            }
                            return;
                        }
                        datas = datas_t
                        if (!datas){
                            return;
                        }
                        value = ""
                        for (i=0;i<datas.length;i++){
                            if(datas[i]["name"] == name && datas[i]["groups"] == groups ){
                                value = datas[i]["value"]
                            }
                        }
                        if(!value){
                            return;
                        }

                        x = (new Date()).getTime()
                        y = parseFloat(value)
                        try{
                            series.addPoint([x, y], true, true);
                            datas_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))
                        }catch(Exception){
                        }
                    }, 5000);
                }
            }


        },

        colors: ["#1ab394"],
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: ''
            },
            events: {
                setExtremes: syncExtremes
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#1ab394'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: '数量',
            data: getRealHistory(server,name,groups)
        }]
    });
}

