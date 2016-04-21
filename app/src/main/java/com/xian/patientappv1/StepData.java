package com.xian.patientappv1;

import java.util.Date;

/**
 * Created by PTTsiuoLIVIA on 3/17/16.
 */
public class StepData
{
    private Date timestamp;
    private int steps;
    private int aimSteps;

    public StepData()
    {
        timestamp = new Date();
        steps = -1;
        aimSteps = -1;
    }

    public StepData(Date timestamp, int steps, int aimSteps) {
        this.timestamp = timestamp;
        this.steps = steps;
        this.aimSteps = aimSteps;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getAimSteps() {
        return aimSteps;
    }

    public void setAimSteps(int aimSteps) {
        this.aimSteps = aimSteps;
    }
}
