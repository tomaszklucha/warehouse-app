package pl.simple.warehouse.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.FluentIterable;

import pl.simple.warehouse.dtos.AddProductDTO;
import pl.simple.warehouse.dtos.AvailabilityDTO;
import pl.simple.warehouse.dtos.ProductDTO;
import pl.simple.warehouse.dtos.UpdateQuantityDTO;
import pl.simple.warehouse.exceptions.ProductAlreadyExistsException;
import pl.simple.warehouse.exceptions.ProductNotFoundException;
import pl.simple.warehouse.exceptions.QuantityBelowZeroException;
import pl.simple.warehouse.model.Product;
import pl.simple.warehouse.repos.ProductRepository;

@Service
public class WarehouseService {

    private final ProductRepository productRepository;

    public WarehouseService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<ProductDTO> getProducts() {
        return FluentIterable.from(productRepository.findAll())
            .transform(this::toProductDTO)
            .toList();
    }

    public ProductDTO getProductById(final long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return toProductDTO(product);
    }

    public ProductDTO getProductByName(final String name) {
        Product product = productRepository.findByName(name).orElseThrow(ProductNotFoundException::new);
        return toProductDTO(product);
    }

    @Transactional
    public ProductDTO addProduct(final AddProductDTO addProductDto) {
        Optional<Product> byName = productRepository.findByName(addProductDto.getName());

        if (byName.isPresent()) {
            throw new ProductAlreadyExistsException();
        }

        Product product = new Product();
        product.setName(addProductDto.getName());
        product.setPrice(addProductDto.getPrice());
        product.setQuantity(0);

        return toProductDTO(productRepository.save(product));
    }

    public Iterable<AvailabilityDTO> getWarehouseEntries() {
        return FluentIterable.from(productRepository.findAll())
            .transform(this::toAvailabilityDTO)
            .toList();
    }

    public AvailabilityDTO getWarehouseEntryForProduct(final long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return toAvailabilityDTO(product);
    }

    @Transactional
    public AvailabilityDTO updateQuantity(final UpdateQuantityDTO update) {
        Product product = productRepository.findById(update.getId()).orElseThrow(ProductNotFoundException::new);

        int newQuantity = product.getQuantity() + update.getQuantityChange();
        if (newQuantity < 0) {
            throw new QuantityBelowZeroException();
        }

        product.setQuantity(newQuantity);

        return toAvailabilityDTO(productRepository.save(product));
    }

    @Transactional
    public List<AvailabilityDTO> updateQuantities(final Collection<UpdateQuantityDTO> updates) {
        return updates.stream()
            .map(this::updateQuantity)
            .collect(Collectors.toList());
    }

    private ProductDTO toProductDTO(final Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getPrice());
    }

    private AvailabilityDTO toAvailabilityDTO(final Product p) {
        return new AvailabilityDTO(p.getId(), p.getName(), p.getQuantity());
    }

}
