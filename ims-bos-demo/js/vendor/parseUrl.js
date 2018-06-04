/**
 * URL的解析和合成，注意：该设计有缺陷，不支持username:userpass，不过一般都用不上
 *
 * var URL = require("lib/util/URL");
 * var urlObj = URL.parse("http://www.baidu.com:8080/index.html?p=1#link1");
 * 得到：
 * {
 *     hash: "link1",
 *     host: "www.baidu.com",
 *     path: "/index.html",
 *     port: "8080",
 *     query: "p=1",
 *     scheme: "http:",
 *     slash: "//",
 *     url: "http://www.baidu.com:8080/index.html?p=1#link1"
 * }
 */
var link = null;
var parseUrl = function(url){
    link = link || document.createElement("A");
    link.href = url;

    var result = {
        "url": url,
        "scheme": link.protocol,
        "host": link.host,
        "port": link.port,
        "path": link.pathname,
        "query": isEmpty(link.search) ? "" : link.search.substr(1),
        "hash": isEmpty(link.hash) ? "" : link.hash.substr(1)
    }
    result["queryJson"] = queryToJson(result["query"]);
    result["hashJson"] = queryToJson(result["hash"]);
    return result;
};
var build = function(url){
    return url.scheme + "//" + url.host + (url.port != "" ? ":" + url.port : "") + url.path + (url.query != "" ? "?" + url.query : "") + (url.hash != "" ? "#" + url.hash : "");    
}
function isEmpty(str){
    str = str || '';
    return str.trim().length == 0;
}
function queryToJson(qs){
        var qList = qs.toString().trim().split("&"),
            json = {},
            i = 0,
            len = qList.length;
    
        for (; i < len; i++) {
            if (qList[i]) {
                var hash = qList[i].split("="),
                    key = hash[0],
                    value = hash[1];
                // 如果只有key没有value, 那么将全部丢入一个$nullName数组中
                if (hash.length < 2) {
                    value = key;
                    key = '$nullName';
                }
                if (!(key in json)) {
                    // 如果缓存堆栈中没有这个数据，则直接存储
                    json[key] = decodeURIComponent(value);
                } else {
                    // 如果堆栈中已经存在这个数据，则转换成数组存储
                    json[key] = [].concat(json[key], decodeURIComponent(value));
                }
            }
        }
        return json;
}