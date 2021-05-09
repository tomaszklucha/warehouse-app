package pl.simple.warehouse.dtos;

import java.util.Objects;

public class AddProductDTO {

    private final String name;
    private final double price;

    public AddProductDTO(final String name, final double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductDTO [name=" + name + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
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
        AddProductDTO other = (AddProductDTO) obj;
        return Objects.equals(name, other.name)
            && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
    }

}
