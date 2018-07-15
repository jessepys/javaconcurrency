package com.eyesee.classloader;

import java.io.InputStream;
import java.util.Objects;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream inputStream = getResourceAsStream(fileName);

                    if (Objects.isNull(inputStream)) {
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[inputStream.available()];

                    inputStream.read(b);
                } catch (Exception e) {

                }

                return super.loadClass(name);
            }
        };
        Object obj = classLoader.loadClass("com.eyesee.classloader.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass().getName());
        System.out.println(obj instanceof ClassLoaderTest);


    }
}
