package com.db.xxii_century_school.Dao;

import com.db.xxii_century_school.Entities.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface TeacherDao extends CrudRepository<Teacher, Integer>{
    @RestResource(path="/findTeacherById")
    Teacher findTeacherById(@Param("id") int id);


    default int saveTeacher(Teacher teacher){
        teacher = save(teacher);
        return teacher.getId();
    }
}
