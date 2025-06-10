package com.project.demo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 考试
 */
@Setter
@Getter
@Entity
public class ExamQuestion implements Serializable {

    private static final long serialVersionUID = -89845695959527153L;

    /**
     * 题库id：[0,10000]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_question_id")
    private Integer exam_question_id;

    /**
     * 类型
     */
    @Basic
    @Column(name = "type")
    private String type;

    /**
     * 题目
     */
    @Basic
    @Column(name = "title")
    private String title;

    /**
     * 选项
     */
    @Basic
    @Column(name = "question_item")
    private String question_item;

    /**
     * 参考答案
     */
    @Basic
    @Column(name = "answer")
    private String answer;

    /**
     * 总分
     */
    @Basic
    @Column(name = "score")
    private Double score;

    /**
     * 排序
     */
    @Basic
    @Column(name = "question_order")
    private Integer question_order;

    /**
     * 所属试卷
     */
    @Basic
    @Column(name = "exam_id")
    private Integer exam_id;


    /**
     * 创建时间：
     */

    @Basic
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 更新时间：
     */

    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;

}

