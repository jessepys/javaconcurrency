package com.eyesee.classloader;

public class Hello {
    public void sayHello() {
        System.out.println("hello");
        System.out.println("this is loaded by: " + this.getClass().getClassLoader());

        Goodbye goodbye = new Goodbye();
        goodbye.sayGoodBye();
    }
}
