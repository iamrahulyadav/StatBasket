package com.example.justi_000.statbasket;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new DbHelper(this);
        final List<String> teams = getAllTeams();
//        Bundle bundle = getIntent().getExtras();
//        List<String> teams = new ArrayList<>(50);
//        if (getCallingActivity() != null) {
////            if (getCallingActivity().getClassName() == "com.example.justi_000.statbasket.ViewTeamActivity") {
//                teams = bundle.getStringArrayList("teams");
////            }
//        }

//        final String[] teamArray = new String[] {"Lorem","Ipsum","dolor","sit","amet"};

        ListView teamList = findViewById(R.id.lv_team_list);
        teamList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, teams));

        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(MainActivity.this, ViewTeamActivity.class);
                bundle.putString("team", teams.get(position));
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        getAllTeams();
    }

    @Override
    public void onClick(View v)
    {

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.add_team:
                Intent i = new Intent(MainActivity.this, ViewTeamActivity.class);
                bundle.putString("team", "");
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<String> getAllTeams()
    {
        Cursor result = myDatabase.getAllData();
        if (result.getCount() == 0)
        {
            return new ArrayList<String>(50);
        }

        List<String> teamList = new ArrayList(50);
        while (result.moveToNext())
        {
            teamList.add(result.getString(1));
        }
        return (ArrayList<String>) teamList;
    }
}
