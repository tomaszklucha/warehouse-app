package pl.simple.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Quantity of product can not be below zero")
public class QuantityBelowZeroException extends RuntimeException {

}
