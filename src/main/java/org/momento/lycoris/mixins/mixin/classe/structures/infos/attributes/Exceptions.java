package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class Exceptions implements SizedByteCodec {

    private final char[] exceptionIndexTable;

    public Exceptions(final char[] exceptionIndexTable) {
        this.exceptionIndexTable = exceptionIndexTable;
    }

    public static Exceptions decode(ByteBuffer buffer) {
        char[] exceptionIndexTable = new char[buffer.getChar()];
        for (int i = 0; i < exceptionIndexTable.length; i++)
            exceptionIndexTable[i] = buffer.getChar();
        return new Exceptions(exceptionIndexTable);
    }

    public char[] getExceptionIndexTable() { return exceptionIndexTable; }

    @Override
    public int getSize() {
        return 2 * exceptionIndexTable.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) exceptionIndexTable.length);
        for (char index : exceptionIndexTable)
            buffer.putChar(index);
    }
}
