package excelUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import customFunctions.Constants;
import driver.DriverScript;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	private static XSSFRow Row;

	public static void setExcelFile(String Path) throws Exception {
		try {
				FileInputStream ExcelFile = new FileInputStream(Path);
				ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			DriverScript.result = false;
		}
	}

	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
				String CellData = Cell.getStringCellValue();
				return CellData;
		} catch (Exception e) {
			DriverScript.result = false;
			return "";
		}
	}

	public static int getRowCount(String SheetName) {
		int iNumber = 0;
		try {
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
				iNumber = ExcelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			DriverScript.result = false;
		}
		return iNumber;
	}

	public static int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int iRowNum = 0;
		try {
				int rowCount = ExcelUtils.getRowCount(SheetName);
				for (; iRowNum < rowCount; iRowNum++) {
					if (ExcelUtils.getCellData(iRowNum, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
		} catch (Exception e) {
			DriverScript.result = false;
		}
		return iRowNum;
	}

	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		try {
				for (int i = iTestCaseStart; i <= ExcelUtils.getRowCount(SheetName); i++) {
				if (!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Test_Case_ID_Col, SheetName))) {
					int number = i;
					return number;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number = ExcelWSheet.getLastRowNum() + 1;
			return number;
		} catch (Exception e) {
			DriverScript.result = false;
			return 0;
		}
	}

	@SuppressWarnings("static-access")
	public static void setCellData(String Result, int RowNum, int ColNum, String SheetName) throws Exception {
		try {

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constants.Test_Data);
			ExcelWBook.write(fileOut);
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Test_Data));
		} catch (Exception e) {
			DriverScript.result = false;
		}
	}
}
