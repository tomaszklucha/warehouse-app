package pl.simple.warehouse.dtos;

import java.util.Objects;

public class UpdateQuantityDTO {

    private final long id;
    private final int quantityChange;

    public UpdateQuantityDTO(final long id, final String name, final int quantityChange) {
        this.id = id;
        this.quantityChange = quantityChange;
    }

    public long getId() {
        return id;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    @Override
    public String toString() {
        return "AvailabilityDTO [id=" + id + ", quantityChange=" + quantityChange + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantityChange);
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
        UpdateQuantityDTO other = (UpdateQuantityDTO) obj;
        return id == other.id && quantityChange == other.quantityChange;
    }

}
