package com.eyesee.designpattern.activeobject.template;

public class TemplateTest {


    public static abstract class Base {
        public abstract void doStep1();
        public abstract void doStep2();

        public void doOperation() {
            doStep1();
            doStep2();
        }
    }

    public static class Detail2 extends Base {
        @Override
        public void doStep1() {

        }

        @Override
        public void doStep2() {

        }
    }

    public static class Detail1 extends Base {
        @Override
        public void doStep1() {

        }

        @Override
        public void doStep2() {

        }
    }
}
