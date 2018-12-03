
//将数据转化成树形结构数组
function fn(data,pid) {
    var result = [], temp;
    for (var i = 0; i < data.length; i++) {
        if (data[i].pid == pid) {
            var obj = {
                "name": data[i].name,
                "id": data[i].id,
                "pid": data[i].pid,
                "folderNum": data[i].folderNum,
                "hasURL": data[i].hasURL,
            };
            temp = fn(data, data[i].id);
            if (temp.length > 0) {
                obj.children = temp;
            }
            result.push(obj);
        }
    }
    return result;
}
//将数据转化成树形结构数组
function buildTree(data) {
    return fn(data,0);
}


//工具对象
var util={
    pageSize:8,//一个页面的总个数
    pageIndex:1,//页面的编号
    //pageSizes:[20, 40, 60, 80],
    pageSizes:[8, 16, 24, 32],
    //异步的ajax请求
    ajax:function (url,data,func) {
        $.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            data: data,
            error: function (data) {
                if(data.status==309){//自己设置的错误码,表示session失效
                    console.dir("session 失效")
                    top.window.location.href='/';//发生错误之后,就直接重定向到登入页面,一般是session失效了
                }
                console.dir("传输失败!")
                console.dir(data);//请求失败时被调用的函数
                console.dir("传输失败!")
            },
            success: function (data) {
                func(data);
            }
        });
    },
    //消息提示,时间停留1秒钟
    message:function (this_,data,type) {
        if(type=='error'){
            this_.$message.error({message:data,duration:1000});
        }else{
            this_.$message({message:data,duration:1000});
        }
    }
}