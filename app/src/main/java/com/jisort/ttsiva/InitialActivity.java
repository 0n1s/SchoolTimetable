package com.jisort.ttsiva;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.jisort.ttsiva.URLs.main_url;
import static com.jisort.ttsiva.URLs.timetable_data;

public class InitialActivity extends AppCompatActivity {
    public static String  batch_name, dept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        getSupportActionBar().setTitle("E-timetable");


        Fragment fragment = new MainMenu();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place_place, fragment);
        ft.addToBackStack("1");
        ft.commit();


    }


    public static class MainMenu extends Fragment {
        Spinner spinner3, spinner;
        Button button;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.f1, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("E-timetable setup");

            spinner = (Spinner) rootView.findViewById(R.id.spinner);
            spinner3 = (Spinner) rootView.findViewById(R.id.spinner3);
            button=(Button)rootView.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Fragment fragment = new Menu2();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_place_place, fragment);
                    ft.addToBackStack("2");

                    ft.commit();

                }
            });
            fetch_depts();
            return rootView;

        }


        public void fetch_yos(final String dept) {


            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog progressDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    progressDialog.setMessage("Fetching years of study.");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("dept", dept);
                    String res = rh.sendPostRequest(main_url + "setup.php", employees);
                    return res;

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    //  new AlertDialog.Builder(getActivity()).setMessage(s).show();
                    ArrayList<String> data1 = new ArrayList<String>();
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        data1.add("Select your year of study");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String batch_name = jsonObject.getString("batch_name");
                            data1.add(batch_name);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data1);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                batch_name = spinner.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
            GetJSON jj = new GetJSON();
            jj.execute();


        }


        public void fetch_depts() {


            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog progressDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    progressDialog.setMessage("Fetching departments...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("bn", "");
                    String res = rh.sendPostRequest(main_url + "dept.php", employees);
                    return res;

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    // new AlertDialog.Builder(getActivity()).setMessage(s).show();
                  final   ArrayList<String> data1 = new ArrayList<String>();

                    final ArrayList<String> data_reference = new ArrayList<String>();
                    try {
                        /*
                        [{"dept_name":"Business Department","dept_code":"BS232"},
                        {"dept_name":"Computer Science","dept_code":"CS101"},
                        {"dept_name":"Information Technology","dept_code":"CS103"}]
                         */
                        JSONArray jsonArray = new JSONArray(s);
                        data1.add("Select your department. ");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dept_name = jsonObject.getString("dept_name");
                            data1.add(dept_name);
                            data_reference.add(jsonObject.getString("dept_code"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data1);
                        spinner3.setAdapter(adapter);

                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                switch (position)
                                {
                                    case 0:
                                        break;
                                   default:

                                        Log.d("datarefrencelen",String.valueOf( data_reference.size()));
                                        fetch_yos(data_reference.get(position-1));
                                        Log.d("dept", data_reference.get(position-1));
                                        dept = data_reference.get(position-1);
                                        break;

                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    } catch (JSONException e) {
                        Log.d("jsoneception", String.valueOf(e));
                    }


                }


            }
            GetJSON jj = new GetJSON();
            jj.execute();


        }


    }

    public static class Menu2 extends Fragment {
        Button button3;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.f2, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Download timetable..");
            button3=(Button)rootView.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetch_timetable( batch_name, dept);
                }
            });
            return rootView;

        }



        public void fetch_timetable(final String batch_name, final String dept)
        {


            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog progressDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();

                    progressDialog.setMessage("Fetching timetable.");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
                @Override
                protected String doInBackground(Void... params)
                {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("batchname", batch_name);
                    employees.put("dept", dept);
                    String res=rh.sendPostRequest(main_url+"tt.php",employees);
                    return res;

                }
                @Override
                protected void onPostExecute(String s)
                {
                    super.onPostExecute(s);

                   // new AlertDialog.Builder(getActivity()).setMessage(s).show();
                    progressDialog.dismiss();
                    timetable_data=s;
                    Log.d("timetable_data",timetable_data);

                    if(!timetable_data.equals("")||timetable_data.length()<3)
                    {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                    else {

                        new AlertDialog.Builder(getActivity()).setMessage("Invalid timetable").setPositiveButton("Cancel", null).show().setTitle("Error!");
                    }

                }

            }
            GetJSON jj =new GetJSON();
            jj.execute();


        }
    }
}
