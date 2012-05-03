To run the program, extract all of the files and navigate to the source directory.

Type `ant` to build the java files.

Usage is:

	jar -jar Grammartizer.jar grammar_file input_file

Where:

* `grammar_file` is the grammar specification, formatted as specified in the project spec.
* `input_file` is the input program to parse.

Alternatively, the files can be run from Eclipse.

1. Create a new Eclipse project and include all of the files.
2. Specify the grammar file and input file as parameters to main (in args).
3. `args[0]` should be the grammar file, and `args[1]` should be the input file.
4. `main` is located in `Driver.java`
5. Running `Driver.java` should run the program.