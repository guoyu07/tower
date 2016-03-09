package com.#{company}.service.#{artifactId};

import com.tower.service.impl.generate.tool.ServiceImplGen;

public class ServiceGen {


    public static void main(String[] args) {
        try {
        	new ServiceImplGen("Integer", "com.#{company}.service.#{artifactId}", "Hello","src/main/java/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
