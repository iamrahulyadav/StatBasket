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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ViewPlayerActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Player player;
    Team team;

    EditText editFirstName;
    EditText editLastName;
    EditText editNumber;
    Spinner spinFeet;
    Spinner spinInches;
    Button btnAdd;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player);

        myDatabase = new DbHelper(this);

        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editNumber = findViewById(R.id.edit_number);
        spinFeet = findViewById(R.id.spin_feet);
        spinInches = findViewById(R.id.spin_inches);
        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        bundle = getIntent().getExtras();
        if(bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            player = myDatabase.getPlayer(bundle.getLong("player_id", 0));
        }

        String[] feet_spinner = new String[] {"3", "4", "5", "6", "7", "8"};
        String[] inches_spinner = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};

        Spinner s = findViewById(R.id.spin_feet);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, feet_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        long feet = player.getHeightFeet();
        if (feet >= 3 && feet <= 8) {
            s.setSelection(adapter.getPosition(String.valueOf(player.getHeightFeet())));
        }

        s = findViewById(R.id.spin_inches);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, inches_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        long inches = player.getHeightInches();
        if (inches >= 0 && inches <= 11) {
            s.setSelection(adapter.getPosition(String.valueOf(player.getHeightInches())));
        }

        editFirstName.setText(player.getFirstName());
        editLastName.setText(player.getLastName());
        editNumber.setText(String.valueOf(player.getNumber()));

        setTitle("Add/Edit Player on " + team.getName());

        addPlayer(team);
        viewAllPlayers(team);
        updatePlayer();
        deletePlayer();
    }

    @Override
    public void onClick(View view)
    {

    }

    public void addPlayer(final Team team)
    {
        btnAdd.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
//                        Team team = new Team(editTeamName.getText().toString());
                        Player player = new Player(editFirstName.getText().toString(), editLastName.getText().toString(), Integer.valueOf(editNumber.getText().toString()),
                                Integer.valueOf(spinFeet.getSelectedItem().toString()), Integer.valueOf(spinInches.getSelectedItem().toString()));
                        if (player.getFirstName().length() != 0)
                        {
                            long success = myDatabase.createPlayer(player, team.getId());
                            if (success > 0) {
                                Toast.makeText(ViewPlayerActivity.this, player.getFirstName() + " " + player.getLastName() + " Added", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(ViewPlayerActivity.this, "Failed To Add Player", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void viewAllPlayers(final Team team)
    {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Player> players = myDatabase.getAllPlayers(team.getId());
                        if (players.size() == 0)
                        {
                            showMessage("Players", "No Players Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("Team: " + team.getName() + "\n--------------------\n");
                        for (Player player : players)
                        {
                            buffer.append("Player ID: " + player.getId() + "\n");
                            buffer.append("Player First Name: " + player.getFirstName() + "\n");
                            buffer.append("Player Last Name: " + player.getLastName() + "\n");
                            buffer.append("Player Number: " + String.valueOf(player.getNumber()) + "\n");
                            buffer.append("Player Height (Feet): " + String.valueOf(player.getHeightFeet()) + "\n");
                            buffer.append("Player Height (Inches): " + String.valueOf(player.getHeightInches()) + "\n\n");
                        }

                        showMessage("Players", buffer.toString());
                    }
                }
        );
    }

    public void updatePlayer()
    {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        player.setFirstName(editFirstName.getText().toString());
                        player.setLastName(editLastName.getText().toString());
                        player.setNumber(Integer.valueOf(editNumber.getText().toString()));
                        player.setHeightFeet(Integer.valueOf(spinFeet.getSelectedItem().toString()));
                        player.setHeightInches(Integer.valueOf(spinInches.getSelectedItem().toString()));
                        int isUpdated = myDatabase.updatePlayer(player);
                        if (isUpdated == 1) {
                            player = myDatabase.getPlayer(player.getId());
                            setTitle(player.getFirstName() + player.getLastName());
                            Toast.makeText(ViewPlayerActivity.this, player.getFirstName() +  " " + player.getLastName() + " Updated", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ViewPlayerActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void deletePlayer()
    {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int isUpdated = myDatabase.deletePlayer(player.getId());
                        if (isUpdated == 1)
                            Toast.makeText(ViewPlayerActivity.this, player.getFirstName() + " " + player.getLastName() + " Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ViewPlayerActivity.this, "Deletion Failed", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ViewPlayerActivity.this, EditTeamActivity.class);
                        bundle.putLong("team_id", team.getId());
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
        );
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_team_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.add_player:
                Intent i = new Intent(ViewPlayerActivity.this, ViewPlayerActivity.class);
                bundle.putLong("team_id", team.getId());
                bundle.putLong("player_id", 0);
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
