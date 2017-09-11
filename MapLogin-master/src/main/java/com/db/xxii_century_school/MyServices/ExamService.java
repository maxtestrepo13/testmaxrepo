package com.db.xxii_century_school.MyServices;

import com.db.xxii_century_school.Entities.Exam;

public interface ExamService {
    int saveExam(Exam exam);
    Exam getExam(int examId, int teacherId);
}
