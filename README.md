# master-thesis

This repository is used for researching and tools testing about text manipulation (tokenizing, chunking, parsing).  
You will find these projects:  
- **basicsnlp-test**: a simple test of OpenNLP tokeninzing features (SimpleTokenizer, WhiteSpaceTokenizer, TokenizerME with english and italian modules)  
- **modelstrainer**: a tool that creates OpeNLP modules, trained from a corpora  
- **lucene-test**: a demo of lucene indexing and querying features with detailed comments about how the features works  

## Getting Started
There's a folder for each test is made. Choose the folder you want to use and follow the instructions. Some projects require to install external files. If exists, please take a look to Instructions.md in the project folder you want to use.
The repository uses Maven for building and running the code. To get started, you need:

1. JDK 1.8 
2. Maven 3.0 or higher

### Instructions (for Linux Users)
1. Locate to the folder you want to use (ex. tokenizer-test)  
	`cd [YOUR PATH]/tokenizer-test`
2. Build the source using Maven
	`mvn clean package`
	
## Changelog
*08/05/2017* - **RENAMED** tokenizer-test folder to basicsnlp-test  
*04/05/2017* - **ADDED** modelstrainer  
*06/04/2017* - **UPDATED** tokenizer-test *-Added*  Stemmer and SentenceDetector  for italian  
*30/03/2017* - **UPDATED** tokenizer-test *-Added* English and Italian tokenizer  
*27/03/2017* - **ADDED** lucene-test with comments (work in progress)  
*23/03/2017* - **ADDED** tokenizer-test with SimpleTokenizer and WhiteSpaceTokenizer 
