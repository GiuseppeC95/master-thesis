# tokenizer-test

## Getting Started
To run this code, you need:
- English OpenNLP tokenizer called "en-token.bin" http://opennlp.sourceforge.net/models-1.5/
- Italian OpenNLP models from aciapetti repository opennlp-italian-models/models/it

## Instruction(for Linux users)  
1. Copy "en-token.bin" and italian models in "opennlp-models" folder
1. Locate to the folder you want to use (ex. tokenizer-test)
	`cd [YOUR PATH]/tokenizer-test`
1. Build the source using Maven
	`mvn clean package`
