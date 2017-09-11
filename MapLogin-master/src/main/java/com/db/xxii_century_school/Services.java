package com.db.xxii_century_school;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public enum Services {
    DISCOVERY("discovery"),
    MAPLOGIN("maplogin"),
    MAP("maplogin"),
    LOGIN("maplogin"),
    MATH("mathservice"),
    AMERICAN_QUESTIONS("american-questions"),
    GEOMETRY("geometry"),
    TELEGRAM("telegram");

    public final String id;

    Services(String id) {
        this.id = id;
    }

    private static final Random random = new Random(new Date().getTime());

    private static ServiceInstance pickRandomInstanceFromList(List<ServiceInstance> instances) {
        return instances.get(Math.abs(random.nextInt(instances.size())) % instances.size());
    }

    public ServiceInstance pickRandomInstance(DiscoveryClient discoveryClient) {
        return pickRandomInstanceFromList(discoveryClient.getInstances(id));
    }

    public static List<Services> getAllExamServices(){
        List<Services> services = new ArrayList<>();
        for (Services s :  Services.values()) {
            if(!(s.id.equals("discovery") || s.id.equals("maplogin") || s.id.equals("gate") || s.id.equals("telegram")))
            services.add(s);
        }
        return services;
    }
}