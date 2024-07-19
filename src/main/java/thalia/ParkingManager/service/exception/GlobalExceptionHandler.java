package thalia.ParkingManager.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import thalia.ParkingManager.exceptionhandler.BadRequestException;

@ControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(BadRequestException.class)
		public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
		}
}
