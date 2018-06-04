
//获取验证码
function generateCode() {
	this.code = '';
	var codeLength = 6;// 验证码的长度
	var codeObj = $('#' + this.codeObjId);
	var selectChar = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];

	for (var i = 0; i < codeLength; i++) {
		var charIndex = Math.floor(Math.random() * 26);
		this.code += selectChar[charIndex];
	}
	if (codeObj.length) {
		codeObj.val(this.code);
	}
	return this.code;
}

function getcode(obj,objvalue){
	var code=generateCode();
	$(obj).text(code);
	$("#"+objvalue).val(code);
	 //obj.text(code);
}

// function setOptions(obj){
// 	obj.set();
// }

common={
    //json转url参数  a=1&b=2 类型
     parseParam : function(param, key) {
        var paramStr = "";
        if (param instanceof String || param instanceof Number || param instanceof Boolean) {
            paramStr += "&" + key + "=" + encodeURIComponent(param);
        } else {
            $.each(param, function(i) {
                var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
                paramStr += '&' + common.parseParam(this, k);
            });
        }
        return paramStr.substr(1);
    },

    unique:function(arr) {
        var result = [], isRepeated;
        for (var i = 0, len = arr.length; i < len; i++) {
            isRepeated = false;
            for (var j = 0, len = result.length; j < len; j++) {
                if (arr[i] == result[j]) {
                    isRepeated = true;
                    break;
                }
            }
            if (!isRepeated) {
                result.push(arr[i]);
            }
        }
        return result;
    }
}