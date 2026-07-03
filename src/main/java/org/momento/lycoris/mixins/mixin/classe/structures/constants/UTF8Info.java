package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class UTF8Info extends ConstantInfo {

    private final String string;

    public UTF8Info(final ConstantPool.Tag tag, String string) {
        super(tag);
        this.string = string;
    }

    public String getString() { return string; }

    public static UTF8Info decode(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.getChar()];
        buffer.get(bytes);
        String string = new String(bytes, StandardCharsets.UTF_8);
        return new UTF8Info(ConstantPool.Tag.UTF8, string);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar((char) string.length());
        buffer.put(StandardCharsets.UTF_8.encode(string));
    }
}
