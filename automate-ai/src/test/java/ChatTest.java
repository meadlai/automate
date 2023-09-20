import com.azure.ai.openai.models.ChatMessage;
import com.ssc.tortoise.automate.ai.ChatTortoise;

public class ChatTest {
    public static void main(String [] args) throws InterruptedException {
//        ChatMessage msgAns = ChatTortoise.getSingleton().chat("What's the best way to train a parrot?");
        ChatMessage msgAns = ChatTortoise.getSingleton().chat("I want to click the '10548.png'");
        System.out.println("Message: " + msgAns.getContent());
        Thread.sleep(5000);
//
        msgAns = ChatTortoise.getSingleton().chat("open chrome");
        System.out.println("Message: " + msgAns.getContent());
    }
}
