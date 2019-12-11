package ccd.sdk.types;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexType {
    String name() default "";
    String label() default "";
    String labelId() default "";
}
