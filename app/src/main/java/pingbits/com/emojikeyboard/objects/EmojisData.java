package pingbits.com.emojikeyboard.objects;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojisData {

    //Shell script : find -name '*.png' -printf 'Emoji.fromCodePoint(0x%f),\n' | sed -e 's/\<png\>//g' | sed -e 's/\.//2' | sed -e 's/p//g' | sort

    public static final Emoji[] DATA = new Emoji[]{
            Emoji.fromCodePoint(0x1f60a),
            Emoji.fromCodePoint(0x1f60b),
            Emoji.fromCodePoint(0x1f60c),
            Emoji.fromCodePoint(0x1f60d),
            Emoji.fromCodePoint(0x1f60e),
            Emoji.fromCodePoint(0x1f60f),
            Emoji.fromCodePoint(0x1f61a),
            Emoji.fromCodePoint(0x1f61b),
            Emoji.fromCodePoint(0x1f61c),
            Emoji.fromCodePoint(0x1f61d),
            Emoji.fromCodePoint(0x1f61e),
            Emoji.fromCodePoint(0x1f61f),
            Emoji.fromCodePoint(0x1f62a),
            Emoji.fromCodePoint(0x1f62b),
            Emoji.fromCodePoint(0x1f62c),
            Emoji.fromCodePoint(0x1f62d),
            Emoji.fromCodePoint(0x1f62e),
            Emoji.fromCodePoint(0x1f62f),
            Emoji.fromCodePoint(0x1f63a),
            Emoji.fromCodePoint(0x1f63b),
            Emoji.fromCodePoint(0x1f63c),
            Emoji.fromCodePoint(0x1f63d),
            Emoji.fromCodePoint(0x1f63e),
            Emoji.fromCodePoint(0x1f63f)
    };
}
