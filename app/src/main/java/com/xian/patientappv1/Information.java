package com.xian.patientappv1;

/**
 * Created by PTTsiuoLIVIA on 3/19/16.
 */
public class Information {
    private int iconId;
    private String title;

    public Information() {
        this.iconId = -1;
        this.title = "";
    }

    public Information(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
