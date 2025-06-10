<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="70px">
			<el-row>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="类型" prop="type">
						<el-select v-model="form.type">
							<el-option value="" label="">请选择</el-option>
							<el-option value="单选题" label="单选题">单选题</el-option>
							<el-option value="多选题" label="多选题">多选题</el-option>
							<el-option value="判断题" label="判断题">判断题</el-option>
							<el-option value="填空题" label="填空题">填空题</el-option>
							<el-option value="主观题" label="主观题">主观题</el-option>
						</el-select>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="题目" prop="title">
						<el-input v-model="form.title" placeholder="请输入题目"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="选项" prop="question_item">
						<el-input type="textarea" v-model="form.question_item" placeholder="输入问题选项，如：
A.正确
B.错误">

						</el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="参考答案" prop="answer">
						<el-input v-model="form.answer" placeholder="请输入参考答案，如：A.正确,多个选项用|分隔"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="分值" prop="score">
						<el-input-number v-model="form.score"></el-input-number>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="排序" prop="question_order">
						<el-input-number v-model="form.question_order"></el-input-number>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item>
						<el-col :xs="24" :sm="24" :lg="12">
							<el-button style="width: 100%; float: left;" type="primary" @click="submit()">提交
							</el-button>
						</el-col>
						<el-col :xs="24" :sm="24" :lg="12">
							<el-button style="width: 100%; float: right;" @click="cancel()">取消</el-button>
						</el-col>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "exam_question_id",
				url_add: "~/api/exam_question/add?",
				url_set: "~/api/exam_question/set?",
				url_get_obj: "~/api/exam_question/get_obj?",
				url_upload: "~/api/exam_question/upload?",

				query: {
					exam_question_id: 0,
				},

				form: {
					exam_question_id: 0,
					type: "",
					title:"",
					question_item:"",
					answer:"",
					score: 0,
					question_order: 0,
					exam_id: 0
				},

			}
		},
		methods: {
			init_exam_id(){
				let exam_id = this.$route.query.exam_id
				if (exam_id){
					this.form.exam_id = exam_id;
				}
			},
			submit_check(){}
		},
		created() {
			this.init_exam_id();
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}

	.el-row {
		justify-content: start;
		justify-items: start;
		align-items: start;
		align-content: flex-start;
	}
</style>
