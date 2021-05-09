package pl.simple.warehouse.dtos;

import java.util.Objects;

public class AvailabilityDTO {

    private final long id;
    private final String name;
    private final int quantity;

    public AvailabilityDTO(final long id, final String name, final int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "AvailabilityDTO [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
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
        AvailabilityDTO other = (AvailabilityDTO) obj;
        return id == other.id && Objects.equals(name, other.name) && quantity == other.quantity;
    }

}
