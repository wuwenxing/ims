
//时间戳转化为日期格式 yyyy-MM-dd 
function getMyDate(str){  
    var oDate = new Date(str),  
    oYear = oDate.getFullYear(),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oHour = oDate.getHours(),  
    oMin = oDate.getMinutes(),  
    oSen = oDate.getSeconds(),  
    oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
    return oTime;  
}; 

//补0操作
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}

//
function timestampToTime(timestamp) {
    var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

//时间戳转化 yyyy-MM-dd HH-mm-ss
function formatDateTime(inputTime) {

    if(inputTime==null || inputTime==undefined  || inputTime=="")
        return "";

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
    var time=y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    return time;    
};  

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) 
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
    
};

function addDate(date,days){ 
    var d=new Date(date); 
    d.setDate(d.getDate()+days); 
    var m=d.getMonth()+1; 
    return d.getFullYear()+'-'+m+'-'+d.getDate(); 
}

function validateDate(startDate,endDate){
	
	 
	 startDate = new Date(startDate.replace(/-/g,"/"));    
	 endDate = new Date(endDate.replace(/-/g,"/"));  
	 
	 var result={msg:'',status:false};
	 if(startDate > endDate){  
         result.msg = '结束时间不能早于开始时间';
         result.status=false;
         return result;  
     } else{
    	 result.msg = 'OK';
         result.status=true;
         return result;  
     } 
	
}

function addTime(end_str){
    if(end_str==null || end_str==undefined)
        return end_str;

    var myDate = new Date(end_str.replace(/-/g,"/"));//将字符串转化为时间
    var hours=myDate.getHours();      //获取当前小时数(0-23)
    var minutes=myDate.getMinutes();    //获取当前分钟数(0-59)
    var seconds=myDate.getSeconds();    //获取当前秒数(0-59)
    if(hours==0 && minutes==0 && seconds==0){
        var year = myDate.getFullYear();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        if(month<10){
            month="0"+month;
        }
        var day = myDate.getDate();
        if(day<10){
            day="0"+day;
        }
        var str=year+"-"+month+"-"+day+" 23:59:59";
        return str;
    }else{
        return end_str;
    }
}

function getSearchEndTime(){
        var myDate = new Date();
        var year = myDate.getFullYear();
        var month = myDate.getMonth() + 1;
        if(month<10){
            month="0"+month;
        }
        var day = myDate.getDate();
         if(day<10){
             day="0"+day;
         }
        var str=year+"-"+month+"-"+day+" 23:59:59";
        return str;
}
