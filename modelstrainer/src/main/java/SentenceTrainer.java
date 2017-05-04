import java.io.File;

/**
 * Created by giuseppe on 04/05/17.
 */
public class SentenceTrainer {
    private File modelFile;
    public SentenceTrainer(String outputName) {
        modelFile= new File(outputName);
    }
/*
    public void train() throws FileNotFoundException {

        ObjectStream<String> lineStream = new PlainTextByLineStream((InputStreamFactory) new FileInputStream("train-corpus/paisa.annotated.CoNLL.utf8"), "UTF-8");
        ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream);

        SentenceModel model = SentenceDetectorME.train("it",sampleStream, true, null, 5, 100);
        OutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(modelFile));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null)
                modelOut.close();
        }
    }
    */
}
