package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;

public interface SizedByteCodec extends ByteCodec {

    int getSize();

}
