package com.example.library.advices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourseNotFound(ResourceNotFoundException e){

        ApiError apiError= ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return buildApiResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationError(MethodArgumentNotValidException e){
        List<String>errors=e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError=ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Failed To Validate")
                .suberrors(errors)
                .build();

        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<?>> illegalstateHandler(IllegalStateException e){
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .build();
        return buildApiResponse(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> ServerError(Exception e){

        ApiError apiError=ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something went wrong")
                .build();

        return buildApiResponse(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        String cause = ex.getMostSpecificCause().getMessage();
        String message = "Duplicate value or constraint violation";

        if (cause != null) {
            String lower = cause.toLowerCase();
            if (lower.contains("isbn")) {
                message = "Book with this ISBN already exists";
            } else if (lower.contains("email")) {
                message = "Author with this email already exists";
            }
        }

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(message)
                .build();

        return buildApiResponse(apiError);
    }


    private ResponseEntity<ApiResponse<?>>buildApiResponse(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }
}
