package com.db.xxii_century_school;

import com.db.xxii_century_school.Dao.TeacherDao;
import com.db.xxii_century_school.Entities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("/maplogin")
public class UIController {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private DiscoveryClient discoveryClient;
    @RequestMapping("/sign/up")
    public  String signup(ModelMap modelMap){
        return "signup";
    }

    @GetMapping("/sign/in")
    public String singin(ModelMap modelMap){
        return "login";
    }

    @GetMapping("/services")
    public String login(@RequestParam int teacherId, ModelMap modelMap){
        Teacher teacher = teacherDao.findTeacherById(teacherId);
        modelMap.addAttribute("id",teacherId);
        if(teacher != null){
            List<Services> examServices = Services.getAllExamServices();
            Map<String, String> serviceUrls = new HashMap<>();
            for (Services service : examServices) {
                if(service.pickRandomInstance(discoveryClient)==null) continue;
                ServiceInstance serviceInstance = service.pickRandomInstance(discoveryClient);
                String url = serviceInstance.getUri().toString() + "?teacherId=" + teacherId;
                String name = serviceInstance.getServiceId();
                serviceUrls.put(name, url);
            }

            modelMap.addAttribute("serviceUrls", serviceUrls);
            return "services";
        }
        else return  "loginerror";
    }




}