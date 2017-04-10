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
import org.tartarus.snowball.ext.ItalianStemmer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by giuseppe on 23/03/17.
 */
public class Main {
    public static void main(String[] args) {
        try {
            String sentence = "This is a test of OpenNLP Tokenization. I'm testing some methods. \n Now i want to tokenize Mr Brown as a single token.\n Testing aren't, can't, mustn't, i'd better...";
            System.out.println("I'm testing OpenNLP Tokenizers.\n The sentence is: ["+ sentence+ "]");
            tokenizeSimple(sentence);
            tokenizeWhiteSpace(sentence);
            tokenizeEn(sentence);
//            String sentenceIt= "ho fatto bene a mangiare";
            String sentenceIt = "Questa è una prova di tokenizzazione in italiano.\n" +
                    " Cantami, o Diva, del pelide Achille\n" +
                    "l'ira funesta che infiniti addusse lutti\n" +
                    "agli Achei, molte anzi tempo all'Orco\n" +
                    "generose travolse alme d'eroi,\n" +
                    "e di cani e d'augelli orrido pasto\n" +
                    "lor salme abbandonò (così di Giove\n" +
                    "l'alto consiglio s'adempìa), da quando\n" +
                    "primamente disgiunse aspra contesa\n" +
                    "il re de' prodi Atride e il divo Achille";
            String[] tokensIt= tokenizeIt(sentenceIt);
            posTaggerItMaxent(tokensIt,sentenceIt);
            posTaggerItPerceptron(tokensIt,sentenceIt);
            stemmerIt();
            sentenceDetectIt();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tokenizeSimple(String sentence) throws InvalidFormatException, IOException{;
        /*---------------------------SIMPLE TOKENIZER-----------------------------------*/
        System.out.println("\n\n-------------------------------SIMPLE TOKENIZER------------------------------------");
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        //Tokenizing the given sentence
        String tokens[] = tokenizer.tokenize(sentence);
        //Printing the tokens
        for(String token : tokens)
            System.out.print(token+ "|");

    }
    private static void tokenizeWhiteSpace(String sentence) throws InvalidFormatException, IOException{
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
    private static void tokenizeEn(String sentence) throws InvalidFormatException, IOException{
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
    private static String[] tokenizeIt(String sentenceIt) throws InvalidFormatException, IOException{
        /*----------------------------ITALIAN TOKENIZER----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN TOKENIZER----------------------------------");

        System.out.println("I'm testing OpenNLP Tokenizers for Italian language.\n The sentence is: ["+ sentenceIt+ "]\n");

        //Loading the Tokenizer model
        InputStream inputStreamIt = new FileInputStream("src/opennlp-models/it-token.bin");
        TokenizerModel tokenModelIt = new TokenizerModel(inputStreamIt);

        //Instantiating the TokenizerME class
        TokenizerME tokenizerIt = new TokenizerME(tokenModelIt);

        //Tokenizing the given raw text
        String tokensIt[] = tokenizerIt.tokenize(sentenceIt);
        inputStreamIt.close();
        //Printing the tokens
        for(String token : tokensIt) {
            System.out.print(token + "|");
            if(token.contains(".") || token.contains(","))
                System.out.println();
        }
        return tokensIt;
    }
    public static void posTaggerItMaxent(String[] tokens, String sentence) throws IOException {
        /*----------------------------ITALIAN POS TAGGER - MAXENT----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN POS TAGGER - MAXENT----------------------------------");
        //Loading Parts of speech-maxent model
        InputStream inputStream = new FileInputStream("src/opennlp-models/it-pos-maxent.bin");
        POSModel model = new POSModel(inputStream);

        //Instantiating POSTaggerME class
        POSTaggerME tagger = new POSTaggerME(model);

//        String sentence = "Hi welcome to Tutorialspoint";
//
//        //Tokenizing the sentence using WhitespaceTokenizer class
//        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
//        String[] tokens = whitespaceTokenizer.tokenize(sentence);

        System.out.println("\n Tagging using italian tokenizer\n");
        //Generating tags given the tokens created by tokenizeIt()
        String[] tags = tagger.tag(tokens);
        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);
        System.out.println(sample.toString());

        System.out.println("\n Tagging using whitespace tokenizer\n");

        //Tokenizing the sentence using WhitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
        String[] tokensWs = whitespaceTokenizer.tokenize(sentence);

        //Generating tags given the tokens created by tokenizeIt()
        String[] tagsWs = tagger.tag(tokensWs);
        //Instantiating the POSSample class
        POSSample sampleWs = new POSSample(tokensWs, tagsWs);
        System.out.println(sampleWs.toString());

    }

    public static void posTaggerItPerceptron(String[] tokens, String sentence) throws IOException {
        /*----------------------------ITALIAN POS TAGGER - PERCEPTRON----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN POS TAGGER - PERCEPTRON----------------------------------");
        //Loading Parts of speech-perceptron model
        InputStream inputStream = new FileInputStream("src/opennlp-models/it-pos_perceptron.bin");
        POSModel model = new POSModel(inputStream);

        //Instantiating POSTaggerME class
        POSTaggerME tagger = new POSTaggerME(model);

//        String sentence = "Hi welcome to Tutorialspoint";
//
//        //Tokenizing the sentence using WhitespaceTokenizer class
//        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
//        String[] tokens = whitespaceTokenizer.tokenize(sentence);

        System.out.println("\n Tagging using italian tokenizer\n");
        //Generating tags given the tokens created by tokenizeIt()
        String[] tags = tagger.tag(tokens);
        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);
        System.out.println(sample.toString());

        System.out.println("\n Tagging using whitespace tokenizer\n");

        //Tokenizing the sentence using WhitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
        String[] tokensWs = whitespaceTokenizer.tokenize(sentence);

        //Generating tags given the tokens created by tokenizeIt()
        String[] tagsWs = tagger.tag(tokensWs);
        //Instantiating the POSSample class
        POSSample sampleWs = new POSSample(tokensWs, tagsWs);
        System.out.println(sampleWs.toString());

    }
    public static void stemmerIt() throws IOException {
        /*----------------------------ITALIAN STEMMER----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN STEMMER----------------------------------");
        ItalianStemmer stemmer = new ItalianStemmer();
        String[] testStemmer = {"banca", "banche", "banchiere", "banchieri"};

        for (int i = 0; i < testStemmer.length; i++) {
            stemmer.setCurrent(testStemmer[i]);
            stemmer.stem();
            System.out.println(testStemmer[i] + "->" + stemmer.getCurrent());
        }
    }
    public static void sentenceDetectIt() throws IOException {
        /*----------------------------ITALIAN SENTENCE DETECTION----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN SENTENCE DETECTION----------------------------------");
        //... Setup the models
        String provaSent="Questa è una prova, molto semplice. Sto provando OpenNLP Sentence detection, riuscirà a ad individuare le frasi? E se scrivessi dott. Rossi, Prof. Rivoira, o S.ra Rossi, li individua come frase?";
        InputStream modelStream = new FileInputStream("src/opennlp-models/it-sent.bin");
        SentenceModel modelSent = new SentenceModel(modelStream);
        SentenceDetector detector =
                new SentenceDetectorME(modelSent);
        String[] result = detector.sentDetect(provaSent);
        for (int i = 0; i < result.length; i++) {
            System.out.println("Sentence: " + result[i]);
        }
    }
}
