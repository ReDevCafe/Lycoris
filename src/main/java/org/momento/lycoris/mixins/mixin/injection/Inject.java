package org.momento.lycoris.mixins.mixin.injection;

import org.momento.lycoris.mixins.mixin.At;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Inject {
    String method();
    At at() default @At();
}
