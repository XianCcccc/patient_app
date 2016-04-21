package com.xian.patientappv1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment{

    private RecyclerView recyclerView;

    public static final String PERF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle myDrawerToggle;
    private DrawerLayout myDrawerLayout;

    private boolean myUserLearnedDrawer;
    private boolean myFromSavedInstanceState;
    private View containerView;
    private InformationAdapter adapter;

    public NavDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myUserLearnedDrawer = Boolean.valueOf(readFromPerferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if(savedInstanceState != null)
            myFromSavedInstanceState = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        adapter = new InformationAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "OnClick " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "OnLongClick " + position, Toast.LENGTH_SHORT).show();
            }
        }));
        return layout;
    }

    public static List<Information> getData()
    {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_person_black_36dp, R.mipmap.ic_event_black_36dp,
                R.mipmap.ic_message_black_36dp, R.mipmap.ic_favorite_black_36dp,
                R.mipmap.ic_info_outline_black_36dp};
        String[] titles = {"Profile", "Appointment", "Comments", "Favorite", "Info"};
        for(int i = 0; i < titles.length; i++)
        {
            Information information = new Information();
            information.setIconId(icons[i]);
            information.setTitle(titles[i]);
            data.add(information);
        }
        System.out.println(data);
        return data;
    }



    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        myDrawerLayout = drawerLayout;
        myDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!myUserLearnedDrawer){
                    myUserLearnedDrawer = true;
                    saveToPerferences(getActivity(), KEY_USER_LEARNED_DRAWER,  myUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset < 0.6)
                    toolbar.setAlpha(1 - slideOffset);
            }
        };

        if(!myUserLearnedDrawer && !myFromSavedInstanceState)
            myDrawerLayout.openDrawer(containerView);
        myDrawerLayout.setDrawerListener(myDrawerToggle);
        myDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                myDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPerferences(Context context, String perferenceName, String perferenceValue)
    {
        SharedPreferences sharedPrefences= context.getSharedPreferences(PERF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefences.edit();
        editor.putString(perferenceName, perferenceValue);
        editor.apply();
    }


    public static String readFromPerferences(Context context, String perferenceName, String defaultValue)
    {
        SharedPreferences sharedPrefences= context.getSharedPreferences(PERF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPrefences.getString(perferenceName, defaultValue);
    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener ) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child != null && clickListener != null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(child != null && clickListener != null && gestureDetector.onTouchEvent(e))
            {
                 clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);


    }
}
