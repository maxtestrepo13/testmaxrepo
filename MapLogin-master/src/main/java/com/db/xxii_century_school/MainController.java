package com.db.xxii_century_school;

import com.db.xxii_century_school.Dao.TeacherDao;
import com.db.xxii_century_school.Entities.Exam;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;


@RestController
@RequestMapping("/cont")
public class MainController {
    @Autowired
    private TeacherDao teacherLoginDao;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(path = "/work")
    public boolean working() {
        return true;
    }

    @RequestMapping(path = "/getId")
    public int getId() {
        return 123;
    }


    @SneakyThrows
    @RequestMapping(path = "/services")
    public String discover() {
        ServiceInstance serviceInstance = Services.MAPLOGIN.pickRandomInstance(discoveryClient);
        System.out.println("URI "+serviceInstance.getUri());
        URL url = serviceInstance.getUri().toURL();
        url = new URL(url.toString()+"/exams/saveExam");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonExam = "";//objectMapper.writeValueAsString(new Exam(2, "americanquestions"));

        HttpEntity<String> entity = new HttpEntity<>(jsonExam, headers);
        ResponseEntity<String> loginResponse = restTemplate.exchange(url.toString(), HttpMethod.POST, entity, String.class);

        String response ="no response";

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            response = loginResponse.getBody();
        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            response = "bas response";
        }
        return response;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8088/cont/work", String.class);
        System.out.println();

        String body = "{\"id\":0,\"teacherId\":2,\"url\":\"americanquestions\"}";

        String url = "http://RUMOSLT500014:8088/exams/saveExam";
    }
}
















