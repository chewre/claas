package de.chewre.resultreaders.jtl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import de.chewre.resultreaders.AbstractSampleLine;

public class JtlSampleLine extends AbstractSampleLine {

    private static final int LOWEST_HTTP_STATUS_CODE = 100;
    private static final int HIGHEST_HTTP_STATUS_CODE = 599;

    public JtlSampleLine(String[] header, CSVRecord records) {
        boolean validLine = true;
        boolean validValue;
        boolean valueIsBoolean = false;
        boolean valueAsBoolean = false;
        boolean valueIsNumber;
        Double valueAsNumber = 0.0;
        // [Year, Month, Day, Hour, Minute, Seconds, Nanoseconds]
        List<Integer> parsedTimeStamp = new ArrayList<>(7);
        String head;
        String value;
        StringBuilder invalidReason = new StringBuilder();

        for (int i = 0; i < header.length; i++) {
            head = header[i];
            value = records.get(i);
            validValue = true;
            valueIsBoolean = false;

            try {
                // Check if value is a number
                valueAsNumber = Double.parseDouble(value);
                valueIsNumber = true;

                if (("elapsed".equals(head) || "Latency".equals(head) || "bytes".equals(head))
                        && valueAsNumber <= 0) {
                    validValue = false;
                    invalidReason.append(head).append(" can not be negative or zero. ");
                } else if ("responseCode".equals(head) &&
                        (valueAsNumber < LOWEST_HTTP_STATUS_CODE || valueAsNumber > HIGHEST_HTTP_STATUS_CODE)) {
                    validValue = false;
                    invalidReason.append("Response Code is not a valid HTTP status code. ");
                }
            } catch (NumberFormatException nfe) {
                valueIsNumber = false;

                // Check if value is a boolean
                if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                    valueAsBoolean = Boolean.parseBoolean(value);
                    valueIsBoolean = true;

                } else {
                    // Value is a String
                    if ("responseCode".equals(head)) {
                        validValue = false;
                        invalidReason.append(value).append(". ");
                    } else if ("URL".equals(head) && value.isEmpty()) {
                        validValue = false;
                        invalidReason.append("URL can not be empty. ");
                    } else if ("timeStamp".equals(head)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        LocalDateTime ldt = LocalDateTime.parse(value, formatter);

                        parsedTimeStamp.add(ldt.getYear());
                        parsedTimeStamp.add(ldt.getMonthValue());
                        parsedTimeStamp.add(ldt.getDayOfMonth());
                        parsedTimeStamp.add(ldt.getHour());
                        parsedTimeStamp.add(ldt.getMinute());
                        parsedTimeStamp.add(ldt.getSecond());
                        parsedTimeStamp.add(ldt.getNano());
                    }
                }
            }

            if (!validValue) {
                value = "";
                valueIsNumber = false;
                validLine = false;
            }
            keys.add(i, head);
            if (valueIsNumber) {
                values.add(i, valueAsNumber);
            } else if (valueIsBoolean) {
                values.add(i, valueAsBoolean);
            } else {
                values.add(i, value);
            }
        }

        keys.add("parsedTimeStamp");
        values.add(parsedTimeStamp);
        keys.add("valid");
        values.add(validLine);
        keys.add("invalidReason");
        values.add(invalidReason.toString());
    }
}
