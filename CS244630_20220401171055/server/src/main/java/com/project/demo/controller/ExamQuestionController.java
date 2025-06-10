package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.ExamQuestion;
import com.project.demo.service.ExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 题库
 *
 */
@RestController
@RequestMapping("exam_question")
public class ExamQuestionController extends BaseController<ExamQuestion, ExamQuestionService> {
    /**
     * 服务对象
     */
    @Autowired
    public ExamQuestionController(ExamQuestionService service) {
        setService(service);
    }

}


