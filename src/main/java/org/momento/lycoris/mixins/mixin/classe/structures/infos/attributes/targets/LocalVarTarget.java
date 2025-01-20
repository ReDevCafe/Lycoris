package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class LocalVarTarget extends EmptyTarget {

    public static class Table implements SizedByteCodec {

        private final char startPC;
        private final char length;
        private final char index;

        public Table(char startPC, char length, char index) {
            this.startPC = startPC;
            this.length = length;
            this.index = index;
        }

        public char getStartPC() { return startPC; }
        public char getLength() { return length; }
        public char getIndex() { return index; }

        public static Table decode(ByteBuffer buffer) {
            return new Table(buffer.getChar(), buffer.getChar(), buffer.getChar());
        }

        @Override
        public int getSize() {
            return 6;
        }

        @Override
        public void encode(ByteBuffer buffer) {
            buffer.putChar(startPC);
            buffer.putChar(length);
            buffer.putChar(index);
        }
    }

    private final Table[] tables;

    public LocalVarTarget(Table[] tables) {
        this.tables = tables;
    }

    public Table[] getTables() { return tables; }

    public static LocalVarTarget decode(ByteBuffer buffer) {
        Table[] tables = new Table[buffer.getChar()];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = Table.decode(buffer);
        }
        return new LocalVarTarget(tables);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (Table table : tables)
            baseSize += table.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) tables.length);
        for (Table table : tables)
            table.encode(buffer);
    }


}
