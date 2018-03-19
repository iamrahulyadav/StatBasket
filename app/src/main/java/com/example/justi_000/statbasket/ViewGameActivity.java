package com.example.justi_000.statbasket;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewGameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Team team;
    List<Team> teams;
    List<String> team_names;

    Spinner spinHomeTeam;
    Spinner spinAwayTeam;
    RadioGroup radioGroupTeam;
    RadioButton radioHome;
    RadioButton radioAway;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        myDatabase = new DbHelper(this);

        spinHomeTeam = findViewById(R.id.spin_home_team);
        spinAwayTeam = findViewById(R.id.spin_away_team);
        radioGroupTeam = findViewById(R.id.radio_group_team);
        radioHome = findViewById(R.id.radio_home);
        radioAway = findViewById(R.id.radio_away);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        bundle = getIntent().getExtras();
        if (bundle != null)
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
        else
            team = new Team();
        setTitle("Create Game For: " + team.getName());

        teams = myDatabase.getAllTeams();
        team_names = new ArrayList<>();
        for(Team team : teams)
        {
            team_names.add(team.getName());
        }

//        lvTeamList = findViewById(R.id.lv_item_list);
        spinHomeTeam.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, team_names));
        spinAwayTeam.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, team_names));

//        spinHomeTeam.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Intent i = new Intent(ViewGameActivity.this, ViewTeamActivity.class);
//                bundle.putLong("team_id", teams.get(position).getId());
//                i.putExtras(bundle);
//                startActivity(i);
//            }
//        });

//        String[] feet_spinner = new String[] {"3", "4", "5", "6", "7", "8"};
//        String[] inches_spinner = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
//
//        Spinner s = findViewById(R.id.spin_feet);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, feet_spinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
//        long feet = player.getHeightFeet();
//        if (feet >= 3 && feet <= 8) {
//            s.setSelection(adapter.getPosition(String.valueOf(player.getHeightFeet())));
//        }
//
//        s = findViewById(R.id.spin_inches);
//        adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, inches_spinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
//        long inches = player.getHeightInches();
//        if (inches >= 0 && inches <= 11) {
//            s.setSelection(adapter.getPosition(String.valueOf(player.getHeightInches())));
//        }
    }

    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean isChecked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_home:
                if (isChecked)
                    // populate home team with current team (from bundle id)
                    // disable home team spinner
                    break;
            case R.id.radio_away:
                if (isChecked)
                    // populate away team with current team (from bundle id)
                    // disable away team spinner
                    break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

//    @Override
//    public void onClick(View view)
//    {
//
//    }
}
