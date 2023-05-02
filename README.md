# 50.003 Mini-Campaign

An individual project for 50.003, where the goal was to make a CSV comparison program and test it in various ways.

## Purpose
This is a simple program to compare two CSV files line by line and check for exceptions. An exception means either of the following:

- A mismatch between 2 lines (e.g. if the program is set to check all lines that have <A,B>, and file1 has a line <A,B,C1> and file2 has a line <A,B,C2>, then both of those lines are exceptions)
- A line that exists in only 1 file but not the other (e.g. file1 has a line <A,B,C> while file2 has no lines that start with <A,B>, so <A,B,C> is an exception)

## Usage
1. Install the [Java Development Kit](https://www.oracle.com/java/technologies/downloads/).
2. In a terminal, run `javac -d . src/CompareCSV/Bootstrap.java src/CompareCSV/Entry.java src/CompareCSV/Helper.java` to compile the java files.
3. Then, run `java CompareCSV.Entry [input csv 1] [input csv 2] [output csv] <i> <y/[n]>`.

Replace `[input csv 1]`, `[input csv 2]`, `[output csv]` with your own file paths. This will compare the two input CSV files and write the exceptions to the output CSV file. To prevent overwrites, the output filepath will be rejected if it corresponds to an existing file.

`<i>` and `<y/[n]>` are optional inputs.
- `<i>` is an integer to represent which column will be chosen for comparison, starting with the leftmost column being represented by 0.
- `<y/[n]>` is a yes/no choice as to whether column headers should be checked. By default, the choice is `n` (do not check). If the input is `y`, the column headers of both input CSV files will be checked to ensure they match, before proceeding with the comparison.

For help, you can run `java CompareCSV.Entry -h`.

## Testing
To run the test files, import this project to your IDE and run the test files from there. I personally use [IntelliJ community edition](https://www.jetbrains.com/idea/download/), so the test files should run on it without issue.

For fuzzing, [Python](https://www.python.org/downloads/) is used to write the fuzzer. Run `python resources/fuzzer.py` to generate FuzzTest.java (which is the test file to be run) and multiple CSV files like input_1a.csv and input_1b.csv (containing random data).

## Use case diagram
![Use case diagram](use_case_diagram.jpg)
