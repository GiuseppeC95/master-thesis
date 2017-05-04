/**
 * Created by giuseppe on 04/05/17.
 */
public class Main {

    //psvm crea direttamente il main
    public static void main(String[] args) {
        PosTrainer posTrainer= new PosTrainer("train-corpus/paisa.annotated.CoNLL.utf8","it-pos.bin");
        //posTrainer.convertFile();
        posTrainer.train();
    }
}
