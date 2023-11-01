// 根据 url 中的参数获取对应的值 http(s)://xxxx?key=value&key2=value2
function getUrlParam(key){
    var urlparam = location.search; // ?key=value&key2=value2
    if(urlparam!="" && urlparam.indexOf("?")>=0){
        // url 有值
        urlparam = urlparam.substring(1); // key=value&key2=value2
        var paramArray = urlparam.split("&");
        for(var i=0;i<paramArray.length;i++){
            if(paramArray[i].indexOf("=")>0){
                var kv = paramArray[i].split("=");
                if(kv[0]==key){
                    return kv[1];
                }
            }
        }
    }
    return "";
}