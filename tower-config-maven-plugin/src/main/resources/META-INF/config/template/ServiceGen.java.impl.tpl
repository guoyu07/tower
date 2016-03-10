package com.#{company}.service.#{artifactId};

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tower.service.annotation.JField;
import com.tower.service.domain.PkType;
import com.tower.service.domain.generate.tool.IDtoGen;
import com.tower.service.generate.tool.IServiceGen;
import com.tower.service.impl.generate.tool.ServiceImplGen;
import com.tower.service.util.Pair;

public class ServiceGen {

	static String pkgName = "com.#{company}.service.#{artifactId}";
	
    private static Field[] getFields(String name) throws ClassNotFoundException{
    	Class cls = Class.forName(pkgName+".dao.model."+name);
    	return cls.getDeclaredFields();
    }
    
    private static List<Pair<String,Field>> genPair(String model) throws ClassNotFoundException{
    	Field[] field = getFields(model);
    	int len = field.length;
    	List<Pair<String,Field>> fields = new ArrayList<Pair<String,Field>>();
    	for(int i=0;i<len;i++){
    		JField ann = field[i].getAnnotation(JField.class);
			if(ann != null){
				String name = field[i].getName();
				String first = name.substring(0,1).toUpperCase();
				String last = name.substring(1);
				Pair<String,Field> pair = new Pair<String,Field>(first+last,field[i]);
				fields.add(pair);
			}
    	}
    	return fields;
    }

    public static void main(String[] args) {
        try {
        	
        	String model = "SoaSp";
        	
        	String path = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();//项目绝对路径
        	new IDtoGen(genPair(model),pkgName, model,path+"/#{artifactId}-domain/src/main/java/");//生成dto
        	new IServiceGen(PkType.INTEGER, pkgName, model,path+"/#{artifactId}-service/src/main/java/");//生成iservice
        	new ServiceImplGen(PkType.INTEGER, pkgName, model,path+"/#{artifactId}-service-impl/src/main/java/");//生成serviceimpl
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
