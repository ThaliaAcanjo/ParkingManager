package thalia.ParkingManager.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice(basePackages = "thalia.ParkingManager.controller")
public class ControllerAdviceExceptions {
		private HttpHeaders headers(){
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
		}
		private ResponseError responseError(String message, HttpStatus statusCode){
				return new ResponseError(new Date(), statusCode.value(), message);
		}

		@ResponseBody
		@ExceptionHandler(NotFoundException.class)
		public ResponseEntity<ResponseError> notFoundException(NotFoundException notFoundException){
				ResponseError error = new ResponseError(
								new Date(), HttpStatus.NOT_FOUND.value(), "Not Found");
				return  new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}

		@ResponseBody
		@ExceptionHandler(BadRequestException.class)
		public ResponseEntity<ResponseError> badRequestException(BadRequestException badRequestException){
				return  new ResponseEntity<>(responseError(badRequestException.getMessage(), HttpStatus.BAD_REQUEST),
													HttpStatus.BAD_REQUEST);

		}
}
