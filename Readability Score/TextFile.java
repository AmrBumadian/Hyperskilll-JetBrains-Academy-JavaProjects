package readability;

import java.io.FileReader;
import java.io.IOException;


public class TextFile {

    private String filePath;

    private long sentences = 0;
    private long words = 0;
    private long characters = 0;
    private long syllables = 0;
    private long polysyllables = 0;

    private double ARIIndex = 0.0;
    private double FKIndex = 0.0;
    private double SMOGIndex = 0.0;
    private double CLIndex = 0.0;

    public TextFile() {
        filePath = "";
    }

    public TextFile(String filePath) throws IOException {
        this.filePath = filePath;
        calculateSentences();
        calculateSyllabus();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) throws IOException {
        this.filePath = filePath;
        calculateSentences();
        calculateSyllabus();
    }

    public long getSentences() {
        return sentences;
    }

    public long getWords() {
        return words;
    }

    public long getCharacters() {
        return characters;
    }

    public long getSyllabus() {
        return syllables;
    }

    public long getPolysyllabus() {
        return polysyllables;
    }

    private void calculateSentences() throws IOException {
        var inputFile = new FileReader(filePath);
        int characterValue = inputFile.read();
        char character = '\0';
        while (characterValue != -1) {
            character = (char) characterValue;
            if (Character.isSpaceChar(character)) {
                ++words;
            } else if (character == '.' || character == '?' || character == '!') {
                ++sentences;
                ++characters;
            } else {
                ++characters;
            }
            characterValue = inputFile.read();
        }
        inputFile.close();
        ++words;
        if (character != '.' && character != '?' && character != '!') {
            ++sentences;
        }
    }

    private void calculateSyllabus() throws IOException {
        var inputFile = new FileReader(filePath);
        int characterValue = inputFile.read();
        long syllablesPerWord = 0;
        String character = "";
        char prevCharacter = ' ';
        char prevPrevCharacter = ' ';

        while (characterValue != -1) {
            character = Character.toString((char) characterValue);
            if (character.matches("[AaEeIiOoUuYy]")) {
                if (!Character.toString(prevCharacter).matches("[AaEeIiOoUuYy]")) {
                    ++syllablesPerWord;
                }
            } else if (character.equals(" ") || character.equals("\t") || character.equals("\n")) {
                if (prevCharacter == 'E' || prevCharacter == 'e') {
                    --syllablesPerWord;
                }
                else if (Character.toString(prevPrevCharacter).matches("[Ee]")
                        && Character.toString(prevCharacter).matches("[\\!\\?\\.,;'\"]")) {
                    --syllablesPerWord;
                }
                if (syllablesPerWord == 0) {
                    syllablesPerWord = 1;
                }
                if (syllablesPerWord > 2) {
                    ++polysyllables;
                }
                syllables += syllablesPerWord;
                syllablesPerWord = 0;
            }
            prevPrevCharacter = prevCharacter;
            prevCharacter = (char) characterValue;
            characterValue = inputFile.read();
        }
        if (prevCharacter != ' ' && prevCharacter != '\n' && prevCharacter != '\t') {
            if (prevCharacter == 'E' || prevCharacter == 'e') {
                --syllablesPerWord;
            }
            syllables += syllablesPerWord;
            if (syllablesPerWord > 2) {
                ++polysyllables;
            }
        }
        inputFile.close();
    }

    public void calculateARIndex() {
        var automatedReadabilityIndex = new AutomatedReadabilityIndex(sentences, words, characters);
        automatedReadabilityIndex.calculateIndex();
        ARIIndex = automatedReadabilityIndex.getIndex();
    }

    public double getARIndex() {
        return ARIIndex;
    }

    public void calculateFKIndex() {
        var fleschKincasidIndex = new FleschKincaidIndex(sentences, words, syllables);
        fleschKincasidIndex.calculateIndex();
        FKIndex = fleschKincasidIndex.getIndex();
    }

    public double getFKIndex() {
        return FKIndex;
    }

    public void calculateSMOGIndex() {
        var smogIndex = new SMOGIndex(sentences, polysyllables);
        smogIndex.calculateIndex();
        SMOGIndex = smogIndex.getIndex();
    }

    public double getSMOGIndex() {
        return SMOGIndex;
    }

    public void calculateCLIndex() {
        var colemanLiauIndex = new ColemanLiauIndex(sentences, words, characters);
        colemanLiauIndex.calculateIndex();
        CLIndex = colemanLiauIndex.getIndex();
    }

    public double getCLIndex() {
        return CLIndex;
    }

    public void calculateAll() {
        calculateARIndex();
        calculateFKIndex();
        calculateSMOGIndex();
        calculateCLIndex();
    }
}