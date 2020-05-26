package readability;

import java.io.IOException;

import static java.lang.Math.round;

public class ColemanLiauIndex {

    private long sentences = 0;
    private long words = 0;
    private long characters = 0;
    private double l = 0.0;
    private double s = 0.0;

    private double index = 0.0;

    public ColemanLiauIndex() {

    }

    public ColemanLiauIndex(long sentences, long words, long characters) {
        this.sentences = sentences;
        this.words = words;
        this.characters = characters;
    }

    public void setSentences(long sentences) {
        this.sentences = sentences;
    }

    public void setWords(long words) {
        this.words = words;
    }

    public void setCharacters(long characters) {
        this.characters = characters;
    }

    public void calculateIndex() {
        l = ((double) characters / words) * 100;
        s = ((double) sentences / words) * 100;
        index = (0.0588 * l) - (0.296 * s) - 15.8;
    }

    public double getIndex() {
        return index;
    }
}