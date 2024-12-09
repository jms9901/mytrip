package com.lec.spring.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/adminLogin")
    public String login() {
        return "admin/adminLogin";
    }

    @GetMapping("/userTables")
    public String userTables() {return "admin/userTables";}

    @GetMapping("/businessTables")
    public String businessTables() {return "admin/businessTables";}

    @GetMapping("/boardTables")
    public String boardTables() {return "admin/boardTables";}

    @GetMapping("/feedTables")
    public String feedTables() {return "admin/feedTables";}

    @GetMapping("/packageAccessTables")
    public String packageAccessTables() {return "admin/packageAccessTables";}

    @GetMapping("/packageStandbyTables")
    public String packageStandbyTables() {return "admin/packageStandbyTables";}

    @GetMapping("/paymentTables")
    public String paymentTables() {return "admin/paymentTables";}

    @GetMapping("/charts")
    public String charts() {return "admin/charts";}

}
