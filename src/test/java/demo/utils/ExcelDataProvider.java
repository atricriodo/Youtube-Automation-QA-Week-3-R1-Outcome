package demo.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] excelData() {
        String fileLocation = System.getProperty("user.dir")+"/src/test/resources/qa_codeathon_week3.xlsx";
        System.out.println("Fetching excel file from "+fileLocation);
        return ExcelReaderUtil.readExcelData(fileLocation);
    }
}