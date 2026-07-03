package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class LineNumberTable implements SizedByteCodec {

    public record Table(char startPC, char lineNumber) implements SizedByteCodec {

        public static Table decode(ByteBuffer buffer) {
                return new Table(buffer.getChar(), buffer.getChar());
            }

            @Override
            public int getSize() {
                return 4;
            }

            @Override
            public void encode(ByteBuffer buffer) {
                buffer.putChar(startPC);
                buffer.putChar(lineNumber);
            }
        }

    private final Table[] table;

    public LineNumberTable(final Table[] table) {
        this.table = table;
    }

    public Table[] getTable() { return table; }

    public static LineNumberTable decode(ByteBuffer buffer) {
        char length = buffer.getChar();
        Table[] table = new Table[length];
        for (int i = 0; i < table.length; i++)
            table[i] = Table.decode(buffer);
        return new LineNumberTable(table);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (Table table : table)
            baseSize += table.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) table.length);
        for (Table table : table)
            table.encode(buffer);
    }
}
