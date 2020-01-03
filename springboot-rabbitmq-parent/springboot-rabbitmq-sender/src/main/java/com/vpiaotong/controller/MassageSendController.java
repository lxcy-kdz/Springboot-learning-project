package com.vpiaotong.controller;

import com.vpiaotong.service.MassageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kongdezhi
 */
@RestController
@RequestMapping("massage")
public class MassageSendController {

    @Autowired
    private MassageSendService massageSendService;

    @GetMapping("/send/{massage}")
    public String sendMassage(@PathVariable("massage") String massage) {
        return massageSendService.sendMassage(massage);
    }
}
