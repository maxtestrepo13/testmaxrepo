package com.db.xxii_century_school.Dao;

import com.db.xxii_century_school.Entities.Exam;
import org.springframework.data.repository.CrudRepository;

public interface ExamDao extends CrudRepository<Exam,Integer> {
    Exam findByIdAndTeacherId(int id, int teacherId);

    default int saveExam(Exam exam){
        exam = save(exam);
        return exam.getId();
    }
}
