<template>
	<div class="diy_edit page_consulting_information" id="consulting_information_edit">
		<div class='warp'>
			<div class='container'>
				<div class='row diy_edit_content_box'>
					<div v-if="$check_field('set','title_name') || $check_field('add','title_name') || $check_field('get','title_name')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>标题名称:
							</span>
						</div>
						<!-- 文本 -->
						<div class="diy_field diy_text">
							<input type="text" id="form_title_name" v-model="form['title_name']" placeholder="请输入标题名称" v-if="(form['title_name'] && $check_field('set','title_name')) || (!form['title_name'] && $check_field('add','title_name'))"  :disabled="disabledObj['title_name_isDisabled']"/>
							<span v-else-if="$check_field('get','title_name')">{{ form['full_name'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','user_information') || $check_field('add','user_information') || $check_field('get','user_information')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>用户信息:
							</span>
						</div>
						<div class="diy_field diy_down">
							<select id="form_user_information" :disabled="disabledObj['user_information_isDisabled']" v-model="form['user_information']" v-if="(form['user_information'] && $check_field('set','user_information')) || (!form['user_information'] && $check_field('add','user_information'))" >
								<option v-for="o in list_user_user_information" :value="o['user_id']">
									{{o['nickname'] + '-' + o['username']}}
								</option>
							</select>
							<span v-else-if="$check_field('get','user_information')">{{ form['user_information'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','full_name') || $check_field('add','full_name') || $check_field('get','full_name')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>姓名:
							</span>
						</div>
						<!-- 文本 -->
						<div class="diy_field diy_text">
							<input type="text" id="form_full_name" v-model="form['full_name']" placeholder="请输入姓名" v-if="(form['full_name'] && $check_field('set','full_name')) || (!form['full_name'] && $check_field('add','full_name'))"  :disabled="disabledObj['full_name_isDisabled']"/>
							<span v-else-if="$check_field('get','full_name')">{{ form['full_name'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','consultation_content') || $check_field('add','consultation_content') || $check_field('get','consultation_content')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>咨询内容:
							</span>
						</div>
						<!-- 多文本 -->
						<div class="diy_field diy_desc">
							<textarea id="form_consultation_content" v-model="form['consultation_content']" v-if="(form['consultation_content'] && $check_field('set','consultation_content')) || (!form['consultation_content'] && $check_field('add','consultation_content'))" :disabled="disabledObj['consultation_content_isDisabled']" />
							<span v-else-if="$check_field('get','consultation_content')">{{ form['full_name'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','consultation_reply') || $check_field('add','consultation_reply') || $check_field('get','consultation_reply')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>咨询回复:
							</span>
						</div>
						<!-- 多文本 -->
						<div class="diy_field diy_desc">
							<textarea id="form_consultation_reply" v-model="form['consultation_reply']" v-if="(form['consultation_reply'] && $check_field('set','consultation_reply')) || (!form['consultation_reply'] && $check_field('add','consultation_reply'))" :disabled="disabledObj['consultation_reply_isDisabled']" />
							<span v-else-if="$check_field('get','consultation_reply')">{{ form['full_name'] }}</span>
						</div>
					</div>
				</div>
				<div class="diy_edit_submit_box row">
					<div class="col-12">
						<div class="btn_box">
							<button class="btn_submit" @click="submit()">提交</button>
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
		components: {},
		data() {
			return {
				url_get_obj: "~/api/consulting_information/get_obj?",
				url_add: "~/api/consulting_information/add?",
				url_set: "~/api/consulting_information/set?",

				// 登录权限
				oauth: {
					"signIn": true,
					"user_group": []
				},

				// 查询条件
				query: {
					"title_name": "",
					"user_information": 0,
					"full_name": "",
					"consultation_content": "",
					"consultation_reply": "",
					"consulting_information_id": 0,
				},

				obj: {
					"title_name":'', // 标题名称
					"user_information": 0, // 用户信息
					"full_name":'', // 姓名
					"consultation_content":'', // 咨询内容
					"consultation_reply":'', // 咨询回复
					"consulting_information_id": 0,
				},

				// 表单字段
				form: {
					"title_name":'', // 标题名称
					"user_information": 0, // 用户信息
					"full_name":'', // 姓名
					"consultation_content":'', // 咨询内容
					"consultation_reply":'', // 咨询回复
					"consulting_information_id": 0,

				},
				disabledObj:{
					"title_name_isDisabled": false,
					"user_information_isDisabled": false,
					"full_name_isDisabled": false,
					"consultation_content_isDisabled": false,
					"consultation_reply_isDisabled": false,
				},
				// 用户列表
				list_user_user_information: [],

				// ID字段
				field: "consulting_information_id",
			}
		},
		methods: {
			/**
			 * 获取普通用户用户列表
			 */
			async get_list_user_user_information() {
				// if(this.user_group !== "管理员" && this.form["user_information"] === 0) {
				//     this.form["user_information"] = this.user.user_id;
				// }
				var json = await this.$get("~/api/user/get_list?user_group=普通用户");
				if(json.result && json.result.list){
					this.list_user_user_information = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			async get_user_session_user_information(){
				var _this = this;
				var json = await this.$get("~/api/user_group/get_obj?name=普通用户");
				if(json.result && json.result.obj){
					var source_table = json.result.obj.source_table;
					var user_id = _this.$store.state.user.user_id;
					if (user_id){
						var url = "~/api/"+source_table+"/get_obj?"
						this.$get(url, {"user_id":_this.$store.state.user.user_id}, function(res) {
							if (res.result && res.result.obj) {
								var arr = []
								for (let key in res.result.obj) {
									arr.push(key)
								}
								var arrForm = []
								for (let key in _this.form) {
									arrForm.push(key)
								}
								_this.form["user_information"] = user_id
								_this.disabledObj['user_information' + '_isDisabled'] = true
								for (var i=0;i<arr.length;i++){
									for (var j=0;j<arrForm.length;j++){
										if (arr[i]===arrForm[j]){
											if (arr[i]!=="user_information") {
												_this.form[arrForm[j]] = res.result.obj[arr[i]]
												_this.disabledObj[arrForm[j] + '_isDisabled'] = true
												break;
											}
										}
									}
								}
							}
						});
					}
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			/**
			 * 修改文件
			 * @param { Object } files 上传文件对象
			 * @param { String } str 表单的属性名
			 */
			change_file(files, str) {
				var form = new FormData();
				form.append("file", files[0]);
				this.$post("~/api/consulting_information/upload?", form, (res) => {
					if (res.result) {
						this.form[str] = res.result.url;
					} else if (res.error) {
						this.$toast(res.error.message);
					}
				});
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_before(param){
				var form = $.db.get("form");
				if (form) {
					this.obj = $.push(this.obj ,form);
					this.form = $.push(this.form ,form);
				}
				var arr = []
				for (let key in form) {
					arr.push(key)
				}
				for (var i=0;i<arr.length;i++){
					this.disabledObj[arr[i] + '_isDisabled'] = true
				}
				return param;
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json ,func){
				var form = $.db.get("form");
				var obj = Object.assign({} ,form ,this.obj);
				if (form) {
					this.obj = $.push(this.obj ,obj);
				}
				if (form) {
					this.form = $.push(this.form ,form);
				}
				if(func){
					func(json);
				}
			},

		},
		created() {
			this.get_user_session_user_information();
			this.get_list_user_user_information();
		}
	}
</script>

<style>
</style>
