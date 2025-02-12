package org.momento.lycoris.mixins.mixin.classe.structures.infos;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.ConstantInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.constants.UTF8Info;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.*;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Module;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Record;

import java.nio.ByteBuffer;

public class AttributeInfo implements SizedByteCodec {

    private final char nameIndex;
    private final SizedByteCodec attribute;

    public AttributeInfo(final char nameIndex, final SizedByteCodec attribute) {
        this.nameIndex = nameIndex;
        this.attribute = attribute;
    }

    public static AttributeInfo decode(final ConstantPool[] constantPools, ByteBuffer buffer) {
        char nameIndex = buffer.getChar();
        ConstantInfo info = constantPools[nameIndex - 1].getInfo();
        if (info == null || info.getTag() != ConstantPool.Tag.UTF8)
            throw new RuntimeException("Unexpected tag: " + info.getTag());
        String attributeName =  ((UTF8Info) info).getString();
        int length = buffer.getInt();
        SizedByteCodec attribute = switch (attributeName) {
            case "ConstantValue" -> ConstantValue.decode(buffer);
            case "Code" -> Code.decode(constantPools, buffer);
            case "StackMapTable" -> StackMapTable.decode(buffer);
            case "Exceptions" -> Exceptions.decode(buffer);
            case "InnerClasses" -> InnerClasses.decode(buffer);
            case "EnclosingMethod" -> EnclosingMethod.decode(buffer);
            case "Signature" -> Signature.decode(buffer);
            case "SourceFile" -> SourceFile.decode(buffer);
            case "SourceDebugExtension" -> SourceDebugExtension.decode(length, buffer);
            case "LineNumberTable" -> LineNumberTable.decode(buffer);
            case "LocalVariableTable", "LocalVariableTypeTable" -> LocalVariableTable.decode(buffer);
            case "RuntimeInvisibleAnnotations", "RuntimeVisibleAnnotations" -> RuntimeAnnotations.decode(buffer);
            case "RuntimeInvisibleParameterAnnotations", "RuntimeVisibleParameterAnnotations" -> RuntimeParameterAnnotations.decode(buffer);
            case "AnnotationDefault" -> AnnotationDefault.decode(buffer);
            case "BootstrapMethods" -> BootstrapMethods.decode(buffer);
            case "RuntimeVisibleTypeAnnotations", "RuntimeInvisibleTypeAnnotations" -> RuntimeTypeAnnotations.decode(buffer);
            case "MethodParameters" -> MethodParameters.decode(buffer);
            case "Module" -> Module.decode(buffer);
            case "ModulePackages" -> ModulePackages.decode(buffer);
            case "ModuleMainClass" -> ModuleMainClass.decode(buffer);
            case "NestHost" -> NestHost.decode(buffer);
            case "NestMembers" -> NestMembers.decode(buffer);
            case "Record" -> Record.decode(constantPools, buffer);
            case "PermittedSubclasses" -> PermittedSubclasses.decode(buffer);
            default -> null;
        };
        return new AttributeInfo(nameIndex, attribute);
    }

    @Override
    public int getSize() { return 2 + attribute.getSize(); }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(nameIndex);
        if (attribute == null) {
            buffer.putInt(0);
            return;
        }
        buffer.putInt(attribute.getSize());
        attribute.encode(buffer);
    }
}
