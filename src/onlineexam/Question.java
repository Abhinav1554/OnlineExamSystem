package onlineexam;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String text;
    private String[] options; // length 4, options A,B,C,D
    private char correct; // 'A','B','C' or 'D'

    public Question(int id, String text, String[] options, char correct) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correct = Character.toUpperCase(correct);
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public char getCorrect() { return correct; }
}