package com.xian.patientappv1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.xian.patientappv1.tabs.SlidingTabLayout;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private SlidingTabLayout slidingTabLayout;
    private NonSwipeableViewpager viewPager;
    private static ArrayList<ActivityData> activityDatas = new ArrayList<>();
    static String userUid = "";
    private Toolbar toolbar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //activity_main_appbar
        Firebase.setAndroidContext(this);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        //pushTestData();

        NavDrawerFragment drawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        viewPager = (NonSwipeableViewpager)findViewById(R.id.pager);

        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);

//        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.primary);
//            }
//        });

        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.iron));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.primary));
        slidingTabLayout.setViewPager(viewPager);

        userUid = SaveSharedPreference.getUserUid(MainActivity.this);
        System.out.println(userUid);
        if(userUid.length() == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {

        }

        //startActivity(new Intent(this, LoginActivity.class));
        //readTestData();
        Log.i("test", activityDatas.size() + " datas");
        getUserName();
        readTestData();

    }

    public void getUserName()
    {
        System.out.println("aaa");
        if(userUid.length() > 0) {
            Firebase ref = new Firebase("https://patient-app.firebaseio.com");
            Firebase userref = ref.child("users").child(userUid).child("profile").child("name");
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println(snapshot.getValue());
                    toolbar.setTitle(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.navigate) {
            SaveSharedPreference.removeUserUid(this);
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static void readTestData()
    {
        Firebase ref = new Firebase("https://patient-app.firebaseio.com");
        Firebase userref = ref.child("users").child(userUid).child("activity_datas");

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " datas");
                ArrayList<ActivityData> tempData = new ArrayList<ActivityData>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String s = postSnapshot.child("date").getValue().toString();
                    long realstep = (long) postSnapshot.child("realStep").getValue();
                    long targetStep = (long) postSnapshot.child("targetStep").getValue();

                    ActivityData data = new ActivityData(s, realstep, targetStep);
                    tempData.add(data);
                }

                activityDatas = tempData;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }


    public static void pushTestData()
    {
        ArrayList<ActivityData> list = new ArrayList<>();
        for(int i = 1; i < 32; i++)
        {
            int target = 10000;
            int realStep = (int) Math.round(Math.random() * 7000 + 7000);
            String date = "2016/1/" + i;
            ActivityData tempdata = new ActivityData(date, realStep, target);
            list.add(tempdata);
        }

        for(int i = 1; i < 30; i++)
        {
            int target = 8000;
            int realStep = (int) Math.round(Math.random() * 7000 + 7000);
            String date = "2016/2/" + i;
            ActivityData tempdata = new ActivityData(date, realStep, target);
            list.add(tempdata);
        }

        for(int i = 1; i < 25; i++)
        {
            int target = 9000;
            int realStep = (int) Math.round(Math.random() * 7000 + 7000);
            String date = "2016/3/" + i;
            ActivityData tempdata = new ActivityData(date, realStep, target);
            list.add(tempdata);
        }



        Firebase ref = new Firebase("https://patient-app.firebaseio.com");

        Firebase userref = ref.child("users").child("5882239c-cb60-4d1d-8a2c-76c87435cfb6");
        Firebase activityref = userref.child("activity_datas");

        for(int i = 0; i < list.size(); i++)
        {
            activityref.push().setValue(list.get(i));
            System.out.print("heihei");
        }
    }


    class MyPageAdapter extends FragmentPagerAdapter {
        int icons[] = {R.mipmap.ic_directions_walk_black_48dp, R.mipmap.ic_equalizer_black_48dp, R.mipmap.ic_ondemand_video_black_48dp};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0, 0, 100, 100);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment {
        private WebView webview;
        public static MyFragment getInstance(int position)
        {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);

            return myFragment;
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_barchart, container, false);
            Bundle bundle = getArguments();
            if(bundle != null) {
                if(bundle.getInt("position") == 0)
                {
                    layout = inflater.inflate(R.layout.fragment_piechart, container, false);
                    PieChart mPieChart = (PieChart) layout.findViewById(R.id.piechart);

                    mPieChart.addPieSlice(new PieModel("Done", 1240, Color.parseColor("#FE6DA8")));
                    mPieChart.addPieSlice(new PieModel("Undone", 8500, Color.parseColor("#56B7F1")));

                    mPieChart.startAnimation();
                }
                else if(bundle.getInt("position") == 1)
                {

                    // TODO: Implement valid function here - if no activity datas in database or no networking connecting


                    layout = inflater.inflate(R.layout.fragment_barchart, container, false);
                    BarChart mBarChart = (BarChart) layout.findViewById(R.id.barchart);

                    Log.i("test", activityDatas.size()+"hahah");
                    for(ActivityData activityData: activityDatas)
                    {
                        String date = activityData.formattedDate();
                        long realStep = activityData.getRealStep();
                        long targetStep = activityData.getTargetStep();
                        int color = 0xFF1FF4AC;
                        if(realStep > targetStep * 1.1)
                            color = R.color.high;
                        else if(realStep < targetStep * 1.1)
                            color = R.color.low;
                        else
                            color = R.color.mid;
                        BarModel barModel = new BarModel(realStep, color);
                        barModel.setLegendLabel(date);
                        mBarChart.addBar(barModel);
                    }

                    mBarChart.startAnimation();
                }
            }
            return layout;
        }
    }
}
