package com.example.justi_000.statbasket;

import android.app.Activity;
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
    ListView lvTeamList;
    List<Team> teams;
    List<String> team_names;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        setTitle("Your Teams");

        myDatabase = new DbHelper(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        teams = myDatabase.getAllTeams();
        team_names = new ArrayList<>();
        for(Team team : teams)
        {
            team_names.add(team.getName());
        }

        lvTeamList = findViewById(R.id.lv_item_list);
        lvTeamList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, team_names));

        lvTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(MainActivity.this, ViewTeamActivity.class);
//                bundle.putString("team", teams.get(position).getName());
                bundle.putLong("team_id", teams.get(position).getId());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
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
                bundle.putLong("team_id", -1);
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}