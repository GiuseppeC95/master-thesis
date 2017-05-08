/**
 * Created by giuseppe on 04/05/17.
 */
public class Main {

    //psvm crea direttamente il main
    public static void main(String[] args) {
        PosTrainer posTrainer;
        switch (args.length) {
            default:
                posTrainer = new PosTrainer("train-corpus/paisa.annotated.CoNLL.utf8", "it-pos-paisa.bin");
                break;
            case 3:
                posTrainer = new PosTrainer(args[1], args[2]);
                break;
        }
        //posTrainer.convertFile();
        posTrainer.train();
    }
}
