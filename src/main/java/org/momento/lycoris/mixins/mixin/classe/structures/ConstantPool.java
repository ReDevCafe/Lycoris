package org.momento.lycoris.mixins.mixin.classe.structures;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.*;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class ConstantPool implements SizedByteCodec {

    public enum Tag {

        Class((byte) 7),
        FieldRef((byte) 9),
        MethodRef((byte) 10),
        InterfaceMethodRef((byte) 11),
        String((byte) 8),
        Integer((byte) 3),
        Float((byte) 4),
        Long((byte) 5),
        Double((byte) 6),
        NameAndType((byte) 12),
        UTF8((byte) 1),
        MethodHandle((byte) 15),
        MethodType((byte) 16),
        Dynamic((byte) 17),
        InvokeDynamic((byte) 18),
        Module((byte) 19),
        Package((byte) 20);

        private final byte value;

        Tag(final byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static Tag getTag(final byte value) {
            for (final Tag tag : Tag.values()) {
                if (tag.value == value)
                    return tag;
            }
            throw new IllegalArgumentException("Unknown tag: " + value);
        }

    }

    private ConstantInfo info;

    public ConstantPool(final ConstantInfo info) {
        this.info = info;
    }

    public ConstantInfo getInfo() { return info; }

    public static ConstantPool decode(ByteBuffer buffer) {
        Tag tag = Tag.getTag(buffer.get());
        ConstantInfo info = switch (tag) {
            case Class -> ClassInfo.decode(buffer);
            case FieldRef, MethodRef, InterfaceMethodRef -> RefInfo.decode(tag, buffer);
            case String -> StringInfo.decode(buffer);
            case Integer -> IntegerInfo.decode(buffer);
            case Float -> FloatInfo.decode(buffer);
            case Long -> LongInfo.decode(buffer);
            case Double -> DoubleInfo.decode(buffer);
            case NameAndType -> NameTypeInfo.decode(buffer);
            case UTF8 -> UTF8Info.decode(buffer);
            case MethodHandle -> MethodHandleInfo.decode(buffer);
            case MethodType -> MethodTypeInfo.decode(buffer);
            case Dynamic -> DynamicInfo.decode(buffer);
            case InvokeDynamic -> InvokeDynamicInfo.decode(buffer);
            case Module -> ModuleInfo.decode(buffer);
            case Package -> PackageInfo.decode(buffer);
        };
        return new ConstantPool(info);
    }

    @Override
    public int getSize() {
        return info.getSize();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        info.encode(buffer);
    }

}
