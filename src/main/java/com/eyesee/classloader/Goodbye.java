package com.eyesee.classloader;

public class Goodbye {
    public void sayGoodBye() {
        System.out.println("good bye");
        System.out.println("this is loaded by: " + this.getClass().getClassLoader());
    }
}
