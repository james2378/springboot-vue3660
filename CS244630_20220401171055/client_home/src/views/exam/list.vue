<template>
    <div class="page_exam" id="exam_list">
        <div class="warp" v-if="$check_exam('/exam/list', 'answers')">
            <div class="container">
                <div class="row">
                    <div class="col-12 col-lg-12 col-md-12">
                        <div class="exam_list_title">
                            <span class="title">名称：</span>
                            <!-- 搜索栏 -->
                            <div class="search_box">
                                <b-form-input
                                        size="sm"
                                        class="mr-sm-2"
                                        placeholder="请输入名称"
                                        v-model.trim="query.name"
                                        @keyup.enter="search()"
                                />
                                <b-button size="sm" @click="search()"
                                >
                                    <b-icon icon="search"/>
                                </b-button>
                            </div>
                        </div>
                        <div class="exam_list_content">
                            <table stripe>
                                <tr class="table_title">
                                    <th width="500px" class="table_th">名称</th>
                                    <th class="table_th">答题时长</th>
                                    <th class="table_th">总分</th>
                                    <th class="table_th">操作</th>
                                </tr>
                                <tr v-for="o in list" :key="o">
                                    <td width="500px">{{o.name}}</td>
                                    <td>{{o.duration}}</td>
                                    <td>{{o.score}}</td>
                                    <td>
                                        <router-link :to="'/exam/details?exam_id='+o.exam_id" class="answer_link"  v-if="answered_list[o.exam_id]">
                                            <span>查看分数</span>
                                        </router-link>
                                        <router-link :to="'/exam/details?exam_id='+o.exam_id" class="answer_link"  v-else-if="o.status==='启用'">
                                            <span>答题</span>
                                        </router-link>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <!-- 分页器 -->
                        <div class="col overflow-auto flex_cc">
                            <b-pagination v-model.number="query.page" :total-rows="count" :per-page="query.size" @change="goTo" />
                        </div>
                    </div>
                </div>
                <div class="row">

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
                // 获取数据地址
                url_get_list: "~/api/exam/get_list?like=0",
                url_del: "~/api/exam/del?",

                // 字段ID
                field:"exam_id",

                // 查询
                query: {
                    size: 10,
                    page: 1,
                    name:""
                },

                // 数据
                list: [],

                list_is: [{name:"否" ,value:0},{name:"是" ,value:1}],
                answered_list:[]
            };
        },
        methods: {
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
        },
        mounted() {
        },
    };
</script>

<style scoped>

    .flex_cc {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 10px;
    }

    .answer_link {
        font-size: 0.875rem;
        overflow: hidden;
        margin-right: 5px;
        color: blue !important;
    }
    .table_th{
        background-color: darkgrey;
    }
    .search_box {
        width: 12rem;
        display: flex;
        height: 4rem;
        justify-content: center;
        align-items: center;
        padding-right: 1rem;
    }
    .exam_list_title {
        margin: 0 0.5rem;
        display: flex;
        height: 4rem;
        align-items: center;
        justify-content:left;
        border-radius: 0.5rem;
    }
</style>

