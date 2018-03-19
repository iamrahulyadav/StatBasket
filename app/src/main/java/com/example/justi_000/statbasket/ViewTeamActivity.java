package com.example.justi_000.statbasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ViewTeamActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Team team;

    EditText editTeamName;
    TextView tvIdValue;
    Button btnAddData;
    Button btnViewData;
    Button btnUpdate;
    Button btnDelete;
    Button btnAddPlayer;
    Button btnPlayers;
    Button btnGames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        myDatabase = new DbHelper(this);

        editTeamName = findViewById(R.id.edit_team_name);
        tvIdValue = findViewById(R.id.tv_id_value);
        btnAddData = findViewById(R.id.btn_add);
        btnViewData = findViewById(R.id.btn_view);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnAddPlayer = findViewById(R.id.btn_add_player);
        btnPlayers = findViewById(R.id.btn_players);
        btnGames = findViewById(R.id.btn_games);
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
//        String team_name = bundle.getString("team");
        //String team_id = String.valueOf(bundle.getLong("id"));
//        long team_id = bundle.getLong("id");
        editTeamName.setText(team.getName());
        editTeamName.setSelection(editTeamName.getText().length());
        tvIdValue.setText(String.valueOf(team.getId()));
        setTitle("Team: " + team.getName());

        addTeam();
        viewAllTeams();
        updateTeam();
        deleteTeam();
        addPlayer(team);
        viewPlayers();
        viewGames();
    }

    @Override
    public void onClick(View view)
    {

    }

    public void addTeam()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
                        Team team = new Team(editTeamName.getText().toString());
                        if (team.getName().length() != 0)
                        {
                            //success holds the team id of the created team
                            long success = myDatabase.createTeam(team);
                            if (success > 0) {
                                Toast.makeText(ViewTeamActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ViewTeamActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(ViewTeamActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void addPlayer(final Team team)
    {
        btnAddPlayer.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
//                        Team team = myDatabase.getTeam(tvIdValue.getText().toString());
                        if (team.getId() != 0)
                        {
                            Intent i = new Intent(ViewTeamActivity.this, ViewPlayerActivity.class);
                            bundle.putLong("team_id", team.getId());
//                            bundle.putString("team_name", team.getName());
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }
                });
    }

    public void viewAllTeams()
    {
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        List<Team> teams = myDatabase.getAllTeams();
                        if (teams.size() == 0)
                        {
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        for (Team team : teams)
                        {
                            buffer.append("Team ID: " + team.getId() + "\n");
                            buffer.append("Team Name: " + team.getName() + "\n\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateTeam()
    {
        btnUpdate.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Team team = myDatabase.getTeam(Integer.valueOf(tvIdValue.getText().toString()));
                    team.setName(editTeamName.getText().toString());
                    int isUpdated = myDatabase.updateTeam(team);
                    if (isUpdated > 0) {
                        team = myDatabase.getTeam(Integer.valueOf(tvIdValue.getText().toString()));
                        setTitle(team.getName());
//                        editTeamName.setText(team.getName());
//                        editTeamName.setSelection(editTeamName.getText().length());
                        Toast.makeText(ViewTeamActivity.this, team.getName() + " Updated", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ViewTeamActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );
    }

    public void deleteTeam()
    {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Team team = myDatabase.getTeam(Integer.valueOf(tvIdValue.getText().toString()));
                        int isUpdated = myDatabase.deleteTeam(team.getId());
                        if (isUpdated == 1)
                            Toast.makeText(ViewTeamActivity.this, team.getName() + " Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ViewTeamActivity.this, "Deletion Failed", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ViewTeamActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void viewPlayers()
    {
        btnPlayers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Team team = myDatabase.getTeam(Integer.valueOf(tvIdValue.getText().toString()));
                        if (team.getId() > 0)
                        {
                            Intent i = new Intent(ViewTeamActivity.this, EditTeamActivity.class);
                            bundle.putLong("team_id", team.getId());
//                            bundle.putString("team_name", team.getName());
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }
                }
        );
    }

    public void viewGames()
    {
        btnGames.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
//                        Team team = myDatabase.getTeam(Integer.valueOf(tvIdValue.getText().toString()));
                        if (team.getId() > 0)
                        {
                            Intent i = new Intent(ViewTeamActivity.this, GameListActivity.class);
                            bundle.putLong("team_id", team.getId());
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.edit_team_menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.add:
//                addTeam();
//                return true;
//            case R.id.update:
//                updateTeam();
//                return true;
//            case R.id.delete:
//                deleteTeam();
//                return true;
//            case R.id.view_all:
//                viewAllTeams();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
