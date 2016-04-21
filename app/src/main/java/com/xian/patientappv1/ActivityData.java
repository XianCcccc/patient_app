package com.xian.patientappv1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PTTsiuoLIVIA on 3/25/16.
 */
public class ActivityData
{
    private String date;
    private long realStep;
    private long targetStep;

    SimpleDateFormat initialSdf = new SimpleDateFormat("yyyy/M/d");


    public ActivityData(String date, long realStep, long targetStep) {
        this.date = date;
        this.realStep = realStep;
        this.targetStep = targetStep;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getRealStep() {
        return realStep;
    }

    public void setRealStep(long realStep) {
        this.realStep = realStep;
    }

    public long getTargetStep() {
        return targetStep;
    }

    public void setTargetStep(long targetStep) {
        this.targetStep = targetStep;
    }

    public String formattedDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
        try {
            Date d = initialSdf.parse(date);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ActivityData{" +
                "date='" + date + '\'' +
                ", realStep=" + realStep +
                ", targetStep=" + targetStep +
                '}';
    }
}
