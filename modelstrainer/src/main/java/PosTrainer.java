import opennlp.tools.formats.ConllXPOSSampleStream;
import opennlp.tools.postag.*;
import opennlp.tools.util.*;

import java.io.*;
import java.nio.charset.Charset;


/**
 * Created by giuseppe on 04/05/17.
 */
public class PosTrainer {
    private String inCorpusFilePath;
    private String outModelPath;

    public PosTrainer(String inCorpusFilePath,String outputName) {
        this.inCorpusFilePath= inCorpusFilePath;
        this.outModelPath= outputName;
    }
    public void convertFile(){
        Charset charset = Charset.forName("UTF-8");
        try {
            File trainFile= new File(inCorpusFilePath);

            BufferedReader reader= new BufferedReader(new FileReader(trainFile));
            String line= reader.readLine();
            String tokens[]= null;
            BufferedWriter writer = new BufferedWriter( new FileWriter( "it-pos-paisa.train"));
            while(line!=null){

                tokens= line.split("\t");

                if(tokens.length>1) {
                    System.out.print(tokens[1] + "_" + tokens[4]+" ");
                    writer.write(tokens[1]+"_"+tokens[4]+" ");
                } else {
                    System.out.println();
                    writer.write("\n");
                }

                tokens=null;
                line=reader.readLine();
//                System.out.println(line);
//                line=reader.readLine();
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void train(){
        POSModel model = null;

        InputStream dataIn = null;
        //dataIn = new FileInputStream("train-corpus/paisa.annotated.CoNLL.utf8");
        try {

            InputStreamFactory isf = new MarkableFileInputStreamFactory(new File(inCorpusFilePath));
            //ObjectStream<String> lineStream = new PlainTextByLineStream(isf,"UTF-8");
            ObjectStream<POSSample> sampleStream = new ConLLXPOSSampleStreamEdit(isf, Charset.forName("UTF-8"));

            POSTaggerFactory factory = new POSTaggerFactory();
            model = POSTaggerME.train("it", sampleStream, TrainingParameters.defaultParams(), factory);

            /*
            InputStreamFactory isf = new MarkableFileInputStreamFactory(new File("it-pos-paisa.train"));
            ObjectStream<String> lineStream = new PlainTextByLineStream(isf,"UTF-8");
            ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

            POSTaggerFactory factory = new POSTaggerFactory();
            model = POSTaggerME.train("it", sampleStream, TrainingParameters.defaultParams(), factory);
            */
            //dataIn = new FileInputStream("train-corpus/paisa.raw.utf8");
            /*
            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileReader(inFile), "UTF-8");
            ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

            model = POSTaggerME.train("it", sampleStream, TrainingParameters.defaultParams(), null);
            */
        }
        catch (IOException e) {
            // Failed to read or parse training data, training failed
            e.printStackTrace();
        }

        finally {
            if (dataIn != null) {
                try {
                    dataIn.close();
                    System.out.println("Modello estratto");
                }
                catch (IOException e) {
                    // Not an issue, training already finished.
                    // The exception should be logged and investigated
                    // if part of a production system.
                    e.printStackTrace();
                }
            }
        }

        OutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(new File(outModelPath)));
            model.serialize(modelOut);
        }
        catch (IOException e) {
            // Failed to save model
            e.printStackTrace();
        }
        finally {
            if (modelOut != null) {
                try {
                    modelOut.close();
                    System.out.println("Modello creato");
                } catch (IOException e) {
                    // Failed to correctly save model.
                    // Written model might be invalid.
                    e.printStackTrace();
                }
            }
        }
    }
}
