<template>
	<el-main class="bg">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="名称">
						<el-input v-model="query.name"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-col :xs="24" :sm="12" :lg="12">
						<el-form-item>
							<el-button type="primary" @click="search()">查询</el-button>
							<el-button @click="reset()">重置</el-button>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :lg="12">
						<el-form-item>
							<el-button v-if="user_group == '管理员' || $check_action('/exam/table','del')" class="float-right" type="danger" @click="delInfo()">删除</el-button>
							<router-link v-if="user_group == '管理员' || $check_action('/exam/table','add')" class="el-button float-right el-button--default el-button--primary"
								to="./view?">添加
							</router-link>
						</el-form-item>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe>

			<el-table-column fixed type="selection" tooltip-effect="dark" width="50">
			</el-table-column>

			<el-table-column fixed prop="name" label="名称" min-width="180">
			</el-table-column>

			<el-table-column prop="duration" label="答题时长" min-width="120">
			</el-table-column>

			<el-table-column prop="score" label="总分" min-width="180">
			</el-table-column>

			<el-table-column prop="status" label="状态" min-width="180">
			</el-table-column>

			<el-table-column sortable prop="create_time" label="创建时间" min-width="200">
			    <template slot-scope="scope">
			        {{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
			    </template>
			</el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间" min-width="200">
			    <template slot-scope="scope">
			        {{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
			    </template>
			</el-table-column>

			<!-- 操作 -->
			<el-table-column fixed="right" label="操作" width="180">
				<template slot-scope="scope">
					<router-link class="el-button el-button--small is-plain el-button--primary" v-if="user_group == '管理员' || $check_action('/exam/table','set')"
						:to="'./view?' + field + '=' + scope.row[field]" size="small">
            详情
					</router-link>
					<router-link class="el-button el-button--small is-plain el-button--primary" v-if="user_group == '管理员' || $check_exam('/exam/table','setting')"
								 :to="'/question_table/table?' + field + '=' + scope.row[field]" size="small">
						题库
					</router-link>
					<!--&& !!-->
					<router-link class="el-button el-button--small is-plain el-button--primary"  :data-num='answered_list[scope.row[field]]' style="margin-left: 0;margin-top: 10px" v-if="(user_group == '管理员' || $check_exam('/exam/table','answers')) && answered_list[scope.row[field]]"
								 :to="'/answer_view/view?' + field + '=' + scope.row[field]" size="small">
						查看分数
					</router-link>
					<router-link class="el-button el-button--small is-plain el-button--primary" style="margin-left: 0;margin-top: 10px" v-else-if="(user_group == '管理员' || $check_exam('/exam/table','answers')) && scope.row.status==='启用'"
								 :to="'/answer_view/view?' + field + '=' + scope.row[field]" size="small">
						答题
					</router-link>
					<router-link class="el-button el-button--small is-plain el-button--primary" style="margin-top: 10px" v-if="user_group == '管理员' || $check_exam('/exam/table','give_score')"
								 :to="'/score_table/table?' + field + '=' + scope.row[field]" size="small">
						评分
					</router-link>
				</template>
			</el-table-column>
			<!-- /操作 -->

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.page"
			 :page-sizes="[7, 10, 30, 100]" :page-size="query.size" layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->

	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				// 获取数据地址
				url_get_list: "~/api/exam/get_list?like=0",
				url_del: "~/api/exam/del?",

				// 字段ID
				field:"exam_id",

				// 查询
				query: {
					size: 10,
					page: 1,
					name: "",
				},

				// 数据
				list: [],

				list_is: [{name:"否" ,value:0},{name:"是" ,value:1}],
				answered_list:[]

			}
		},
		computed:{

		},
		methods: {
			deleteRow(index, rows) {
				rows.splice(index, 1);
			},
			get_list_after(){
				for (let i=0;i<this.list.length;i++){
					this.get_user_answer_state(this.list[i].exam_id);
				}
			},
			get_user_answer_state(exam_id) {
				let _this = this
				this.$get("~/api/user_answer/get_obj?user_id="+this.$store.state.user.user_id+"&exam_id="+exam_id).then((res)=>{
					console.log(res)
					if(res.result && res.result.obj != null){
						_this.$set(_this.answered_list,exam_id,true)
					}else {
						_this.$set(_this.answered_list,exam_id,false)
					}
				})
			},
			table_class({row, column, rowIndex, columnIndex}){
				return "table_class";
			}
		},
		created(){
		}
	}
</script>

<style type="text/css">

</style>
