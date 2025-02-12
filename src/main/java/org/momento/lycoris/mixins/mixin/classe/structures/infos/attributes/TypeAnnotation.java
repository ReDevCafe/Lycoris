package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets.*;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets.EmptyTarget;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values.ElementValue;
import org.momento.lycoris.utils.Pair;

import java.nio.ByteBuffer;

public class TypeAnnotation implements SizedByteCodec {

    public enum Type {
        GENERIC_CLASS((byte) 0x0),
        GENERIC_METHOD((byte) 0x01),
        SUPER_CLASS((byte) 0x10),
        BOUND_GENERIC_CLASS((byte) 0x11),
        BOUND_GENERIC_METHOD((byte) 0x12),
        FIELD_TYPE((byte) 0x13),
        RETURN_TYPE((byte) 0x14),
        RECEIVER_TYPE((byte) 0x15),
        PARAMETER_TYPE((byte) 0x16),
        THROW_TYPE((byte) 0x17),
        LOCAL_VAR_TYPE((byte) 0x40),
        RESOURCE_VAR_TYPE((byte) 0x41),
        EXCEPTION_TYPE((byte) 0x42),
        INSTANCEOF_TYPE((byte) 0x43),
        NEW_TYPE((byte) 0x44),
        NEW_REF_TYPE((byte) 0x45),
        IDENTIFIER_REF_TYPE((byte) 0x46),
        CAST_TYPE((byte) 0x47),
        GENERIC_CONSTRUCTOR_TYPE((byte) 0x48),
        GENERIC_METHOD_TYPE((byte) 0x49),
        GENERIC_CONSTRUCTOR_NEW_REF_TYPE((byte) 0x4a),
        GENERIC_METHOD_IDENTIFIER_REF_TYPE((byte) 0x4b);


        private final byte value;

        Type(byte value) {
            this.value = value;
        }

        public byte getValue() { return value; }

        public static Type fromValue(byte value) {
            for (Type type : Type.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return null;
        }
    }

    private final Type targetType;
    private final EmptyTarget targetInfo;
    private final TypePath targetPath;
    private final char typeIndex;
    private final Pair<Character, ElementValue>[] elementValuePairs;

    public TypeAnnotation(Type targetType, EmptyTarget targetInfo, TypePath targetPath, char typeIndex, Pair<Character, ElementValue>[] elementValuePairs) {
        this.targetType = targetType;
        this.targetInfo = targetInfo;
        this.targetPath = targetPath;
        this.typeIndex = typeIndex;
        this.elementValuePairs = elementValuePairs;
    }

    public Type getEmptyTargetType() { return targetType; }
    public EmptyTarget getEmptyTargetInfo() { return targetInfo; }
    public TypePath getEmptyTargetPath() { return targetPath; }
    public char getTypeIndex() { return typeIndex; }
    public Pair<Character, ElementValue>[] getElementValuePairs() { return elementValuePairs; }

    public static TypeAnnotation decode(ByteBuffer buffer) {
        Type targetType = Type.fromValue(buffer.get());
        EmptyTarget target = switch (targetType) {
            case GENERIC_CLASS, GENERIC_METHOD -> TypeParameterTarget.decode(buffer);
            case SUPER_CLASS -> SuperTypeTarget.decode(buffer);
            case BOUND_GENERIC_CLASS, BOUND_GENERIC_METHOD -> TypeParameterBoundTarget.decode(buffer);
            case FIELD_TYPE, RETURN_TYPE, RECEIVER_TYPE -> EmptyTarget.decode(buffer);
            case PARAMETER_TYPE -> FormalParameterTarget.decode(buffer);
            case THROW_TYPE -> ThrowsTarget.decode(buffer);
            case LOCAL_VAR_TYPE, RESOURCE_VAR_TYPE -> LocalVarTarget.decode(buffer);
            case EXCEPTION_TYPE -> CatchTarget.decode(buffer);
            case INSTANCEOF_TYPE, NEW_TYPE, NEW_REF_TYPE, IDENTIFIER_REF_TYPE -> OffsetTarget.decode(buffer);
            case CAST_TYPE, GENERIC_CONSTRUCTOR_TYPE, GENERIC_METHOD_TYPE, GENERIC_CONSTRUCTOR_NEW_REF_TYPE, GENERIC_METHOD_IDENTIFIER_REF_TYPE -> TypeArgumentTarget.decode(buffer);
        };
        TypePath path = TypePath.decode(buffer);
        char typeIndex = buffer.getChar();
        Pair<Character, ElementValue>[] elementValuePairs = new Pair[buffer.getChar()];
        for (int i = 0; i < elementValuePairs.length; i++)
            elementValuePairs[i] = new Pair<>(buffer.getChar(), ElementValue.decode(buffer));
        return new TypeAnnotation(targetType, target, path, typeIndex, elementValuePairs);
    }

    @Override
    public int getSize() {
        int baseSize = 1 + targetInfo.getSize() + targetPath.getSize() + 4;
        for (Pair<Character, ElementValue> pair : elementValuePairs)
            baseSize += 2 + pair.getSecond().getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(targetType.value);
        targetInfo.encode(buffer);
        targetPath.encode(buffer);
        buffer.putChar(typeIndex);
        buffer.putChar((char) elementValuePairs.length);
        for (Pair<Character, ElementValue> pair : elementValuePairs) {
            buffer.putChar(pair.getFirst());
            pair.getSecond().encode(buffer);
        }
    }
}
