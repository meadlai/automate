package org.sikuli.ide;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import org.sikuli.basics.Debug;
import org.sikuli.script.ImagePath;

import com.azure.ai.openai.models.ChatMessage;
import com.ssc.tortoise.automate.ai.ChatTortoise;

public class AIChatPanel extends JSplitPane implements Runnable {

	private static final String me = "AIChatPanel: ";
	//
	private EditorPane chatHistory = new EditorPane();
	private EditorPane chatInputBox = new EditorPane();
	private JPanel chatPanel = new JPanel();
	private JButton btnSend = new ButtonChatSend();
	private JButton btnAccept = new ButtonAcceptAI();
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private static String aiRes = "";
	private static StringBuffer conversation = new StringBuffer();
	public static String newline = System.getProperty("line.separator");
	private static Map<String, String> imageNames = Collections.synchronizedMap(new HashMap<String, String>());
	String tmpStr = "";
	String currentChatMsg = "";
	public AIChatPanel() {
//		super(layout);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setTopComponent(chatHistory);
		this.setBottomComponent(buildChatComponent());
//		chatPanel.add(chatInputBox);
//		chatPanel.add(btnSend);
//		chatPanel.add(btnAccept);
//		chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		chatInputBox.setAutoscrolls(true);
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
		
		chatHistory.setContentType("text/html");
		String userIcon = SikulixIDE.class.getResource("/icons/chat/user_icon.png").getFile();
		String botIcon = SikulixIDE.class.getResource("/icons/chat/bot_icon.png").getFile();
//		conversation.append("<html><head><meta charset=\"utf-8\"/> <title>title</title> <body> Hello CHAT!! <h2>HTML Image Example</h2> <div style=\"text-align: left\"><img  align=\"left\" src=\"https://img0.baidu.com/it/u=1749989396,456916184&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500\" width=\"20\" height=\"20\"/> Hi </div><br><div style=\"text-align: right\"><img style=\"text-align: right\" align=\"right\" src=\"https://img.88icon.com/download/jpg/20200829/56a8d4bda0c3b22de3bfbffd5ff43adc_512_401.jpg%2188bg\" width=\"20\" height=\"20\"/>  Robot here   </div>");
		conversation.append(" <div style=\"text-align: right\"> Hi <img  align=\"left\" src=\"file://" + userIcon + "\"/> </div><br><div style=\"text-align: left\"><img style=\"text-align: right\" align=\"right\" src=\"file://" + botIcon + "\"/>  Robot here   </div>");
		/*
		 * chatHistory.setText(""" <html><head><meta charset="utf-8"/>
		 * <title>title</title> <body> Hello CHAT!! <h2>HTML Image Example</h2> <div
		 * style="text-align: left"><img align="left" src=
		 * "https://img0.baidu.com/it/u=1749989396,456916184&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500"
		 * width="20" height="20"/> Hi </div> <br> <div style="text-align: right"><img
		 * style="text-align: right" align="right" src=
		 * "https://img.88icon.com/download/jpg/20200829/56a8d4bda0c3b22de3bfbffd5ff43adc_512_401.jpg%2188bg"
		 * width="20" height="20"/> Robot here </div> </body> </html> """ );
		 */
//		chatHistory.setText(conversation.toString() + "</body></html>");
		chatHistory.setText(conversation.toString());
	}

	JSplitPane buildChatComponent() {
//		chatPanel.add(chatInputBox, BorderLayout.NORTH);
		chatPanel.add(btnSend);
		chatPanel.add(btnAccept);
		return new JSplitPane(JSplitPane.VERTICAL_SPLIT, chatInputBox, chatPanel);
	}

	
	private JButton initUserIcon() {
		JButton btnUserIcon = new JButton();
		 URL imageURL = SikulixIDE.class.getResource("/icons/chat/user_icon.png");
		 btnUserIcon.setIcon(new ImageIcon(imageURL));
		 btnUserIcon.setText("");
		return  btnUserIcon;
	};
	
	public String getChatInputString() {
		//Adding user icon as component
//		chatInputBox.add(initUserIcon());
		
		Document doc = this.chatInputBox.getDocument();
		int count = doc.getDefaultRootElement().getElementCount();
		StringBuilder sb = new StringBuilder();
//		sb.append();
//		sb.append("User Input: ");
		for(int i = 0; i<count ; i++) {
			Element item = doc.getDefaultRootElement().getElement(i);
			String result = getLineTextFrom(item);
			sb.append(result);
		}
		return sb.toString();
	}
	
