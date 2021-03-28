public class MaritalFieldNames {
    public static String setFieldName(String marital, String gender) {
        if (marital.equals("M") )
        {
            marital = "Married";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        else if (marital.equals("S"))
        {
            marital = "Single";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        else {
            marital = "Unknown";
            if (gender.equals("M")) { marital = "Male-" + marital;}
            else if (gender.equals("F")) { marital = "Female-" + marital;}
            else { marital = "Unknown-" + marital;}
        }
        return marital;
    }

}
