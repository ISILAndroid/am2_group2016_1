# am2_group2016_1
Curso de Aplicaciones Móviles II - Grupo 2016-I

# Conexión Remota
En este sesión veremos como conectar nuestra aplicación Android con un Base de Datos que esta en la nube mediante servicios RESTFul, para lo cual usaremos backendless.

1. Crear un proyecto en backendless y agregar la tabla Note con los siguientes campos name (String) y description (String). Tambien usaremos la tabla User que viene por defecto  .

2. Usaremos el proyecto de Notas para la integración

2.1 Para poder acceder a la nube necesitamos el permiso de INTERNET, esto en el AndroidManifest de nuetsro proyecto
```
    <uses-permission android:name="android.permission.INTERNET" />
```
  2.2 Luego importar las librería para realizar la conexión, Retrofit , OkHttp y el conversor GSON . Esto en el gradle de nuestra APP
```
    //RETROFIT https://github.com/square/retrofit
    compile 'com.squareup.retrofit:retrofit:1.9.0'

    //GSON https://github.com/google/gson
    compile 'com.google.code.gson:gson:2.6.2'

    //OKHTTP
    compile 'com.squareup.okhttp:okhttp:2.5.0'
```
3 . LogIn 
 3.1 Revisamos la documentación de la API Rest https://backendless.com/products/documentation/
 3.2 LogIn: https://backendless.com/documentation/users/rest/users_login.htm
```
 method : POST
 url : /<version name>/users/login
 raw : 
 {
  "login" : value,
  "password" : value,
 }
```
3.3 Vamos a necesitar 3 entidades para poder trabajar el usuario, el logIn y la respuesta del servidor 
    UserEntity
```
public class UserEntity implements Serializable {
    private String email;
    private String name;
    private String objectId;
    private String token;
}
```

3.4 LogInRaw ( Esta entidad es lo que se va enviar al servidor con el email y password)

```
public class LogInRaw {
    private String login;
    private String password;
}

```

3.5 LogInResponse ( Esto es para la respuesta del servidor al hacer LogIn)

```
public class LogInResponse {

    private String message;

    private String name;

    @SerializedName("___class")
    private String type;

    @SerializedName("user-token")
    private String token;

    private String email;

    private String objectId;
}
```
3.6 Creamos una clase llamada ApiClient donde vamos a declarar todas las llamadas al servidor. Recordar que deben usar las credenciales de la BD que crearon en BackendLess "Identificador de Aplicación" y "Clave secreta REST"

 ```
 public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String PATH="http://api.backendless.com";

    private static ServicesApiInterface servicesApiInterface;

    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(PATH)
                    .setClient(new OkClient(getClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            servicesApiInterface = restAdapter.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        ///<version name>/users/login
        //@Headers({"Content-Type: application/json"})


        @Headers({
                "Content-Type: application/json",
                "application-id: xxxxxxx",
                "secret-key: xxxxxx",
                "application-type: REST"
        })
        //v1/users/login
        @POST("/v1/users/login")
        void login(@Body LogInRaw raw, Callback<LogInResponse> callback);


        @Headers({
                "Content-Type: application/json",
                "application-id: xxxxxx",
                "secret-key: xxxxx",
                "application-type: REST"
        })
        //v1/data/Notes
        @GET("/v1/data/Notes")
        void notes( Callback<NotesResponse> callback);


        @Headers({
                "Content-Type: application/json",
                "application-id: xxxxx",
                "secret-key: xxxxxx",
                "application-type: REST"
        })
        @POST("/v1/data/Notes")
        void addNote(@Body NoteRaw raw, Callback<NoteResponse> callback);
    }

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(2, TimeUnit.MINUTES);
        client.setReadTimeout(2, TimeUnit.MINUTES);
        return client;
    }
}
 ```
