

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