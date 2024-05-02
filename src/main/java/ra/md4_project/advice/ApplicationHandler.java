package ra.md4_project.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.md4_project.exception.*;
import ra.md4_project.model.dto.response.ResponeError;
import ra.md4_project.model.dto.respornWapper.ApiError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidData(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        e.getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        map.put("error", new ResponeError(400, "BAD_REQUEST", errors));
        return map;
    }

    @ExceptionHandler(DataExist.class)
    public ResponseEntity<?> handleDataExist(DataExist e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(CategoryNotEmpty.class)
    public ResponseEntity<?> handleCategoryNotEmpty(CategoryNotEmpty e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(RequestError.class)
    public ResponseEntity<?> RequestError(RequestError e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
  @ExceptionHandler(Locked.class)
    public ResponseEntity<?> Locked(Locked e) {
        return ResponseEntity.status(HttpStatus.LOCKED)
                .body(new ApiError(HttpStatus.LOCKED, e.getMessage()));
    }

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<Object> handleDataNotFound(DataNotFound e) {
        String errorMessage = "Không tìm thấy tài nguyên: " + e.getMessage();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, errorMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
