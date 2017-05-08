import opennlp.tools.postag.POSSample;
import opennlp.tools.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * Edited by giuseppe on 08/05/17.
 */
public class ConLLXPOSSampleStreamEdit extends FilterObjectStream<String, POSSample> {

    public ConLLXPOSSampleStreamEdit(ObjectStream<String> lineStream) {
        super(new ParagraphStream(lineStream));
    }

    public ConLLXPOSSampleStreamEdit(InputStreamFactory in, Charset charset) throws IOException {
        super(new ParagraphStream(new PlainTextByLineStream(in, charset)));
    }

    public POSSample read() throws IOException {

        // The CONLL-X data has a word per line and each line is tab separated
        // in the following format:
        // ID, FORM, LEMMA, CPOSTAG, POSTAG, ... (max 10 fields)

        // One paragraph contains a whole sentence and, the token
        // and tag will be read from the FORM and POSTAG field.

        String paragraph = samples.read();

        POSSample sample = null;
        boolean isIgnored=false;
        if (paragraph != null) {

            // paragraph get lines
            BufferedReader reader = new BufferedReader(new StringReader(paragraph));

            List<String> tokens = new ArrayList<>(100);
            List<String> tags = new ArrayList<>(100);

            String line;
            while ((line = reader.readLine())  != null) {

                final int minNumberOfFields = 5;

                String parts[] = line.split("\t");

                if (parts.length >= minNumberOfFields) {
                    tokens.add(parts[1]);
                    tags.add(parts[4]);
                }
                else {
                    if (line.charAt(0)=='#' || line.charAt(0)=='<')
                        isIgnored=true;
                    else
                     throw new InvalidFormatException("Every non-empty line must have at least " +
                            minNumberOfFields + " fields: '" + line + "'!");
                }
            }

            // just skip empty samples and read next sample
            if (tokens.size() == 0 || isIgnored) {
                sample = read();
                isIgnored=false;
            }
            sample = new POSSample(tokens.toArray(new String[tokens.size()]),
                    tags.toArray(new String[tags.size()]));
        }

        return sample;
    }
}
