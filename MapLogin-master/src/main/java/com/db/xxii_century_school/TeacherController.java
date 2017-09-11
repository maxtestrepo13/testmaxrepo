package com.db.xxii_century_school;

import com.db.xxii_century_school.Entities.Teacher;
import com.db.xxii_century_school.MyServices.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/saveTeacher")
    public int saveTeacher(@RequestBody Teacher teacher){
        return teacherService.saveTeacher(teacher);
    }


}
