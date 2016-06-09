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
4 . Listar notas
 - Revisamos la documentación en la sección Data : https://backendless.com/documentation/data/rest/data_retrieving_properties_of_the_d.htm
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
5. Referencias
Librerías que vamos a usar, Retrofit, OkHttp y GSON

- http://square.github.io/retrofit/
- http://square.github.io/okhttp/
- https://github.com/google/gson