	public String getChatHistoryString() {
		Document doc = this.chatHistory.getDocument();
		int count = doc.getDefaultRootElement().getElementCount();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<count ; i++) {
			Element item = doc.getDefaultRootElement().getElement(i);
			String result = getLineTextFrom(item);
			sb.append(result);
		}
		return sb.toString();
	}
	
	private String getLineTextFrom(Element elem) {
		Document doc = elem.getDocument();
		Element subElem;
		String text;
		String line = "";
		for (int i = 0; i < elem.getElementCount(); i++) {
			text = "";
			subElem = elem.getElement(i);
			int start = subElem.getStartOffset();
			int end = subElem.getEndOffset();
			if (subElem.getName().contains("component")) {
				text = StyleConstants.getComponent(subElem.getAttributes()).toString();
			} else {
				try {
					text = doc.getText(start, end - start);
				} catch (Exception ex) {
				}
			}
			line += text;
		}
		return line.trim();
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
			setText("Send to AI Chat");
			// setMaximumSize(new Dimension(45,45));
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
			fileWalk();
//	    	String currentChatMsg = ((EditorPane)chatTabs.getTabComponentAt(0)).getText();
			currentChatMsg = getChatInputString();
			//

			error("Input is: " + currentChatMsg);
			ChatMessage msgAns = ChatTortoise.getSingleton().chat(currentChatMsg);

//			error("Output is: " + msgAns.getContent());
			if (msgAns != null && msgAns.getContent() != null) {
				aiRes =  msgAns.getContent().replace("<code-line>", "").replace("</code-line>", "");
			}

//			String currentChatConversactionMsg = getChatHistoryString();
			
//			StringBuffer strBuffer = new StringBuffer();
//			strBuffer.append(currentChatConversactionMsg);
//			if(currentChatConversactionMsg !=null && !currentChatConversactionMsg.isEmpty()) {
//				strBuffer.append(newline);
//			}
//			aiRes = "doubleClick(\"chrome\")\r\n"
//					+ "wait(1)";
			String userIcon = SikulixIDE.class.getResource("/icons/chat/user_icon.png").getFile();
			String botIcon = SikulixIDE.class.getResource("/icons/chat/bot_icon.png").getFile();
			
			
			imageNames.forEach((k,v) -> {tmpStr = aiRes.replace(k, "<img align=\"left\" src=\"file:///" + v +"\"  />");
				currentChatMsg = currentChatMsg.replace(k, "<img align=\"right\" src=\"file:///" + v +"\"  />").replace("\"", "");
			});
			conversation.append("<div style=\"text-align: right\">")
			.append(currentChatMsg)
			.append("<img align=\"left\" src=\"file://" + userIcon +"\"  />")
			.append("</div><br/>")
			.append("<div style=\"text-align: left\"><img style=\"text-align: right\" align=\"right\" src=\"file://" + botIcon +"\" />")
//			.append(newline).
			;
			
			
			conversation.append(tmpStr.replace("\n", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"))
			.append("</div>");
			chatInputBox.setText("");
//			chatHistory.setText(strBuffer.toString());
//			chatHistory.setContentType("text/html");
			chatHistory.setText(conversation.toString() + "</body></html>");
			
			Debug.log(3, currentChatMsg);
		}

//	    void doBeforeRun() {
//	      Settings.ActionLogs = prefs.getPrefMoreLogActions();
//	      Settings.DebugLogs = prefs.getPrefMoreLogDebug();
//	      Settings.InfoLogs = prefs.getPrefMoreLogInfo();
//	      Settings.Highlight = prefs.getPrefMoreHighlight();
//	    }
	}
	void fileWalk() {
		var dirName = 	new ImagePath().getBundlePath();;

	    try (Stream<Path> paths = Files.walk(Paths.get(dirName), 2)) {
	        paths.filter(Files::isRegularFile)
	                .forEach((f) -> {imageNames.put(f.getFileName().toString().replace(".png", ""), f.toAbsolutePath().toString());}
//	                		System.out::println
	                		);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	class ButtonAcceptAI extends JButton implements ActionListener {

		private Thread thread = null;

		ButtonAcceptAI() {
			super();

//	      URL imageURL = SikulixIDE.class.getResource("/icons/run_big_green.png");
//	      setIcon(new ImageIcon(imageURL));
			initTooltip();
			addActionListener(this);
			setText("Accept AI command");
			// setMaximumSize(new Dimension(45,45));
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
			acceptChatResult();
		}

		void acceptChatResult() {
//	    	String currentChatMsg = ((EditorPane)chatTabs.getTabComponentAt(0)).getText();
			/*
			 * String currentChatMsg = getChatInputString(); //
			 * 
			 * error("Input is: " + currentChatMsg); ChatMessage msgAns =
			 * ChatTortoise.getSingleton().chat(currentChatMsg);
			 * 
			 * error("Output is: " + msgAns.getContent()); if (msgAns != null &&
			 * msgAns.getContent() != null) { aiRes =
			 * msgAns.getContent().replace("<code-line>", "").replace("</code-line>", ""); }
			 * 
			 * String currentChatConversactionMsg = chatHistory.getText(); StringBuffer
			 * strBuffer = new StringBuffer();
			 * strBuffer.append(currentChatConversactionMsg).append(newline).append(
			 * currentChatMsg).append(newline) .append(aiRes); chatInputBox.setText("");
			 * chatHistory.setText(strBuffer.toString()); Debug.log(3, currentChatMsg);
			 */
			String history = getActiveEditPanel().getText();
			history = history + newline + aiRes;
			getActiveEditPanel().setText(history);
			SikulixIDE.sikulixIDE.getActiveContext().pane = AIChatPanel.getActiveEditPanel();
			getActiveEditPanel().context.reparse();
		}

//	    void doBeforeRun() {
//	      Settings.ActionLogs = prefs.getPrefMoreLogActions();
//	      Settings.DebugLogs = prefs.getPrefMoreLogDebug();
//	      Settings.InfoLogs = prefs.getPrefMoreLogInfo();
//	      Settings.Highlight = prefs.getPrefMoreHighlight();
//	    }
	}

}
