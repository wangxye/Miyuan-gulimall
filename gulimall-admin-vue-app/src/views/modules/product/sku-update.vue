<template>
  <el-dialog
    :title="!flag ? '预览' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    @closed="dialogClose"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-form-item label="sku名称" prop="skuName">
        <el-input v-model="dataForm.skuName" placeholder="名称" v-if="flag"></el-input>
        <el-input v-model="dataForm.skuName" placeholder="名称" v-if="!flag" disabled></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="skuDesc">
        <el-input v-model="dataForm.skuDesc" placeholder="描述" v-if="flag"></el-input>
        <el-input v-model="dataForm.skuDesc" placeholder="描述" v-if="!flag" disabled></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model="dataForm.price" placeholder="价格" v-if="flag"></el-input>
        <el-input v-model="dataForm.price" placeholder="价格" v-if="!flag" disabled></el-input>
      </el-form-item>

      <el-form-item label="标题" prop="skuTitle">
        <el-input v-model="dataForm.skuTitle" placeholder="标题" v-if="flag"></el-input>
        <el-input v-model="dataForm.skuTitle" placeholder="标题" v-if="!flag" disabled></el-input>
      </el-form-item>

       <el-form-item label="副标题" prop="skuSubtitle">
        <el-input v-model="dataForm.skuSubtitle" placeholder="副标题" v-if="flag"></el-input>
        <el-input v-model="dataForm.skuSubtitle" placeholder="副标题" v-if="!flag" disabled></el-input>
      </el-form-item>

      <el-form-item label="默认照片" prop="skuDefaultImg">
        <img :src="dataForm.skuDefaultImg" style="width:160px;height:120px">
      </el-form-item>

    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="!flag" disabled>确定</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="flag">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import CategoryCascader from '../common/category-cascader'
export default {
  data() {
    return {
      props:{
        value:"skuId",
        label:"name",
        children:"children"
      },
      visible: false,
      flag:true,
      categorys: [],
      catelogPath: [],
      dataForm: {
        skuId: 0,
        skuName: "",
        skuDesc: "",
        price: "",
        skuTitle: "",
        skuSubtitle: "",
        skuDefaultImg:"",
      },
      dataRule: {
        skuName: [
          { required: true, message: "sku名称不能为空", trigger: "blur" }
        ],
        price: [{ required: true, message: "价格不能为空", trigger: "blur" }],
        // skuDesc: [
        //   { required: true, message: "描述不能为空", trigger: "blur" }
        // ],
        skuTitle: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        skuSubtitle: [
          { required: true, message: "副标题不能为空", trigger: "blur" }
        ]
      }
    };
  },
  components:{CategoryCascader},
  
  methods: {
    dialogClose(){
      this.catelogPath = [];
    },
    getCategorys(){
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        this.categorys = data.data;
      });
    },
    init(id,flag) {
      this.dataForm.skuId = id || 0;
      this.visible = true;
      this.flag = flag;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.skuId) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/skuinfo/info/${this.dataForm.skuId}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.skuName = data.skuInfo.skuName;
              this.dataForm.price = data.skuInfo.price;
              this.dataForm.skuDesc = data.skuInfo.skuDesc;
              this.dataForm.skuTitle = data.skuInfo.skuTitle;
              this.dataForm.skuSubtitle = data.skuInfo.skuSubtitle;
              this.dataForm.skuDefaultImg = data.skuInfo.skuDefaultImg;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/skuinfo/update`
            ),
            method: "post",
            data: this.$http.adornData({
              skuId: this.dataForm.skuId || undefined,
              skuName: this.dataForm.skuName,
              price: this.dataForm.price,
              skuDesc: this.dataForm.skuDesc,
              skuTitle: this.dataForm.skuTitle,
              skuSubtitle: this.dataForm.skuSubtitle,
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  },
  created(){
    this.getCategorys();
  }
};
</script>
