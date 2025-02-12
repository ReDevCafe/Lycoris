package org.momento.lycoris.mixins;

import org.momento.lycoris.Lycoris;
import org.momento.lycoris.mixins.mixin.Mixin;
import org.momento.lycoris.mixins.mixin.classe.ClassWrapper;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.ClassInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.ConstantInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.UTF8Info;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.MethodInfo;
import org.momento.lycoris.mixins.mixin.injection.Inject;
import org.yaml.snakeyaml.scanner.Constant;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

public class Bootstrapper {

    public static void init(Instrumentation instrumentation, List<Class<?>> classList) {
        for (Class<?> clazz : classList) {
            Object object;
            try {
                object = clazz.getConstructor().newInstance();
            } catch (ReflectiveOperationException error) {
                continue;
            }
            Mixin mixin = Reflection.getAnnotation(object, Mixin.class);
            if (mixin == null) continue;
            Class<?> mixinClass = mixin.value();
            ClassWrapper wrapper;
            try {
                wrapper = ClassWrapper.decode(mixinClass);
            } catch (IOException error) {
                Lycoris.LOGGER.log(Level.SEVERE, error.getMessage(), error);
                continue;
            }
            redefineMethods(wrapper, clazz);
            ClassDefinition definition = new ClassDefinition(mixinClass, wrapper.toByteArray());
            try {
                instrumentation.redefineClasses(definition);
            } catch (ClassNotFoundException | UnmodifiableClassException error) {
                Lycoris.LOGGER.log(Level.SEVERE, error.getMessage(), error);
            }
        }
    }

    public static MethodInfo searchFromSignature(ConstantPool[] constantPools, MethodInfo[] methodInfos, String signature) {
        for (MethodInfo methodInfo : methodInfos) {
            String descriptor = ((UTF8Info) constantPools[methodInfo.getDescriptorIndex() - 1].getInfo()).getString();
            String name = ((UTF8Info) constantPools[methodInfo.getNameIndex() - 1].getInfo()).getString();
            String methodSignature = name + descriptor;
            if (signature.equals(methodSignature))
                return methodInfo;
        }
        return null;
    }

    public static void redefineMethods(ClassWrapper mixinClass, Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            Inject inject = method.getAnnotation(Inject.class);
            if (inject == null) continue;
            String signature = inject.method();
            MethodInfo methodInfo = searchFromSignature(mixinClass.getConstantPool(), mixinClass.getMethods(), signature);
            if (methodInfo == null) continue;

        }
    }


}
