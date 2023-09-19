import com.azure.ai.openai.models.ChatMessage;
import com.ssc.tortoise.automate.ai.ChatTortoise;

public class ChatTest {
    public static void main(String [] args) throws InterruptedException {
//        ChatMessage msgAns = ChatTortoise.getSingleton().chat("What's the best way to train a parrot?");
        ChatMessage msgAns = ChatTortoise.getSingleton().chat("wait 3 second");
        System.out.println("Message: " + msgAns.getContent());
//        Thread.sleep(5000);
//
//        msgAns = ChatTortoise.getSingleton().chat("What's a parrot' favorite food?");
//        System.out.println("Message: " + msgAns.getContent());
    }
}
