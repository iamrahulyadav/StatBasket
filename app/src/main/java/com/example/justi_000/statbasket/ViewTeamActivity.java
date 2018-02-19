package com.example.justi_000.statbasket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ViewTeamActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    EditText editTeamName;
    EditText editId;
    Button btnAddData;
    Button btnViewData;
    Button btnUpdateData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        myDatabase = new DbHelper(this);

        editTeamName = findViewById(R.id.edit_team_name);
        editId = findViewById(R.id.edit_id);

        btnAddData = findViewById(R.id.btn_add_data);
        btnViewData = findViewById(R.id.btn_view_data);
        btnUpdateData = findViewById(R.id.btn_update);

        Bundle bundle = getIntent().getExtras();
        String teamName = bundle.getString("team");
        String team_id = String.valueOf(bundle.getInt("id"));
        editTeamName.setText(teamName);
        editId.setText(team_id);

        addData();
        viewAllTeams();
        updateData();
//        returnToTeams();
    }

    @Override
    public void onClick(View view)
    {

    }

    public void addData()
    {
        btnAddData.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public  void onClick(View v)
                {
                    if (editTeamName.getText().toString().length() != 0)
                    {
                        boolean success = myDatabase.insertData(editTeamName.getText().toString());
                        if (success)
                            Toast.makeText(ViewTeamActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ViewTeamActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    public void viewAllTeams()
    {
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDatabase.getAllData();
                        if (result.getCount() == 0)
                        {
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext())
                        {
                            buffer.append("Team ID: " + result.getString(0) + "\n");
                            buffer.append("Team Name: " + result.getString(1) + "\n\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateData()
    {
        btnUpdateData.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    boolean isUpdated = myDatabase.updateData(editId.getText().toString(), editTeamName.getText().toString());
                    if (isUpdated == true)
                        Toast.makeText(ViewTeamActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ViewTeamActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
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
}
