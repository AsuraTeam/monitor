function show_hosts(host) {
    url = "/resource/configure/server/listData?length=100000&start=1&draw=1";
    result = post({hosts:host},url);
    data = result["data"];

    html = ""
    for(i=0;i<data.length;i++) {
        html += '<button class="col-sm-3 btn btn-outline btn-default" style="margin-left:10px;border:1px solid #f0f0f0;width:150px;height: 40px;margin-top:5px;">' +data[i]["ipAddress"] +"</button>"
    }
    $("#show_ip_detail_html").html(html);
    $('#show_ip_address').modal("toggle")
}