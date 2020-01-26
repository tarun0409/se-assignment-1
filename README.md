## Command Line Argument Storage

### Specifications and Requirements
* The code has been written in Java SE 8
* The unit tests have been written in JUnit 4
* JRE 8 or above and JUnit JAR files are required to run this project

### Architecture of the project

```

src - Contains code for all functionalities
|
|-- javaargs
|       |
|       |-- functional - javaargs.function package in which all code files exists
|                |
|                |-- Argument.java - Interface implemented by argument POJO classes
|                |-- ArgumentList.java - Parses command line arguments and its values
|                |-- ArgumentTypeMismatch.java - Exception for argument type mismatch
|                |-- ArgumentUnavailable.java - Exception for unavailable argument
|                |-- BooleanArgument.java - POJO class for storing boolean arguments
|                |-- Constants.java - Stores static constants for easy access
|                |-- DoubleArgument.java - POJO class for storing double arguments
|                |-- IntegerArgument.java - POJO class for storing integer arguments
|                |-- InvalidArgumentList.java - Exception for invalid command line arg
|                |-- InvalidSchema.java - Exception for invalid schema string
|                |-- LoadedArgument.java - POJO class storing intermediate values while parsing and storing arguments
|                |-- MapArgument.java - POJO class for storing map argument
|                |-- Schema.java - Parses the schema string
|                |-- *SerializedArguments.java* - This class is used in main method to start process of parsing and storing schema and command line arguments
|                |-- StringArgument.java - POJO class for storing string arguements
|                |-- StringArrayArgument.java - POJO class for storing array of strings
|
|
test - contains JUnit4 unit test codes
|
|-- javaargs
|       |
|       |-- functional
|                |
|                |-- ArgumentListTest.java - Tests functionalities of ArgumentList.java
|                |-- DoubleArgumentTest.java - Tests functionalities of DoubleArgument.java
|                |-- IntegerArgumentTest.java - Tests functionalities of IntegerArgument.java
|                |-- LoadedArgumentTest.java - Tests functionalities of LoadedArgument.java
|                |-- MapArgumentTest.java - Tests functionalities of MapArgument.java
|                |-- SchemaTest.java - Tests functionalities of Schema.java
|                |-- SerializedArgumentsTest.java - Tests functionalities of SerializedArguments.java
|                |-- StringArgumentTest.java - Tests functionalities of StringArgument.java
|                |-- StringArrayArgumentTest.java - Tests functionalities of StringArrayArgument.java

```

### Instructions

* Create an instance of the class *SerializedArguments* with a valid schema string and command line arguments to store the command line arguments to be used later
* An **ArgTest.java** has already been provided with a main function as a sample to use the feature
* Syntax for a valid schema is given below

[One character ID][Type Escape Sequence],[One character ID][Type Escape Sequence],...

#### Type Escape Sequences
**Boolean** - No escape sequence. Simply providing ID will be enough

**Integer** - #

**Double** - ##

**String** - *

**String Array** - [*]

**Map** - &

#### Examples of valid schema

"f,s*,n#,a##,p[*]"
"f,s*,k&"
"s*"

* The command line arguments should contain values for the IDs present in the schema. The following is the syntax for command line arguments

-[One Character ID] [Value] -[One Character ID] [Value] ...

#### Example for valid command line arguments

"-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3 -k a:b -k c:d"

* Please note that an empty string in place of schema or command line arguments in considered invalid

### Characteristics of clean code followed

* The code has been written in **Functional Programming Paradigm** which makes it neat as a function is assigned for each specific task to be done in the code
* Java 8 **Lambdas and Streams** have been used whenever necessary to modularize the code and make it look more intuitive
* Majority of the functions do not contain more than 3 arguments. If it contains, it will mostly be lambdas being passed.
* Function arguments have been written one below the other in a neat and readable fashion
* Most of the functions do not have more than 3-4 lines of code since it executes only one specific functionalities
* Function names have been given wholly based on what exactly the function does which makes the code more readable and modularized and eliminates the necessity of comments
* Unit tests have been written for every functionality and have been separated based on classes
* Exceptions are all unchecked exceptions
* Exception classes are named based on what exception is being thrown and "Exception" is not added to its name



