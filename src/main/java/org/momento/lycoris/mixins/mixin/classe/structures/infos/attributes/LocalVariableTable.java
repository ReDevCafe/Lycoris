package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class LocalVariableTable implements SizedByteCodec {

    public record Table(char startPC, char length, char nameIndex, char descriptorIndex,
                        char index) implements SizedByteCodec {

        public static Table decode(ByteBuffer buffer) {
                return new Table(buffer.getChar(), buffer.getChar(), buffer.getChar(), buffer.getChar(), buffer.getChar());
            }

            @Override
            public int getSize() {
                return 10;
            }

            @Override
            public void encode(ByteBuffer buffer) {
                buffer.putChar(startPC);
                buffer.putChar(length);
                buffer.putChar(nameIndex);
                buffer.putChar(descriptorIndex);
                buffer.putChar(index);
            }
        }

    private final Table[] table;

    public LocalVariableTable(final Table[] table) {
        this.table = table;
    }

    public Table[] getTable() { return table; }

    public static LocalVariableTable decode(ByteBuffer buffer) {
        Table[] table = new Table[buffer.getChar()];
        for (int i = 0; i < table.length; i++)
            table[i] = Table.decode(buffer);
        return new LocalVariableTable(table);
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
