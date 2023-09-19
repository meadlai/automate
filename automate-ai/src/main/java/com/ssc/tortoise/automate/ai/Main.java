package com.ssc.tortoise.automate.ai;
import java.util.ArrayList;
import java.util.List;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.core.credential.AzureKeyCredential;

public class Main {
	
	private static String TORTOISE_AI_ENDPOINT = "https://tortoise-openai-001.openai.azure.com/";
	private static String TORTOISE_AI_TOKEN = "c0cf2718ddcb48f6a5bb8a1faf081a61";
	private static String TORTOISE_AI_MODEL = "tortoise-chat16k";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String endpoint = System.getProperty("TORTOISE_AI_ENDPOINT", TORTOISE_AI_ENDPOINT);
		String token = System.getProperty("TORTOISE_AI_TOKEN", TORTOISE_AI_TOKEN);
		String model = System.getProperty("TORTOISE_AI_MODEL", TORTOISE_AI_MODEL);

		
		OpenAIClient client = new OpenAIClientBuilder()
			    .credential(new AzureKeyCredential(token))
			    .endpoint(endpoint)
			    .buildClient();
		
		List<ChatMessage> chatMessages = new ArrayList<>();
		chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "You are a helpful assistant. You will talk like a pirate."));
		chatMessages.add(new ChatMessage(ChatRole.USER, "Can you help me?"));
		chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Of course, me hearty! What can I do for ye?"));
		chatMessages.add(new ChatMessage(ChatRole.USER, "What's the best way to train a parrot?"));

		ChatCompletions chatCompletions = client.getChatCompletions(model,
		    new ChatCompletionsOptions(chatMessages));

		System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
		for (ChatChoice choice : chatCompletions.getChoices()) {
		    ChatMessage message = choice.getMessage();
		    System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
		    System.out.println("Message:");
		    System.out.println(message.getContent());
		}
	}

}
