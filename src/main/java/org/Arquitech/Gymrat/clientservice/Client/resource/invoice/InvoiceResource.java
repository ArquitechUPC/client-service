package org.Arquitech.Gymrat.clientservice.Client.resource.invoice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InvoiceResource {
    private Long id;
    private String clientName;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentStatus;
    private String stripeTransactionId;
    private String email;
    private LocalDateTime sentDate;
    private String invoiceStatus;
}
