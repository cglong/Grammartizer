**Grammartizer** is an LL(1) parser written in Java. The program was designed to translate each piece of a parser into a high-level Java construct, which are then manipulated to complete a parse.

The program is implemented as follows:

    1. GrammarReader reads in the grammar and generates appropriate tokens and rules from it.
    2. The Grammar scans through each Rule, removing any left recursion.
    3. The Grammar analyzes the Rules, looking for and eliminating any common prefixes.
    4. Each Symbol updates its First set continuously until the Grammar no longer detects any changes.
    5. Each Nonterminal updates its Follow set until no more changes occur in any.
    6. A parse table is constructed using the generated First and Follow sets.
    7. ProgramReader reads in the provided input and splits it into tokens. If any identifiers, integers or symbols appear, they are translated into the appropriate Terminal.
    8. Parser runs through the input tokens, using the ParseTable and a Stack as tools.

Full implementation details can be found at Grammartizer's GitHub repository: https://github.com/cglong/Grammartizer

The TINY grammar, when including operator precedence, can be defined as:

    %Tokens begin end print read LEFTPAR RIGHTPAR SEMICOLON ID ASSIGN COMMA INTNUM PLUS MINUS MULTIPLY MODULO
    %Non-terminals <Tiny-program> <statement-list> <statement> <id-list> <exp-list> <exp-arith> <exp-mul> <exp-mod> <term>
    %Start <Tiny-program>
    %Rules
    <Tiny-program> : begin <statement-list> end
    <statement-list> : <statement-list> <statement> | <statement>
    <statement> : print ( <exp-list> ) ;
    <statement> : ID := <exp-arith> ;
    <statement> : read ( <id-list> ) ;
    <id-list> : <id-list> , ID | ID
    <exp-list> : <exp-list> , <exp-arith> | <exp-arith>
    <exp-arith> : <exp-arith> + <exp-mul> | <exp-arith> - <exp-mul> | <exp-mul>
    <exp-mul> : <exp-mul> * <exp-mod> | <exp-mod>
    <exp-mod> : <exp-mod> % <term> | <term>
    <term> : ID | INTNUM | ( <exp-arith> )

When a sample TINY program, such as `begin print(42); end`, is run through Grammartizer, the following output is received:

    Successful parse

If invalid input is provided, useful errors are provided to help the programmer determine how to repair the issue.