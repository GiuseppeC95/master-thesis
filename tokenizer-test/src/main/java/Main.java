import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by giuseppe on 23/03/17.
 */
public class Main {
    public static void main(String[] args) {
        try {
            tokenize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tokenize() throws InvalidFormatException, IOException{
        String sentence = "This is a test of OpenNLP Tokenization. I'm testing some methods. \n Now i want to tokenize Mr Brown as a single token.\n Testing aren't, can't, mustn't, i'd better...";
        System.out.println("I'm testing OpenNLP Tokenizers.\n The sentence is: ["+ sentence+ "]");

        /*---------------------------SIMPLE TOKENIZER-----------------------------------*/
        System.out.println("\n\n-------------------------------SIMPLE TOKENIZER------------------------------------");
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        //Tokenizing the given sentence
        String tokens[] = tokenizer.tokenize(sentence);
        //Printing the tokens
        for(String token : tokens)
            System.out.print(token+ "|");

        /*---------------------------WHITESPACE TOKENIZER--------------------------------*/
        System.out.println("\n\n-----------------------------WHITESPACE TOKENIZER----------------------------------");

        //Instantiating whitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;

        //Tokenizing the given paragraph
        String tokensW[] = whitespaceTokenizer.tokenize(sentence);

        //Printing the tokens
        for(String token : tokensW)
            System.out.print(token+"|");
        /*----------------------------ENGLISH TOKENIZER----------------------------------*/
        System.out.println("\n\n-------------------------------ENGLISH TOKENIZER----------------------------------");
        //Loading the Tokenizer model
        InputStream inputStream = new FileInputStream("src/opennlp-models/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStream);

        //Instantiating the TokenizerME class
        TokenizerME tokenizerEN = new TokenizerME(tokenModel);

        //Tokenizing the given raw text
        String tokensEN[] = tokenizerEN.tokenize(sentence);

        //Printing the tokens
        for(String token : tokensEN)
            System.out.print(token+ "|");

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
        inputStream.close();
        System.out.println("I'm testing OpenNLP Tokenizers for Italian language.\n The sentence is: ["+ sentenceIt+ "]");

        /*----------------------------ITALIAN TOKENIZER----------------------------------*/
        System.out.println("\n\n-------------------------------ITALIAN TOKENIZER----------------------------------");
        //Loading the Tokenizer model
        InputStream inputStreamIt = new FileInputStream("src/opennlp-models/it-token.bin");
        TokenizerModel tokenModelIt = new TokenizerModel(inputStreamIt);

        //Instantiating the TokenizerME class
        TokenizerME tokenizerIt = new TokenizerME(tokenModelIt);

        //Tokenizing the given raw text
        String tokensIt[] = tokenizerIt.tokenize(sentenceIt);

        //Printing the tokens
        for(String token : tokensIt)
            System.out.print(token+ "|");
    }
}
