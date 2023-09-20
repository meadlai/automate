package com.ssc.tortoise.automate.ai;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
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
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "You are Tortoise Automate, a helpful assistant. You will talk like an assistant."));
        chatMessages.add(new ChatMessage(ChatRole.USER, "Please help us to translate my statement into Python script. Our script has following functions:\n\n\tdoubleClick\n\trightClick\n\tclick\n\twait\n\nHere is an example of full script:\n\n\tdoubleClick(\"1695042724219.png\")\n\trightClick(\"1695043265396.png\")\n\tclick(\"1695023223962.png\")\n\twait(1)\n\ttype(\"word\")\n\tclick(\"1695016905325.png\")\n\nIf I tell you: I want to click the '10548.png', you should output \n<code-line>click('10548.png')</code-line>\n<code-line>wait(1)</code-line>\n\nIf I tell you: double click '65396.png' or open '65396.png', the double click means open, please response with\n<code-line>doubleClick('65396.png')</code-line>\n<code-line>wait(1)</code-line>\n\nIf I tell you: input or type 'word', you should print\n<code-line>type(\"word\")</code-line>\n<code-line>wait(1)</code-line>\n\nIf I tell you: write 'Excel', you should print\n<code-line>type(\"Excel\")</code-line>\n<code-line>wait(1)</code-line>\n\nIf I tell you: open the context menu \"16956.png\", you should return\n<code-line>rightClick(‘16956.png’)</code-line>\n<code-line>wait(1)</code-line>\n\nIf I tell you: wait a second, you should return\n<code-line>wait(1)</code-line>\n\nIf I tell you: wait 3 second, you should return\n<code-line>wait(3)</code-line>\n\nPlease don't replace anything in the quotation."));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Certainly! Here's a Python script that implements the desired functionality:\n\n```python\ndef doubleClick(image):\n    print(f'doubleClick(\"{image}\")')\n    print('wait(1)')\n\ndef rightClick(image):\n    print(f'rightClick(\"{image}\")')\n    print('wait(1)')\n\ndef click(image):\n    print(f'click(\"{image}\")')\n    print('wait(1)')\n\ndef wait(seconds):\n    print(f'wait({seconds})')\n\ndef type(text):\n    print(f'type(\"{text}\")')\n    print('wait(1)')\n\ndef translate_statement(statement):\n    if statement.startswith('I want to click'):\n        image = statement.split(\"'\")[1]\n        print(f'click(\"{image}\")')\n        print('wait(1)')\n    elif 'double click' in statement or 'open' in statement:\n        image = statement.split(\"'\")[1]\n        print(f'doubleClick(\"{image}\")')\n        print('wait(1)')\n    elif statement.startswith('input') or statement.startswith('type'):\n        text = statement.split(\"'\")[1]\n        print(f'type(\"{text}\")')\n        print('wait(1)')\n    elif statement.startswith('write'):\n        text = statement.split(\"'\")[1]\n        print(f'type(\"{text}\")')\n        print('wait(1)')\n    elif statement.startswith('open the context menu'):\n        image = statement.split(\"'\")[1]\n        print(f'rightClick(\"{image}\")')\n        print('wait(1)')\n    elif statement.startswith('wait'):\n        seconds = statement.split('wait')[-1].strip().split()[0]\n        print(f'wait({seconds})')\n\n# Example usage\ntranslate_statement(\"I want to click '10548.png'\")\ntranslate_statement(\"double click '65396.png'\")\ntranslate_statement(\"open '65396.png'\")\ntranslate_statement(\"input 'word'\")\ntranslate_statement(\"write 'Excel'\")\ntranslate_statement(\"open the context menu '16956.png'\")\ntranslate_statement(\"wait a second\")\ntranslate_statement(\"wait 3 second\")\n```\n\nThis script defines the functions `doubleClick`, `rightClick`, `click`, `wait`, and `type` to print the desired function calls and waits. The `translate_statement` function takes a statement as input and translates it into the corresponding function calls and waits. The example usage at the end demonstrates how to use the `translate_statement` function with different statements."));
        chatMessages.add(new ChatMessage(ChatRole.USER, "open '65396.png'"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "doubleClick(\"65396.png\")\nwait(1)"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "right click the 'mead.png'"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "rightClick(\"mead.png\")\nwait(1)"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "i want to open Word"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "doubleClick(\"Word\")\nwait(1)"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "open chrome"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "doubleClick(\"chrome\")\nwait(1)"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "open chrome pls"));


//        String trainMsg = ReadFromFile.readFileByLines(chatTortoise.getClass().getClassLoader().getResource("").getPath() + "trainning.md");
//        System.out.println(trainMsg);
//        chatMessages.add(new ChatMessage(ChatRole.USER, trainMsg));

//        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "You are Tortoise Automate, a helpful assistant. You will talk like an assistant."));
//        chatMessages.add(new ChatMessage(ChatRole.USER, "Can you help me?"));
//        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "Of course, my pleasure! What can I do for you?"));

        ChatCompletions chatCompletions = client.getChatCompletions(TORTOISE_AI_MODEL, new ChatCompletionsOptions(chatMessages));
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }
        System.out.println("Train is done.");
    }

    public ChatMessage chat(String msg) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.USER, msg));

        ChatCompletions chatCompletions = client.getChatCompletions(TORTOISE_AI_MODEL, new ChatCompletionsOptions(chatMessages));
        return chatCompletions.getChoices().get(0).getMessage();
    }
}