package com.example.notificationservice.config;

import com.example.notificationservice.NotificationServiceApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Configuration
public class FirebaseConfig {

    ClassLoader classLoader = NotificationServiceApplication.class.getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource("darkom-ce2c6-firebase-adminsdk-s49qg-cd17a6872d.json")).getFile());
    FileInputStream serviceAccount;
    FirebaseOptions options = null;

    public FirebaseConfig()
    {
        try {
            serviceAccount = new FileInputStream(file.getAbsolutePath());
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://darkom-ce2c6-default-rtdb.firebaseio.com/")
                    .build();
            if (options != null) {
                FirebaseApp.initializeApp(options);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
