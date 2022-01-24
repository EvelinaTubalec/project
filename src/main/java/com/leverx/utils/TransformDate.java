package com.leverx.utils;

import lombok.AllArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
public class TransformDate {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static LocalDate addPeriodToLocalDate(Integer period) throws ParseException {
        String s = LocalDate.now().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);

        c.setTime(format.parse(s));
        c.add(Calendar.DATE, period);  // number of days to add
        s = format.format(c.getTime());
        Date docDate = format.parse(s);
        return docDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
