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

/**
 * 获取是否需要显示图例
 */
function get_legend_status() {
    value  = $.cookie("legend_show")
    if(value=="1"){
        return true;
    }else{
        return false;
    }
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
    width = $('#'+id+"BorderWidth").val()
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
    width = $('#'+id+"BorderWidth").val()
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
            type: "area",
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
            },
            series: {
                lineWidth : 0.8,
                fillOpacity: 0.15,
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
            enabled: get_legend_status(),
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

function get_data(max, value){
    data_result = value 
    unit = ""
    if(max > 1024){
        data_result =  value / 1024 
        unit =  "K"
        if(data_result > 1024){
          data_result =  value / 1024 / 1024
          unit =  "M"
          if(data_result > 1024){
             data_result = value/ 1024 / 1024 / 1024 
             unit = "G"
          }
        }
    }
   try{
     return data_result.toFixed(2) +unit
   }catch(err){
     return data_result
   }
}

/**
 *
 * 非实时使用
 *
 * */
function get_max_min_avg_last_value(data, id , realtime){
    value = 0
    new_data = new Array();
    for(i=0;i<data.length;i++){
        if(realtime){
          ddd = data[i].split(",")
          if(ddd && ddd[0].length > 1){
             ddd = ddd[1].split(";")
             new_data.push(parseFloat(ddd[1]));
             value += parseFloat(ddd[1])
          }
        }else{
          new_data.push(data[i][1]);
          value += data[i][1]
        }
    }
    last = new_data[new_data.length-1];
    new_data.sort(function (a,b) {
        return a - b;
    })
    min = new_data[0]
    avg = value / new_data.length
    max = new_data[new_data.length-1]
    $("#"+id+"last").html(get_data(max, last))
    $("#"+id+"max").html(get_data(max, max))
    $("#"+id+"min").html(get_data(max, min))
    $("#"+id+"avg").html(get_data(max, avg.toFixed(2)))
    return max
}



function get_select(select){
   if(select){
        return {
            show : true,
            realtime : true,
            start : 80,
            end : 100
        }

   }else{
        return {
            show : false,
            realtime : true,
        }
   }
}

function formatDateTime(inputTime) {
   var date = new Date(inputTime);  
   var y = date.getFullYear();    
   var m = date.getMonth() + 1;    
     m = m < 10 ? ('0' + m) : m;    
   var d = date.getDate();    
   d = d < 10 ? ('0' + d) : d;    
   var h = date.getHours();  
   h = h < 10 ? ('0' + h) : h;  
   var minute = date.getMinutes();  
   var second = date.getSeconds();  
   minute = minute < 10 ? ('0' + minute) : minute;    
   second = second < 10 ? ('0' + second) : second;   
   return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};  

function get_week_day(day){
  switch(day) {

       case 0: 
          return "日"
          break;
       case 1: 
          return "一"
          break;
       case 2: 
          return "二"
          break;
       case 3: 
          return "三"
          break;
       case 4: 
          return "四"
          break;
       case 5: 
          return "五"
          break;
       case 6: 
          return "六"
          break;
  }
}


function get_date(data){
  result = new Array()
  dates = new Array()
  datas = new Array()
} 

function get_grid(select){
   if(select){
     return { x:"56px", 
        x2:"15px", 
        y:"5px", 
        y2:"65px",
      } 
    }else{
     return { x:"56px", 
        x2:"15px", 
        y:"5px", 
        y2:"25px",
      } 
    }
}

function get_date(data){
  result = new Array()
  dates = new Array()
  datas = new Array()
  for(datai=0;datai<data.length; datai++){
     dates.push(formatDateTime(data[datai][0]))
     d = data[datai][1]
     if(d > 1024){
        d1 = d / 1024 
        if(d1 > 1024){
           d2 = d1 / 1024 
           if(d2 > 1024){
              datas.push(d2)
           }else{
              datas.push(d1)
           }
        }else{
           datas.push(d1)
        }
     }else{
        datas.push(data[datai][1])
     }
  }
  result.push(dates)
  result.push(datas)
  return result 
}

function get_dygraph_data(data, name){
  d_data = "Date,"+name+"\n"
  for(datai=0;datai<data.length; datai++){
     ddd = data[datai][1]
     d_data += formatDateTime(data[datai][0])+","+0+";"+ddd+";"+ddd+ "\n" 
  }
  return d_data
}

function graph_min(color, id, title, ytitle, url, chartype,lstartT,lendT, select, notime){
    $('#show_image_data_'+id).show()
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
   if (startT){
        startT = startT.split(",")[0]
       endT = endT.split(",")[0]
   }

   if(url.indexOf("?") == -1){
     data= eval(post({}, url+"?startT="+startT+"&endT="+endT))
   }else{
     data= eval(post({}, url+"&startT="+startT+"&endT="+endT))
   }
   max =  get_max_min_avg_last_value(data, id );
   if(notime){
        rollPeriod = 1
   }else{
        rollPeriod = 11
   }
   
   g = new Dygraph(
      document.getElementById(id),
      get_dygraph_data(data,title),
      {
        showRoller:false, 
        rollPeriod: 1,
        
        color: "#"+color,
        customBars: true,
        //title: 'NYC vs. SF',
        //ylabel: 'Temperature (F)',
        //legend: 'always'
        legend:false,
      }
   );
}

function graph_min_baidu(color, id, title, ytitle, url, chartype,lstartT,lendT, select, notime){
    $('#show_image_data_'+id).show()
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
    timeformar = 0
    if(startT == "" && endT == ""){
         timeformar = 1
    }

    if(!notime){
       data= eval(post({}, url+"&startT="+startT+"&endT="+endT))
       max =  get_max_min_avg_last_value(data, id );
    }else{
      data= eval(post({}, url))
      max =  0; 
    }
   
    data_dates = get_date(data)
    dates = data_dates[0]
    datas = data_dates[1]
    option = {
	    tooltip: {
		trigger: 'axis',
                backgroundColor: '#FeFeFe',
                borderColor: '#'+color,
                borderWidth: 1,
                textStyle: {
                   color:"#000000",
                },
                formatter : function(a,b,c) {  
                       name = a[0]["0"]
                       time = a[0]["1"]
                       date = new Date(time.split(" ")[0])
                       week_day = date.getDay(date)
                       data = a[0]["2"]
                     return  "周"+get_week_day(week_day)+ "," + time.replace(/ /,",") + "<br>"+ name + ": <strong>" + data +"</strong>"
                },  
	    },
	    xAxis: {
		type: 'category',
		boundaryGap: false,
		data: dates,
                axisLine:{//x轴、y轴的深色轴线，如图2
                 show: false,
                 
                },
	        splitLine: {           // 分隔线
	            show: true,        // 默认显示，属性show控制显示与否
	            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	        	color: ['#ccc'],
	        	width: 1,
	        	type: 'solid'
	            }
	        },
                axisLabel :{  
                   interval:"auto",
                   formatter : function(v) {  
                      if(notime == "month"){
                        v1 = v.split(" ") 
                        v3 = v1[0].split("-")
                        return v3[1]
                      }
                      if(timeformar == 1){
                        v1 = v.split(" ") 
                        v3 = v1[1].split(":")
                        return v3[0]+":"+v3[1] 
                      }else{
                        v1 = v.split(" ") 
                        v2 = v1[0].split("-")
                        v3 = v1[1].split(":")
                        return  v2[1]+"-"+v2[2]+" "+v3[0]+":"+v3[1]; 
                      }
                   },  
                } , 
	    },
            grid: get_grid(select),
            dataZoom : get_select(select), 
	    yAxis: {
		type: 'value',
                axisLabel :{  
                   interval:"auto",
                   formatter : function(v) {  
                     return get_data(max, v) 
                   },  
                } , 
                axisLine:{//x轴、y轴的深色轴线，如图2
                 show: false,
                },
	        splitLine: {           // 分隔线
	            show: true,        // 默认显示，属性show控制显示与否
	            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	        	color: ['#ccc'],
	        	width: 1,
	        	type: 'solid'
	            }
	        },
	    },
	    series: [
		{
		    name: title,
		    type: 'line',
		    //stack: '总量',
		    data: datas, 
                    itemStyle : {  
                        normal : {  
                            lineStyle:{  
                                color: "#"+color
                            },
                        //    areaStyle: {type: 'default'}
                        }  
                    },  
		}
	    ]
	};


        // 路径配置
        require.config({
            paths: {
                echarts: '/static/js/echarts/'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart1 = ec.init(document.getElementById(id)); 
                // 为echarts对象加载数据 
                myChart1.setOption(option); 
            }
        );
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
                lineWidth: 1.2,
                states: {
                    hover: {
                        lineWidth: 1.6
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
        plotOptions: {
            spline: {
                lineWidth: 0.2,
                states: {
                    hover: {
                        lineWidth: 0.6
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 3600000, // one hour
                pointStart: Date.UTC(2015, 4, 31, 0, 0, 0),
            },
            series: {
                lineWidth : 0.8,
                fillOpacity: 0.15,
            }
        },
        legend: {
            enabled: get_legend_status(),
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
 * 已废弃
 * @param server
 * @param groups
 * @param name
 */
function realtime_graph_2017_05_03(id, server, groups, name) {
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
            gridLineWidth: 1,
            gridLineColor: '#f0f0f1',
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
        plotOptions: {
            spline: {
                lineWidth: 0.2,
                states: {
                    hover: {
                        lineWidth: 0.6
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 3600000, // one hour
                pointStart: Date.UTC(2015, 4, 31, 0, 0, 0),
            },
            series: {
                lineWidth : 0.8,
                fillOpacity: 0.15,
            }
        },
        legend: {
            enabled: get_legend_status(),
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

function realtime_graph(id, server, groups, name) {
    $('#show_image_data_'+id).show()

    var data_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))

    function get_realtime_data(){
             datas = data_t
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
             try{
                 data_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))
             }catch(Exception){
             }
             return parseFloat(value)
    }


  function get_real_history_data(ip, name, type) {
       url = "/monitor/graph/historyData?ip="+ip+"&name="+name+"&type="+type;
      var datas = eval(post({},url));
       if(!datas){
           return getRandomData(100);
       }
       if (datas.length > 100 ){
           datas = datas.slice(datas.length-100, datas.length);
       }else{
           return getRandomData(100);
       }
       new_data = new Array()
       for(i=0;i<100;i++){
            dd = new Array()
            dd.push(datas[i][0]) 
            dd.push(datas[i][1]) 
            new_data.push(dd)
       }
       return new_data;
   }

   var data = get_dygraph_data(get_real_history_data(server, name , groups), name)
   
   var g = new Dygraph(
      document.getElementById(id),
      data,
      {
        showRoller:false, 
        rollPeriod: 1,
        //drawPoints: true,
        color: "#1ab394",
        customBars: true,
        //title: 'NYC vs. SF',
        //ylabel: 'Temperature (F)',
        //legend: 'always'
        legend:false,
      }
   );
   var input_id = server.replace(/\./g,"")+name.replace(/\./g,"").replace(/-/g,"")
   var timer = new Date().getTime()
   var last_time = $("#"+input_id).val()
   if(!last_time){
      $("#"+id).append("<input style='display:none;' value='"+timer+"' id='"+input_id+"'>")
   }else{
        $("#"+input_id).val(timer)
   }

   var header = "Date,"+name
   var start = 0
   var timers ;
   timers = setInterval(function() {
      input_data = timer 
      input_value = $("#"+input_id).val()
      if(timer != input_value){
        console.log("退出"+input_id)
        clearInterval(timers)
        return
      }
      var new_data = data;
      var v = get_realtime_data() 
      if(!v){return}
      new_data += formatDateTime(new Date().getTime())+ ","+0+";"+v+";"+v+"\n"

      var file_data = header+"\n" 

      var old_data = new_data.split("\n");
      var datas
      if(old_data.length>100){
         datas = old_data.slice(old_data.length-100, old_data.length);
      }else{
        datas = old_data
      }
      for (oi=0;oi<datas.length; oi++){
          if(datas[oi]!=header){
            file_data +=  datas[oi] +"\n"
          }
      }
      data = file_data
      get_max_min_avg_last_value(datas, id, 1);
      start += 1
      if(start > 300){clearInterval(timers)}
      g.updateOptions( { 'file': new_data});
      }, 5000);

}


function realtime_graph_baidu(id, server, groups, name) {

function get_real_history_data(ip, name, type) {
    url = "/monitor/graph/historyData?ip="+ip+"&name="+name+"&type="+type;
    datas = eval(post({},url));
    if(!datas){
console.log("new_data1")
        return getRandomData(100);
    }
    if (datas.length > 100 ){
        datas = datas.slice(datas.length-100, datas.length);
    }else{
        return getRandomData(100);
    }
    new_data = new Array()
    for(i=0;i<100;i++){
         new_data.push(datas[i][1])
    }
    return new_data;
}


    var color = "1ab394";
    option = {
	    tooltip: {
		trigger: 'axis',
                backgroundColor: '#FeFeFe',
                borderColor: '#'+color,
                borderWidth: 1,
                textStyle: {
                   color:"#000000",
                },
                formatter : function(a,b,c) {  
                       name = a[0]["0"]
                       time = a[0]["1"]
                       date = new Date(time.split(" ")[0])
                       week_day = date.getDay(date)
                       data = a[0]["2"]
                     return time.replace(/ /,",") + "<br>"+ name + ": <strong>" + data +"</strong>"
                },  
	    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : true,
            axisLine:{//x轴、y轴的深色轴线，如图2
             show: false,
             
            },
	    splitLine: {           // 分隔线
	        show: true,        // 默认显示，属性show控制显示与否
	        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	    	color: ['#ccc'],
	    	width: 1,
	    	type: 'solid'
	        }
	    },
            axisLabel :{  
               interval:"auto",
               formatter : function(v) {  
                      return  v
               },  
            } , 
            data : (function (){
                var now = new Date();
                var res = [];
                var len = 100;
                while (len--) {
                    res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                    now = new Date(now - 2000);
                }
                return res;
            })()
        },
    ],
    grid: get_grid(""),
    yAxis : [

            {
               type : 'value',
               scale: true,
               name : '',
               axisLine:{//x轴、y轴的深色轴线，如图2
                  show: false,
               },
	    splitLine: {           // 分隔线
	        show: true,        // 默认显示，属性show控制显示与否
	        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	    	color: ['#ccc'],
	    	width: 1,
	    	type: 'solid'
	        }
	    },
            axisLabel :{  
               interval:"auto",
               formatter : function(v) {  
                      return  v
               },  
            } , 
            }
    ],
    series : [
        {
            name:name,
            type:'line',
            itemStyle : {  
                normal : {  
                    lineStyle:{  
                        color: "#"+color
                    },
                }  
            },  
            data: get_real_history_data(server,name,groups)
        }
    ]
};

    var data_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))

    function get_realtime_data(){
             datas = data_t
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
             try{
                 data_t = eval(post({}, "/monitor/graph/all/realtime?server="+server+"&groups="+groups+"&name="+name))
             }catch(Exception){
             }
             return parseFloat(value)
    }

    // 路径配置
    require.config({
            paths: {
                echarts: '/static/js/echarts/'
            }
    });
        
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(id)); 
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                var start = 0
		var axisData;
		var timeTicket;
		clearInterval(timeTicket);
		timeTicket = setInterval(function (){
                        lastData = get_realtime_data() 
                        axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
                        start += 1
                        if(start > 300){clearInterval(timeTicket)}
                        try{ 
                            myChart.addData([
                        	[
                        	    0,        // 系列索引
                        	    lastData, // 新增数据
                        	    false,    // 新增数据是否从队列头部插入
                        	    false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                        	    axisData  // 坐标轴标签
                        	]
                            ]);
                          }catch(err){}
                        }, 2100);
                    
           })
}



