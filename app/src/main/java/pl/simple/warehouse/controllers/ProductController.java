package pl.simple.warehouse.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.simple.warehouse.dtos.AddProductDTO;
import pl.simple.warehouse.dtos.ProductDTO;
import pl.simple.warehouse.services.WarehouseService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final WarehouseService service;

    public ProductController(final WarehouseService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<ProductDTO> getAllProducts() {
        return service.getProducts();
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody final AddProductDTO productDto) {
        return service.addProduct(productDto);
    }

    @GetMapping(params = "id")
    public ProductDTO getProductById(@RequestParam("id") final long id) {
        return service.getProductById(id);
    }

    @GetMapping(params = "name")
    public ProductDTO getProductByName(@RequestParam("name") final String name) {
        return service.getProductByName(name);
    }
}
