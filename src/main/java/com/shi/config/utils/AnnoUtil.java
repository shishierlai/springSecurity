package com.shi.config.utils;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
* 根据需要获取指定注解的Class
* */
public class AnnoUtil {


    public static List<Class<?>> getAllAnnoClz(String packageName, Class<? extends Annotation> annotation){

        //  第一个class类的集合
        List<Class<?>> classes=new ArrayList<>();
        boolean recursive=true;

        String packageDirName=packageName.replace('.','/');

        Enumeration<URL> dirs;
        try{
            dirs=Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()){
                URL url=dirs.nextElement();
                String protocol=url.getProtocol();
                // 如果是以文件的形式
                if("file".equals(protocol)){
                    // 获取包的物理路径
                    String filePath= URLDecoder.decode(url.getFile(),"UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName,filePath,recursive,classes,annotation);
                }else if("jar".equals(protocol)){
                    JarFile jar;
                    try{
                        jar=((JarURLConnection)url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries=jar.entries();
                        while (entries.hasMoreElements()){

                            JarEntry entry=entries.nextElement();
                            String name=entry.getName();
                            if(name.charAt(0)=='/'){

                                name=name.substring(1);
                            }
                            if(name.startsWith(packageDirName)){
                                int idx=name.lastIndexOf("/");
                                if(idx!=-1){
                                    packageName=name.substring(0,idx).replace('/','.');

                                }
                                if((idx!=-1) ||recursive){
                                    if(name.endsWith(".class") && !entry.isDirectory() ){
                                        String className=name.substring(packageName.length()+1,name.length()-6);
                                        try {
                                            Class clz=Class.forName(packageName+'.'+className);
                                            if(null!=clz.getAnnotation(annotation)){
                                                classes.add(clz);
                                            }
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, final String filePath,final boolean recursive, List<Class<?>> classes, Class<? extends Annotation> annotation) {
        File dir=new File(filePath);
        //不存在
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        //存在
        File[] dirfiles=dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        for(File file:dirfiles){
            if(file.isDirectory()){
                findAndAddClassesInPackageByFile(packageName+"."+file.getName(),file.getAbsolutePath(),recursive,classes,annotation);
            }else {
                String className=file.getName().substring(0,file.getName().length()-6);

                try{
                    Class clz=Class.forName(packageName+'.'+className);
                    if(null!=clz.getAnnotation(annotation)){
                        classes.add(clz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }



    }

}
