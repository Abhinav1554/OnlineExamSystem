package onlineexam;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExamFrame extends JFrame {
    private User user;
    private List<Question> questions;
    private int[] answers; // -1 = unanswered; stores 0..3 for options A..D
    private int currentIndex = 0;

    // UI
    private JLabel lblQuestion;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup bg;
    private JLabel lblTimer;
    private Timer countdownTimer;
    private int remainingSeconds;

    public ExamFrame(User user) {
        this.user = user;
        this.questions = DataManager.questions;
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions found. Contact admin.");
            new DashboardFrame(user);
            dispose();
            return;
        }
        answers = new int[questions.size()];
        for (int i = 0; i < answers.length; i++) answers[i] = -1;

        setTitle("Exam - " + user.getName());
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel p = new JPanel(null);
        lblTimer = new JLabel("Time left: --:--");
        lblTimer.setBounds(520, 10, 150, 25);
        p.add(lblTimer);

        lblQuestion = new JLabel();
        lblQuestion.setBounds(20, 40, 640, 60);
        lblQuestion.setFont(new Font("Arial", Font.PLAIN, 14));
        p.add(lblQuestion);

        rbA = new JRadioButton();
        rbA.setBounds(40, 120, 600, 30);
        rbB = new JRadioButton();
        rbB.setBounds(40, 160, 600, 30);
        rbC = new JRadioButton();
        rbC.setBounds(40, 200, 600, 30);
        rbD = new JRadioButton();
        rbD.setBounds(40, 240, 600, 30);

        bg = new ButtonGroup();
        bg.add(rbA); bg.add(rbB); bg.add(rbC); bg.add(rbD);

        p.add(rbA); p.add(rbB); p.add(rbC); p.add(rbD);

        JButton btnPrev = new JButton("Previous");
        btnPrev.setBounds(100, 300, 120, 35);
        p.add(btnPrev);

        JButton btnNext = new JButton("Next");
        btnNext.setBounds(260, 300, 120, 35);
        p.add(btnNext);

        JButton btnSubmit = new JButton("Submit Exam");
        btnSubmit.setBounds(420, 300, 150, 35);
        p.add(btnSubmit);

        JButton btnEnd = new JButton("End Session (Logout)");
        btnEnd.setBounds(420, 340, 150, 30);
        p.add(btnEnd);

        // Actions
        btnNext.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentIndex < questions.size() - 1) {
                currentIndex++;
                loadQuestion(currentIndex);
            }
        });

        btnPrev.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentIndex > 0) {
                currentIndex--;
                loadQuestion(currentIndex);
            }
        });

        btnSubmit.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(this, "Are you sure you want to submit?", "Submit", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                finalizeExam();
            }
        });

        btnEnd.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "End session and logout? This will discard this attempt.", "End Session", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                new LoginFrame();
                dispose();
            }
        });

        add(p);
        setVisible(true);

        // set exam duration: e.g., 5 minutes per exam (you can change)
        remainingSeconds = 5 * 60; // 5 minutes; change as needed
        startTimer();
        loadQuestion(currentIndex);
    }

    private void startTimer() {
        countdownTimer = new Timer(1000, e -> {
            remainingSeconds--;
            updateTimerLabel();
            if (remainingSeconds <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "Time is up! Exam will be submitted automatically.");
                finalizeExam();
            }
        });
        countdownTimer.start();
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        int mins = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        lblTimer.setText(String.format("Time left: %02d:%02d", mins, secs));
    }

    private void loadQuestion(int idx) {
        Question q = questions.get(idx);
        lblQuestion.setText((idx + 1) + ". " + q.getText());
        String[] opts = q.getOptions();
        rbA.setText("A. " + opts[0]);
        rbB.setText("B. " + opts[1]);
        rbC.setText("C. " + opts[2]);
        rbD.setText("D. " + opts[3]);

        // set radio according to saved answer
        bg.clearSelection();
        if (answers[idx] == 0) rbA.setSelected(true);
        else if (answers[idx] == 1) rbB.setSelected(true);
        else if (answers[idx] == 2) rbC.setSelected(true);
        else if (answers[idx] == 3) rbD.setSelected(true);
    }

    private void saveCurrentAnswer() {
        int selected = -1;
        if (rbA.isSelected()) selected = 0;
        else if (rbB.isSelected()) selected = 1;
        else if (rbC.isSelected()) selected = 2;
        else if (rbD.isSelected()) selected = 3;
        answers[currentIndex] = selected;
    }

    private void finalizeExam() {
        saveCurrentAnswer();
        if (countdownTimer != null) countdownTimer.stop();

        // calculate score
        int score = 0;
        StringBuilder sbDetail = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            char correct = q.getCorrect();
            int correctIndex = correct - 'A';
            if (answers[i] == correctIndex) {
                score++;
            } else {
                // nothing
            }
            sbDetail.append(String.format("%d) Your: %s | Correct: %s%n",
                    i + 1,
                    (answers[i] == -1 ? "Not answered" : String.valueOf((char)('A' + answers[i]))),
                    correct));
        }

        // show result frame
        new ResultFrame(user, score, questions.size(), sbDetail.toString());
        dispose();
    }
}