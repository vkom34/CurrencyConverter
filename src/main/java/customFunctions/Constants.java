package customFunctions;

public class Constants {

	// System Variables
	public static final String URL = "https://www.westpac.co.nz/";
	public static final String Test_Data = ".\\src\\main\\java\\excelUtils\\DataEngine.xlsx";
	public static final String OR_PATH = ".\\src\\main\\java\\ORManager\\OR.txt";
	public static final String Test_Data_File = "DataEngine.xlsx";
	public static final String LOG_FAIL = "FAIL";
	public static final String LOG_PASS = "PASS";

	// UI Variables
	public static final String FX_Link = "FX, travel & migrant";
	public static final String Currency_Link = "Currency converter";
	public static final String Frame_Name = "westpac-iframe";

	// Data Sheet Column Numbers
	public static final int Test_Case_ID_Col = 0;
	public static final int TestScenarioID_Col = 1;
	public static final int PageObject_Col = 4;
	public static final int ActionKeyword_Col = 5;
	public static final int Trigger = 2;
	public static final int Result_Col = 3;
	public static final int DataSet_Col = 6;
	public static final int TestStepResult_Col = 7;

	// Data Engine Excel sheets
	public static final String TestSteps_Sheet = "Test Steps";
	public static final String TestCases_Sheet = "Test Cases";
}
