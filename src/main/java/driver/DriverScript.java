package driver;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.Test;

import customFunctions.Constants;
import customFunctions.Keywords;
import customFunctions.Log;
import excelUtils.ExcelUtils;

public class DriverScript {

	public static Properties OR;
	public static Keywords keywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];

	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String trigger;
	public static String sData;
	public static boolean result;

	public DriverScript() throws NoSuchMethodException, SecurityException {
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
	}

	@Test
	public void f() throws Exception {
		ExcelUtils.setExcelFile(Constants.Test_Data);
		DOMConfigurator.configure("log4j.xml");
		String Path_OR = Constants.OR_PATH;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR = new Properties(System.getProperties());
		OR.load(fs);

		DriverScript startEngine = new DriverScript();
		startEngine.execute_Test();

	}

	private void execute_Test() throws Exception {
		int totalTestCases = ExcelUtils.getRowCount(Constants.TestCases_Sheet);
		for (int testcase = 1; testcase < totalTestCases; testcase++) {
			result = true;
			sTestCaseID = ExcelUtils.getCellData(testcase, Constants.Test_Case_ID_Col, Constants.TestCases_Sheet);
			trigger = ExcelUtils.getCellData(testcase, Constants.Trigger, Constants.TestCases_Sheet);
			if (trigger.equals("Yes")) {
				Log.startTestCase(sTestCaseID);
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Test_Case_ID_Col,Constants.TestSteps_Sheet);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.TestSteps_Sheet, sTestCaseID, iTestStep);
				result = true;
				for (; iTestStep < iTestLastStep; iTestStep++) {
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.ActionKeyword_Col,Constants.TestSteps_Sheet);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.PageObject_Col,Constants.TestSteps_Sheet);
					sData = ExcelUtils.getCellData(iTestStep, Constants.DataSet_Col, Constants.TestSteps_Sheet);
					execute_Actions();
					if (result == false) {
						Log.endTestCase(sTestCaseID);
						ExcelUtils.setCellData(Constants.LOG_FAIL, testcase, Constants.Result_Col,Constants.TestCases_Sheet);
						break;
					}
				}
				if (result == true) {
					Log.endTestCase(sTestCaseID);
					ExcelUtils.setCellData(Constants.LOG_PASS, testcase, Constants.Result_Col,Constants.TestCases_Sheet);
				}
			}
		}
	}

	private static void execute_Actions() throws Exception {

		for (int i = 0; i < method.length; i++) {

			if (method[i].getName().equals(sActionKeyword)) {
				method[i].invoke(keywords, sPageObject, sData);
				if (result == true) {
					ExcelUtils.setCellData(Constants.LOG_PASS, iTestStep, Constants.TestStepResult_Col,Constants.TestSteps_Sheet);
					break;
				} else {
					ExcelUtils.setCellData(Constants.LOG_FAIL, iTestStep, Constants.TestStepResult_Col,Constants.TestSteps_Sheet);
					keywords.closeBrowser("", "");
					break;
				}
			}
		}
	}
}
