package org.sikuli.ide;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import org.sikuli.basics.Debug;
import org.sikuli.basics.Settings;

import com.azure.ai.openai.models.ChatMessage;
import com.ssc.tortoise.automate.ai.ChatTortoise;

public class AIChatPanel extends JSplitPane implements Runnable {

	private static final String me = "AIChatPanel: ";
	//
	private EditorPane chatHistory = new EditorPane();
	private EditorPane chatInputBox = new EditorPane();
	private JPanel chatPanel = new JPanel();
	private JButton btnSend = new ButtonChatSend();
	private JButton btnAccept = new JButton("Accept");
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	
	public static String newline = System.getProperty("line.separator");

	public AIChatPanel() {
//		super(layout);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setTopComponent(chatHistory);
		this.setBottomComponent(buildChatComponent());
//		chatPanel.add(chatInputBox);
//		chatPanel.add(btnSend);
//		chatPanel.add(btnAccept);
//		chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		chatInputBox.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SikulixIDE.sikulixIDE.getActiveContext().pane = AIChatPanel.this.chatInputBox;
				System.out.println("focusGained");
				AIChatPanel.error("focusGained");
			}

			@Override
			public void focusLost(FocusEvent e) {
				// SikulixIDE.sikulixIDE.getActiveContext().pane =
				// SikulixIDE.sikulixIDE.getActiveContext().getTabs();
				// recover it
				System.out.println("focusLost");
				AIChatPanel.error("focusLost");
			}

		});
		
		chatHistory.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SikulixIDE.sikulixIDE.getActiveContext().pane = AIChatPanel.this.chatHistory;
				System.out.println("focusGained");
				AIChatPanel.error("focusGained");
			}

			@Override
			public void focusLost(FocusEvent e) {
				// SikulixIDE.sikulixIDE.getActiveContext().pane =
				// SikulixIDE.sikulixIDE.getActiveContext().getTabs();
				// recover it
				System.out.println("focusLost");
				AIChatPanel.error("focusLost");
			}

		});

	}
	
	JSplitPane buildChatComponent() {
		chatPanel.add(btnSend);
		chatPanel.add(btnAccept);
		return new JSplitPane(JSplitPane.VERTICAL_SPLIT, chatInputBox, chatPanel);
		
	}

	public static EditorPane getActiveEditPanel() {
		JScrollPane scrollPane = null;
		Component componentSP = SikulixIDE.sikulixIDE.getTabs().getSelectedComponent();
		if (componentSP instanceof JScrollPane) {
			scrollPane = (JScrollPane) componentSP;
		} else {
			error("Unable to find an active editor of JScrollPane");
			return null;
		}
		JViewport viewport = scrollPane.getViewport();
		EditorPane editorPane = null;
		Component componentV = viewport.getView();
		if (componentV instanceof EditorPane) {
			editorPane = (EditorPane) componentV;
		} else {
			error("Unable to find an active editor of EditorPane");
			return null;
		}
		return editorPane;
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
	
	class ButtonChatSend extends JButton implements ActionListener {

	    private Thread thread = null;

	    ButtonChatSend() {
	      super();

//	      URL imageURL = SikulixIDE.class.getResource("/icons/run_big_green.png");
//	      setIcon(new ImageIcon(imageURL));
	      initTooltip();
	      addActionListener(this);
	      setText("Send");
	      //setMaximumSize(new Dimension(45,45));
	    }

	    private void initTooltip() {
//	      PreferencesUser pref = PreferencesUser.get();
//	      String strHotkey = Key.convertKeyToText(
//	          pref.getStopHotkey(), pref.getStopHotkeyModifiers());
//	      String stopHint = _I("btnRunStopHint", strHotkey);
//	      setToolTipText(_I("btnRun", stopHint));
	    }

	    @Override
	    public void actionPerformed(ActionEvent ae) {
	    	runChat();
	    }
	    
	    void runChat() {
//	    	String currentChatMsg = ((EditorPane)chatTabs.getTabComponentAt(0)).getText();
	    	String currentChatMsg = chatInputBox.getText();
	    	
	    	ChatMessage msgAns = ChatTortoise.getSingleton().chat(currentChatMsg);
	    	String aiRes = "";
	    	if(msgAns != null && msgAns.getContent() != null) {
	    		aiRes = msgAns.getContent().replace("<code-line>", "").replace("</code-line>", "");
	    	}
	    	String currentChatConversactionMsg = chatHistory.getText();
	    	StringBuffer strBuffer = new StringBuffer();
	    	strBuffer.append(currentChatConversactionMsg)
	    	.append(newline)
	    	.append(currentChatMsg)
	    	.append(newline)
	    	.append(aiRes);
	    	chatInputBox.setText("");
	    	chatHistory.setText(strBuffer.toString());
	    	Debug.log(3, currentChatMsg);
	    }


//	    void doBeforeRun() {
//	      Settings.ActionLogs = prefs.getPrefMoreLogActions();
//	      Settings.DebugLogs = prefs.getPrefMoreLogDebug();
//	      Settings.InfoLogs = prefs.getPrefMoreLogInfo();
//	      Settings.Highlight = prefs.getPrefMoreHighlight();
//	    }
	}

}
