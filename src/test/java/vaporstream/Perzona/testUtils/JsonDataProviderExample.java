package vaporstream.Perzona.testUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDataProviderExample {

    @DataProvider(name = "jsonData")
    public Object[][] jsonDataProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("test_data.json");

        // Read JSON data into a List of TestData objects
        List<TestData> testDataList = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, TestData.class));

        // Convert the List of TestData into a 2D array
        Object[][] data = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            data[i][0] = testDataList.get(i);
        }

        return data;
    }
//
//    @Test(dataProvider = "jsonData")
//    public void testWithData(TestData testData) {
//        // Your test logic here using testData
//        System.out.println("Test with data: " + testData);
//    }
//
    public static class TestData {
        private String countryCode;
        private String countryName;

        // Getter and setter methods for your JSON properties

        @Override
        public String toString() {
            return "TestData{" +
                    "countryCode='" + countryCode + '\'' +
                    ", countryName=" + countryName +
                    '}';
        }
    }
}

