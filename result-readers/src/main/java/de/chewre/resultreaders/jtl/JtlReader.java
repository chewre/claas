package de.chewre.resultreaders.jtl;

import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.chewre.resultreaders.AbstractReader;
import de.chewre.resultreaders.AbstractSampleLine;
import de.chewre.resultreaders.ReadSampleFileException;

public class JtlReader extends AbstractReader {

    private String[] header;
    private JtlSampleLine nextSampleLine;

    public JtlReader(String filePath) throws ReadSampleFileException {
        super(filePath);

        try {
            header = reader.readLine().split(",");
        } catch (IOException ioe) {
            throw new ReadSampleFileException("Can not read the header of the sample file.", ioe);
        }
    }

    @Override
    public boolean hasNext() throws ReadSampleFileException {
        String line;
        boolean hasNext;

        try {
            line = reader.readLine();
            hasNext = line != null;

            if (hasNext) {
                CSVParser parsedLine = CSVParser.parse(line, CSVFormat.DEFAULT);
                // Since we read line by line, the records will only have one element.
                CSVRecord record = parsedLine.getRecords().get(0);
                nextSampleLine = new JtlSampleLine(header, record);
            }
        } catch (IOException ioe) {
            throw new ReadSampleFileException("Can not read the next line.", ioe);
        }

        return hasNext;
    }

    @Override
    public AbstractSampleLine next() {
        return nextSampleLine;
    }
}
