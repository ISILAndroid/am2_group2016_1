## Persistencia de Datos (S4-S6)

  - Preferencias de Usuario
  - Base de datos Sqlite
  - Ejemplos , CRUD
  
### Aplicación MyNotes

![splash](https://github.com/ISILAndroid/am2_group2016_1/blob/Lesson4/screenshots/Splash.png)

Validación de sesión
 - SplashActivity.java 
 
 ```
    TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                Intent intent;
                boolean session= PreferencesHelper.isSignedIn(SplashActivity.this);
                if(session)
                {
                    intent=new Intent(SplashActivity.this, MainActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
 ```
 - PreferencesHelper.java
 ```
package com.isil.mynotes.storage;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesHelper {

    private static final String MYNOTES_PREFERENCES = "mynotesPreferences";
    private static final String PREFERENCES_USERNAME = MYNOTES_PREFERENCES + ".username";
    private static final String PREFERENCES_PASSWORD = MYNOTES_PREFERENCES + ".password";

    private PreferencesHelper() {
        //no instance
    }

    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCES_USERNAME);
        editor.remove(PREFERENCES_PASSWORD);
        editor.apply();
    }

    public static void saveSession(Context context,String username,String password)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCES_USERNAME, username);
        editor.putString(PREFERENCES_PASSWORD, password);
        editor.apply();
    }

    public static String getUserSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String username= sharedPreferences.getString(PREFERENCES_USERNAME,null);

        return username;
    }

    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCES_USERNAME) &&
                preferences.contains(PREFERENCES_PASSWORD);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MYNOTES_PREFERENCES, Context.MODE_PRIVATE);
    }
}

    Status API Training Shop Blog About 

    © 2016 GitHub, Inc. Terms Privacy Security Contact Help 


 ```
 
 


  
