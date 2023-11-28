package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class DialogueAuthenticator extends Authenticator {
    private JDialog passwordDialogue;
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");
    private JLabel mainLabel = new JLabel("Please Enter your username and password: ");

    public DialogueAuthenticator(){
        this("", new JFrame());
    }

    public DialogueAuthenticator(String username){
        this(username, new JFrame());
    }

    public DialogueAuthenticator(JFrame parent){
        this("", parent);
    }

    public DialogueAuthenticator(String username, JFrame parent){
        this.passwordDialogue = new JDialog(parent, true);
        Container pane = passwordDialogue.getContentPane();
        pane.setLayout(new GridLayout(4,1));

        JLabel userLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        pane.add(mainLabel);
        JPanel p2 = new JPanel();
        p2.add(userLabel);
        p2.add(usernameField);
        usernameField.setText(username);
        pane.add(p2);
        JPanel p4 = new JPanel();
        p4.add(okButton);
        p4.add(cancelButton);
        pane.add(p4);
        passwordDialogue.pack();
        ActionListener al = new OKResponse();
        okButton.addActionListener(al);
        usernameField.addActionListener(al);
        passwordField.addActionListener(al);
        cancelButton.addActionListener(new CancelResponse());
    }

    private void show(){
        String prompt = this.getRequestingPrompt();
        if(prompt==null){
            String site = this.getRequestingSite().getHostName();
            String protocol = this.getRequestingProtocol();
            int port = this.getRequestingPort();
            if(site!=null & protocol != null){
                prompt = protocol + "://" + site;
                if(port>0) prompt += ":" + port;
            } else {
                prompt = "";
            }
        }
        mainLabel.setText("Please enter credentials for " + prompt + ": ");
        passwordDialogue.pack();
        passwordDialogue.setVisible(true);
    }

    PasswordAuthentication response = null;

    class OKResponse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            passwordDialogue.setVisible(true);
            char[] password = passwordField.getPassword();
            String username = usernameField.getText();
            passwordField.setText("");
            response = new PasswordAuthentication(username,password);

        }
    }

    class CancelResponse implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            passwordDialogue.setVisible(false);
            passwordField.setText("");
            response = null;
        }
    }

    public PasswordAuthentication getPasswordAuthentication(){
        this.show();
        return this.response;
    }

}
