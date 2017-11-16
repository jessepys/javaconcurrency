package com.eyesee.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PersonalClassLoader extends ClassLoader {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalClassLoader.class);
    private static final String FILE_TYPE = ".class";
    private String loaderName;
    private String path;

    public PersonalClassLoader(String loaderName) {
        super();
        this.loaderName = loaderName;
    }

    public PersonalClassLoader(ClassLoader parentLoader, String loaderName) {
        super(parentLoader);
        this.loaderName = loaderName;
    }

    public PersonalClassLoader setPath(String path) {
        this.path = path;
        return this;
    }

    private byte[] loadClassData(String name){
        byte[] data = null;
        String filePath = name.replace(".", "/");
        try (InputStream is = new FileInputStream(new File(path + filePath + FILE_TYPE));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int c = 0;
            while(-1 != (c = is.read())){
                baos.write(c);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            LOG.error("load class data error: {}", e);
        }

        return data;
    }

    @Override
    public Class<?> findClass(String name){
        byte[] data = loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        PersonalClassLoader personalClassLoader = new PersonalClassLoader("Hello");
        personalClassLoader.setPath("/Users/jessepi/Desktop/");
        Class<Hello> helloClass = (Class<Hello>) personalClassLoader.loadClass("com.eyesee.classloader.Hello");
        helloClass.newInstance().sayHello();

        PersonalClassLoader loader2 = new PersonalClassLoader(null, "loader2");
        loader2.setPath("/Users/jessepi/Desktop/");
        Class<Goodbye> goodbyeClass = (Class<Goodbye>) personalClassLoader.loadClass("com.eyesee.classloader.Goodbye");
        goodbyeClass.newInstance().sayGoodBye();
    }

}
