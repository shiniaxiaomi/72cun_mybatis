
//静态常量
var static={
    treeData:"treeData",
    rootFolderId:"rootFolderId",
    nodeId:"nodeId",
    nodeName:"nodeName",
    user:"user",
}

//公共数据方法
var common={
    //获取文件夹数据
    loadTreeData:function (this_) {
        //如果mainVue没有数据,则请求一次,如果已经请求一次了,就是使用已经请求的
        //如果mainVue有数据,则以mainVue为准
        var buff=store.get(static.treeData);
        if(buff==undefined){
            util.ajax('/folder/query',{},function (data) {
                if(data.length!=0){
                    var treeData=buildTree(data);
                    this_.treeData=treeData;
                    store.set(static.treeData,treeData);//保存一份在localstorage中
                    store.set(static.rootFolderId,treeData[0].id);
                }
            })
        }else{
            this_.treeData=store.get(static.treeData);//从localstorage中获取数据
        }
    },
    //修改之后重新加载文件夹数据
    reloadTreeData:function (this_, data) {
        //var mainVue=top.window.mainVue;
        //mainVue.treeData=data;
        var treeData=buildTree(data);
        this_.treeData=treeData;
        store.set(static.treeData,treeData);//更新localstorage中的数据
    },
    //加载自定义文件夹id
    loadNodeId:function (this_) {
        //如果mainVue没有数据,则请求一次,如果已经请求一次了,就是使用已经请求的
        //如果mainVue有数据,则以mainVue为准
        var buff=store.get(static.nodeId);
        if(buff==undefined){
            util.ajax("/userSettings/query", {}, function (data) {
                if (data != null) {
                    this_.form.pid=data.defaultFolderId;//更新数据
                    this_.form.location=data.defaultFolderName;

                    store.set(static.nodeId,data.defaultFolderId);//保存一份在公共数据区
                    store.set(static.nodeName,data.defaultFolderName);
                }
            });
        }else{
            this_.form.pid=store.get(static.nodeId);//从公共数据区获取
            this_.form.location=store.get(static.nodeName);
        }
    },
}

