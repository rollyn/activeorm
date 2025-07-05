package com.ethanium.activeorm.annotations;
import org.springframework.data.mongodb.core.mapping.Document;
import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface Domain { String collection() default ""; }
