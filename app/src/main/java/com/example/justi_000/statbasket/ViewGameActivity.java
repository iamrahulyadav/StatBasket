package com.example.justi_000.statbasket;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
    ArrayAdapter<String> arrayAdapter;
    Game game;

    Spinner spinHomeTeam;
    Spinner spinAwayTeam;
    RadioGroup radioGroupTeam;
    RadioButton radioHome;
    RadioButton radioAway;
    Button btnStartGame;
    EditText editLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);

        myDatabase = new DbHelper(this);

        spinHomeTeam = findViewById(R.id.spin_home_team);
        spinAwayTeam = findViewById(R.id.spin_away_team);
        radioGroupTeam = findViewById(R.id.radio_group_team);
        radioHome = findViewById(R.id.radio_home);
        radioAway = findViewById(R.id.radio_away);
        btnStartGame = findViewById(R.id.btn_start_game);
        editLocation = findViewById(R.id.edit_location);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            game = myDatabase.getGame(bundle.getLong("game_id", 0));
        }
        else {
            team = new Team();
            game = new Game();
        }
        setTitle("Create Game For: " + team.getName());

        teams = myDatabase.getAllTeams();
        team_names = new ArrayList<>();
        for(Team team : teams)
        {
            team_names.add(team.getName());
        }

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, team_names);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHomeTeam.setAdapter(arrayAdapter);
        spinAwayTeam.setAdapter(arrayAdapter);

        spinHomeTeam.setSelection(arrayAdapter.getPosition(myDatabase.getTeam(game.getHomeTeamId()).getName()));
        spinAwayTeam.setSelection(arrayAdapter.getPosition(myDatabase.getTeam(game.getAwayTeamId()).getName()));
        editLocation.setText(game.getLocation());
        if (team.getId() == game.getHomeTeamId()) {
            radioHome.setChecked(true);
            spinHomeTeam.setEnabled(false);
        }
        else if (team.getId() == game.getAwayTeamId()) {
            radioAway.setChecked(true);
            spinAwayTeam.setEnabled(false);
        }

        startGame();
    }

    public void startGame()
    {
        btnStartGame.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Team homeTeam = myDatabase.getTeamFromName(spinHomeTeam.getSelectedItem().toString());
                        Team awayTeam = myDatabase.getTeamFromName(spinAwayTeam.getSelectedItem().toString());
                        Game newGame = new Game(homeTeam.getId(), awayTeam.getId(), editLocation.getText().toString());
                        long success = myDatabase.createGame(newGame);
                        if (success > 0) {
                            Toast.makeText(ViewGameActivity.this, "Game Started", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ViewGameActivity.this, GameActivity.class);
                            bundle.putLong("game_id", game.getGameId());
                            bundle.putLong("team_id", team.getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ViewGameActivity.this, "Starting Game Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean isChecked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_home:
                if (isChecked) {
                    spinHomeTeam.setSelection(arrayAdapter.getPosition(team.getName()));
                    spinAwayTeam.setEnabled(true);
                    spinHomeTeam.setEnabled(false);
                }
                else{
                    spinAwayTeam.setEnabled(true);
                    spinHomeTeam.setEnabled(true);
                }
                break;
            case R.id.radio_away:
                if (isChecked) {
                    spinAwayTeam.setSelection(arrayAdapter.getPosition(team.getName()));
                    spinHomeTeam.setEnabled(true);
                    spinAwayTeam.setEnabled(false);
                }
                else{
                    spinAwayTeam.setEnabled(true);
                    spinHomeTeam.setEnabled(true);
                }
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

    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.new_game:
                Intent i = new Intent(this, ViewGameActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onClick(View view)
//    {
//
//    }
}
