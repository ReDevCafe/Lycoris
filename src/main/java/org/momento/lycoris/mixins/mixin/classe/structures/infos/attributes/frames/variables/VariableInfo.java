package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.VerificationTypeInfo;

import java.nio.ByteBuffer;

public class VariableInfo implements SizedByteCodec {

    private final VerificationTypeInfo.Tag tag;

    public VariableInfo(final VerificationTypeInfo.Tag tag) {
        this.tag = tag;
    }

    public VerificationTypeInfo.Tag getTag() { return tag; }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put((byte) tag.ordinal());
    }
}
