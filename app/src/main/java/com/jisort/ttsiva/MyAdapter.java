package com.jisort.ttsiva;
/**
 * Created by sikinijjs on 10/1/17.
 */
        import android.app.AlarmManager;
        import android.app.AlertDialog;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

        import static android.content.Context.ALARM_SERVICE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{



     Calendar cal;
     AlarmManager alarmManager;

    private List<DataClass> listItems;

    public MyAdapter(List<DataClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }
    SharedPreferences pref;
    private Context context;
    String alarmid;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent,false);
        return new ViewHolder(v);
    }
    String day;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {

         pref = context.getSharedPreferences("MyPref", 0);


        final DataClass dataClass =listItems.get(position);

        switch (dataClass.getDay()){
            case "1":
                day="Monday";
                break;
            case "2":
                day="Tuesday";
                break;
            case "3":
                day="Wednesday";
                break;
            case "4":
                day="Thursday";
                break;
            case "5":
                day="Friday";
                break;
            default:
                day="Its not a valid day";

        }













        holder.linear_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean isset= false;

                String alarms= pref.getString("alarms", "[]");
                try {
                    JSONArray alrms = new JSONArray(alarms);
                    for (int i=0; i<alrms.length(); i++)
                    {
                        JSONObject  jsonObject =  alrms.getJSONObject(i);
                        String unit_name = jsonObject.getString("unit_name");
                        String hour = jsonObject.getString("hour");
                        String day = jsonObject.getString("day");
                        String minute = jsonObject.getString("minute");
                        alarmid= jsonObject.getString("alarm_id");
                        if(unit_name.equals(dataClass.getUnit_name())&& day.equals(dataClass.getDay()))
                        {
                            isset=true;
                        }


                    }
                } catch (JSONException e) {
                    Log.d("jsonexception", String.valueOf(e));
                }


                if(isset)
                {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to cancel the reminder for '" + dataClass.getUnit_name() + "' on " + day);
                    builder.setTitle("Please confirm");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String alarms= pref.getString("alarms", "[]");

                            try {
                                JSONArray alrms = new JSONArray(alarms);
                                for (int i=0; i<alrms.length(); i++)
                                {
                                    JSONObject  jsonObject =  alrms.getJSONObject(i);
                                    String unit_name = jsonObject.getString("unit_name");
                                    String hour = jsonObject.getString("hour");
                                    String day = jsonObject.getString("day");
                                    String minute = jsonObject.getString("minute");
                                    String id= jsonObject.getString("alarm_id");
                                    if(alarmid.equals(id))
                                    {
                                        holder.imageView.setVisibility(View.GONE);
                                        alrms.remove(i);
                                        Toast.makeText(context, "Reminder cancelled!", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("alarms",String.valueOf(alrms));
                                editor.commit();



                            } catch (JSONException e) {
                                Log.d("jsonexception", String.valueOf(e));
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", null).show();
                }
                else {


                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to set a reminder for '" + dataClass.getUnit_name() + "' on " + day);
                    builder.setTitle("Please confirm");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            context.startService(new Intent(context, NotifyService.class));

                            cal = Calendar.getInstance();
                            cal.add(Calendar.SECOND, 0);

                            Intent intent = new Intent(context, NotifyService.class);
                            PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
                            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            /// 6000, pintent);

                            Intent myIntent = new Intent(context, NotifyService.class);
                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                            PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MINUTE, 27);
                            calendar.set(Calendar.HOUR, 20);
                            calendar.set(Calendar.AM_PM, Calendar.PM);


                            int hour = 0;
                            int min = 0;
                            String time = dataClass.getStart_time();
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
                            try {
                                Date date = dateFormat2.parse(time);
                                hour = date.getHours();
                                min = date.getMinutes();
                                Log.d("min", String.valueOf(min));
                            } catch (ParseException e) {
                            }

                            holder.imageView.setBackgroundResource(R.drawable.ic_access_alarm);

                            forday(cal, Integer.parseInt(dataClass.getDay()), pendingIntent, 11, 00, dataClass.getUnit_name());


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builder.show();
                }

            }
        });

        holder.tutor.setText(dataClass.getLecturer_name());
        holder.endtime.setText(dataClass.getEnd_time());
        holder.unit_name.setText(dataClass.getUnit_name());
        holder.startTime.setText(dataClass.getStart_time());
        holder.room_number.setText(dataClass.getRoomnumber());


//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("alarms","[]");
//        editor.commit();



        int start_time =0, end_time =0;


        String time = dataClass.getStart_time();
        String time2= dataClass.getEnd_time();

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");

        try {
            Date date = dateFormat2.parse(time);
            Date date2 = dateFormat2.parse(time2);

            start_time=date.getHours();
            end_time = date2.getHours();


        } catch (ParseException e) {
        }





        String alarms= pref.getString("alarms", "[]");
        try {
            JSONArray alrms = new JSONArray(alarms);
            for (int i=0; i<alrms.length(); i++)
            {
                JSONObject  jsonObject =  alrms.getJSONObject(i);

                String unit_name = jsonObject.getString("unit_name");
                String hour = jsonObject.getString("hour");
                String day = jsonObject.getString("day");
                String minute = jsonObject.getString("minute");

                if(unit_name.equals(dataClass.getUnit_name())&& day .equals(dataClass.getDay()))
                {
                    holder.imageView.setBackgroundResource(R.drawable.ic_access_alarm);
                }

            }
        } catch (JSONException e) {
          Log.d("jsonexception", String.valueOf(e));
        }






      int ent_time=end_time- start_time;

        if(ent_time==1)
        {
            final float scale = context.getResources().getDisplayMetrics().density;
            int pixels = (int) (74 * scale *1.5);
            holder.linear_layout.getLayoutParams().height = pixels;

        }
        else if(ent_time==2)
        {
            final float scale = context.getResources().getDisplayMetrics().density;

            int pixels = (int) (74 * scale *2);
            holder.linear_layout.getLayoutParams().height = pixels;
        }
        else if(ent_time==3)
        {
            final float scale = context.getResources().getDisplayMetrics().density;

            int pixels = (int) (74 * scale *3);
            holder.linear_layout.getLayoutParams().height = pixels;
        }

        else if(ent_time==4)
        {
            final float scale = context.getResources().getDisplayMetrics().density;

            int pixels = (int) (74 * scale *4);
            holder.linear_layout.getLayoutParams().height = pixels;
        }
        else if(ent_time>4)
        {
            final float scale = context.getResources().getDisplayMetrics().density;

            int pixels = (int) (74 * scale *5);
            holder.linear_layout.getLayoutParams().height = pixels;
        }




    }

    @Override
    public int getItemCount() {
        return  listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

TextView startTime, endtime, tutor, room_number,unit_name;
public ImageView imageView;
LinearLayout linear_layout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            startTime = (TextView)itemView.findViewById(R.id.startTime);
            endtime=itemView.findViewById(R.id.endTime);
            tutor =itemView.findViewById(R.id.prof);
            imageView=itemView.findViewById(R.id.image);
            unit_name=itemView.findViewById(R.id.name);
            room_number=itemView.findViewById(R.id.place);
            linear_layout=itemView.findViewById(R.id.linear_layout);


        }
    }


    public void forday(Calendar calSet, int day, PendingIntent pendingIntent, int hour, int minute, String unit_name )
    {

        String alarms= pref.getString("alarms", "[]");


        try 
        
        {
            JSONArray jsonArray =new JSONArray(alarms);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("alarm_id",String.valueOf(System.currentTimeMillis()));
            jsonObject.put("unit_name",unit_name);
            jsonObject.put("hour",hour);
            jsonObject.put("day",day);
            jsonObject.put("minute",minute);
            jsonArray.put(jsonObject);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("alarms",String.valueOf(jsonArray));
            editor.commit();
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

       

        calSet.set(Calendar.DAY_OF_WEEK, day);
        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), 1 * 60 * 60 * 1000, pendingIntent);
        Toast.makeText(context, "Reminder set", Toast.LENGTH_SHORT).show();
    }



}