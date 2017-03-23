import opennlp.tools.tokenize.*;
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
        String sentence = "This is a test of OpenNLP Tokenization. I'm testing some methods and it's difficult. Help me!";
        System.out.println("I'm testing OpenNLP Tokenizers.\n The sentence is: ["+ sentence+ "]");

        /*---------------------------SIMPLE TOKENIZER-----------------------------------*/
        System.out.println("\n\n-------------------------------SIMPLE TOKENIZER------------------------------------");
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        //Tokenizing the given sentence
        String tokens[] = tokenizer.tokenize(sentence);
        //Printing the tokens
        for(String token : tokens)
            System.out.print(token+ "|");

        /*---------------------------WHITESPACE TOKENIZER--------------------------------

         */
        System.out.println("\n\n-----------------------------WHITESPACE TOKENIZER----------------------------------");

        //Instantiating whitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;

        //Tokenizing the given paragraph
        String tokensW[] = whitespaceTokenizer.tokenize(sentence);

        //Printing the tokens
        for(String token : tokensW)
            System.out.print(token+"|");
    }
}
