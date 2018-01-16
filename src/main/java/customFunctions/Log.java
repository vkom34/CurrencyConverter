package customFunctions;

import org.apache.log4j.Logger;

public class Log {

	// Initialize Log4j logs
	private static Logger Log = Logger.getLogger(Log.class.getName());

	public static void startTestCase(String sTestCaseName) {
		Log.info(sTestCaseName);
	}

	public static void endTestCase(String sTestCaseName) {
		Log.info(sTestCaseName + " ends here");
	}

	public static void info(String message) {
		Log.info(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}

}