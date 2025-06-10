package com.project.demo.controller;

import com.project.demo.entity.ConsultingInformation;
import com.project.demo.service.ConsultingInformationService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *咨询信息：(ConsultingInformation)表控制层
 *
 */
@RestController
@RequestMapping("/consulting_information")
public class ConsultingInformationController extends BaseController<ConsultingInformation,ConsultingInformationService> {

    /**
     *咨询信息对象
     */
    @Autowired
    public ConsultingInformationController(ConsultingInformationService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        return success(1);
    }

}