3.7  Vamos a considerar la actividad del LogIn como un vista sin lógica, solo con métodos para ser llamados por otra clase que se encargue de administrar estas acciones. Esta clase la vamos a llamar LogInPresenter y nuestra activity va implementar una insterfaz llamada LogInView 
 ```
 public interface LogInView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void gotoMain();
}

 ```
 
 ```
 public class LogInPresenter {

    private static final String TAG = "LogInPresenter";
    private LogInView logInView;
    private String email;
    private String password;

    public   void attachedView(LogInView logInView){
        this.logInView = logInView;
    }

    public  void detachView(){
        this.logInView=null;
    }

    public void logIn(String email,String password) {
        this.email = email;
        this.password = password;
        LogInRaw logInRaw= new LogInRaw();
        logInRaw.setLogin(this.email);
        logInRaw.setPassword(this.password);

        logInView.showLoading();

        ApiClient.getMyApiClient().login(logInRaw, new Callback<LogInResponse>() {
            @Override
            public void success(LogInResponse loginResponse, Response response) {
                loginSuccess(loginResponse,response);
            }

            @Override
            public void failure(RetrofitError error)
            {
                String json="Error ";
                try {
                    json= new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                loginError(json);

            }
        });

    }
    public void loginSuccess(LogInResponse loginResponse, Response response){
        if(loginResponse.isSuccess()){
            UserEntity userEntity= new UserEntity();
            userEntity.setEmail(loginResponse.getEmail());
            userEntity.setName(loginResponse.getName());
            userEntity.setObjectId(loginResponse.getObjectId());
            userEntity.setToken(loginResponse.getToken());
        }
        logInView.hideLoading();
        logInView.gotoMain();
    }

    public void loginError(String messageError){
        logInView.hideLoading();
        logInView.onMessageError(messageError);
    }
}
 ```
 3.8 De la conexión recibimos 2 tipos de respuesta: un SUCCESS y FAILURE, en donde informamos a la vista si la autenticación fue exitosa o si ocurrio algún error.
 
4 . Listar notas

4.1  Revisamos la documentación en la sección Data : https://backendless.com/documentation/data/rest/data_retrieving_properties_of_the_d.htm
 ```
 method : GET
 url : https://api.backendless.com/<version>/data/<table-name>/properties
 response: 
 [
 {
  "name": "updated",
  "required": false,
  "type": "DATETIME",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 },
 {
  "name": "created",
  "required": false,
  "type": "DATETIME",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 },
 {
  "name": "objectId",
  "required": false,
  "type": "STRING_ID",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 },
 {
  "name": "ownerId",
  "required": false,
  "type": "STRING",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 },
 {
  "name": "name",
  "required": false,
  "type": "STRING",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 },
 {
  "name": "age",
  "required": false,
  "type": "INT",
  "defaultValue": null,
  "relatedTable": null,
  "customRegex": null,
  "autoLoad": false
 }
]
```
4.2 Creamos una interfaz para declarar los métodos de la vista donde pintaremos las notas que están en backendless
```
public interface NotesView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void renderNotes(List<NoteEntity> notes);

}
```
4.3 Creamos un presenter donde esta la lógica de conexión usando una petición GET

```
public class NotesPresenter {

    private static final String TAG = "NotesPresenter";
    private NotesView notesView;

    public   void attachedView(NotesView notesView){
        this.notesView = notesView;
    }

    public  void detachView(){
        this.notesView=null;
    }

    public void loadNotes(){
        notesView.showLoading();

        ApiClient.getMyApiClient().notes(new Callback<NotesResponse>() {
            @Override
            public void success(NotesResponse notesResponse, Response response) {
                    notesSuccess(notesResponse,response);
            }

            @Override
            public void failure(RetrofitError error) {
                notesError("");
            }
        });
    }

    private void notesSuccess(NotesResponse notesResponse, Response response) {
        notesView.hideLoading();

        if(notesResponse.isSuccess()){
            List<NoteEntity> notes= notesResponse.getData();
            notesView.renderNotes(notes);
        }

    }
    private void notesError(String messageError){
        notesView.hideLoading();
        notesView.onMessageError(messageError);
    }
}
```

