package testHelloWork.util;

public class XSLTFunctionsOLD {

    public static String toUpperCase(String input) {
        return input != null ? input.toUpperCase() : null;
    }

    public static String frenchDateToUTC(String frenchDate) {
        if (frenchDate == null) {
            return "";
        }
        try {
            java.text.SimpleDateFormat frenchFormat = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.FRANCE);
            java.util.Date date = frenchFormat.parse(frenchDate);
            java.text.SimpleDateFormat utcFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            return utcFormat.format(date);
        } catch (java.text.ParseException e) {
            return "";
        }
    }
}
