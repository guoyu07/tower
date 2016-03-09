package com.#{company}.service.#{artifactId};

import com.tower.service.domain.generate.tool.IDtoGen;

public class DtoGen {


    public static void main(String[] args) {
        try {
        	new IDtoGen("com.#{company}.service.#{artifactId}","Hello","src/main/java/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
