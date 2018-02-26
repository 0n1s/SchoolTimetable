package com.jisort.ttsiva;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jisort.ttsiva.InitialActivity.batch_name;
import static com.jisort.ttsiva.InitialActivity.dept;
import static com.jisort.ttsiva.InitialActivity.islec;
import static com.jisort.ttsiva.InitialActivity.lecturer_id;
import static com.jisort.ttsiva.URLs.timetable_data;

public class MainActivity extends AppCompatActivity
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(islec)
        {
            getSupportActionBar().setSubtitle("For "+lecturer_id);
        }
        else
        getSupportActionBar().setSubtitle("For "+batch_name + " department code " + dept );
        //get json file from assets folder


        try
        {
            //timetable_data =readJSONFromAsset();

Log.d("timetable", timetable_data);

        }  catch (Exception e) {
            e.printStackTrace();
        }


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(today);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }



/*
$batch_name = '4.2';
$dept= 'CS101';
 */



    public String readJSONFromAsset()
    {
        String json = null;
        try {
            InputStream is = getAssets().open("timetable.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            Log.d("Error", String.valueOf(ex));
            return null;
        }
        return json;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_day, container, false);
            return rootView;

        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

            switch (position) {

                case 0:
                    Fragment_days fragment_days = new Fragment_days();
                    Bundle bundle = new Bundle();
                    bundle.putString("day", "1");
                    fragment_days.setArguments(bundle);
                    return fragment_days;

                case 1:
                    Fragment_days fragment_days1 = new Fragment_days();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("day", "2");
                    fragment_days1.setArguments(bundle2);
                    return fragment_days1;

                case 2:
                    Fragment_days fragment_days2 = new Fragment_days();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("day", "3");
                    fragment_days2.setArguments(bundle3);
                    return fragment_days2;

                case 3:
                    Fragment_days fragment_days3 = new Fragment_days();
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("day", "4");
                    fragment_days3.setArguments(bundle4);
                    return fragment_days3;

                case 4:
                    Fragment_days fragment_days4 = new Fragment_days();
                    Bundle bundle5 = new Bundle();
                    bundle5.putString("day", "5");
                    fragment_days4.setArguments(bundle5);
                    return fragment_days4;

            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount()
        {
            return 5;
        }




    }


    public static class Fragment_days extends Fragment
    {

        public RecyclerView recyclerView;
        public RecyclerView.Adapter adapter;
        private Context mContext;
        public static final String MyPREFERENCES = "MyPrefs";
        SharedPreferences sharedpreferences;
        RecyclerView.LayoutManager layoutManager;
        String actvity_title;
        SharedPreferences sharedPreferences;
        public List<DataClass> listitems;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_day, container, false);



                String day_sent = getArguments().getString("day");
                Log.d("day",day_sent);


            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);//done here
            layoutManager = new GridLayoutManager(mContext, 1);
            recyclerView.setLayoutManager(layoutManager);
            try {
                 String roomnumber;
                 String course;
                 String start_time;
                 String end_time;
                 String unit_name;
                 String lecturer_name;
                 String day;

                listitems = new ArrayList<>();
                HashMap<String,String> bounds_data= new HashMap<String, String>();
                DataClass time_table_data;
                JSONArray jsonArray = new JSONArray(timetable_data);
                for (int i=0; i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    day=jsonObject.getString("day");

                    if(day_sent.equals("1") && day.equals("1"))
                    {
                        roomnumber= jsonObject.getString("place");
                        course =jsonObject.getString("course");
                        start_time= jsonObject.getString("startTime");
                        end_time =jsonObject.getString("endTime");
                        unit_name =jsonObject.getString("name");
                        lecturer_name=jsonObject.getString("prof");
                        time_table_data = new DataClass(roomnumber,course,start_time, end_time,unit_name, lecturer_name,day);
                        listitems.add(time_table_data);
                        adapter = new MyAdapter(listitems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                    if(day_sent.equals("2") && day.equals("2"))
                    {
                        roomnumber= jsonObject.getString("place");
                        course =jsonObject.getString("course");
                        start_time= jsonObject.getString("startTime");
                        end_time =jsonObject.getString("endTime");
                        unit_name =jsonObject.getString("name");
                        lecturer_name=jsonObject.getString("prof");
                        time_table_data = new DataClass(roomnumber,course,start_time, end_time,unit_name, lecturer_name,day);
                        listitems.add(time_table_data);
                        adapter = new MyAdapter(listitems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                    if(day_sent.equals("3") && day.equals("3"))
                    {
                        roomnumber= jsonObject.getString("place");
                        course =jsonObject.getString("course");

                        start_time= jsonObject.getString("startTime");
                        end_time =jsonObject.getString("endTime");
                        unit_name =jsonObject.getString("name");
                        lecturer_name=jsonObject.getString("prof");
                        time_table_data = new DataClass(roomnumber,course,start_time, end_time,unit_name, lecturer_name,day);
                        listitems.add(time_table_data);
                        adapter = new MyAdapter(listitems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                    if(day_sent.equals("4") && day.equals("4"))
                    {
                        roomnumber= jsonObject.getString("place");
                        course =jsonObject.getString("course");
                        start_time= jsonObject.getString("startTime");
                        end_time =jsonObject.getString("endTime");
                        unit_name =jsonObject.getString("name");
                        lecturer_name=jsonObject.getString("prof");
                        time_table_data = new DataClass(roomnumber,course,start_time, end_time,unit_name, lecturer_name,day);
                        listitems.add(time_table_data);
                        adapter = new MyAdapter(listitems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                    if(day_sent.equals("5") && day.equals("5"))
                    {
                        roomnumber= jsonObject.getString("place");
                        course =jsonObject.getString("course");
                        start_time= jsonObject.getString("startTime");
                        end_time =jsonObject.getString("endTime");
                        unit_name =jsonObject.getString("name");
                        lecturer_name=jsonObject.getString("prof");
                        time_table_data = new DataClass(roomnumber,course,start_time, end_time,unit_name, lecturer_name,day);
                        listitems.add(time_table_data);
                        adapter = new MyAdapter(listitems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }


                }
            } catch (JSONException e) {
              Log.d("json erro", String.valueOf(e));
            }

            return rootView;
        }



    }
}
