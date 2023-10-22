package thalia.ParkingManager.exceptionhandler;

public class BadRequestException extends RuntimeException{
		public BadRequestException(String messageError) {
				super(messageError);
		}
}