function getPie(title, name, type, id, entName, data,size, legend,colors) {
 function get_name(data){
   names = new Array()
   for(i=0;i<data.length;i++){
        name = data[i]["name"]
        if(!name){
           name = "空" 
        }
        if(name && name==""){
           name = "未知" 
        }
        names.push(name)
   }
   return names
 }

 function get_data(data){
     temp = new Array()
     for(i=0;i<data.length;i++){
        td ={}
        name = data[i]["name"]
        if(!name){
           name = "空" 
        }
        if(name && name==""){
           name = "未知" 
        }
        td["name"] = name
        td["value"] = data[i]["y"]
        temp.push(td)
     }
     return temp;
 }
 if(!entName){
      entName = ""
 }

if(!colors){
var  colors = ["#1ab394", "#f8ac59","#CC66CC","#1f90d8","#FFB94F","#1cc09f","#3c763d","#99389f","#017ebc","#ACD6FF", "#CAFFFF", "#ADFEDC", "#28FF28", "#FFD306", "#CCFF80", "#FeFFAA", "#FFE4CA", "#81C0C0", "#82D900", "#D2E9FF", "#FF5151", "#921AFF", "#28FF28", "#FFDC35", "#DEFFAC", "#FF5809", "#4A4AFF"];
}

if(!data){
   data= post({type: type}, '/resource/configure/server/countDataReport?entName=' + entName)
}
if(!size){
  size = 100
}
if(!legend){
 legend = true
}else{
 legend = false
}
option = {
    tooltip : {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        show: true,
        orient : 'vertical',
        x : 'bottom',
        data:get_name(data)
    },
    title:{
        x: "center",
        textAlign:'left',
        y:10,
        text: title,
    },
    color: colors,
    calculable : false,
    series : [
        {
            name: name,
            type:'pie',
            selectedMode: 'single',
            radius : [0, size],
            
            // for funnel
            y: '140%',
            width: '90%',
            funnelAlign: 'right',
            //max: 1548,
            
            itemStyle : {
                normal : {
                    label : {
                        position : 'outer',
                        formatter: "{b} : {c} ({d}%)",
                        textStyle: {
                             color :"#111111",
                        },
                    },
                    labelLine : {
                        show : true 
                    },
                },
                color:"#000000",
            },
            data:get_data(data),
        },
    ]
};
                    

    // 路径配置
    require.config({
            paths: {
                echarts: '/static/js/echarts/'
            }
    });
        
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                var ecConfig = require('echarts/config');
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(id)); 
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                myChart.on(ecConfig.EVENT.PIE_SELECTED, function (param){
                      var selected = param.selected;
                      var serie;
                      var str = '当前选择： ';
                      for (var idx in selected) {
                          serie = option.series[idx];
                          for (var i = 0, l = serie.data.length; i < l; i++) {
                              if (selected[idx][i]) {
                          	str += '【系列' + idx + '】' + serie.name + ' : ' + 
                          	       '【数据' + i + '】' + serie.data[i].name + ' ';
                              }
                          }
                      }
                      //console.log(str)
                  //    document.getElementById('wrong-message').innerHTML = str;
                })
            }
     )

}
