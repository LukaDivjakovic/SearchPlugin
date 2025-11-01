package lukaluka.searchplugin.forms;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class FileSearch {
    private JPanel contentPane;
    private JTextField directoryPathField;
    private JTextField stringToSearchField;
    private JButton startSearchButton;
    private JButton cancelSearchButton;
    private JTextArea resultsArea;

    public FileSearch() {
        buildUI();
    }

    private void buildUI() {
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setPreferredSize(new Dimension(500, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

        // Row 0: Label "Directory path"
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0;
        contentPane.add(new JLabel("Directory path"), gbc);

        // Row 0: Text field spans remaining columns
        directoryPathField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3; gbc.weightx = 1.0;
        contentPane.add(directoryPathField, gbc);

        // Row 1: Label "String to search"
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0;
        contentPane.add(new JLabel("String to search"), gbc);

        // Row 1: Text field spans remaining columns
        stringToSearchField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        contentPane.add(stringToSearchField, gbc);

        // Row 2: Start button at column 1
        startSearchButton = new JButton("Start");
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        contentPane.add(startSearchButton, gbc);

        // Row 2: Cancel button at column 3
        cancelSearchButton = new JButton("Cancel");
        gbc.gridx = 3; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        contentPane.add(cancelSearchButton, gbc);

        // Row 3: Results area spanning all columns
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JBScrollPane(resultsArea);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4; gbc.weightx = 1.0; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(scrollPane, gbc);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getStartSearchButton() {
        return startSearchButton;
    }

    public JButton getCancelSearchButton() {
        return cancelSearchButton;
    }

    public String getDirectoryPath() {
        return directoryPathField.getText();
    }

    public String getStringToSearch() {
        return stringToSearchField.getText();
    }

    public void clearResults() {
        resultsArea.setText("");
    }

    public void appendResultLine(String line) {
        resultsArea.append(line + "\n");
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }
}
