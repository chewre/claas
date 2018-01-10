package de.chewre.resultreaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractReader {

	protected BufferedReader reader;

	public AbstractReader(String filePath) throws ReadSampleFileException {
		try {
			reader = Files.newBufferedReader(Paths.get(filePath));
		} catch (IOException ioe) {
			throw new ReadSampleFileException("Can't open sample file.", ioe);
		}
	}

    public abstract boolean hasNext() throws ReadSampleFileException;

    public abstract AbstractSampleLine next();

    public void close() throws ReadSampleFileException {
        try {
            reader.close();
        } catch (IOException ioe) {
            throw new ReadSampleFileException("Can not close the sample file", ioe);
        }
    }
}
