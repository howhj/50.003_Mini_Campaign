# 50003 Mini-Campaign
By: Jaron Ho (1005011)

## Purpose
This is a simple program to compare two CSV files line by line and check for exceptions. An exception means either of the following:

- A mismatch between 2 lines (e.g. if the program is set to check all lines that have <A,B>, and file1 has a line <A,B,C1> and file2 has a line <A,B,C2>, then both of those lines are exceptions)
- A line that exists in only 1 file but not the other (e.g. file1 has a line <A,B,C> while file2 has no lines that start with <A,B>, so <A,B,C> is an exception)

## Usage
1. Install the [Java Development Kit](https://www.oracle.com/java/technologies/downloads/).
2. Download the [Junit jar file](https://mvnrepository.com/artifact/junit/junit).
3. In a terminal, run `javac -d . -cp target:path/to/junit.jar compareCSV/src/*.java` to compile the java file.
4. Then, run `java compareCSV.entry [input csv 1] [input csv 2] [output csv] <i> <y/[n]>`.

Replace `[input csv 1]`, `[input csv 2]`, `[output csv]` with your own file paths. This will compare the two input CSV files and write the exceptions to the output CSV file. To prevent overwrites, the output filepath will be rejected if it corresponds to an existing file.

`<i>` and `<y/[n]>` are optional inputs.
- `<i>` is an integer to represent which column will be chosen for comparison, starting with the leftmost column being represented by 0.
- `<y/[n]>` is a yes/no choice as to whether column headers should be checked. By default, the choice is `n` (do not check). If the input is `y`, the column headers of both input CSV files will be checked to ensure they match, before proceeding with the comparison.

For help, you can run `java compareCSV.entry -h`.

## Use case diagram
![Use case diagram](use_case_diagram.jpg)
