package pl.simple.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product already exists")
public class ProductAlreadyExistsException extends RuntimeException {
}
