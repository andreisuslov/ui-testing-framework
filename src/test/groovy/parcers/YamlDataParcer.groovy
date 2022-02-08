package parcers

import org.yaml.snakeyaml.Yaml

class YamlDataParcer {
    private YamlDataParcer() {
        //not called
    }

    public static Object[][] parseFile(String[] fields, File tFile) {
        Map<String, Map<String, String>> parsedYaml = new Yaml().load(new FileReader(tFile))
        def testcasesList = parsedYaml.keySet().dropWhile {it=='Common'}
        int NumberOfTestCases = testcasesList.size()
        assert NumberOfTestCases > 0, "TestCase section missing in dataparam file"
        int NumberOfParameters = fields.size()
        Object[][] testParam = new String[NumberOfTestCases][NumberOfParameters]
        def commonParameters = parsedYaml.get('Common')
        if (commonParameters) {
            for (int j = 0; j < NumberOfParameters; j++) {
                def fieldValue = commonParameters.get(fields[j])
                if (fieldValue) {
                    for (int i = 0; i < NumberOfTestCases; i++)
                        testParam[i][j] = fieldValue
                }
            }
        }
        for (int i = 0; i < NumberOfTestCases; i++) {
            for (int j = 0; j < NumberOfParameters; j++) {
                def row = parsedYaml.get(testcasesList.getAt(i))
                if (row){
                    def fieldValue = row.get(fields[j])
                    if (fieldValue)
                        testParam[i][j] = fieldValue.toString()
                }
            }
        }
        return testParam
    }

}