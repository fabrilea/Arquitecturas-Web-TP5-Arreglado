package edu.tudai.microservicioadministrador.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-factura", url = "http://localhost:8004/api/factura")
public interface facturaClient {

    @GetMapping("/total-facturado")
    Double obtenerTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin
    );

    // define un cliente Feign para interactuar con un microservicio
    // llamado microservicio-factura. Este cliente se utiliza para realizar
    // solicitudes HTTP al servicio remoto de facturación y recuperar
    // el total facturado en un rango de tiempo específico.

}
