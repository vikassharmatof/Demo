package com.example.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.AcquireTokenParameters;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MicrosoftAuth extends AppCompatActivity {

    private ISingleAccountPublicClientApplication msalApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microsoft);

        // Initialize MSAL
        PublicClientApplication.createSingleAccountPublicClientApplication(
                this,
                R.raw.auth_config_single_account,
                new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        msalApp = application;
                    }

                    @Override
                    public void onError(MsalException exception) {
                        Toast.makeText(MicrosoftAuth.this, "Initialization error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Set up the Sign-In button
        Button signInButton = findViewById(R.id.btn_signinwithmicrosoft);
        signInButton.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        List<String> scopes = Arrays.asList("User.Read");

        if (msalApp != null) {
            msalApp.acquireToken(new AcquireTokenParameters.Builder()
                    .startAuthorizationFromActivity(this)
                    .withScopes(scopes)
                    .withCallback(new AuthenticationCallback() {
                        @Override
                        public void onSuccess(IAuthenticationResult authenticationResult) {
                            String accessToken = authenticationResult.getAccessToken();
                            Toast.makeText(MicrosoftAuth.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(MsalException exception) {
                            Toast.makeText(MicrosoftAuth.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(MicrosoftAuth.this, "Sign-in canceled.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build());
        } else {
            Toast.makeText(this, "MSAL not initialized properly.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // No need to call handleInteractiveRequestRedirect for ISingleAccountPublicClientApplication
    }


}
