package de.chewre.resultreaders;

import java.io.IOException;

public class CreateReaderException extends IOException {

	private static final long serialVersionUID = 8709561042105121799L;

	public CreateReaderException() {
		super();
	}

	public CreateReaderException(String message) {
		super(message);
	}

	public CreateReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateReaderException(Throwable cause) {
		super(cause);
	}
}
