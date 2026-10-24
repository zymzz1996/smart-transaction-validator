package com.yourname.validator.agent;

import com.yourname.validator.model.OrderTransaction;
import com.yourname.validator.tool.AccountingRuleTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class RetrievalAgent {
    private final ChatClient chatClient;
    private final AccountingRuleTools tools;

    public RetrievalAgent(ChatClient.Builder builder, AccountingRuleTools tools) {
        this.chatClient = builder.build();
        this.tools = tools;
    }

    public String retrieve(OrderTransaction tx) {
        String prompt = """
            You are a rule retrieval assistant. Based on the transaction info below, 
            decide which tools to call to fetch validation rules.
            
            Event: %s
            FeeType: %s
            Status: %s
            
            Summarize the retrieved rules in natural language.
            """.formatted(tx.getEvent(), tx.getFeeType(), tx.getOrderStatus());

        return chatClient.prompt().user(prompt).tools(tools).call().content();
    }
}
