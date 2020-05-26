package readability;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class SMOGIndex {

    private long sentences = 0;
    private long polysyllabus = 0;

    private double index = 0.0;

    public SMOGIndex() {

    }

    public SMOGIndex(long sentences, long polysyllabus) {
        this.sentences = sentences;
        this.polysyllabus = polysyllabus;
    }

    public void setSentences(long sentences) {
        this.sentences = sentences;
    }

    public void setPolysyllabus(long polysyllabus) {
        this.polysyllabus = polysyllabus;
    }

    public void calculateIndex() {
        index = 1.043 * sqrt((double)polysyllabus * ((double)30 / sentences))
                + 3.1291;
    }

    public double getIndex() {
        return index;
    }

}