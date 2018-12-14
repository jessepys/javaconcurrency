package com.eyesee.algorithms.annotation;

import java.lang.annotation.*;

/**
 * The {@code Solution} class represents .
 *
 * @author jessepi on 11/29/18
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
public @interface Solution {
    boolean recommend() default false;
    boolean easy() default false;
}
