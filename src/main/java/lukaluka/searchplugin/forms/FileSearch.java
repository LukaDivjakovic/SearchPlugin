package lukaluka.searchplugin.forms;

import javax.swing.*;
import java.awt.*;

public class FileSearch {
    private JPanel contentPane;
    private JTextField directoryPathField;
    private JTextField stringToSearchField;
    private JButton startSearchButton;
    private JButton cancelSearchButton;

    public FileSearch() {
        buildUI();
    }

    private void buildUI() {
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setPreferredSize(new Dimension(350, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
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
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
