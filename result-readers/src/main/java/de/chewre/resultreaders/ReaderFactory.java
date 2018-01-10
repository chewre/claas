package de.chewre.resultreaders;

import de.chewre.resultreaders.jtl.JtlReader;
import de.chewre.resultreaders.sar.SarReader;

public class ReaderFactory {

    private ReaderFactory() {
        throw new AssertionError();
    }

    public static AbstractReader createFor(String filePath) throws CreateReaderException {
        try {
            if (filePath.endsWith(".jtl")) {
                return new JtlReader(filePath);
            } else if (filePath.endsWith(".sar")) {
                return new SarReader(filePath);
            } else {
                throw new CreateReaderException("Can not choose the right reader for the following file: "
                        + filePath);
            }
        } catch (ReadSampleFileException rsfe) {
            throw new CreateReaderException(rsfe);
        }
    }

    public static AbstractReader createFor(String filePath, String readerType) throws CreateReaderException {
        try {
            if ("jtl".equals(readerType)) {
                return new JtlReader(filePath);
            } else if ("sar".equals(readerType)) {
                return new SarReader(filePath);
            } else {
                throw new CreateReaderException("Can not choose the right reader for the given type: "
                        + readerType);
            }
        } catch (ReadSampleFileException rsfe) {
            throw new CreateReaderException(rsfe);
        }
    }
}
