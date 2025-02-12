package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.ClassWrapper;

import java.util.ArrayList;
import java.util.List;

public enum AccessFlag {
    MASKED((char) 0x0),
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

    private char value;

    AccessFlag(final char value) {
        this.value = value;
    }

    public char getValue() { return value; }

    public char setValue(char value) { this.value = value; return value; }

    public static AccessFlag fromValue(final char value) {
        for (final AccessFlag flag : AccessFlag.values()) {
            if (flag.getValue() == value)
                return flag;
        }
        AccessFlag flag = MASKED;
        flag.setValue(value);
        return flag;
    }

    public AccessFlag[] getAccessFlags() {
        List<AccessFlag> flags = new ArrayList<>();
        for (AccessFlag flag : AccessFlag.values()) {
            if ((value & flag.getValue()) != 0)
                flags.add(flag);
        }
        return flags.toArray(new AccessFlag[0]);
    }

    public void addAccessFlag(final AccessFlag flag) {
        this.value = (char) (value | flag.value);
    }

    public void removeAccessFlag(final AccessFlag flag) {
        this.value = (char) (value & ~flag.value);
    }
}