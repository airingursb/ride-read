package com.rideread.rideread.data.been;

/**
 * Created by SkyXiao on 2017/4/4.
 */

public class Tab {
    private int icon;
    private int title;
    private Class fragment;

    public Tab(Class fragment,int title, int icon) {
        this.title=title;
        this.icon = icon;
        this.fragment = fragment;
    }


    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
