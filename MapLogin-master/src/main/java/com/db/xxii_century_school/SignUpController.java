package com.db.xxii_century_school;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    @RequestMapping("/getid")
    public int getId(){
        //return next free id from mongo
        return 0;
    }
}
