package ra.md4_project.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.md4_project.exception.CategoryNotEmpty;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.response.ResponeError;

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
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotEmpty.class)
    public ResponseEntity<?> handleDataExist(CategoryNotEmpty e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<?> handleDataNotFound(DataNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
