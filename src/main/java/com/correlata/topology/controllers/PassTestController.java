package com.correlata.topology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/pass"})
public class PassTestController {
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ResponseBody
    public String myRequest(@RequestBody String str){
        System.out.println(str);
        return str;
    }

    @RequestMapping(value = "passPage")
    public String passPage(){
        return "passTest";
    }
}
