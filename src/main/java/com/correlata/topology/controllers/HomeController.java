package com.correlata.topology.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Denis on 15/12/13.
 */
@Controller
@RequestMapping({"/"})
public class HomeController {
    @Autowired
    ServletContext context;
    static Logger logger = Logger.getLogger(HomeController.class.getName());
    FileHandler fh;

    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }

//    @RequestMapping(value = "/loggerTest", method= RequestMethod.GET)
//    public @ResponseBody
//    String loggerTest() {
//        try {
//            fh = new FileHandler(context.getRealPath("/WEB-INF/log/log.log"));
//            logger.addHandler(fh);
//            SimpleFormatter formatter = new SimpleFormatter();
//            fh.setFormatter(formatter);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (SecurityException ex) {
//            ex.printStackTrace();
//        }
//        logger.info("My first log");
//
//        return "loggerTest has finished";
//    }
}
