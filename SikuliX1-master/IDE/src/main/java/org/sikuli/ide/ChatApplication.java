package org.sikuli.ide;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;  
  
public class ChatApplication extends JFrame {  
  
    public ChatApplication() {  
        setTitle("Chat Application");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setPreferredSize(new Dimension(400, 300));  
          
        JPanel panel = new JPanel(new BorderLayout());  
          
        JTextArea textArea = new JTextArea();  
        panel.add(textArea, BorderLayout.NORTH);  
          
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  
        JButton button1 = new JButton("Button 1");  
        JButton button2 = new JButton("Button 2");  
        buttonPanel.add(button1);  
        buttonPanel.add(button2);  
        panel.add(buttonPanel, BorderLayout.SOUTH);  
          
        add(panel);  
        pack();  
        setLocationRelativeTo(null);  
    }  
  
    public static void main(String[] args) {  
        SwingUtilities.invokeLater(() -> {  
            new ChatApplication().setVisible(true);  
        });  
    }  
}  