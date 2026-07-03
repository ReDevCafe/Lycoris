package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts.ExportsCount;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts.OpensCount;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts.ProvidesCount;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts.RequiresCount;

import java.nio.ByteBuffer;

public class Module implements SizedByteCodec {

    public enum Flag {
        ACC_OPEN((char ) 0x20),
        ACC_SYNTHETIC((char) 0x1000),
        ACC_MANDATED((char) 0x8000);

        private final char value;

        Flag(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        public static Flag fromValue(char value) {
            for (Flag flag : Flag.values()) {
                if (flag.getValue() == value) {
                    return flag;
                }
            }
            return null;
        }
    }

    private char moduleNameIndex;
    private Flag moduleFlags;
    private char moduleVersionIndex;
    private RequiresCount[] requires;
    private ExportsCount[] exports;
    private OpensCount[] opens;
    private char[] usesIndex;
    private ProvidesCount[] provides;

    public Module(char moduleNameIndex, Flag moduleFlags, char moduleVersionIndex, RequiresCount[] requires, ExportsCount[] exports, OpensCount[] opens, char[] usesIndex, ProvidesCount[] provides) {
        this.moduleNameIndex = moduleNameIndex;
        this.moduleFlags = moduleFlags;
        this.moduleVersionIndex = moduleVersionIndex;
        this.requires = requires;
        this.exports = exports;
        this.opens = opens;
        this.usesIndex = usesIndex;
        this.provides = provides;
    }

    public static Module decode(ByteBuffer buffer) {
        char moduleNameIndex = buffer.getChar();
        Flag moduleFlags = Flag.fromValue(buffer.getChar());
        char moduleVersionIndex = buffer.getChar();
        RequiresCount[] requires = new RequiresCount[buffer.getChar()];
        for (int i = 0; i < requires.length; i++)
            requires[i] = RequiresCount.decode(buffer);
        ExportsCount[] exports = new ExportsCount[buffer.getChar()];
        for (int i = 0; i < exports.length; i++)
            exports[i] = ExportsCount.decode(buffer);
        OpensCount[] opens = new OpensCount[buffer.getChar()];
        for (int i = 0; i < opens.length; i++)
            opens[i] = OpensCount.decode(buffer);
        char[] usesIndex = new char[buffer.getChar()];
        for (int i = 0; i < usesIndex.length; i++)
            usesIndex[i] = buffer.getChar();
        ProvidesCount[] provides = new ProvidesCount[buffer.getChar()];
        for (int i = 0; i < provides.length; i++)
            provides[i] = ProvidesCount.decode(buffer);
        return new Module(moduleNameIndex, moduleFlags, moduleVersionIndex, requires, exports, opens,usesIndex, provides);
    }

    @Override
    public int getSize() {
        int baseSize = 16;
        for (RequiresCount requiresCount : requires)
            baseSize += requiresCount.getSize();
        for (ExportsCount exportsCount : exports)
            baseSize += exportsCount.getSize();
        for (OpensCount opensCount : opens)
            baseSize += opensCount.getSize();
        baseSize += usesIndex.length * 2;
        for (ProvidesCount providesCount : provides)
            baseSize += providesCount.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(moduleNameIndex);
        buffer.putChar(moduleFlags.getValue());
        buffer.putChar(moduleVersionIndex);
        buffer.putChar((char) requires.length);
        for (RequiresCount requiresCount : requires)
            requiresCount.encode(buffer);
        buffer.putChar((char) exports.length);
        for (ExportsCount exportsCount : exports)
            exportsCount.encode(buffer);
        buffer.putChar((char) usesIndex.length);
        for (int i = 0; i < usesIndex.length; i++)
            buffer.putChar(usesIndex[i]);
        for (ProvidesCount providesCount : provides)
            providesCount.encode(buffer);
    }
}
