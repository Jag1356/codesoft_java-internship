import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;

public class task2_GUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton countButton;
    private JTextField commonWordsField;
    private JComboBox<String> actionComboBox;

    public task2_GUI() {
        setTitle("Word Count Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        inputTextArea = new JTextArea(10, 40);
        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        countButton = new JButton("Count Words");
        commonWordsField = new JTextField("a, and, as, in, is, it, of, that, the, to, with");
        actionComboBox = new JComboBox<>(new String[]{"Total Word Count", "Word Frequency (Include Common Words)", "Word Frequency (Exclude Common Words)"});

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(actionComboBox);
        topPanel.add(commonWordsField);
        topPanel.add(countButton);

        panel.add(new JScrollPane(inputTextArea), BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(outputTextArea), BorderLayout.SOUTH);

        add(panel);

        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                int action = actionComboBox.getSelectedIndex();
                String commonWordsText = commonWordsField.getText();

                if (inputText.isEmpty()) {
                    outputTextArea.setText("Please enter text.");
                    return;
                }

                if (action == 0) {
                    int totalWordCount = countWords(inputText);
                    outputTextArea.setText("Total Word Count: " + totalWordCount);
                } else if (action == 1) {
                    Map<String, Integer> wordFrequency = getWordFrequency(inputText, commonWordsText, 1);
                    outputTextArea.setText("Total Number of Words: " + wordFrequency.size() + "\n");
                    for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                        outputTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                } else if (action == 2) {
                    Map<String, Integer> wordFrequency = getWordFrequency(inputText, commonWordsText, 2);
                    outputTextArea.setText("Total Number of Words (except common words): " + wordFrequency.size() + "\n");
                    outputTextArea.append("Common Words: " + commonWordsText + "\n");
                    for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                        outputTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                }
            }
        });
    }

    public static int countWords(String text) {
        String[] words = text.split("[\\s.,!?;:]+");
        return words.length;
    }

    public static Map<String, Integer> getWordFrequency(String text, String commonWordsText, int n) {
        String[] commonWords = commonWordsText.split(", ");
        String[] words = text.split("[\\s.,!?;:]+");
        Map<String, Integer> wordFrequency = new java.util.HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();
            if (isCommonWord(word, commonWords) && n == 2) {
                //wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                continue;
            }
            else
            {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        return wordFrequency;
    }

    public static boolean isCommonWord(String word, String[] commonWords) {
        return Arrays.asList(commonWords).contains(word.toLowerCase());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            task2_GUI wordCountGUI = new task2_GUI();
            wordCountGUI.setVisible(true);
        });
    }
}