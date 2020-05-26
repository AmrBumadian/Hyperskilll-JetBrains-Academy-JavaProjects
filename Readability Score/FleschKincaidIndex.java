package readability;

import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileLock;

import static java.lang.Math.round;

public class FleschKincaidIndex {

    private long sentences = 0;
    private long words = 0;
    private long syllables = 0;

    private double index = 0.0;

    public FleschKincaidIndex() {
    }

    public FleschKincaidIndex(long sentences, long words, long syllables) {
        this.sentences = sentences;
        this.words = words;

        this.syllables = syllables;
    }

    public void setSentences(long sentences) {
        this.sentences = sentences;
    }

    public void setWords(long words) {
        this.words = words;
    }

    public void setSyllabus(long syllabus) {
        this.syllables = syllabus;
    }

    public void calculateIndex() {
        index = 0.39 * ((double)words / sentences)
                + 11.8 * ((double) syllables / words) - 15.59;
    }

    public double getIndex() {
        return index;
    }
}