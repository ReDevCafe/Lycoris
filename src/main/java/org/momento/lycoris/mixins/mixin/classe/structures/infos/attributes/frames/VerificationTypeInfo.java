package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables.ObjectVariableInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables.UninitializedVariableInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables.VariableInfo;

import java.nio.ByteBuffer;

public class VerificationTypeInfo implements SizedByteCodec {

    private final VariableInfo variableInfo;

    public enum Tag {
        TOP,
        INTEGER,
        FLOAT,
        DOUBLE,
        LONG,
        NULL,
        UNINITIALIZED_THIS,
        OBJECT,
        UNINITIALIZED;
    }

    public VerificationTypeInfo(final VariableInfo variableInfo) {
        this.variableInfo = variableInfo;
    }


    public VariableInfo getVariableInfo() { return variableInfo; }

    public static VerificationTypeInfo decode(ByteBuffer buffer) {
        Tag tag = Tag.values()[buffer.get() & 0xFF];
        VariableInfo variableInfo =  switch (tag) {
            case TOP, INTEGER, FLOAT, LONG, DOUBLE, NULL, UNINITIALIZED_THIS -> new VariableInfo(tag);
            case OBJECT -> ObjectVariableInfo.decode(buffer);
            case UNINITIALIZED -> UninitializedVariableInfo.decode(buffer);
        };
        return new VerificationTypeInfo(variableInfo);
    }

    @Override
    public int getSize() {
        return variableInfo.getSize();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        variableInfo.encode(buffer);
    }
}
