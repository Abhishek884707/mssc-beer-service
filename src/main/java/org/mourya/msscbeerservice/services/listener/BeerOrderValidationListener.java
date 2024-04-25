package org.mourya.msscbeerservice.services.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mourya.brewery.model.events.ValidateOrderRequest;
import org.mourya.brewery.model.events.ValidateOrderResult;
import org.mourya.msscbeerservice.config.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final BeerOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void  listener(ValidateOrderRequest validateOrderRequest){
        Boolean isValid = validator.validateOrder(validateOrderRequest.getBeerOrderDto());
        log.info("Validation Result for Order Id " + validateOrderRequest.getBeerOrderDto().getId() + " : " + isValid);
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                .orderId(validateOrderRequest.getBeerOrderDto().getId())
                .isValid(isValid)
                .build());
    }

}
