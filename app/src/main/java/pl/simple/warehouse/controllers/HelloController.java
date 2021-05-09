package pl.simple.warehouse.controllers;

import org.springframework.web.bind.annotation.*;
import pl.simple.warehouse.dtos.AddProductDTO;
import pl.simple.warehouse.dtos.ProductDTO;
import pl.simple.warehouse.services.WarehouseService;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello from service with new version";
    }
}
