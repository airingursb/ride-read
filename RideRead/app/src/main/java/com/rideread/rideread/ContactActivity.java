package com.rideread.rideread;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Jackbing on 2017/2/1.
 * 联系骑阅
 */

public class ContactActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_contact_layout);
    }
}
