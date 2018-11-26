/**
 * Created by LuYingJie on 2018/10/10.
 */



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

//公共数据方法
var common={
    //获取文件夹数据
    loadTreeData:function (this_) {
        //如果mainVue没有数据,则请求一次,如果已经请求一次了,就是使用已经请求的
        //如果mainVue有数据,则以mainVue为准
        var mainVue=top.window.mainVue;
        if(mainVue==undefined){//没有定义,则是快捷收藏
            util.ajax('/folder/query',{},function (data) {
                if(data!=null){
                    this_.treeData= buildTree(data);
                }
            })
        }else if(mainVue.treeData.length==0){
            //请求树数据,,并保存在公共区
            util.ajax('/folder/query',{},function (data) {
                if(data!=null){
                    var treeData=buildTree(data);
                    mainVue.treeData=treeData;//保存一份在mainVue中
                    this_.treeData=treeData;
                    mainVue.rootFolderId=treeData[0].id;
                    console.dir(mainVue.rootFolderId)
                }
            })
        }else{
            this_.treeData=mainVue.treeData;//从mainVue中获取数据
        }
    },
    //修改之后重新加载文件夹数据
    reloadTreeData:function (this_, data) {
        var mainVue=top.window.mainVue;
        mainVue.treeData=data;//重新加载树
        this_.treeData=data;
    },
    //获取公共对象
    getMainVue:function () {
        var mainVue=top.window.mainVue;
        return mainVue;
    },
    //加载自定义文件夹id
    loadNodeId:function (this_) {
        //如果mainVue没有数据,则请求一次,如果已经请求一次了,就是使用已经请求的
        //如果mainVue有数据,则以mainVue为准
        var mainVue=top.window.mainVue;
        if(mainVue==undefined){//没有定义,则是快捷收藏
            util.ajax("/userSettings/query", {}, function (data) {
                if (data != null) {
                    this_.form.pid=data.defaultFolderId;
                    this_.form.location=data.defaultFolderName;
                    this_.$forceUpdate();//手动更新数据
                }
            });
        }else if(mainVue.nodeId==''){//如果公共区没有数据,加载并赋值
            //请求自定义文件夹id
            util.ajax("/userSettings/query", {}, function (data) {
                if (data != null) {
                    mainVue.nodeId = data.defaultFolderId;//保存一份在公共数据区
                    mainVue.nodeName=data.defaultFolderName;
                    this_.form.pid=data.defaultFolderId;
                    this_.form.location=data.defaultFolderName;
                }
            });
        }else {
            this_.form.pid=mainVue.nodeId;//从公共数据区获取
            this_.form.location=mainVue.nodeName;//从公共数据区获取
        }
    },
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

//url表格展示组件
var urlTable={
    // language=HTML
    template:`
        <div>
            <!-- url展示 -->
            <el-table :data="content" style="width: 100%;height: 100%" >
                
                
                <el-table-column label="网址名称">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="网址链接" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span><a :href="scope.row.url" target="_blank" style="text-decoration:none" @click="urlClick">{{ scope.row.url }}</a></span>
                    </template>
                </el-table-column>
                <el-table-column label="链接位置">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.location }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="创建时间">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" @click="handleDelete(scope.$index, content,scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
    
            <!-- 分页 -->
            <div style="text-align: center;">
                <el-pagination @size-change="handleSizeChange" 
                               @current-change="handleCurrentChange" 
                               :current-page="pageIndex"
                               :page-sizes="pageSizes" 
                               :page-size="pageSize" 
                               layout="total, sizes, prev, pager, next, jumper" 
                               :total="totalSize">
                </el-pagination>
            </div>
            
            <!---------------------------------------- 弹窗 -------------------------------------------->
            <!-- url编辑窗口 -->
            <el-dialog title="编辑" :visible.sync="urlUpdateVisible" :close-on-click-modal="false" :center="false" :showClose="false">
                <el-form :model="form" :rules="rules" ref="urlUpdateForm">
                
                    <input v-model="form.id" style="display: none">
                
                    <el-form-item label="网址名称" label-width="120px" prop="name">
                        <el-input v-model="form.name" autocomplete="off" :maxlength="100"></el-input>
                    </el-form-item>
                    <el-form-item label="网址链接" label-width="120px" prop="url">
                        <el-input v-model="form.url" autocomplete="off" :maxlength="600"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="文件位置" label-width="120px" prop="location">
                        <folder-location :form="form" ></folder-location><!-- 文件夹位置组件 -->
                    </el-form-item>
                    
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="cancleClick">取消</el-button>
                    <el-button type="primary" @click="updateClick(content)">更改</el-button>
                </div>
            </el-dialog>
            
            
        </div>
    `,
    props:['tableData','form','type','nodeId','keyword','currentNodeId','fasttype'],
    data(){
        return{
            pageSize:this.tableData.pageSize,//一页中的条数
            pageIndex:this.tableData.pageIndex,//显示第1页
            totalSize:this.tableData.totalSize,//总共的条数
            pageSizes:util.pageSizes,
            content:this.tableData.content,//表格数据

            urlUpdateVisible:false,
            currentRow:'',//当前的编辑行
            currentIndex:'',//当前编辑行的编号
            rules: {
                name: [
                    { required: true, message: '请输入网址名称', trigger: 'blur' },
                    { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
                ],
                url: [
                    { required: true, message: '请输入网址', trigger: 'blur' },
                    { min: 1, max: 600, message: '长度在 1 到 600 个字符', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请选择网址位置', trigger: 'blur' },
                ],
            },
        }
    },
    methods:{
        handleSizeChange(val) {
            this.pageSize=val;

            this.search();
        },
        handleCurrentChange(val) {
            this.pageIndex=val;

            this.search();
        },
        handleEdit(index,row){//编辑行
            // console.dir(row)
            this.form.id=row.id;
            this.form.pid=row.pid;
            this.form.name=row.name;
            this.form.url=row.url;
            this.form.location=row.location;
            this.currentRow=row;//将当前编辑行保存起来,方便使用
            this.currentIndex=index;
            this.urlUpdateVisible=true;
        },
        handleDelete(index,rows,row){//删除行
            var this_=this;
            util.ajax('/url/delete',{'id':row.id},function (data) {
                if(data!=null){

                    rows.splice(index, 1);
                    //this_.totalSize=this_.totalSize-1;

                    util.message(this_,'删除成功!');
                }
            })

        },
        cancleClick(){//编辑取消
            this.$refs['urlUpdateForm'].resetFields();

            this.urlUpdateVisible = false;
            this.form.name='';
            this.form.url='';
            this.form.location='';
        },
        updateClick(rows){//更新当前行
            //进行表单验证
            var isPass;
            this.$refs['urlUpdateForm'].validate(function (valid) {
                isPass=valid;
            });
            if(!isPass) return;

            console.dir(this.currentRow)
            var this_=this;
            util.ajax('/url/update',{
                'id':this.form.id,
                'pid':this.form.pid,
                'name':this.form.name,
                'url':this.form.url,
                'location':this.form.location
            },function (data) {
                if(data!=null){
                    console.dir(this_.type)
                    if(this_.type!='search' && this_.currentRow.pid!=this_.form.pid){//如果是url管理界面,并且url位置改变,则删除
                        rows.splice(this_.currentIndex,1);
                    }else{
                        this_.currentRow.pid=this_.form.pid;
                        this_.currentRow.name=this_.form.name;
                        this_.currentRow.url=this_.form.url;
                        this_.currentRow.location=this_.form.location;
                    }

                    this_.urlUpdateVisible = false;
                    util.message(this_,'更改成功!');
                }
            })
        },
        search(){
            var this_=this;
            var url;
            var data;
            //请求表格数据
            if(this.type=='search'){
                console.dir(this_.keyword)
                url='/url/queryAllLike';
                data={
                    'urlName':this_.keyword,
                    'pageSize':this_.pageSize,
                    'pageIndex':this_.pageIndex
                }
            }else{
                console.dir(this_.currentNodeId)
                url='/url/query';
                data={
                    'id':this_.currentNodeId,
                    'pageSize':this_.pageSize,
                    'pageIndex':this_.pageIndex
                }
            }

            util.ajax(url,data,function (data) {
                console.dir(data)
                if(data!=null){
                    this_.content=data.content;
                }
            })
        },
        urlClick(){
            console.dir(this.fasttype)
            if(this.fasttype!=""){
                console.dir(11111111111)
                this.$emit("closewindow");
            }
        }

    },
    watch:{
        tableData(){
            console.dir(this.tableData)
            this.content=this.tableData.content;
            this.pageSize=this.tableData.pageSize;
            this.pageIndex=this.tableData.pageIndex;
            this.totalSize=this.tableData.totalSize;
        },
    }
}

var urlAddDialog={
    // language=HTML
    template:`
        <el-dialog title="添加" :visible.sync="isShow" :close-on-click-modal="false" :showClose="false" width="700px">
            <el-form :model="form" :rules="rules" ref="urlAddForm">
                <el-form-item label="网址名称" label-width="120px" prop="name">
                    <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item label="网址链接" label-width="120px" prop="url">
                    <el-input v-model="form.url" autocomplete="off" style="width: 90%"></el-input>
                </el-form-item>
                
                <el-form-item label="网址位置" label-width="120px" prop="location">
                    <folder-location :form="form" ></folder-location><!-- 文件夹位置组件 -->
                </el-form-item>
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancleClick">取消</el-button>
                <el-button type="primary" @click="addClick">添加</el-button>
            </div>
        </el-dialog>
    `,
    props:['isShow','form'],
    methods:{
        cancleClick(){
            this.$refs['urlAddForm'].resetFields();
            this.$emit('close');
        },
        addClick(){
            var _this=this;

            //进行表单验证
            var isPass;
            this.$refs['urlAddForm'].validate(function (valid) {
                isPass=valid;
            });
            if(!isPass) return;

            var this_=this;
            util.ajax("/url/save",{
                'url':this.form.url,
                'name':this.form.name,
                'pid':this.form.pid,
            },function (data) {
                util.message(this_,data.message);
                setTimeout(function () {//一秒钟后关闭浏览器
                    _this.$emit('finished',data);
                },1000);

            })
        },
    },
    data(){
        return{
            rules: {
                name: [
                    { required: true, message: '请输入网址名称', trigger: 'blur' },
                    { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
                ],
                url: [
                    { required: true, message: '请输入网址', trigger: 'blur' },
                    { min: 1, max: 600, message: '长度在 1 到 600 个字符', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请选择网址位置', trigger: 'blur' },
                ],
            },
            nodeId:'',
        }
    },
    mounted(){
        common.loadTreeData(this);
    },
    watch: {
        isShow(){//显示前清空表单
            console.dir(this.isShow)
            if(this.isShow==true){
                this.form.name='';
                this.form.url='';
                this.form.location='默认文件夹';
                this.form.pid=this.treeData[0].id;
            }
        },
    },
}

var folderAddDialog={
    // language=HTML
    template:`
        <!-- 文件夹添加窗口 -->
        <el-dialog title="添加" :visible.sync="isShow" :close-on-click-modal="false" :center="false" :showClose="false">
            <el-form :model="form" :rules="rules" ref="folderAddForm">
                <el-form-item label="文件夹名称" label-width="120px" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                
                <el-form-item label="文件夹位置" label-width="120px" prop="location">
                    <folder-location :form="form" ></folder-location><!-- 文件夹位置组件 -->
                </el-form-item>
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancleClick">取消</el-button>
                <el-button type="primary" @click="addClick">添加</el-button>
            </div>
        </el-dialog>
    `,
    props:['isShow','form'],
    data(){
        return{
            rules: {
                name: [
                    { required: true, message: '请输入文件夹名称', trigger: 'blur' },
                    { min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请选择文件夹位置', trigger: 'blur' },
                ],
            }
        }
    },
    methods:{
        cancleClick(){
            this.$refs['folderAddForm'].resetFields();
            this.$emit('close',null);
        },
        addClick(){
            //进行表单验证
            var isPass;
            this.$refs['folderAddForm'].validate(function (valid) {
                isPass=valid;
            });
            if(!isPass) return;

            var this_=this;
            //发送添加请求,返回文件夹id
            util.ajax('/folder/insertChildren',{'name':this.form.name,'pid':this.form.pid},function (data) {
                if(data==null) return;
                util.ajax('folder/query',{},function (data) {
                    this_.$emit('close',data);//发布关闭事件,并把返回的node添加到父组件上
                })
            });
        },
    },
    watch:{
        isShow(){
            if(this.isShow==true){//默认选中默认文件夹
                //this.form.pid=this.treeData[0].id;
                //this.form.name='';
                //this.form.location='默认文件夹';
            }
        },
    }


}

var folderUpdateDialog={
    // language=HTML
    template:`
        <!-- 文件夹添加窗口 -->
        <el-dialog title="更改" :visible.sync="isShow" :close-on-click-modal="false" :center="false" :showClose="false">
            <el-form :model="form" :rules="rules" ref="folderUpdateForm">
                <input style="display: none" v-model="form.id">
                <input style="display: none" v-model="form.pid">
                <el-form-item label="文件夹名称" label-width="120px" prop="name">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                
                <el-form-item label="文件夹位置" label-width="120px" prop="location">
                    <folder-location :form="form" type="update" :ids="ids"></folder-location><!-- 文件夹位置组件 -->
                </el-form-item>
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancleClick">取消</el-button>
                <el-button type="primary" @click="updateClick">更改</el-button>
            </div>
        </el-dialog>
    `,
    props:['isShow','form','ids'],
    data(){
        return{
            rules: {
                name: [
                    { required: true, message: '请输入文件夹名称', trigger: 'blur' },
                    { min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请选择文件夹位置', trigger: 'blur' },
                ],
            }
        }
    },
    methods:{
        cancleClick(){
            this.$refs['folderUpdateForm'].resetFields();
            this.$emit('close',null);
        },
        updateClick(){
            //进行表单验证
            var isPass;
            this.$refs['folderUpdateForm'].validate(function (valid) {
                isPass=valid;
            });
            if(!isPass) return;


            var this_=this;
            util.ajax('folder/update',{'id':this.form.id,'name':this.form.name,'pid':this.form.pid},function (data) {
                if(data==null) return;
                util.ajax('folder/query',{},function (data) {
                    this_.$emit('close',data);
                })
            })
        },
    },
    watch:{
    }


}

//注册全局组件
Vue.component('folderLocation',{
    // language=HTML
    template:`
        <div>
            <input v-model="form.pid" style="display: none">
            <el-input v-model="form.location" placeholder="请选择文件夹" style="width: 200px" :disabled="true"></el-input>

            <el-popover placement="bottom-end" width="400" trigger="click" v-model="treeShowValue" >

                <div style="overflow:auto;width: 380px;height: 200px">
                    <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
                    <el-tree class="filter-tree"
                             :data="treeData"
                             :props="defaultProps"
                             default-expand-all
                             :highlight-current="true"
                             :expand-on-click-node="false"
                             node-key="id"
                             @node-click="nodeClick"
                             ref="tree5"
                             :filter-node-method="filterNode">
                    </el-tree>
                </div>

                <el-button slot="reference">选择文件夹</el-button>
            </el-popover>

            <el-button @click="defaultClick">自定义文件夹</el-button>
        </div>
    `,
    props:['form','type','ids'],
    data(){
        return{
            treeShowValue:false,
            filterText:'',
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            treeData:[],
        }
    },
    methods:{
        filterNode(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        nodeClick(node){//可以接收三个参数

            if(this.type=='update'){//如果是更新文件夹则要进行检查
                for(var i=0;i<this.ids.length;i++){
                    if(node.id==this.ids[i]){
                        util.message(this,'不能选择本身及子结点','error');
                        return;
                    }
                }
            }

            // console.dir(node)
            this.form.pid=node.id;
            this.form.location=node.name;
            this.treeShowValue=false

            console.dir('pid:'+this.form.pid)
        },
        defaultClick(){//自定义文件夹
            common.loadNodeId(this);//加载自定义文件夹的id和name
        },
    },
    watch:{
        filterText(val) {
            this.$refs.tree5.filter(val);
        },
        treeShowValue(){//更新文件夹数据

            this.$refs.tree5.setCurrentKey(this.form.pid);//显示原来的文件夹的位置

            this.filterText='';//清除搜索数据

            if(this.treeShowValue){//当显示的时候,获取一下树数据
                common.loadTreeData(this);//自动加载数据
            }
        },
    }
})