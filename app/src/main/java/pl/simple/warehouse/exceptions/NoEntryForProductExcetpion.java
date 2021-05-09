package pl.simple.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No product in warehouse")
public class NoEntryForProductExcetpion extends RuntimeException {

}
