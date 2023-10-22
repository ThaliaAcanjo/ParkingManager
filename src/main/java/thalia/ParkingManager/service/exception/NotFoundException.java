package thalia.ParkingManager.service.exception;

import jakarta.persistence.NoResultException;

public class NotFoundException extends NoResultException {

		private static final long serialVersionUID = 1L;

		public NotFoundException() {
				super("Resource not found.");
		}

}