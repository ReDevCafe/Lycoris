package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class SameLocalStackItemFrame extends  StackMapFrame {

    protected final VerificationTypeInfo verificationTypeInfo;

    public SameLocalStackItemFrame(final Type type, final VerificationTypeInfo verificationTypeInfo) {
        super(type);
        this.verificationTypeInfo = verificationTypeInfo;
    }

    public VerificationTypeInfo getVerificationTypeInfo() { return verificationTypeInfo; }

    @Override
    public int getSize() {
        return super.getSize() + verificationTypeInfo.getSize();
    }

    public static SameLocalStackItemFrame decode(final Type type, ByteBuffer buffer) {
        return new SameLocalStackItemFrame(type, VerificationTypeInfo.decode(buffer));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        encodePrimitive(buffer);
        verificationTypeInfo.encode(buffer);
    }

    protected void encodePrimitive(ByteBuffer buffer) {
        super.encode(buffer);
    }
}
