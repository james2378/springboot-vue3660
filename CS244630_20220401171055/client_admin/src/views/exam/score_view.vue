<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="70px">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="24">
					<div class="title_margin" style="margin-bottom: 20px"  v-if="form.score_state===1">已评分，得分：<span style="color: red">{{form.score}}</span></div>
				</el-col>
				<el-col v-for="(o,i) in question_list" :xs="24" :sm="24" :lg="24">
					<div class="title_margin" v-if="o.type === '单选题'">
						{{o.question_order+'.单选题：'+o.title+"   ("+o.score+"分)"}}
						<span class="score_show">--------{{answer_scores[i]}}分</span>
					</div>
					<div class="title_margin" v-else-if="o.type === '多选题'">
						{{o.question_order+'.多选题：'+o.title+"   ("+o.score+"分)"}}
						<span class="score_show">--------{{answer_scores[i]}}分</span>
					</div>
					<div class="title_margin" v-else-if="o.type === '判断题'">
						{{o.question_order+'.判断题：'+o.title+"   ("+o.score+"分)"}}
						<span class="score_show">--------{{answer_scores[i]}}分</span>
					</div>
					<div class="title_margin" v-else-if="o.type === '填空题'">
						{{o.question_order+'.填空题：'+o.title+"   ("+o.score+"分)"}}
						<span class="give_score" v-if="!if_scored">评分
							<el-input-number v-model="answer_scores[i]">
							</el-input-number>
							分
						</span>
						<span class="score_show" v-else>--------{{answer_scores[i]}}分</span>
					</div>
					<div class="title_margin" v-else>
						{{o.question_order+'.主观题：'+o.title+"   ("+o.score+"分)"}}
						<span class="give_score" v-if="!if_scored">评分
							<el-input-number v-model="answer_scores[i]">
							</el-input-number>
							分
						</span>
						<span class="score_show" v-else>--------{{answer_scores[i]}}分</span>
					</div>
					<el-form-item v-if="o.type === '单选题'" label="">
						<el-radio :disabled="true" v-for="q in get_question_list(o.question_item)" v-model="answers[i]" :label="q">{{q}}</el-radio>
						<div v-if="if_scored && reference_answers[i]">
							参考答案：{{reference_answers[i]}}
						</div>
					</el-form-item>
					<el-form-item v-else-if="o.type === '多选题'" label="">
						<el-checkbox-group :disabled="true" v-model="answers[i]">
							<el-checkbox v-for="q in get_question_list(o.question_item)" :label="q"></el-checkbox>
						</el-checkbox-group>
						<div v-if="if_scored && reference_answers[i]">
							参考答案：{{reference_answers[i]}}
						</div>
					</el-form-item>
					<el-form-item v-else-if="o.type === '判断题'" label="">
						<el-radio :disabled="true" v-for="q in get_question_list(o.question_item)" v-model="answers[i]" :label="q">{{q}}</el-radio>
						<div v-if="if_scored && reference_answers[i]">
							参考答案：{{reference_answers[i]}}
						</div>
					</el-form-item>
					<el-form-item v-else-if="o.type === '填空题'" label="">
						<el-input :disabled="true" style="width: 500px" v-model="answers[i]"></el-input>
						<div v-if="if_scored">
							参考答案：{{reference_answers[i]}}
						</div>
					</el-form-item>
					<el-form-item v-else label="">
						<el-input :disabled="true" type="textarea" v-model="answers[i]">
						</el-input>
						<div v-if="if_scored">
							参考答案：{{reference_answers[i]}}
						</div>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item label="提交人">
						<el-input disabled="" v-model="this.form.nickname"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-form-item>
						<el-col :xs="24" :sm="24" :lg="12">
							<el-button v-if="!if_scored" style="width: 100%; float: left;" type="primary" @click="submit()">提交
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
				field: "user_answer_id",
				url_add: "~/api/user_answer/add?",
				url_set: "~/api/user_answer/set?",
				url_get_obj: "~/api/user_answer/get_obj?",
				url_upload: "~/api/user_answer/upload?",

				query: {
					user_answer_id: 0,
				},
				form: {
					user_answer_id:0,
					user_id:0,
					exam_id:0,
					score:0,
					score_state:0,
					answers:[],
					score_detail:[],
					objective_score:0,
					subjective_score:0,
					nickname:""
				},
				url_get_question_list: "~/api/exam_question/get_list?",
				question_list:[],
				answers:[],
				if_scored:false,
				answer_scores:[],
				reference_answers:[]
			}
		},
		methods: {
			async get_obj_after(){
				if (this.form.score_state===1){
					this.if_scored = true
				}
				let json = await this.$get(this.url_get_question_list+"exam_id="+this.form.exam_id);
				if(json.result){
					this.question_list = json.result.list;
					for (let i=0;i<this.question_list.length;i++){
						if (this.question_list[i].type==='多选题'){
							this.answers[i]=[];
						}else{
							this.answers[i]="";
						}
					}
				}else if(json.error){
					console.error(json.error);
				}
				this.form.answers = JSON.parse(this.form.answers);
				this.form.score_detail = JSON.parse(this.form.score_detail);
				for(let i=0;i<this.form.answers.length;i++){
					this.answers[i] = this.form.answers[i].answer;
					this.answer_scores[i] = this.form.score_detail[i].score;
					if (this.question_list[i].type==='单选题' || this.question_list[i].type==='判断题' || this.question_list[i].type==='多选题') {
						if (this.answer_scores[i]!==this.question_list[i].score){
							this.reference_answers[i]=this.question_list[i].answer;
						}
					}else{
						this.reference_answers[i]=this.question_list[i].answer;
					}
				}
			},
			get_question_list(question_str){
				let question_list = question_str.split("\n");
				return question_list;
			},
			submit_main(){
				let _this = this;
				_this.form.answers = JSON.stringify(_this.form.answers);
				for (let i=0;i<_this.answer_scores.length;i++){
					if (this.question_list[i].type==='填空题' || this.question_list[i].type==='主观题') {
						_this.form.score_detail[i].score = this.answer_scores[i];
            _this.form.subjective_score = parseFloat(_this.form.subjective_score) + parseFloat(this.answer_scores[i]);
					}
				}
        _this.form.score = parseFloat(_this.form.subjective_score)+parseFloat(_this.form.objective_score);
				_this.form.score_state = 1;
				_this.form.score_detail = JSON.stringify(_this.form.score_detail);
				let url = _this.url_set+"user_answer_id="+_this.query.user_answer_id;
				this.$post(url, _this.form, function(json, status) {
					console.log("提交结果：" ,json);
					if (json.result) {
						_this.events("submit_after", json, null);
					} else if (json.error) {
						_this.$toast(json.error.message, 'danger');
					}
				});
			},
		},
		created() {
		}
	}
</script>

<style>
	.title_margin{
		margin-left: 70px;
	}
	.give_score{
		margin-left: 50px;
	}
	.countdown span{
		color: red;
	}
	.score_show{
		margin-left: 20px;
		color: red;
	}
</style>
