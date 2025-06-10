<template>
    <div class="page_exam" id="exam_view">
        <div class="warp" v-if="$check_exam('/exam/list', 'answers')">
            <div class="container exam-detail">
                <div class="row">
                    <div v-if="!if_answered" class="countdown">
                        距离考试结束还有：<span>{{minutes+":"+seconds}}</span>
                    </div>
                    <div class="col-24 col-lg-24 col-md-24">
                        <div v-if="if_answered" class="col-24 col-lg-24 col-md-24">
                            <div class="title_margin" style="margin-bottom: 20px" v-if="score_state===0">未评分</div>
                            <div class="title_margin" style="margin-bottom: 20px" v-else>已评分，得分：<span style="color: red">{{answer_form.score}}</span></div>
                        </div>
                    </div>
                    <div v-for="(o,i) in question_list" :key="o">
                        <div class="title_margin" v-if="o.type === '单选题'">
                            {{o.question_order+'.单选题：'+o.title+"   ("+o.score+"分)"}}
                            <span v-if="if_answered" class="score_show">--------{{answer_scores[i]}}分</span>
                        </div>
                        <div class="title_margin" v-else-if="o.type === '多选题'">
                            {{o.question_order+'.多选题：'+o.title+"   ("+o.score+"分)"}}
                            <span v-if="if_answered" class="score_show">--------{{answer_scores[i]}}分</span>
                        </div>
                        <div class="title_margin" v-else-if="o.type === '判断题'">
                            {{o.question_order+'.判断题：'+o.title+"   ("+o.score+"分)"}}
                            <span v-if="if_answered" class="score_show">--------{{answer_scores[i]}}分</span>
                        </div>
                        <div class="title_margin" v-else-if="o.type === '填空题'">
                            {{o.question_order+'.填空题：'+o.title+"   ("+o.score+"分)"}}
                            <span v-if="if_answered" class="score_show">
                                <span v-if="score_state===0">--------未评分</span>
                                <span v-else>--------{{answer_scores[i]}}分</span>
						    </span>
                        </div>
                        <div class="title_margin" v-else>
                            {{o.question_order+'.主观题：'+o.title+"   ("+o.score+"分)"}}
                            <span v-if="if_answered" class="score_show">
							<span v-if="score_state===0">--------未评分</span>
							<span v-else>--------{{answer_scores[i]}}分</span>
						</span>
                        </div>
                        <div v-if="o.type === '单选题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="radio" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                            <div class="score_show" v-if="if_answered && reference_answers[i]">
                                参考答案：{{reference_answers[i]}}
                            </div>
                        </div>
                        <div v-else-if="o.type === '多选题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="checkbox" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                            <div class="score_show" v-if="if_answered && reference_answers[i]">
                                参考答案：{{reference_answers[i]}}
                            </div>
                        </div>
                        <div v-else-if="o.type === '判断题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="radio" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                            <div class="score_show" v-if="if_answered && reference_answers[i]">
                                参考答案：{{reference_answers[i]}}
                            </div>
                        </div>
                        <div v-else-if="o.type === '填空题'">
                            <input :name="o.title" :disabled="if_answered" style="width: 500px" v-model="answers[i]" />
                            <div class="score_show" v-if="if_answered">
                                参考答案：{{reference_answers[i]}}
                            </div>
                        </div>
                        <div v-else-if="o.type === '主观题'">
                            <textarea :name="o.title" type="" :disabled="if_answered" style="width: 500px" rows="3" cols="20" v-model="answers[i]" ></textarea>
                            <div class="score_show" v-if="if_answered">
                                参考答案：{{reference_answers[i]}}
                            </div>
                        </div>
                    </div>
                    <div class="title_margin">
                        <div>
                            <span>提交人：</span>
                            <input :disabled="true" style="width: 500px" v-model="submit_user_str" />
                        </div>
                    </div>
                </div>
                <div class="row answer_submit_button">
                    <div class="col-12">
                        <div class="btn_box">
                            <button v-if="!if_answered" class="answer_btn_submit" @click="submit()">提交</button>
                            <button class="answer_btn_cancel" @click="cancel()">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import mixin from "@/mixins/page.js";
    export default {
        mixins: [mixin],
        components: {
        },
        data() {
            return {
                field: "exam_id",
                url_add: "~/api/user_answer/add?",
                url_set: "~/api/user_answer/set?",
                url_get_obj: "~/api/exam/get_obj?",
                url_upload: "~/api/user_answer/upload?",

                query: {
                    exam_id: 0,
                },

                form: {
                    exam_id: 0,
                    name: "",
                    duration: 0,
                    score: 0,
                    status: "启用"
                },
                url_get_question_list: "~/api/exam_question/get_list?",
                question_list:[],
                answers:[],
                submit_user_str:"",
                answer_form:{
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
                if_answered:false,
                maxtime:0,
                minutes:0,
                seconds:0,
                timer:"",
                score_state:0,
                answer_scores:[],
                reference_answers:[]
            };
        },
        methods: {
            async get_obj_after(){
                let userJson = await this.$get("~/api/user/get_obj?user_id="+this.$store.state.user.user_id);
                if(userJson.result){
                    this.submit_user_str = userJson.result.obj.username+"-"+userJson.result.obj.nickname;
                    this.answer_form.user_id = userJson.result.obj.user_id;
                    this.answer_form.nickname = userJson.result.obj.nickname;
                }else if(userJson.error){
                    console.error(userJson.error);
                }
                let json = await this.$get(this.url_get_question_list+"exam_id="+this.query.exam_id+"&orderby=question_order");
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
                let answerJson = await this.$get("~/api/user_answer/get_obj?user_id="+this.answer_form.user_id+"&exam_id="+this.query.exam_id);
                if(answerJson.result && answerJson.result.obj != null){
                    this.answer_form = answerJson.result.obj;
                    this.score_state = answerJson.result.obj.score_state
                    this.answer_form.answers = JSON.parse(this.answer_form.answers);
                    this.answer_form.score_detail = JSON.parse(this.answer_form.score_detail);
                    for(let i=0;i<this.answer_form.answers.length;i++){
                        this.answers[i] = this.answer_form.answers[i].answer;
                        this.answer_scores[i] = this.answer_form.score_detail[i].score;
                        if (this.question_list[i].type==='单选题' || this.question_list[i].type==='判断题' || this.question_list[i].type==='多选题') {
                            if (this.answer_scores[i]!==this.question_list[i].score){
                                this.reference_answers[i]=this.question_list[i].answer;
                            }
                        }else{
                            this.reference_answers[i]=this.question_list[i].answer;
                        }
                    }
                    this.if_answered = true
                }else if(answerJson.error){
                    console.error(answerJson.error);
                }
                if (!this.if_answered){
                    this.maxtime = this.form.duration*60;
                    this.timer = window.setInterval(() => {
                        setTimeout(this.set_countdown(), 0)
                    }, 1000)
                }
            },
            get_question_list(question_str){
                let question_list = question_str.split("\n");
                return question_list;
            },
            submit_main(){
                let _this = this;
                _this.answer_form.exam_id = _this.query.exam_id;
                for (let i=0;i<_this.answers.length;i++){
                    let obj = {"exam_question_id":_this.question_list[i].exam_question_id,"answer":_this.answers[i]};
                    _this.answer_form.answers[i] = obj;
                    if (_this.question_list[i].type==='单选题' || _this.question_list[i].type==='判断题') {
                        if (_this.answers[i]===_this.question_list[i].answer){
                            let obj = {"exam_question_id":_this.question_list[i].exam_question_id,"score":_this.question_list[i].score};
                            _this.answer_form.score_detail[i] = obj;
                            _this.answer_form.objective_score = _this.answer_form.objective_score + _this.question_list[i].score;
                        }else {
                            let obj = {"exam_question_id":_this.question_list[i].exam_question_id,"score":0};
                            _this.answer_form.score_detail[i] = obj;
                        }
                    }else if (_this.question_list[i].type==='多选题'){
                        let flag = true;
                        let question_answer_list = _this.question_list[i].answer.split("|")
                        if (_this.answers[i].length!==question_answer_list.length){
                            flag = false;
                        }else {
                            for (let j=0;j<question_answer_list.length;j++){
                                if (_this.answers[i].indexOf(question_answer_list[j])===-1){
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if (flag){
                            let obj = {"question_id":_this.question_list[i].question_id,"score":_this.question_list[i].score};
                            _this.answer_form.score_detail[i] = obj;
                            _this.answer_form.objective_score = parseFloat(_this.answer_form.objective_score) + parseFloat(_this.question_list[i].score);
                        }else {
                            let obj = {"question_id":_this.question_list[i].question_id,"score":0};
                            _this.answer_form.score_detail[i] = obj;
                        }
                    }else{
                        let obj = {"question_id":_this.question_list[i].question_id,"score":0};
                        _this.answer_form.score_detail[i] = obj;
                    }
                }
                _this.answer_form.answers = JSON.stringify(_this.answer_form.answers);
                _this.answer_form.score_detail = JSON.stringify(_this.answer_form.score_detail);
                _this.answer_form.score = parseFloat(_this.answer_form.subjective_score) + parseFloat(_this.answer_form.objective_score);
                let url = _this.url_add;
                this.$post(url, _this.answer_form, function(json, status) {
                    console.log("提交结果：" ,json);
                    if (json.result) {
                        _this.events("submit_after", json, null);
                    } else if (json.error) {
                        _this.$toast(json.error.message, 'danger');
                    }
                });
            },
            set_countdown(){
                if (this.maxtime >= 0) {
                    this.minutes = Math.floor(this.maxtime / 60);
                    this.seconds = Math.floor(this.maxtime % 60);
                    --this.maxtime;
                } else{
                    clearInterval(this.timer);
                    alert("时间到，结束!");
                }
            }
        },
        mounted() {
        },
    };
</script>

<style scoped>
    .title_margin{
        width: 1000px;
        margin-top: 20px;
    }
    .answer_submit_button{
        margin-top: 30px;
        padding-left: 70px;
        margin-bottom: 30px;
    }
    .answer_submit_button button{
        width: 100px;
        line-height: 40px;
        border-radius: 5px;
    }
    .answer_btn_submit{
        background-color: blue;
        margin-right: 20px;
        color: white;
    }
    .answer_btn_cancel{
    }
    .countdown{
        width: 1000px;
        margin-top: 20px;
    }
    .countdown span{
        color: red;
    }
    .score_show{
        color: red;
    }
</style>

