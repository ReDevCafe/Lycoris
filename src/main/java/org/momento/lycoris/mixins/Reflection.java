package org.momento.lycoris.mixins;

import org.momento.lycoris.mixins.mixin.Mixin;
import org.momento.lycoris.mixins.mixin.classe.ClassWrapper;

import java.lang.annotation.Annotation;

public class Reflection {

    public static <T extends Annotation> T getAnnotation(Object object, Class<T> annotation) {
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(annotation))
            return null;
        return clazz.getAnnotation(annotation);
    }

}
