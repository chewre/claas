package de.chewre.resultreaders;

import java.io.IOException;

public class ReadSampleFileException extends IOException {

	private static final long serialVersionUID = 6187378979480036617L;

	public ReadSampleFileException() {
        super();
    }

    public ReadSampleFileException(String message) {
        super(message);
    }

    public ReadSampleFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadSampleFileException(Throwable cause) {
        super(cause);
    }
}
