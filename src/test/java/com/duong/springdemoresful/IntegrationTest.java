package com.duong.springdemoresful;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@SpringBootTest
@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
