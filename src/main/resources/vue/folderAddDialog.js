
var folderAddDialog={
    // language=HTML
    template:`
        <!-- 文件夹添加窗口 -->
        <el-dialog title="添加" :visible.sync="isShow" :close-on-click-modal="false" :center="false" :showClose="false">
            <el-form :model="form" :rules="rules" ref="folderAddForm" >
                <el-form-item label="文件夹名称" label-width="120px" prop="name">
                    <el-input v-model="form.name" autocomplete="off" ></el-input>
                </el-form-item>

                <el-form-item label="文件夹位置" label-width="120px" prop="location">
                    <folder-location :form="form" ></folder-location><!-- 文件夹位置组件 -->
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer" >
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