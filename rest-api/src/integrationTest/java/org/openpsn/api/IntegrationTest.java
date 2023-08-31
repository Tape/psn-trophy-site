package org.openpsn.api;

import io.jooby.Jooby;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Test
@ExtendWith({IntegrationTestExtension.class})
public @interface IntegrationTest {
    Class<? extends Jooby> value();

    String environment() default "test";
}
