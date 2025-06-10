package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *咨询信息：(ConsultingInformation)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "ConsultingInformation")
public class ConsultingInformation implements Serializable {

    //ConsultingInformation编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consulting_information_id")
    private Integer consulting_information_id;
    // 标题名称
    @Basic
    private String title_name;
    // 用户信息
    @Basic
    private Integer user_information;
    // 姓名
    @Basic
    private String full_name;
    // 咨询内容
    @Basic
    private String consultation_content;
    // 咨询回复
    @Basic
    private String consultation_reply;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
