package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.utils.Pair;

import java.nio.ByteBuffer;

public class TypePath implements SizedByteCodec {

    private final Pair<Byte, Byte>[] paths;

    public TypePath(Pair<Byte, Byte>[] paths) {
        this.paths = paths;
    }


    public Pair<Byte, Byte>[] getPaths() { return paths; }

    @Override
    public int getSize() {
        return 1 + paths.length * 2;
    }

    public static TypePath decode(ByteBuffer buffer) {
        Pair<Byte, Byte>[] paths = new Pair[buffer.get()];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = new Pair<>(buffer.get(), buffer.get());
        }
        return new TypePath(paths);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put((byte) paths.length);
        for (Pair<Byte, Byte> path : paths) {
            buffer.put(path.getFirst());
            buffer.put(path.getSecond());
        }
    }
}
