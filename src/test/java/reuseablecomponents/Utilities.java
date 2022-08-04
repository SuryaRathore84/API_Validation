package reusablecomponents;

import java.io.IOException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.time.LocalDateTime;

import java.time.ZoneId;

import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;

import java.util.Date;

import java.util.TimeZone;

import config.FrameworkException;

public class Utilities {

	/**
	 * 
	 * Get timestamp in yyyymmdd_hhmmss format.
	 * 
	 * @param timeZone - local or utc
	 * 
	 * @return timestamp in desired format.
	 * 
	 */

	public static String getTimeStamp(String timeZone) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

		if (timeZone.toLowerCase().equals("utc")) {

			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		} else {

			dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

		}

		return dateFormat.format(new Date());

	}

	/**
	 * 
	 * Returns current date in MM/DD/YYYY format.
	 *
	 * 
	 * 
	 * @return Returns current date. @ in case of error.
	 * 
	 */

	public static String getCurrentDate() {

		int month, date, year;

		// String strDate, strMonth;

		String[] Date = LocalDateTime.now().toString().split("T")[0].split("-");

		date = Integer.parseInt(Date[2]);

		month = Integer.parseInt(Date[1]);

		year = Integer.parseInt(Date[0]);

		return month + "/" + date + "/" + year;

	}

	/**
	 *
	 * 
	 * 
	 * @param DateToSelect will be in "T+1"
	 * 
	 * @param dateFormat   MM/dd/yyyy or 2/8/2018 >> M/d/yyyy
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 */

	public static String dateFormat(String DateToSelect, String dateFormat) {

		String formatters = "";

		try {

			if (!isNull_Empty_WhiteSpace(DateToSelect)) {

				LocalDate localDateT = LocalDate.now();

				LocalDate localDateT_1 = null;

				if (DateToSelect.toLowerCase().contains("+".toLowerCase())) {

					String[] DateToSelectArr = DateToSelect.split("\\+");

					String DayNum = DateToSelectArr[1];

					int DaysToIncrement = Integer.parseInt(DayNum.trim());

					localDateT_1 = localDateT.plus(DaysToIncrement, ChronoUnit.DAYS);

				} else if (DateToSelect.toLowerCase().contains("-".toLowerCase())) {

					// it contain the - and we need t - X value ///////

					String[] DateToSelectArr = DateToSelect.split("\\-");

					String DayNum = DateToSelectArr[1];

					int DaysToIncrement = Integer.parseInt(DayNum.trim());

					localDateT_1 = localDateT.minus(DaysToIncrement, ChronoUnit.DAYS);

					// localDateT_1 = localDateT.plus(DaysToIncrement, ChronoUnit.MONTHS);

				} else if (DateToSelect.contains("/")) {

					localDateT_1 = LocalDate.parse(formatDate(DateToSelect, "MM/dd/yyyy"),

							DateTimeFormatter.ofPattern("MM/dd/yyyy"));

				} else if (DateToSelect.matches("^[a-zA-Z]{4,}, [a-zA-Z]{4,} [0-9]{2}, [0-9]{4}$")

						|| DateToSelect.toLowerCase().matches("^[a-zA-Z]{4,}, may [0-9]{2}, [0-9]{4}$")) {

					localDateT_1 = LocalDate.parse(DateToSelect, DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));

				} else if (DateToSelect.matches("^[a-zA-Z]{4,}, [a-zA-Z]{4,} [0-9]{1}, [0-9]{4}$")

						|| DateToSelect.toLowerCase().matches("^[a-zA-Z]{4,}, may [0-9]{1}, [0-9]{4}$")) {

					localDateT_1 = LocalDate.parse(DateToSelect, DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

				} else {

					//////// Do nothing we need current date only////////////////////

					localDateT_1 = localDateT.plus(0, ChronoUnit.DAYS);

				}

				if (!(isNull_Empty_WhiteSpace(dateFormat))) {

					formatters = DateTimeFormatter.ofPattern(dateFormat).format(localDateT_1);

					// formatters_1 =

					// DateTimeFormatter.ofPattern("MM/dd/YYYY").format(localDateT_1);

				} else {

					formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(localDateT_1);

				}

			}

			// String formatters1 =

			// DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDateT_1);

			// String formatters3 =

			// DateTimeFormatter.ofPattern("dd-MM-YYYY").format(localDateT_1);

		} catch (Exception e) {

			System.out.println("Error occured dateFormat " + e.getMessage());

		}

		return formatters;

	}

	public static boolean isNull_Empty_WhiteSpace(String CmpVal) {

		try {

			if (CmpVal == null) {

				return true;

			} else {

				CmpVal = CmpVal.replaceAll("\u00a0", "");

				CmpVal = CmpVal.replaceAll("&nbsp", "").trim();

			}

		} catch (Exception e) {

			System.out.println("Error occured isNull_Empty_WhiteSpace " + e.getMessage());

		}

		if (CmpVal.trim() != "" && CmpVal != null && (CmpVal.isEmpty()) == false) {

			return false;

		} else {

			return true;

		}

		// return CmpVal != null && !string.isEmpty() &&

		// !string.trim().isEmpty();

	}

	/**
	 * 
	 * Returns the current date in (MM/DD/YYYY) format.
	 *
	 * 
	 * 
	 * @param Date
	 * 
	 * @param format TODO
	 * 
	 * @return Date in in (MM/DD/YYYY) format. @
	 * 
	 */

	public static String formatDate(String Date, String format) {

		int date_Day, date_Month, date_Year;

		try {

			date_Day = Integer.parseInt(Date.split("/")[1]);

			date_Month = Integer.parseInt(Date.split("/")[0]) - 1;

			date_Year = Integer.parseInt(Date.split("/")[2]) - 1900;

			@SuppressWarnings("deprecation")

			Date convertedServiceDate = new Date(date_Year, date_Month, date_Day);

			SimpleDateFormat sdf = new SimpleDateFormat(format);

			Date = sdf.format(convertedServiceDate);

			return Date;

		} catch (Exception e) {

			throw new FrameworkException(

					"Unknown Error encountered while formatting. ---" + e.getClass() + "---" + e.getMessage());

		}

	}

	public static String getMethodName() {

		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];

		final String s = e.getMethodName();

//                            System.out.println(s);

		return s;

	}

}