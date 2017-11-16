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
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.Calendar;
        import java.util.List;

        import static android.content.Context.ALARM_SERVICE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{

    private List<DataClass> listItems;

    public MyAdapter(List<DataClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;

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


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to set a reminder for '"+dataClass.getUnit_name() +"' on "+ day);
                builder.setTitle("Please confirm");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        context.startService(new Intent(context, NotifyService.class));

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, 0);

                        Intent intent = new Intent(context, NotifyService.class);
                        PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
                        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                6000, pintent);




                        Intent myIntent = new Intent(context , NotifyService.class);


                        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MINUTE, 27);
                        calendar.set(Calendar.HOUR, 20);
                        calendar.set(Calendar.AM_PM, Calendar.PM);
                        //calendar.add(Calendar.DAY_OF_WEEK,4);
                //Integer.parseInt(dataClass.getDay()
                        //Integer.parseInt(dataClass.getDay()
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*60*24*7 , pendingIntent);
                        Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                    }
                });
                builder.show();

            }
        });

        holder.tutor.setText(dataClass.getLecturer_name());
        holder.endtime.setText(dataClass.getEnd_time());
        holder.unit_name.setText(dataClass.getUnit_name());
        holder.startTime.setText(dataClass.getStart_time());
        holder.room_number.setText(dataClass.getRoomnumber());





        int ent_time=Integer.parseInt(dataClass.getEnd_time())-Integer.parseInt(dataClass.getStart_time());

       //Toast.makeText(context, String.valueOf(ent_time), Toast.LENGTH_SHORT).show();
        if(ent_time==1)
        {
            final float scale = context.getResources().getDisplayMetrics().density;

            int pixels = (int) (74 * scale *1);
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

LinearLayout linear_layout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            startTime = (TextView)itemView.findViewById(R.id.startTime);
            endtime=itemView.findViewById(R.id.endTime);
            tutor =itemView.findViewById(R.id.prof);
            unit_name=itemView.findViewById(R.id.name);
            room_number=itemView.findViewById(R.id.place);
            linear_layout=itemView.findViewById(R.id.linear_layout);


        }
    }
    {

    }



}