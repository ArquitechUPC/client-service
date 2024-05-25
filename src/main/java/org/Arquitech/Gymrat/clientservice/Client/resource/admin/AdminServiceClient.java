package org.Arquitech.Gymrat.clientservice.Client.resource.admin;

import org.Arquitech.Gymrat.clientservice.Client.resource.invoice.InvoiceResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "admin-service", url = "http://localhost:8082")
public interface AdminServiceClient {

    @GetMapping("/api/v1/admin/invoices/client/{clientId}")
    List<InvoiceResource> getInvoicesByClientId(@PathVariable Integer clientId);
}

