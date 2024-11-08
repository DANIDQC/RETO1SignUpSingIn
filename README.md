<h1>Repositorio de GitHub</h1>

Repositorio -> https://github.com/DANIDQC/RETO1SignUpSingIn.git </br>

<h1>Parámetros de la base de datos en ".propierties"</h1>

<h3>Ficheros de cliente: </h3>

<h5>conexionInfo.propierties</h5>
<ul>
  <li>PUERTO=Puerto en el que escucha el server</li>
  <li>IP=Ip local por que es donde ejecutaremos el server</li>
</ul>

<h3>Ficheros de Server: </h3>

<h5>database.propierties</h5>
<ul>
  <li>url=url de la maquina que tenga odoo para conectarse junto con el nombre de la base de datos</li>
  <li>username=odoo, por que es el nombre del usuario en la base de datos</li>
  <li>password=contraseña del usuario</li>
  <li>size=maximo de hilos que se pueden crear</li>
</ul>

<h3>Orden de ejecución de los tests</h3>
<ul>
  <li>1. TestSignUp</li>
  <li>2. TestSignIn</li>
  <li>3. TestServerError -> Para probar correctamente este test, el server deberá estar apagado</li>
</ul>

<h3>Orden de ejecución del proyecto</h3>
<h5>Aunque realmente no importa el orden en que se ejecuten, se recomienda iniciar primero el server</h5>
