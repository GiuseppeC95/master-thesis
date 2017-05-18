package sample;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
//import org.tartarus.snowball.ext.ItalianStemmer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by giuseppe on 23/03/17.
 */
public class BasicsNLP {
    private String inputText;
    public BasicsNLP() {
        this.inputText=null;
    }


    public String tokenizeSimple(String sentence) throws InvalidFormatException, IOException{;
        String s="";
        /*---------------------------SIMPLE TOKENIZER-----------------------------------*/
        System.out.println("\n\n-------------------------------SIMPLE TOKENIZER------------------------------------");
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        //Tokenizing the given sentence
        String tokens[] = tokenizer.tokenize(sentence);
        //Printing the tokens
        for(String token : tokens) {
            s=token+"|";
            System.out.print(token + "|");
        }
        return s;

    }

    public void tokenizeWhiteSpace(String sentence) throws InvalidFormatException, IOException{
        /*---------------------------WHITESPACE TOKENIZER--------------------------------*/
        System.out.println("\n\n-----------------------------WHITESPACE TOKENIZER----------------------------------");

        //Instantiating whitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;

        //Tokenizing the given paragraph
        String tokensW[] = whitespaceTokenizer.tokenize(sentence);

        //Printing the tokens
        for(String token : tokensW)
            System.out.print(token+"|");
    }
    public void tokenizeEn(String sentence) throws InvalidFormatException, IOException{
        /*----------------------------ENGLISH TOKENIZER----------------------------------*/
        System.out.println("\n\n-------------------------------ENGLISH TOKENIZER----------------------------------");
        //Loading the Tokenizer model
        InputStream inputStream = new FileInputStream("src/opennlp-models/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStream);
        inputStream.close();
        //Instantiating the TokenizerME class
        TokenizerME tokenizerEN = new TokenizerME(tokenModel);

        //Tokenizing the given raw text
        String tokensEN[] = tokenizerEN.tokenize(sentence);

        //Printing the tokens
        for(String token : tokensEN)
            System.out.print(token+ "|");
    }

    public String[] tokenizeIt(String sentenceIt) throws InvalidFormatException, IOException{

        //Loading the Tokenizer model
        InputStream inputStreamIt = new FileInputStream("src/opennlp-models/it-token.bin");
        TokenizerModel tokenModelIt = new TokenizerModel(inputStreamIt);

        //Instantiating the TokenizerME class
        TokenizerME tokenizerIt = new TokenizerME(tokenModelIt);

        //Tokenizing the given raw text
        String tokensIt[] = tokenizerIt.tokenize(sentenceIt);
        inputStreamIt.close();

        return tokensIt;
    }
    public POSSample posTaggerItMaxent(String[] tokens) throws IOException {
        /*----------------------------ITALIAN POS TAGGER - MAXENT----------------------------------*/
        //Loading Parts of speech-maxent model
        InputStream inputStream = new FileInputStream("src/opennlp-models/it-pos-maxent.bin");
        POSModel model = new POSModel(inputStream);

        //Instantiating POSTaggerME class
        POSTaggerME tagger = new POSTaggerME(model);
        //Generating tags given the tokens created by tokenizeIt()
        String[] tags = tagger.tag(tokens);
        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);

        return sample;
    }
    /*
    public POSSample posTaggerItPerceptron(String[] tokens, String sentence) throws IOException {

        System.out.println("\n\n-------------------------------ITALIAN POS TAGGER - PERCEPTRON----------------------------------");
        //Loading Parts of speech-perceptron model
        InputStream inputStream = new FileInputStream("src/opennlp-models/it-pos_perceptron.bin");
        POSModel model = new POSModel(inputStream);

        //Instantiating POSTaggerME class
        POSTaggerME tagger = new POSTaggerME(model);
        //Generating tags given the tokens created by tokenizeIt()
        String[] tags = tagger.tag(tokens);
        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);

        //System.out.println(sample.toString());

        return sample;
    }

    public static void stemmerIt() throws IOException {
        //----------------------------ITALIAN STEMMER----------------------------------
        System.out.println("\n\n-------------------------------ITALIAN STEMMER----------------------------------");
        ItalianStemmer stemmer = new ItalianStemmer();
        String[] testStemmer = {"banca", "banche", "banchiere", "banchieri"};

        for (int i = 0; i < testStemmer.length; i++) {
            stemmer.setCurrent(testStemmer[i]);
            stemmer.stem();
            System.out.println(testStemmer[i] + "->" + stemmer.getCurrent());
        }
    }
    */

    public void sentenceDetectIt() throws IOException {
        /*----------------------------ITALIAN SENTENCE DETECTION----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN SENTENCE DETECTION----------------------------------");
        //... Setup the models
        String provaSent="Questa è una prova, molto semplice. Sto provando OpenNLP Sentence detection, riuscirà a ad individuare le frasi? E se scrivessi dott. Rossi, Prof. Rivoira, o S.ra Rossi, li individua come frase?";
        InputStream modelStream = new FileInputStream("src/opennlp-models/it-sent.bin");
        SentenceModel modelSent = new SentenceModel(modelStream);
        SentenceDetector detector =new SentenceDetectorME(modelSent);
        String[] result = detector.sentDetect(provaSent);
        for (int i = 0; i < result.length; i++) {
            System.out.println("Sentence: " + result[i]);
        }
    }
}
