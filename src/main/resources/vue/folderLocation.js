
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

                <el-button slot="reference">选择</el-button>
            </el-popover>

            <el-button @click="defaultClick">自定义</el-button>
        </div>
    `,
    props:['form',//传入的form表单,跟父组件的pid和location双向绑定
        'type',//区分是否是更新文件夹
        'ids'],//传入所有treedata的id
    data(){
        return{
            treeShowValue:false,
            filterText:'',
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            treeData:store.get(static.treeData),
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

            //console.dir('pid:'+this.form.pid)
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

            this.$refs.tree5.setCurrentKey(this.form.pid);//显示父的文件夹的位置

            this.filterText='';//清除搜索数据

            if(this.treeShowValue){//当显示的时候,获取一下树数据
                common.loadTreeData(this);//自动加载数据
            }
        },
    },
    mounted(){
        console.dir(this.form)
    },
})