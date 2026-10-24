package com.yourname.validator.config;

import com.yourname.validator.graph.node.EventNode;
import com.yourname.validator.graph.node.FeeTypeNode;
import com.yourname.validator.graph.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.graph.enabled", havingValue = "true")
public class GraphDataInitializer {
    @Bean
    CommandLineRunner initGraph(EventRepository eventRepository) {
        return args -> {
            if (eventRepository.count() > 0) return;

            FeeTypeNode item = createFeeNode("ITEM_PRICE", "商品价格");
            FeeTypeNode ship = createFeeNode("SHIPPING_FEE", "运费");
            FeeTypeNode late = createFeeNode("LATE_FEE", "滞纳金");

            EventNode payment = new EventNode();
            payment.setCode("ORDER_PAYMENT");
            payment.setName("订单支付");
            payment.setAllowedFeeTypes(Set.of(item, ship));

            EventNode lateFeeEvent = new EventNode();
            lateFeeEvent.setCode("LATE_FEE_CHARGE");
            lateFeeEvent.setName("滞纳金扣款");
            lateFeeEvent.setAllowedFeeTypes(Set.of(late));

            eventRepository.save(payment);
            eventRepository.save(lateFeeEvent);
        };
    }

    private FeeTypeNode createFeeNode(String code, String desc) {
        FeeTypeNode node = new FeeTypeNode();
        node.setCode(code);
        node.setDesc(desc);
        return node;
    }
}
