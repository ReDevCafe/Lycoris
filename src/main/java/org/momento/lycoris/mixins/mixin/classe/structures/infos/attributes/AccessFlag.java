package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

public enum AccessFlag {
    PUBLIC((char) 0x1),
    PRIVATE((char) 0x2),
    PROTECTED((char) 0x4),
    STATIC((char) 0x8),
    FINAL((char) 0x10),
    VOLATILE((char) 0x40),
    TRANSIENT((char) 0x80),
    INTERFACE((char) 0x200),
    ABSTRACT((char) 0x400),
    SYNTHETIC((char) 0x1000),
    ANNOTATION((char) 0x2000),
    ENUM((char) 0x4000);

    private final char value;

    AccessFlag(final char value) {
        this.value = value;
    }

    public char getValue() { return value; }

    public static AccessFlag fromValue(final char value) {
        for (final AccessFlag flag : AccessFlag.values()) {
            if (flag.getValue() == value)
                return flag;
        }
        return null;
    }
}