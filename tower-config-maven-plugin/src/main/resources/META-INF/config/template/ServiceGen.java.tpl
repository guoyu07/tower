package com.#{company}.service.#{artifactId};

import com.tower.service.domain.PkType;
import com.tower.service.generate.tool.IServiceGen;

public class ServiceGen {


    public static void main(String[] args) {
        try {
        	new IServiceGen(PkType.INTEGER, "com.#{company}.service.#{artifactId}", "Hello","src/main/java/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
