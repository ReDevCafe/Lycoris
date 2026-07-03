package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.AttributeInfo;

import java.nio.ByteBuffer;

public class Code implements SizedByteCodec {

    public static class Exception implements SizedByteCodec {
        private final char startPC;
        private final char endPC;
        private final char handlerPC;
        private final char catchType;

        public Exception(final char startPC, final char endPC, final char handlerPC, final char catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }

        public char getStartPC() { return startPC; }
        public char getEndPC() { return endPC; }
        public char getHandlerPC() { return handlerPC; }
        public char getCatchType() { return catchType; }


        public static Exception decode(ByteBuffer buffer) {
            return new Exception(buffer.getChar(), buffer.getChar(), buffer.getChar(), buffer.getChar());
        }

        @Override
        public int getSize() { return 4; }

        @Override
        public void encode(ByteBuffer buffer) {
            buffer.putChar(startPC);
            buffer.putChar(endPC);
            buffer.putChar(handlerPC);
            buffer.putChar(catchType);
        }
    }

    private final char maxStack;
    private final char maxLocals;
    private final byte[] code;
    private final Exception[] exceptionTable;
    private final AttributeInfo[] attributes;

    public Code(final char maxStack, final char maxLocals, final byte[] code, final Exception[] exceptionTable, final AttributeInfo[] attributes) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.exceptionTable = exceptionTable;
        this.attributes = attributes;

    }

    public static Code decode(final ConstantPool[] constantPools, ByteBuffer buffer) {
        char maxStack = buffer.getChar();
        char maxLocals = buffer.getChar();
        byte[] code = new byte[buffer.getInt()];
        buffer.get(code);
        Exception[] exceptionTable = new Exception[buffer.getChar()];
        for (int i = 0; i < exceptionTable.length; ++i)
            exceptionTable[i] = Exception.decode(buffer);
        AttributeInfo[] attributes = new AttributeInfo[buffer.getChar()];
        for (int i = 0; i < attributes.length; ++i)
            attributes[i] = AttributeInfo.decode(constantPools, buffer);
        return new Code(maxStack, maxLocals, code, exceptionTable, attributes);
    }

    @Override
    public int getSize() {
        int baseSize = 18 + code.length;
        for (Exception exception : exceptionTable)
            baseSize += exception.getSize();
        for (AttributeInfo attribute : attributes)
            baseSize += attribute.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(maxStack);
        buffer.putChar(maxLocals);
        buffer.putInt(code.length);
        buffer.put(code);
        buffer.putChar((char) exceptionTable.length);
        for (Exception exception : exceptionTable)
            exception.encode(buffer);
        buffer.putChar((char) attributes.length);
        for (AttributeInfo attribute : attributes)
            attribute.encode(buffer);
    }
}