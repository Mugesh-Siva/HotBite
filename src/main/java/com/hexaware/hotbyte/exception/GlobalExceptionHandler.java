package com.hexaware.hotbyte.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundExp(UserNotFoundException ex) {
        log.error("UserNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>("User not found in the database", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(reason = "Role not found", code = HttpStatus.NOT_FOUND)
    public void handleRoleNotFoundExp() {
        log.error("RoleNotFoundException thrown");
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputExp(InvalidInputException ex) {
        log.error("InvalidInputException: {}", ex.getMessage());
        return new ResponseEntity<>("Invalid input provided", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceExp(DuplicateResourceException ex) {
        log.error("DuplicateResourceException: {}", ex.getMessage());
        return new ResponseEntity<>("Resource already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(reason = "Restaurant not found", code = HttpStatus.NOT_FOUND)
    public void handleRestaurantNotFoundExp() {
        log.error("RestaurantNotFoundException thrown");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(reason = "Order not found", code = HttpStatus.NOT_FOUND)
    public void handleOrderNotFoundExp() {
        log.error("OrderNotFoundException thrown");
    }

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(reason = "Cart not found", code = HttpStatus.NOT_FOUND)
    public void handleCartNotFoundExp() {
        log.error("CartNotFoundException thrown");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(reason = "Category not found", code = HttpStatus.NOT_FOUND)
    public void handleCategoryNotFoundExp() {
        log.error("CategoryNotFoundException thrown");
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseStatus(reason = "MenuItem not found", code = HttpStatus.NOT_FOUND)
    public void handleMenuItemNotFoundExp() {
        log.error("MenuItemNotFoundException thrown");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        return new ResponseEntity<>("Cannot delete this item because it is referenced by existing orders. Please mark it as out of stock instead.", HttpStatus.CONFLICT);
    }
}
