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
public class Exam implements Serializable {

    private static final long serialVersionUID = -89845695959527153L;

    /**
     * 考试id：[0,10000]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer exam_id;

    /**
     * 考试名称：[2,32]
     */
    @Basic
    @Column(name = "name")
    private String name;

    /**
     * 答题时长
     */
    @Basic
    @Column(name = "duration")
    private Integer duration;

    /**
     * 总分
     */
    @Basic
    @Column(name = "score")
    private Double score;

    /**
     * 状态：启用、禁用
     */
    @Basic
    @Column(name = "status")
    private String status;

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

