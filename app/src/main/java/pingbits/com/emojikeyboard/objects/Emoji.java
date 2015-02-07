package pingbits.com.emojikeyboard.objects;

/**
 * Created by aagam on 6/2/15.
 */
public class Emoji {

    public String emoji;
    public int codepoint;
    public static Emoji fromCodePoint(int codePoint) {
        Emoji emoji = new Emoji();
        emoji.emoji = newString(codePoint);
        emoji.codepoint = codePoint;
        return emoji;
    }

    public static final String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }

    public static int intFromCodePoint(String emojiValue){
        return Integer.parseInt(emojiValue);
    }
}
