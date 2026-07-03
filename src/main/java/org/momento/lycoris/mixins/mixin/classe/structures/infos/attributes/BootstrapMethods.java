package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class BootstrapMethods implements SizedByteCodec {

    public static class BootsrapMethod implements SizedByteCodec {

        private final char ref;
        private final char[] args;

        public BootsrapMethod(final char ref, final char[] args) {
            this.ref = ref;
            this.args = args;
        }

        public char getRef() { return ref; }
        public char[] getArgs() { return args; }

        public static BootsrapMethod decode(ByteBuffer buffer) {
            char ref = buffer.getChar();
            char[] args = new char[buffer.getChar()];
            for (int i = 0; i < args.length; i++)
                args[i] = buffer.getChar();
            return new BootsrapMethod(ref, args);
        }

        @Override
        public int getSize() {
            return 2 + args.length * 2;
        }

        @Override
        public void encode(ByteBuffer buffer) {
            buffer.putChar(ref);
            buffer.putChar((char) args.length);
            for (int i = 0; i < args.length; i++)
                buffer.putChar(args[i]);
        }
    }

    private final BootsrapMethod[] methods;

    public BootstrapMethods(final BootsrapMethod[] methods) {
        this.methods = methods;
    }

    public BootsrapMethod[] getMethods() { return methods; }

    public static BootstrapMethods decode(ByteBuffer buffer) {
        BootsrapMethod[] methods = new BootsrapMethod[buffer.getChar()];
        for (int i = 0; i < methods.length; ++i)
            methods[i] = BootsrapMethod.decode(buffer);
        return new BootstrapMethods(methods);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) methods.length);
        for (BootsrapMethod method : methods)
            method.encode(buffer);
    }
}
