package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.UserAnswer;
import com.project.demo.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户答题
 *
 */
@RestController
@RequestMapping("user_answer")
public class UserAnswerController extends BaseController<UserAnswer, UserAnswerService> {
    /**
     * 服务对象
     */
    @Autowired
    public UserAnswerController(UserAnswerService service) {
        setService(service);
    }

}


