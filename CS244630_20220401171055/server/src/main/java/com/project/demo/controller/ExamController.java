package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Exam;
import com.project.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试
 *
 */
@RestController
@RequestMapping("exam")
public class ExamController extends BaseController<Exam, ExamService> {
    /**
     * 服务对象
     */
    @Autowired
    public ExamController(ExamService service) {
        setService(service);
    }

}


