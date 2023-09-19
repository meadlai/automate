package com.ssc.tortoise.automate.ai;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.core.credential.AzureKeyCredential;

import java.util.ArrayList;
import java.util.List;

public class ChatTortoise {
    private static ChatTortoise chatTortoise;
    private static String TORTOISE_AI_ENDPOINT = "https://tortoise-openai-001.openai.azure.com/";
    private static String TORTOISE_AI_TOKEN = "c0cf2718ddcb48f6a5bb8a1faf081a61";
    private static String TORTOISE_AI_MODEL = "tortoise-chat16k";
    private static OpenAIClient client = null;

    private ChatTortoise() {

    }

    public static ChatTortoise getSingleton() {
        if (chatTortoise == null) {
            synchronized (ChatTortoise.class) {
                if (chatTortoise == null) {
                    chatTortoise = new ChatTortoise();
                    train();
                }
            }
        }
        return chatTortoise;
    }

    private static void train() {
        client = new OpenAIClientBuilder().endpoint(TORTOISE_AI_ENDPOINT).credential(new AzureKeyCredential(TORTOISE_AI_TOKEN)).buildClient();
        List<ChatMessage> chatMessages = new ArrayList<>();
        String trainMsg = ReadFromFile.readFileByLines(chatTortoise.getClass().getClassLoader().getResource("").getPath() + "trainning.md");
        System.out.println(trainMsg);
        chatMessages.add(new ChatMessage(ChatRole.USER, trainMsg));
//        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "You are Tortoise Automate, a helpful assistant. You will talk like an assistant."));
//        chatMessages.add(new ChatMessage(ChatRole.USER, "Can you help me?"));
//        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Of course, my pleasure! What can I do for you?"));

        ChatCompletions chatCompletions = client.getChatCompletions(TORTOISE_AI_MODEL, new ChatCompletionsOptions(chatMessages));
        System.out.println("Train is done.");
    }

    public ChatMessage chat(String msg) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.USER, msg));

        ChatCompletions chatCompletions = client.getChatCompletions(TORTOISE_AI_MODEL, new ChatCompletionsOptions(chatMessages));
        return chatCompletions.getChoices().get(0).getMessage();
    }
}