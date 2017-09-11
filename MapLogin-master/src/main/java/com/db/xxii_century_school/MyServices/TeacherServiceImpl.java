package com.db.xxii_century_school.MyServices;

import com.db.xxii_century_school.Dao.TeacherDao;
import com.db.xxii_century_school.Entities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Override
    public int saveTeacher(Teacher teacher) {
        return teacherDao.saveTeacher(teacher);
    }
}
