package pl.simple.warehouse.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.simple.warehouse.dtos.AvailabilityDTO;
import pl.simple.warehouse.dtos.UpdateQuantityDTO;
import pl.simple.warehouse.services.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(final WarehouseService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<AvailabilityDTO> getWarehouseEntries() {
        return service.getWarehouseEntries();
    }

    @PostMapping
    public List<AvailabilityDTO> updateAvailabilities(@RequestBody final Collection<UpdateQuantityDTO> updates) {
        return service.updateQuantities(updates);
    }

    @GetMapping("/{id}")
    public AvailabilityDTO getAvailabilityByProductId(@PathVariable("id") final long id) {
        return service.getWarehouseEntryForProduct(id);
    }
}
