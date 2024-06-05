package com.cbl.cityrtgs.engine.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

    public static String toDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
        return formatter.format(new Date());
    }
}
