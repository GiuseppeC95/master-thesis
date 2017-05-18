/**
 * Created by giuseppe on 04/05/17.
 */
public class Main {

    //psvm crea direttamente il main
    public static void main(String[] args) {
        PosTrainer posTrainer;
        boolean exit=true;
        System.out.println("Numero di parametri"+ args.length);
        switch (args.length) {
            default:
                posTrainer = new PosTrainer("train-corpus/paisa.annotated.CoNLL.utf8", "it-pos-paisa.bin");
                break;
            case 2:
                posTrainer = new PosTrainer(args[0], args[1]);
                break;
        }
        while(exit) {
            //posTrainer.convertFile();
            posTrainer.train();
        }
    }
}