```
public class MainActivity extends ActionBarActivity implements NotesView {

    private static final String TAG ="MainActivity" ;
    private static final int ACTION_ADD=1;
    private static final int ACTION_DETAIL=2;

    private TextView tviLogout,tviUser;
    private ListView lstNotes;
    private Button btnAddNote;
    private View rlayLoading,container;
    private List<NoteEntity> lsNoteEntities;
    private CRUDOperations crudOperations;
    private NoteAdapter noteAdapter;

    private NotesPresenter notesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //populate();
        notesPresenter= new NotesPresenter();
        notesPresenter.attachedView(this);
        init();
        //loadData();
        //loadCloud();
    }

    private void loadCloud() {
        notesPresenter.loadNotes();
    }

    private void loadData() {
        crudOperations= new CRUDOperations(new MyDatabase(this));
        lsNoteEntities= crudOperations.getAllNotes();
        noteAdapter= new NoteAdapter(this,lsNoteEntities);
        lstNotes.setAdapter(noteAdapter);


    }

    private void populate() {

        CRUDOperations crudOperations= new CRUDOperations(new MyDatabase(this));
        crudOperations.addNote(new NoteEntity("Mi Nota","Esta es un nota ",null));
        crudOperations.addNote(new NoteEntity("Segunda Nota","Esta es la segunds nota ",null));
        crudOperations.addNote(new NoteEntity("Tercera Nota","Esta es la tercera nota ",null));
        crudOperations.addNote(new NoteEntity("Cuarta Nota","Esta es la cuarta nota ",null));
        crudOperations.addNote(new NoteEntity("Quinta Nota","Esta es la quinta nota ",null));
        crudOperations.addNote(new NoteEntity("Sexta Nota","Esta es la sexta nota ",null));

        Log.v(TAG, "populate " + crudOperations.getAllNotes());
    }

    private void init() {
        tviLogout= (TextView)findViewById(R.id.tviLogout);
        tviUser= (TextView)findViewById(R.id.tviUser);
        lstNotes= (ListView)(findViewById(R.id.lstNotes));
        btnAddNote= (Button)(findViewById(R.id.btnAddNote));
        rlayLoading= (findViewById(R.id.rlayLoading));

        //user Info
        String username = PreferencesHelper.getUserSession(this);
        if(username!=null)
        {
            tviUser.setText("Bienvenido "+ StringUtils.firstCapitalize(username));
        }

        //events
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoNote(ACTION_ADD, null);
            }
        });

        lstNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoteEntity noteEntity = (NoteEntity) adapterView.getAdapter().getItem(i);
                gotoNote(ACTION_DETAIL, noteEntity);
            }
        });

        tviLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void gotoNote(int action, NoteEntity noteEntity) {
        Intent intent= new Intent(this,NoteActivity.class);

        switch (action)
        {
            case ACTION_ADD:
                    intent.putExtra("FRAGMENT",NoteActivity.ADD_NOTE);
                    startActivity(intent);
                break;
            case ACTION_DETAIL:
                intent.putExtra("FRAGMENT",NoteActivity.DETAIL_NOTE);
                intent.putExtra("NOTE", noteEntity);
                startActivity(intent);
                break;
        }
    }

    private void logout() {
        PreferencesHelper.signOut(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResumen");
        //loadData();
        loadCloud();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void showLoading() {
        this.rlayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.rlayLoading.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onMessageError(String message) {
        Snackbar snackbar = Snackbar
                .make(container,message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void renderNotes(List<NoteEntity> notes) {
        lsNoteEntities= notes;
        noteAdapter= new NoteAdapter(this,lsNoteEntities);
        lstNotes.setAdapter(noteAdapter);
    }
}
```

5 .  Referencias
Librerías que vamos a usar, Retrofit, OkHttp y GSON

- http://square.github.io/retrofit/
- http://square.github.io/okhttp/
- https://github.com/google/gson

