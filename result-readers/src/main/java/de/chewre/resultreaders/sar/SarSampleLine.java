package de.chewre.resultreaders.sar;

import de.chewre.resultreaders.AbstractSampleLine;

public class SarSampleLine extends AbstractSampleLine {

    public SarSampleLine(String[] header, String[] data) {
        Double valueAsNumber = 0.0;
        String head;
        String value;
        boolean valueIsNumber;

        for (int i = 0; i < header.length; i++) {
            head = header[i];
            value = data[i];

            try {
                valueAsNumber = Double.parseDouble(value);
                valueIsNumber = true;
            } catch (NumberFormatException nfe) {
                valueIsNumber = false;

                if ("timestamp".equals(head)) {
                    value = value.replace(" UTC", "Z");
                    value = value.replace(" ", "T");
                }
            }
            keys.add(i, head);
            values.add(i, valueIsNumber ? valueAsNumber : value);
        }
    }
}
