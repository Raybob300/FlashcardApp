package flashcardapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Visual {
    private ArrayList<Flashcard> flashcards = new ArrayList<>();

    public Visual() {
        JFrame frame = new JFrame("Flashcard App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JTabbedPane tabs = new JTabbedPane();

        // --- Add Card Tab ---
        JPanel addPanel = new JPanel(new GridLayout(13, 1));

        JLabel typeLabel = new JLabel("Select Card Type:");
        String[] types = {"Normal", "Multiple Choice"};
        JComboBox<String> typeSelector = new JComboBox<>(types);

        JTextField questionField = new JTextField();
        JTextField answerField = new JTextField();
        JTextField imageField = new JTextField(); // optional image for normal card
        JTextField choicesField = new JTextField(); // comma-separated choices for MCQ

        JLabel qLabel = new JLabel("Enter Question:");
        JLabel aLabel = new JLabel("Enter Answer:");
        JLabel imgLabel = new JLabel("Optional Image filename (for Normal)/ ig 'temp.jpg' file must be in src file:");
        JLabel choicesLabel = new JLabel("Choices (comma-separated, for MCQ):");
        choicesLabel.setVisible(false);
        choicesField.setVisible(false);

        JButton addButton = new JButton("Add Flashcard");
        JLabel statusLabel = new JLabel("");

        addPanel.add(typeLabel);
        addPanel.add(typeSelector);
        addPanel.add(qLabel);
        addPanel.add(questionField);
        addPanel.add(aLabel);
        addPanel.add(answerField);
        addPanel.add(imgLabel);
        addPanel.add(imageField);
        addPanel.add(choicesLabel);
        addPanel.add(choicesField);
        addPanel.add(addButton);
        addPanel.add(statusLabel);

        // Show/hide image or choices fields based on selected type
        typeSelector.addActionListener(e -> {
            String selected = (String) typeSelector.getSelectedItem();
            if ("Normal".equals(selected)) {
                imgLabel.setVisible(true);
                imageField.setVisible(true);
                choicesLabel.setVisible(false);
                choicesField.setVisible(false);
            } else {
                imgLabel.setVisible(false);
                imageField.setVisible(false);
                choicesLabel.setVisible(true);
                choicesField.setVisible(true);
            }
        });

        addButton.addActionListener(e -> {
            String question = questionField.getText().trim();
            String answer = answerField.getText().trim();
            String selected = (String) typeSelector.getSelectedItem();

            if (question.isEmpty() || answer.isEmpty()) {
                statusLabel.setText("Question and answer cannot be empty.");
                return;
            }

            if ("Normal".equals(selected)) {
                String imageName = imageField.getText().trim();
                if (imageName.isEmpty()) imageName = null;
                flashcards.add(new Flashcard(question, answer, imageName));
                statusLabel.setText("Normal flashcard added!");
            } else {
                // MCQ case
                String choicesText = choicesField.getText().trim();
                if (choicesText.isEmpty()) {
                    statusLabel.setText("Please enter choices for MCQ separated by commas.");
                    return;
                }
                List<String> choices = Arrays.asList(choicesText.split("\\s*,\\s*"));
                if (!choices.contains(answer)) {
                    statusLabel.setText("Answer must be one of the choices.");
                    return;
                }
                flashcards.add(new MultipleChoiceFlashcard(question, answer, choices));
                statusLabel.setText("Multiple Choice flashcard added!");
            }

            // Clear fields
            questionField.setText("");
            answerField.setText("");
            imageField.setText("");
            choicesField.setText("");
        });

        // --- View Tab ---
        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> {
            displayArea.setText("");
            for (Flashcard f : flashcards) {
                displayArea.append(f.toString() + "\n\n");
            }
        });

        viewPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);

        // --- Quiz Tab ---
        JPanel quizPanel = new JPanel(new BorderLayout());
        JLabel quizLabel = new JLabel("Click 'Next' to start quiz", SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(5, 1));
        JTextField answerInput = new JTextField();
        JButton checkButton = new JButton("Check");
        JButton nextButton = new JButton("Next");
        JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);
        JPanel choicesButtonsPanel = new JPanel();

        bottomPanel.add(answerInput);
        bottomPanel.add(choicesButtonsPanel);
        bottomPanel.add(checkButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(feedbackLabel);

        quizPanel.add(quizLabel, BorderLayout.NORTH);
        quizPanel.add(centerPanel, BorderLayout.CENTER);
        quizPanel.add(bottomPanel, BorderLayout.SOUTH);

        Flashcard[] currentCard = new Flashcard[1];

        nextButton.addActionListener(e -> {
            feedbackLabel.setText("");
            answerInput.setText("");
            choicesButtonsPanel.removeAll();
            imageLabel.setIcon(null);
            imageLabel.setText("");

            if (flashcards.size() == 0) {
                quizLabel.setText("No flashcards to quiz.");
                return;
            }
            int i = (int) (Math.random() * flashcards.size());
            currentCard[0] = flashcards.get(i);
            quizLabel.setText("Q: " + currentCard[0].getQuestion());

            if (currentCard[0] instanceof MultipleChoiceFlashcard mcq) {
                answerInput.setVisible(false);
                choicesButtonsPanel.setVisible(true);
                List<String> options = mcq.getChoices();
                choicesButtonsPanel.setLayout(new GridLayout(options.size(), 1));
                ButtonGroup bg = new ButtonGroup();
                for (String option : options) {
                    JRadioButton rb = new JRadioButton(option);
                    bg.add(rb);
                    choicesButtonsPanel.add(rb);
                }
                choicesButtonsPanel.revalidate();
                choicesButtonsPanel.repaint();
            } else {
                // Normal card
                answerInput.setVisible(true);
                choicesButtonsPanel.setVisible(false);

                String imageName = ((Flashcard) currentCard[0]).getImageName();
                if (imageName != null && !imageName.isEmpty()) {
                    URL imageURL = getClass().getResource("/flashcardapp/" + imageName);
                    if (imageURL != null) {
                        ImageIcon icon = new ImageIcon(imageURL);
                        imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                    } else {
                        imageLabel.setText("Image not found: " + imageName);
                    }
                }
            }
        });

        checkButton.addActionListener(e -> {
            if (currentCard[0] == null) return;

            String userAnswer;
            if (currentCard[0] instanceof MultipleChoiceFlashcard) {
                userAnswer = null;
                for (Component c : choicesButtonsPanel.getComponents()) {
                    if (c instanceof JRadioButton rb && rb.isSelected()) {
                        userAnswer = rb.getText();
                        break;
                    }
                }
                if (userAnswer == null) {
                    feedbackLabel.setText("Please select an option.");
                    return;
                }
            } else {
                userAnswer = answerInput.getText().trim();
                if (userAnswer.isEmpty()) {
                    feedbackLabel.setText("Please enter your answer.");
                    return;
                }
            }

            if (userAnswer.equalsIgnoreCase(currentCard[0].getAnswer().trim())) {
                feedbackLabel.setText("Correct!");
            } else {
                feedbackLabel.setText("Wrong. Correct answer: " + currentCard[0].getAnswer());
            }
        });

        // Tabs
        tabs.add("Add Card", addPanel);
        tabs.add("View Cards", viewPanel);
        tabs.add("Quiz Mode", quizPanel);

        frame.add(tabs);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Visual::new);
    }

    // --- Flashcard base class ---
    public class Flashcard {
        private String question;
        private String answer;
        private String imageName;

        public Flashcard(String question, String answer, String imageName) {
            this.question = question;
            this.answer = answer;
            this.imageName = imageName;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getImageName() {
            return imageName;
        }

        @Override
        public String toString() {
            String s = "Q: " + question + "\nA: " + answer;
            if (imageName != null) {
                s += "\n[Image: " + imageName + "]";
            }
            return s;
        }
    }

    // --- MultipleChoiceFlashcard subclass ---
    public class MultipleChoiceFlashcard extends Flashcard {
        private List<String> choices;

        public MultipleChoiceFlashcard(String question, String answer, List<String> choices) {
            super(question, answer, null);
            this.choices = choices;
        }

        public List<String> getChoices() {
            return choices;
        }

        @Override
        public String toString() {
            return super.toString() + "\nChoices: " + String.join(", ", choices);
        }
    }
}
