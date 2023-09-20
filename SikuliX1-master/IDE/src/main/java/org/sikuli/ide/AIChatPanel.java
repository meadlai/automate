package org.sikuli.ide;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.sikuli.basics.Debug;

public class AIChatPanel extends JSplitPane implements Runnable {

	private static final String me = "AIChatPanel: ";
	private EditorPane chatHistory = new EditorPane();
	private EditorPane chatInputBox = new EditorPane();
	private JPanel chatPanel = new JPanel();
	private JButton btnSend = new JButton("Send");
	private JButton btnAccept = new JButton("Accept");
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();

	public AIChatPanel() {
//		super(layout);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setTopComponent(chatHistory);
		this.setBottomComponent(chatPanel);
		chatPanel.add(chatInputBox);
		chatPanel.add(btnSend);
		chatPanel.add(btnAccept);
		chatInputBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SikulixIDE.sikulixIDE.getActiveContext().pane = AIChatPanel.this.chatInputBox;
				System.out.println("focusGained");
				AIChatPanel.error("focusGained");
			}

			@Override
			public void focusLost(FocusEvent e) {
				SikulixIDE.sikulixIDE.getTabs();
				SikulixIDE.sikulixIDE.getActiveContext().pane = SikulixIDE.sikulixIDE.getActiveContext().pane;
				System.out.println("focusLost");
				AIChatPanel.error("focusLost");
			}
			
		});
	}

	@Override
	public void run() {

	}
	
	private static void error(String message, Object... args) {
		Debug.logx(-1, me + message, args);
	}

	private static void fatal(String message, Object... args) {
		Debug.logx(-1, me + "FATAL: " + message, args);
	}

}
