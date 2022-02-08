package parcers

class JsonDataParcer {
    private JsonDataParcer() {
        //not called
    }

    public static Object[][] parseFile(String[] fields, File tFile) {
        def parsedJson = new com.google.gson.JsonParser().parse(new FileReader(tFile).text).getAsJsonObject()
        assert parsedJson.get("TestCase"), "TestCase section missing in dataparam file"
        def testCases = parsedJson.get("TestCase").getAsJsonArray()
        int NumberOfTestCases = testCases.size()
        int NumberOfParameters = fields.size()
        Object[][] testParam = new String[NumberOfTestCases][NumberOfParameters]
        if (parsedJson.get("Common")) {
            for (int j = 0; j < fields.size(); j++) {
                def fieldValue = parsedJson.get("Common").getAsJsonArray()[0].asJsonObject.get(fields[j])
                if (fieldValue) {
                    for (int i = 0; i < NumberOfTestCases; i++)
                        testParam[i][j] = fieldValue.asString
                }
            }
        }
        for (int i = 0; i < NumberOfTestCases; i++) {
            for (int j = 0; j < fields.size(); j++) {
                def fieldValue = testCases[i].asJsonObject.get(fields[j])
                if (fieldValue)
                    testParam[i][j] = fieldValue.asString
            }
        }
        return testParam
    }

}