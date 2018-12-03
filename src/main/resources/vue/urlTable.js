

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
    props:['tableData',
        'form',
        'type',//标记是搜索页面还是文件夹管理页面
        'nodeId',
        'keyword',//搜索的关键字
        'currentNodeId',//当前选中文件夹的id
        'fasttype',//标记是否是快捷页面操作
        'searchtype',//标记查询类型(只区分在搜索页面的,因为要分url和文件夹搜索),1--搜索页面查询,2---文件夹管理查询
    ],
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
            mysearchtype:this.searchtype,
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
            this.form.id=row.id;
            this.form.pid=row.folderId;
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
                'folderId':this.form.pid,
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
                    'pageIndex':this_.pageIndex,
                    'searchType':this_.mysearchtype,
                    'needCount':true,
                }
            }else{
                console.dir(this_.currentNodeId)
                url='/url/query';
                data={
                    'id':this_.currentNodeId,
                    'pageSize':this_.pageSize,
                    'pageIndex':this_.pageIndex,
                    'needCount':true,
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
        searchtype(){
            this.mysearchtype=this.searchtype;
        },
    }
}


