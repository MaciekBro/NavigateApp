package com.maciekbro.navigateapp.login;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.maciekbro.navigateapp.R;
import com.maciekbro.navigateapp.utils.ViewsUtils;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 1111;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //logowanie, prosimy o rzeczy ktore uzytkownik nam zwroci przy logowaniu
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        setContentView(R.layout.activity_login);

        //tworzymy klienta Google
        //mowimy ze googleClient ma api do logowania
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this ,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)        //mowimy ze googleClient ma api do logowania
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Uri photoUrl = acct.getPhotoUrl();
            TextView name = ViewsUtils.findView(this, R.id.name_textview);
            name.setText(acct.getDisplayName());
//            ImageView image = (ImageView) findViewById(R.id.profile_image);
            ImageView image = ViewsUtils.findView(this,R.id.profile_image);
            if (photoUrl!=null){
                Glide.with(this).load(photoUrl).into(image);
            }

        } else {

        }
    }
}
