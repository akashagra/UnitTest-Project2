package com.example.avma1997.unittest_project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
String name;
String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton=findViewById(R.id.login_button);
        final EditText nameEdittext=findViewById(R.id.name_edittext);
        final EditText phoneEditText=findViewById(R.id.phone_edittext);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              name=nameEdittext.getText().toString();
              phoneNo=phoneEditText.getText().toString();
              // name would be alphabets and space only and mobile no would be numeric only,done that in edittext
              if(!name.equals("") && !name.equals("")){
              senddataToServer();

            }
            else
              { Toast.makeText(MainActivity.this,"Please enter Name and PhoneNO correctly",Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
    public void senddataToServer()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://www.ikai.co.in/api/login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Successful Login");
                        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Error");
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error in Login");
                        builder.show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("phoneno",phoneNo);

                return params;
            }
        };
        queue.add(postRequest);

    }
}
