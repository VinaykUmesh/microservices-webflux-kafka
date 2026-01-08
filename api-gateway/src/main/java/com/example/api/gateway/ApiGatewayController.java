package com.example.api.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class ApiGatewayController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> testApiGateway() {
        Map<String, Object> map = Map.of("message", "api gateway is running.");
        return ResponseEntity.ok().body(map);
    }

}
