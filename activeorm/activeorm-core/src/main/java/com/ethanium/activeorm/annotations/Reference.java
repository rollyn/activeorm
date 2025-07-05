package com.ethanium.activeorm.annotations;
import java.lang.annotation.*;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference { CascadeMode cascade() default CascadeMode.NONE; }
