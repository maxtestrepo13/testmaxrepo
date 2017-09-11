package com.db.xxii_century_school.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String mails;


    public static void main(String[] args) throws JsonProcessingException {
        Teacher bka = new Teacher(1, "bka","adsa");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(bka));
    }
}
