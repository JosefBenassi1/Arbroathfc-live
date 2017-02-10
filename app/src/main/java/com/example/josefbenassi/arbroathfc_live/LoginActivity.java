package com.example.josefbenassi.arbroathfc_live;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity implements View.OnClickListener, View.OnKeyListener {


    EditText usernameField;
    EditText passwordField;
    TextView changeSignUpModeTextView;
    Button   signUpButton;
    ImageView logo;
    RelativeLayout relativeLayoutView;



    Boolean signUpModeActive;



    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_DOWN ){

        signUpOrLogin(v);

        }
        return false;
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.tvLogin){


            if(signUpModeActive==true){

                signUpModeActive = false;
                changeSignUpModeTextView.setText("Sign Up");
                signUpButton.setText("Login");

            }else{

                signUpModeActive = true;
                changeSignUpModeTextView.setText("Login");
                signUpButton.setText("Sign Up");

            }

        } else if(view.getId()== R.id.ivLogo || view.getId()==R.id.relativeLayout){

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }


    }



    public void signUpOrLogin (View view) {

     /*   Log.i("AppInfo", String.valueOf(usernameField.getText()));
        Log.i("AppInfo", String.valueOf(passwordField.getText()));*/


        if (signUpModeActive == true) {


            ParseUser user = new ParseUser();
            user.setUsername(String.valueOf(usernameField.getText()));
            user.setPassword(String.valueOf(passwordField.getText()));

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Log.i("AppInfo", "Sign up Successful");
                        //showUserList();

                        final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Signing up. Please wait.");
                        dlg.show();

                       showHomeScreen();


                    } else {

                        Toast.makeText(getApplicationContext(),"UserName and Password Needed", Toast.LENGTH_LONG).show();
                        //Toast.makeText(Delete_Order_History_Acitivity.this,"Item Deleted",Toast.LENGTH_LONG).show();
                    }


                }
            });
        } else {

            ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Log.i("AppInfo", "Login Successful");
                        final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Logging in. Please wait.");
                        dlg.show();
                        //showUserList();
                       showHomeScreen();

                    } else {

                        Toast.makeText(getApplicationContext(), "Username or Password Incorrect", Toast.LENGTH_LONG).show();
                        //e.getMessage().substring(e.getMessage().indexOf(" ")

                    }
                }
            });
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /* if(ParseUser.getCurrentUser() !=null) {
            Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
            startActivity(intent);
            finish();
        }*/


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("gj7sUhAtGqABEoOg02Z3abKjIiKOJSISsvy77wE6")
                .clientKey("kkaObCS71TzeckPPgdAXa2lVpADdy4yWqpFrIj8K")
                .server("https://parseapi.back4app.com/").build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


        signUpModeActive = true;

        usernameField = (EditText) findViewById(R.id.etUsername);
        passwordField = (EditText) findViewById(R.id.etPassword);
        changeSignUpModeTextView = (TextView) findViewById(R.id.tvLogin);
        signUpButton = (Button) findViewById(R.id.btSignUp);
        logo = (ImageView) findViewById(R.id.ivLogo);
        relativeLayoutView = (RelativeLayout) findViewById(R.id.relativeLayout);

        changeSignUpModeTextView.setOnClickListener(this);
        logo.setOnClickListener(this);
        relativeLayoutView.setOnClickListener(this);

        usernameField.setOnKeyListener(this);
        passwordField.setOnKeyListener(this);

    }

    public void showHomeScreen(){
        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }


}
// parse object putting user data into the database

      /* ParseObject score = new ParseObject("score");
        score.put("username", "tommy");
        score.put("score",139);
        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("SaveInBackground", "Successful");
                } else {
                    Log.i("SaveInBackground", "Failed");
                }
            }
        });*/

// query to change users details in databse
       /* ParseQuery<ParseObject> query = ParseQuery.getQuery("score");

        query.getInBackground("9ryIIIHZhS", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null) {

                    object.put("score", 300);
                    object.saveInBackground();
                    Log.i("SaveInBackground", "Successful");
                }

            }
        });*/

//retrieves specific objects and shows them in logs in this case the usernames
       /* ParseQuery<ParseObject> query = ParseQuery.getQuery("score");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

            if (e==null){

                Log.i("findInBackground", "Retrieved " + objects.size() + "results");

                for(ParseObject object :objects ){

                    Log.i("findInBackgroundUser", String.valueOf(object.get("username")));
                }

            }


            }
        });*/

/*// query to find individual user score in logs
        ParseQuery<ParseObject> query = ParseQuery.getQuery("score");

        query.whereEqualTo("username", "josef");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e==null){

                    Log.i("findInBackground", "Retrieved " + objects.size() + "results");

                    for(ParseObject object :objects ){

                        Log.i("findInBackgroundUser", String.valueOf(object.get("score")));
                    }

                }


            }
        });*/