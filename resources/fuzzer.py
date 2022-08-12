import random

def gen_csv(n):
    line_max = random.randint(1, 10)
    expected = []

    for i in range(n):
        line1 = randomLine(line_max)
        line2a = randomLine(line_max)
        line2b = randomLine(line_max)
        with open(f"input_{i}a.csv", "w") as fa:
            fa.writelines([line1, line2a])
        with open(f"input_{i}b.csv", "w") as fb:
            fb.writelines([line1, line2b])
        expected.append((line2a, line2b))
    return expected

def randomLine(length):
    str_max = 50
    result = ""

    for i in range(length):
        result += randomString(random.randint(1, str_max)) + ","
    return result + "\n"

def randomString(length):
    result = ""
    quoted = False
    #charRange = (0,1114112)

    while len(result) < length:
        i = random.randint(0,1114112)
        c = chr(i)
        if c.isprintable():
            result += c
            if c == '"':
                if quoted:
                    quoted = False
                else:
                    quoted = True
    if quoted:
        result += '"'
    return result

def paramMaker(i, expected0, expected1, isLast):
    result = "            {new String[] {"
    result += f"\"resources/input_{i}a.csv\", \"resources/input_{i}b.csv\", \"resources/output_{i}.csv\""
    result += "}"
    result += f", \"resources/output_{i}.csv\", Arrays.asList(\"{expected0.strip()}\", \"{expected1.strip()}\")"
    if isLast:
        result += "}\n"
    else:
        result += "},\n"
    return result

def gen_test_files(n=10):
    expected = gen_csv(n)
    gen_java(n, expected)

def gen_java(n, expected):
    content = []
    content.append("package CompareCSV;\n")
    content.append("import org.junit.*;\n")
    content.append("import org.junit.runner.RunWith;\n")
    content.append("import org.junit.runners.Parameterized;\n")
    content.append("import org.junit.runners.Parameterized.Parameters;\n")
    content.append("import static org.junit.Assert.*;\n")
    content.append("import java.util.*;\n")
    content.append("@RunWith(Parameterized.class)\n")

    content.append("public class FuzzTest {\n")
    content.append("    String[] args;\n")
    content.append("    String output;\n")
    content.append("    List<String> expected;\n")

    content.append("    public FuzzTest(String[] args, String output, List<String> expected) {\n")
    content.append("        this.args = args;\n")
    content.append("        this.output = output;\n")
    content.append("        this.expected = expected;\n")
    content.append("    }\n")

    content.append("    @Parameters\n")
    content.append("    public static Collection<Object[]> parameters() {\n")
    content.append("        return Arrays.asList(new Object[][] {\n")
    for i in range(n):
        content.append(paramMaker(i, expected[i][0], expected[i][1], i==n-1))
    content.append("        });\n")
    content.append("    }\n")

    content.append("    @Test\n")
    content.append("    public void test() {\n")
    content.append("        try {\n")
    content.append("            new Bootstrap().start(this.args);\n")
    content.append("            List<String> actual = Helper.ReadLines(this.output);\n")
    content.append("            assertTrue(validateList(expected, actual));\n")
    content.append("        }\n")
    content.append("        catch(Exception e) { fail(\"Unexpected error.\"); }\n")
    content.append("    }\n")

    content.append("    private boolean validateList(List<String> expected, List<String> actual) {\n")
    content.append("        for (int i=0; i<expected.size(); i++) {\n")
    content.append("            if (!expected.get(i).equals(actual.get(i))) { return false; }\n")
    content.append("        }\n")
    content.append("        return true;\n")
    content.append("    }\n")
    content.append("}\n")

    with open("../src/CompareCSV/FuzzTest.java", "w") as f:
        f.writelines(content)

if __name__ == "__main__":
    gen_test_files()