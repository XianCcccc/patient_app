package com.xian.patientappv1;

import com.xian.patientappv1.ActivityData;

import java.util.ArrayList;

/**
 * Created by PTTsiuoLIVIA on 3/29/16.
 */
public class ActivityDataList {
    private ArrayList<ActivityData> datas = new ArrayList<>();

    public ActivityDataList(ArrayList<ActivityData> datas) {
        this.datas = datas;
    }

    public ActivityDataList()
    {
        datas = new ArrayList<ActivityData>();
    }

    public boolean addData(ActivityData newData)
    {
        if(datas.isEmpty())
        {
            this.datas.add(newData);
            return true;
        }

        for(ActivityData activityData: datas)
        {
            if(activityData.getDate().equals(newData.getDate()))
            {
                activityData.setTargetStep(newData.getTargetStep());
                activityData.setRealStep(newData.getRealStep());
                return true;
            }

        }
        this.datas.add(newData);
        return true;
    }



}
