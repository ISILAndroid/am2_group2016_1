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
3. LogIn 
 - Revisamos la documentación de la API Rest https://backendless.com/products/documentation/
 - LogIn: https://backendless.com/documentation/users/rest/users_login.htm
```
 method : POST
 url : /<version name>/users/login
 raw : 
 {
  "login" : value,
  "password" : value,
 }
```
4. Listar notas
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

