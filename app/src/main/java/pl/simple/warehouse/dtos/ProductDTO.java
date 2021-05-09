package pl.simple.warehouse.dtos;

import java.util.Objects;

public class ProductDTO {

    private final long id;
    private final String name;
    private final double price;

    public ProductDTO(final long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProductDTO other = (ProductDTO) obj;
        return id == other.id && Objects.equals(name, other.name)
            && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
    }

}
