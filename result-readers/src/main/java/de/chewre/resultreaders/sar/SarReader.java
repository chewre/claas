package de.chewre.resultreaders.sar;

import java.io.IOException;

import de.chewre.resultreaders.AbstractReader;
import de.chewre.resultreaders.AbstractSampleLine;
import de.chewre.resultreaders.ReadSampleFileException;

public class SarReader extends AbstractReader {

    private String[] header;
    private SarSampleLine nextSampleLine;

    public SarReader(String filePath) throws ReadSampleFileException {
        super(filePath);

        try {
            String rawLine = reader.readLine();
            String line = rawLine.replaceAll("\\[...\\]", "");
            header = line.split(";");
        } catch (IOException ioe) {
            throw new ReadSampleFileException("Can not read the header of the sample file.", ioe);
        }
    }

	@Override
    public boolean hasNext() throws ReadSampleFileException {
        String line;

        try {
            line = reader.readLine();
        } catch (IOException ioe) {
            throw new ReadSampleFileException("Can not read the next line.", ioe);
        }

        boolean hasNext = line != null;
        if (hasNext) {
            nextSampleLine = new SarSampleLine(header, line.split(";"));
        }

        return hasNext;
    }

	@Override
	public AbstractSampleLine next() {
		return nextSampleLine;
	}
}
