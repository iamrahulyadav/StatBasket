package com.example.justi_000.statbasket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class EditTeamActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();

    EditText editTeamName;
    TextView tvIdValue;
    Button btnAddData;
    Button btnViewData;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        myDatabase = new DbHelper(this);

        editTeamName = findViewById(R.id.edit_team_name);
        tvIdValue = findViewById(R.id.tv_id_value);

        btnAddData = findViewById(R.id.btn_add_data);
        btnViewData = findViewById(R.id.btn_view_data);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        Bundle bundle = getIntent().getExtras();
        String team_name = bundle.getString("team");
        //String team_id = String.valueOf(bundle.getLong("id"));
        long team_id = bundle.getLong("id");
        editTeamName.setText(team_name);
        editTeamName.setSelection(editTeamName.getText().length());
        tvIdValue.setText(String.valueOf(team_id));

        addData();
        viewAllTeams();
        updateTeam();
        deleteTeam();
//        returnToTeams();
    }

    @Override
    public void onClick(View view)
    {

    }

    public void addData()
    {
//        btnAddData.setOnClickListener(
//            new View.OnClickListener()
//            {
//                @Override
//                public  void onClick(View v)
//                {
//                    if (editTeamName.getText().toString().length() != 0)
//                    {
//                        boolean success = myDatabase.insertData(editTeamName.getText().toString());
//                        if (success)
//                            Toast.makeText(ViewTeamActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(ViewTeamActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        btnAddData.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
                        Team team = new Team(editTeamName.getText().toString());
                        if (team.getName().length() != 0)
                        {
                            long success = myDatabase.createTeam(team);
                            if (success > 0) {
                                Toast.makeText(EditTeamActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditTeamActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(EditTeamActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void viewAllTeams()
    {
//        btnViewData.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Cursor result = myDatabase.getAllData();
//                        if (result.getCount() == 0)
//                        {
//                            showMessage("Error", "No Data Found");
//                            return;
//                        }
//                        StringBuffer buffer = new StringBuffer();
//                        while (result.moveToNext())
//                        {
//                            buffer.append("Team ID: " + result.getString(0) + "\n");
//                            buffer.append("Team Name: " + result.getString(1) + "\n\n");
//                        }
//
//                        showMessage("Data", buffer.toString());
//                    }
//                }
//        );
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Team> teams = myDatabase.getAllTeams();
                        if (teams.size() == 0)
                        {
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
//                        for (int i = 0; i < teams.size(); i++)
//                        {
//                            buffer.append("Team ID: " + teams.get(i).getId() + "\n");
//                            buffer.append("Team Name: " + teams.get(i).getName() + "\n\n");
//                        }
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
                    Team team = myDatabase.getTeam(tvIdValue.getText().toString());
                    team.setName(editTeamName.getText().toString());
                    int isUpdated = myDatabase.updateTeam(team);
                    if (isUpdated == 1) {
                        team = myDatabase.getTeam(tvIdValue.getText().toString());
//                        editTeamName.setText(team.getName());
//                        editTeamName.setSelection(editTeamName.getText().length());
                        Toast.makeText(EditTeamActivity.this, team.getName() + " Updated", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(EditTeamActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
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
                        Team team = myDatabase.getTeam(tvIdValue.getText().toString());
                        int isUpdated = myDatabase.deleteTeam(team.getId());
                        if (isUpdated == 1)
                            Toast.makeText(EditTeamActivity.this, team.getName() + " Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(EditTeamActivity.this, "Deletion Failed", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditTeamActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

//    public void returnToTeams()
//    {
//        btnSaveData.setOnClickListener(
//                new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Cursor result = myDatabase.getAllData();
//                        if (result.getCount() == 0)
//                        {
//                            showMessage("Error", "No Data Found");
//                            return;
//                        }
//                        List<String> teamList = new ArrayList(50);
//                        Intent i = new Intent(ViewTeamActivity.this, MainActivity.class);
//                        Bundle bundle = new Bundle();
//
//                        startActivity(i);
//                        while (result.moveToNext())
//                        {
//                            teamList.add(result.getString(1));
//                        }
//                        bundle.putStringArrayList("teams", (ArrayList<String>) teamList);
//                        i.putExtras(bundle);
//                        startActivityForResult(i, 0);
//                    }
//                }
//        );
//    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
                Intent i = new Intent(EditTeamActivity.this, MainActivity.class);
                bundle.putString("team", "");
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
