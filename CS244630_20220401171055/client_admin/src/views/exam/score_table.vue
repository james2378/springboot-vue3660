<template>
	<el-main class="bg">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="提交人">
						<el-input v-model="query.nickname"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="评分状态" prop="score_state">
						<el-select v-model="query.score_state">
							<el-option value="" label="">请选择</el-option>
							<el-option value="0" label="未评分">未评分</el-option>
							<el-option value="1" label="已评分">已评分</el-option>
						</el-select>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24">
					<el-col :xs="24" :sm="12" :lg="12">
						<el-form-item>
							<el-button type="primary" @click="search()">查询</el-button>
							<el-button @click="reset()">重置</el-button>
						</el-form-item>
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe>

			<el-table-column fixed type="selection" tooltip-effect="dark" width="50">
			</el-table-column>

			<el-table-column fixed prop="nickname" label="提交人" min-width="120">
			</el-table-column>

			<el-table-column prop="objective_score" label="客观题得分" min-width="200">
			</el-table-column>

			<el-table-column prop="subjective_score" label="主观题得分" min-width="180">
			</el-table-column>

			<el-table-column prop="score" label="总分" min-width="180">
			</el-table-column>

			<el-table-column prop="score_state" label="评分状态" min-width="180">
				<template slot-scope="scope">
					{{ scope.row["score_state"] ===1?"已评分":"未评分" }}
				</template>
			</el-table-column>

			<el-table-column sortable prop="create_time" label="提交时间" min-width="200">
			    <template slot-scope="scope">
			        {{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
			    </template>
			</el-table-column>

			<el-table-column sortable prop="update_time" label="评分时间" min-width="200">
			    <template slot-scope="scope">
			        {{ scope.row["score_state"] ===1?$toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss"):"/" }}
			    </template>
			</el-table-column>

			<!-- 操作 -->
			<el-table-column fixed="right" label="操作" width="150">
				<template slot-scope="scope">
					<router-link v-if="scope.row['score_state']===1" class="el-button el-button--small is-plain el-button--primary"
						:to="'/score_view/view?user_answer_id=' + scope.row['user_answer_id']" size="small">
						查看
					</router-link>
					<router-link v-else class="el-button el-button--small is-plain el-button--primary"
								 :to="'/score_view/view?user_answer_id=' + scope.row['user_answer_id']" size="small">
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
				url_get_list: "~/api/user_answer/get_list?like=0",
				url_del: "~/api/user_answer/del?",

				// 字段ID
				field:"user_answer_id",

				// 查询
				query: {
					size: 10,
					page: 1,
					exam_id: 0,
					nickname:"",
					score_state:""
				},

				// 数据
				list: []
			}
		},
		methods: {
			deleteRow(index, rows) {
				rows.splice(index, 1);
			},
			/**
			 * 重置
			 */
			reset() {
				let exam_id = {"exam_id":this.query.exam_id};
				$.clear(this.query);
				$.push(this.query, this.config);
				$.push(this.query, exam_id);
				this.get_list();
			},
		},
		created(){
		}
	}
</script>

<style type="text/css">

</style>
