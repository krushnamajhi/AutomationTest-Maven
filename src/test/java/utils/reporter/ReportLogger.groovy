package utils.reporter

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.MarkupHelper
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.lang.reflect.Method

class ReportLogger {

    Logger log = LoggerFactory.getLogger(this.getClass().getName())
    ExtentTest extentTest
    String testName
    String className

    protected static ExtentReports extentReport

    static void setExtentReport(ExtentReports extentReport)
    {
        this.extentReport = extentReport
    }

    void startTest(Method testMethod)
    {
        testName = testMethod.getName()
        className = testMethod.getDeclaringClass().getName()
        extentTest = extentReport.createTest(className + " || "+testName)
        extentTest.assignCategory(className)

    }

    void logInfo(String s) {
        log.info(s)
        logToExtentReport(Status.INFO, s)
    }

    void logPass(String s) {
        log.info(s)
        logToExtentReport(Status.PASS, s)
    }

    void logFail(String s, String screenshot = null) {
        log.error(s)
        logToExtentReport(Status.FAIL, s, screenshot)
    }

    private void logToExtentReport(Status status, String s, String screenshot = null) {
        if(screenshot!= null)
        {
            def m = MediaEntityBuilder.createScreenCaptureFromBase64String(toBase64(screenshot)).build()
            extentTest.log(status, s, m)
        }
        else
        {
            extentTest.log(status, s)
        }
    }

    static def toBase64(String filepath)
    {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filepath))
       return Base64.getEncoder().encodeToString(fileContent)
    }

    void logFailJson(String json){
        extentTest.log(Status.FAIL,MarkupHelper.createCodeBlock(json, CodeLanguage.JSON))
    }

    void logFailXML(String xml){
        extentTest.log(Status.FAIL,MarkupHelper.createCodeBlock(xml, CodeLanguage.XML))
    }

    void logInfoJson(String json){
        extentTest.log(Status.INFO,MarkupHelper.createCodeBlock(json, CodeLanguage.JSON))
    }

    void logInfoXML(String xml){
        extentTest.log(Status.INFO,MarkupHelper.createCodeBlock(xml, CodeLanguage.XML))
    }

    void logInfoTable(List<List<String>> tableData){
        extentTest.log(Status.INFO,MarkupHelper.toTable(tableData))
    }
}
