package pl.simple.warehouse.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import pl.simple.warehouse.dtos.AddProductDTO;
import pl.simple.warehouse.dtos.AvailabilityDTO;
import pl.simple.warehouse.dtos.ProductDTO;
import pl.simple.warehouse.dtos.UpdateQuantityDTO;
import pl.simple.warehouse.exceptions.ProductAlreadyExistsException;
import pl.simple.warehouse.exceptions.ProductNotFoundException;
import pl.simple.warehouse.exceptions.QuantityBelowZeroException;
import pl.simple.warehouse.model.Product;
import pl.simple.warehouse.repos.ProductRepository;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    ProductRepository repository;

    @InjectMocks
    WarehouseService service;

    @Test
    @DisplayName("Should return empty product list")
    void testGetProducts1() {
        // when
        Iterable<ProductDTO> products = service.getProducts();

        // then
        Assertions.assertThat(products).hasSize(0);
    }

    @Test
    @DisplayName("Should return product list with size 1")
    void testGetProducts2() {
        // given
        List<Product> prods = Lists.newArrayList(
            new Product(1L, "prod", 5.0, 1));
        Mockito.when(repository.findAll()).thenReturn(prods);

        // when
        Iterable<ProductDTO> products = service.getProducts();

        // then
        Assertions.assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException")
    void testGetProductById1() {
        // given
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> service.getProductById(0))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should return product using id")
    void testGetProductById2() {
        // given
        Product p = new Product(1L, "prod", 5.0, 1);
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(p));

        // when
        ProductDTO productDTO = service.getProductById(0);
        // then
        Assertions.assertThat(productDTO).isNotNull();
        Assertions.assertThat(productDTO.getId()).isEqualTo(1L);
        Assertions.assertThat(productDTO.getName()).isEqualTo("prod");
        Assertions.assertThat(productDTO.getPrice()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException")
    void testGetProductByName1() {
        // given
        Mockito.when(repository.findByName(ArgumentMatchers.any())).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> service.getProductByName("prod"))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should return product using name")
    void testGetProductByName2() {
        // given
        Product p = new Product(1L, "prod", 5.0, 1);
        Mockito.when(repository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(p));

        // when
        ProductDTO productDto = service.getProductByName("prod");
        // then
        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto.getId()).isEqualTo(1L);
        Assertions.assertThat(productDto.getName()).isEqualTo("prod");
        Assertions.assertThat(productDto.getPrice()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Should throw ProductAlreadyExistsException")
    void testAddProduct1() {
        // given
        Mockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.of(new Product()));

        // when
        // then
        AddProductDTO addProductDto = new AddProductDTO("prod", 5.0);
        Assertions.assertThatThrownBy(() -> service.addProduct(addProductDto))
            .isInstanceOf(ProductAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Should throw add new product")
    void testAddProduct2() {
        // given
        Mockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(new Product(1L, "prod", 5.0, 0));

        // when
        AddProductDTO addProductDto = new AddProductDTO("prod", 5.0);
        ProductDTO productDto = service.addProduct(addProductDto);

        // then
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());

        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto.getId()).isEqualTo(1L);
        Assertions.assertThat(productDto.getName()).isEqualTo("prod");
        Assertions.assertThat(productDto.getPrice()).isEqualTo(5.0);

    }

    @Test
    @DisplayName("Should return empty warehouse entries list")
    void testGetWarehouseEntries1() {
        // when
        Iterable<AvailabilityDTO> entries = service.getWarehouseEntries();

        // then
        Assertions.assertThat(entries).hasSize(0);
    }

    @Test
    @DisplayName("Should return warehouse entries list with size 1")
    void testGetWarehouseEntries2() {
        // given
        List<Product> prods = Lists.newArrayList(
            new Product(1L, "prod", 5.0, 1));
        Mockito.when(repository.findAll()).thenReturn(prods);

        // when
        Iterable<AvailabilityDTO> entries = service.getWarehouseEntries();

        // then
        Assertions.assertThat(entries).hasSize(1);
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException")
    void testGetEntryForProduct1() {
        // given
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> service.getWarehouseEntryForProduct(0))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should return warehouse entry using product id")
    void testGetEntryForProduct2() {
        // given
        Product p = new Product(1L, "prod", 5.0, 1);
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(p));

        // when
        AvailabilityDTO availabilityDTO = service.getWarehouseEntryForProduct(0);
        // then
        Assertions.assertThat(availabilityDTO).isNotNull();
        Assertions.assertThat(availabilityDTO.getId()).isEqualTo(1L);
        Assertions.assertThat(availabilityDTO.getName()).isEqualTo("prod");
        Assertions.assertThat(availabilityDTO.getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException")
    void testUpdateQuantities1() {
        // given
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        // when
        // then
        Collection<UpdateQuantityDTO> updates = Lists.newArrayList(
            new UpdateQuantityDTO(0, "prod", 2));
        Assertions.assertThatThrownBy(() -> service.updateQuantities(updates))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should update quantity for product")
    void testUpdateQuantities2() {
        // given
        Product p = new Product(1L, "prod", 5.0, 1);
        Product p2 = new Product(1L, "prod", 5.0, 3);
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(p));
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(p2);

        // when
        // then
        Collection<UpdateQuantityDTO> updates = Lists.newArrayList(
            new UpdateQuantityDTO(0, "prod", 2));

        List<AvailabilityDTO> updatedQuantities = service.updateQuantities(updates);

        Mockito.verify(repository).save(p2);

        assertThat(updatedQuantities).hasSize(1);

        AvailabilityDTO availabilityDTO = Iterables.getOnlyElement(updatedQuantities);

        Assertions.assertThat(availabilityDTO).isNotNull();
        Assertions.assertThat(availabilityDTO.getId()).isEqualTo(1L);
        Assertions.assertThat(availabilityDTO.getName()).isEqualTo("prod");
        Assertions.assertThat(availabilityDTO.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should throw QuantityBelowZeroException")
    void testUpdateQuantities3() {
        // given
        Product p = new Product(1L, "prod", 5.0, 1);
        Mockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(p));

        // when
        // then
        Collection<UpdateQuantityDTO> updates = Lists.newArrayList(
            new UpdateQuantityDTO(0, "prod", -2));

        Assertions.assertThatThrownBy(() -> service.updateQuantities(updates))
            .isInstanceOf(QuantityBelowZeroException.class);
    }

}
