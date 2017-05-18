package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import opennlp.tools.postag.POSSample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Map<String, String> tagList;
    @FXML
    private TextArea inputText;
    @FXML
    private Button start;
    @FXML
    private CheckBox flagTokenizer, flagPos, flagStemming, flagSentence;
    @FXML
    private TabPane outTab;
    @FXML
    private Tab tabTokenizer, tabPos, tabStemming, tabSentence;
    @FXML
    private Label textTokenizer, textPos, textStemming, textSentence, titleTag, bodyTag;
    @FXML
    private FlowPane outPos;

    private SingleSelectionModel<Tab> selectionModel;

    public Controller() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tagList = new HashMap<>();
        loadTagSet();
        selectionModel = outTab.getSelectionModel();
        selectionModel.select(0);
        flagStemming.setVisible(false);
        flagSentence.setVisible(false);
        tabTokenizer.setDisable(true);
        tabPos.setDisable(true);
        tabStemming.setDisable(true);
        tabSentence.setDisable(true);
    }

    @FXML
    private void startAnalize(ActionEvent event) {
        //Reset dei tabs
        textTokenizer.setText("");
        outPos.getChildren().clear();
        if (!inputText.getText().isEmpty()) {
            selectionModel.select(tabTokenizer);
            BasicsNLP nlp = new BasicsNLP();
            String out = new String("");
            String tokensIt[] = new String[0];
            try {
                tokensIt = nlp.tokenizeIt(inputText.getText());
                if (flagTokenizer.isSelected()) {
                    tabTokenizer.setDisable(false);
                    for (String token : tokensIt)
                        out += token + " | ";
                    textTokenizer.setText(out);
                    out = "";
                } else {
                    tabTokenizer.setDisable(true);
                    selectionModel.select(tabPos);
                }
                if (flagPos.isSelected()) {
                    tabPos.setDisable(false);
                    POSSample pos = nlp.posTaggerItMaxent(tokensIt);
                    out = pos.toString();
                    textPos.setText(out);
                    Map<String, String> posTags = new HashMap<>();
                    posTags.clear();
                    String tags[] = pos.getTags();
                    for (int i = 0; i < tokensIt.length; i++) {
                        Label l = new Label(tokensIt[i]);
                        posTags.put(tokensIt[i], tags[i]);
                        l.setOnMouseClicked((MouseEvent event1) -> {
                            //System.out.println("Parola:" + l.getText() + " Tag:" + posTags.get(l.getText()));
                            titleTag.setText(l.getText().substring(0, 1).toUpperCase() + l.getText().substring(1));
                            String translatedTag= new String(tagToString(posTags.get(l.getText())));
                            bodyTag.setText(translatedTag.substring(0,1).toUpperCase()+translatedTag.substring(1)+".");
                            //bodyTag.setText(posTags.get(l.getText()));
                        });
                        outPos.getChildren().add(l);
                    }

                } else {
                    tabPos.setDisable(true);
                }
                if (flagStemming.isSelected()) {
                    tabStemming.setDisable(false);
                    textStemming.setText(out);
                } else {
                    //tabStemming.setDisable(true);
                }
                if (flagSentence.isSelected()) {
                    tabSentence.setDisable(false);
                    textSentence.setText(out);
                } else {
                    tabSentence.setDisable(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String tagToString(String tag) {
        String mainTag = "";
        String translated = "";
        //System.out.println("Valore translated: " + translated);
        if (tagList.get(tag) == null) {
            for (char c : tag.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    //System.out.println("Uppercase letter: " + c);
                    mainTag += c;
                }
            }
            if (mainTag.charAt(0) != 'V') {
                translated += tagList.get(mainTag);
                for (int i = mainTag.length(); i < tag.length(); i++) {
                    switch (tag.charAt(i)) {
                        case 'm':
                            translated += ", maschile";
                            break;
                        case 'f':
                            translated += ", femminile";
                            break;
                        case 's':
                            translated += ", singolare";
                            break;
                        case 'p':
                            translated += ", plurale";
                            break;
                    }
                }
            } else {
                if (Character.isDigit(tag.charAt(tag.length() - 1))) {
                    translated = tagList.get(tag.substring(0, tag.length() - 1));
                    //System.out.println("Carattere: " + translated);
                    switch (tag.charAt(tag.length() - 1)) {
                        case '1':
                            translated += ", prima persona";
                            break;
                        case '2':
                            translated += ", seconda persona";
                            break;
                        case '3':
                            translated += ", terza persona";
                            break;
                    }

                } else {
                    if (Character.isAlphabetic(tag.charAt(tag.length())) && Character.isDigit(tag.charAt(tag.length() - 2))) {
                        translated = tagList.get(tag.substring(0, tag.length() - 2));
                        switch (tag.charAt(tag.length() - 2)) {
                            case '1':
                                translated += ", prima persona";
                                break;
                            case '2':
                                translated += ", seconda persona";
                                break;
                            case '3':
                                translated += ", terza persona";
                                break;
                        }
                        switch (tag.charAt(tag.length() - 2)) {
                            case 's':
                                translated += ", singolare";
                                break;
                            case 'p':
                                translated += ", plurale";
                                break;
                        }

                    }
                }

            }
            return translated;
        } else
            return tagList.get(tag);

    }

    private void loadTagSet() {
        try {
            File name= new File("src/resources/Tagset.txt");
            BufferedReader reader = new BufferedReader(new FileReader(name));
            String line = reader.readLine();
            while (line != null) {
                String tokens[] = line.split("=");
                tagList.put(tokens[0], tokens[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
