package org.Arquitech.Gymrat.clientservice.Client.resource.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private String cvc;
}