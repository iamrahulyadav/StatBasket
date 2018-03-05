package com.example.justi_000.statbasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
        Team team = myDatabase.getTeam(bundle.getLong("team_id", 0));
        Player player = myDatabase.getPlayer(bundle.getLong("player_id", 0));
//        String team_name = bundle.getString("team_name");
//        long team_id = bundle.getLong("team_id");
//        String first_name = bundle.getString("first_name");
//        String last_name = bundle.getString("last_name");
//        int number = bundle.getInt("number");
//        int height_feet = bundle.getInt("height_feet");
//        int height_inches = bundle.getInt("height_inches");
//        int player_id = bundle.getInt("player_id");
//
//        editFirstName.setText(first_name);
//        editLastName.setText(last_name);
//        editNumber.setText(number);

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

//        int compareValue = height_feet;
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.height_feet_list, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinFeet.setAdapter(adapter);
//        if (compareValue != 0) {
//            int spinnerPosition = adapter.getPosition(compareValue);
//            spinFeet.setSelection(spinnerPosition);
//        }

//        editFirstName.setSelection(editFirstName.getText().length());
//        setTitle(first_name + last_name);

        addPlayer(team);
        viewAllPlayers(team);
        updateTeam();
        deleteTeam();
//        returnToTeams();
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        btnAdd.setOnClickListener(
//                new View.OnClickListener()
//                {
//                    @Override
//                    public  void onClick(View v)
//                    {
//                        Team team = new Team(editTeamName.getText().toString());
//                        if (team.getName().length() != 0)
//                        {
//                            long success = myDatabase.createPlayer(team);
//                            if (success > 0) {
//                                Toast.makeText(ViewPlayerActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(ViewPlayerActivity.this, MainActivity.class);
//                                startActivity(intent);
//                            }
//                            else {
//                                Toast.makeText(ViewPlayerActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                });
//    }

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
                                Toast.makeText(ViewPlayerActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(ViewPlayerActivity.this, ViewTeamActivity.class);
//                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(ViewPlayerActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
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
                            showMessage("Error", "No Data Found");
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

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateTeam()
    {
//        btnUpdate.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Team team = myDatabase.getTeam(tvIdValue.getText().toString());
//                        team.setName(editTeamName.getText().toString());
//                        int isUpdated = myDatabase.updateTeam(team);
//                        if (isUpdated == 1) {
//                            team = myDatabase.getTeam(tvIdValue.getText().toString());
//                            setTitle(team.getName());
////                        editTeamName.setText(team.getName());
////                        editTeamName.setSelection(editTeamName.getText().length());
//                            Toast.makeText(ViewTeamActivity.this, team.getName() + " Updated", Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(ViewTeamActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//        );
    }

    public void deleteTeam()
    {
//        btnDelete.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Team team = myDatabase.getTeam(tvIdValue.getText().toString());
//                        int isUpdated = myDatabase.deleteTeam(team.getId());
//                        if (isUpdated == 1)
//                            Toast.makeText(ViewTeamActivity.this, team.getName() + " Deleted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(ViewTeamActivity.this, "Deletion Failed", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(ViewTeamActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
