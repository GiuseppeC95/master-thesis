# tokenizer-test

## Getting Started
To run this code, you need:
- English OpenNLP tokenizer called "en-token.bin" http://opennlp.sourceforge.net/models-1.5/
- Italian OpenNLP tokenizer called "it-token.bin" that you will find in aciapetti repository opennlp-italian-models/models/it/it-token.bin

## Instruction(for Linux users)  
1. Copy "en-token.bin" and "it-token.bin" in "opennlp-models" folder
1. Locate to the folder you want to use (ex. tokenizer-test)
	`cd [YOUR PATH]/tokenizer-test`
1. Build the source using Maven
	`mvn clean package`
