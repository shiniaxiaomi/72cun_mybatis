
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
    props:['isShow',
        'form',
        'fasttype',//标记是否是快捷页面
    ],
    methods:{
        cancleClick(){
            this.$refs['urlAddForm'].resetFields();
            if(this.fasttype!=""){
                this.$emit('close');
            }
        },
        addClick(){
            var this_=this;

            //进行表单验证
            var isPass;
            this.$refs['urlAddForm'].validate(function (valid) {
                isPass=valid;
            });
            if(!isPass) return;


            util.ajax("/url/save",{
                'url':this.form.url,
                'name':this.form.name,
                'pid':this.form.pid,
            },function (data) {
                util.message(this_,data.message);
                console.dir(this_.fasttype)
                if(this_.fasttype!=undefined){
                    setTimeout(function () {//一秒钟后关闭浏览器
                        this_.$emit('finished',data);
                    },1000);
                }else{
                    this_.$emit('addfinished');
                }

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
                this.form.pid=store.get(static.rootFolderId);
            }
        },
    },
}


