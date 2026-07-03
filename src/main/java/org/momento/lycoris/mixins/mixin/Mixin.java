package org.momento.lycoris.mixins.mixin;

public class Mixin<T> {

    private Class<T> classe;

    public Mixin(Class<T> classe) {
        this.classe = classe;
    }

}
