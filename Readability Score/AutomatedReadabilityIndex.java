package readability;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.round;

public class AutomatedReadabilityIndex {

    private double index = 0.0;

    private long sentences = 0;
    private long words = 0;
    private long characters = 0;

    public AutomatedReadabilityIndex() {
    }

    public AutomatedReadabilityIndex(long sentences, long words, long characters) {
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
        index = 4.71 * ((double) characters / words)
                + 0.5 * ((double) words / sentences) - 21.43;
    }

    public double getIndex() {
        return index;
    }
}