package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values.ElementValue;
import org.momento.lycoris.utils.Pair;

import java.nio.ByteBuffer;

public class Annotation implements SizedByteCodec {

    private final char typeIndex;
    private final Pair<Character, ElementValue>[] pairs;

    public Annotation(final char typeIndex, final Pair<Character, ElementValue>[] pairs) {
        this.typeIndex = typeIndex;
        this.pairs = pairs;
    }

    public char getTypeIndex() {
        return typeIndex;
    }

    public Pair<Character, ElementValue>[] getPairs() {
        return pairs;
    }

    public static Annotation decode(ByteBuffer buffer) {
        char typeIndex = buffer.getChar();
        Pair<Character, ElementValue>[] pairs = new Pair[buffer.getChar()];
        for (int i = 0; i < pairs.length; i++) {
            char nameIndex = buffer.getChar();
            pairs[i] = new Pair<>(nameIndex, ElementValue.decode(buffer));
        }
        return new Annotation(typeIndex, pairs);
    }

    @Override
    public int getSize() {
        int baseSize = 4;
        for (Pair<Character, ElementValue> pair : pairs)
            baseSize += 2 + pair.getSecond().getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(typeIndex);
        for (Pair<Character, ElementValue> pair : pairs) {
            buffer.putChar(pair.getFirst());
            pair.getSecond().encode(buffer);
        }
    }
}
