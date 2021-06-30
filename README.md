<h1>Operaci&oacute;n Fuego de Quasar</h1>
<p><a href="quees">Descripci&oacute;n, desafio y consideraciones.</a></p>
<p><a href="#comofunciona">C&oacute;mo funciona el servicio?</a></p>
<p><a href="hosting">URL hosting del servicio</a></p>
<p><a href="ejecutar">Como ejecutar el programa?</a></p>
<p><a href="arquitectura">Arquitectura del servicio</a></p>
<p>&nbsp;</p>
<p id="quees"><strong>Descripci&oacute;n<br /></strong></p>
<p><span class="fontstyle0">Han Solo ha sido recientemente nombrado General de la Alianza Rebelde y busca dar un gran golpe contra el Imperio Gal&aacute;ctico para reavivar la llama de la resistencia.<br />El servicio de inteligencia rebelde ha detectado un llamado de auxilio de una nave portacarga imperial a la deriva en un campo de asteroides. El manifiesto de la nave es ultra clasificado, pero se rumorea que transporta raciones y armamento para una legi&oacute;n entera.</span></p>
<p><span class="fontstyle0"><strong>Desaf&iacute;o</strong><br /></span><span class="fontstyle0" style="color: #000000; font-size: 11pt;">Como jefe de comunicaciones rebelde, tu misi&oacute;n es crear un programa en el lenguaje de tu preferencia que </span><span class="fontstyle2">retorne la fuente y contenido del mensaje de auxilio</span><span class="fontstyle0" style="color: #000000; font-size: 11pt;">. Para esto, cuentas con tres sat&eacute;lites que te permitir&aacute;n triangular la&nbsp; posici&oacute;n, &iexcl;pero cuidado! el mensaje puede no llegar completo a cada&nbsp; sat&eacute;lite debido al campo de asteroides frente a la nave.<br /></span><span class="fontstyle2">Posici&oacute;n de los sat&eacute;lites actualmente en servicio<br /></span><span class="fontstyle0" style="color: #000000; font-size: 11pt;">● Kenobi: [-500, -200]<br />● Skywalker: [100, -100]<br />● Sato: [500, 100]</span></p>
<p><span class="fontstyle0"><strong>Consideraciones</strong><br />La unidad de distancia en los par&aacute;metros de </span><span class="fontstyle2">GetLocation </span><span class="fontstyle0">es la misma que la que se utiliza para indicar la posici&oacute;n de cada sat&eacute;lite.<br />El mensaje recibido en cada sat&eacute;lite se recibe en forma de arreglo de strings.<br />Cuando una palabra del mensaje no pueda ser determinada, se reemplaza por un string en blanco en el array.<br /><span class="fontstyle0" style="color: #000000; font-size: 11pt;">&gt;</span>Ejemplo: </span><span class="fontstyle2">[&ldquo;este&rdquo;, &ldquo;es&rdquo;, &ldquo;&rdquo;, &ldquo;mensaje&rdquo;]<br /></span><span class="fontstyle0">Considerar que existe un desfasaje (a determinar) en el mensaje que se recibe en cada sat&eacute;lite.<br />&gt;Ejemplo:<br /><span class="fontstyle0" style="color: #000000; font-size: 11pt;">● </span>Kenobi: </span><span class="fontstyle2">[&ldquo;&rdquo;, &ldquo;este&rdquo;, &ldquo;es&rdquo;, &ldquo;un&rdquo;, &ldquo;mensaje&rdquo;]<br /></span><span class="fontstyle0"><span class="fontstyle0" style="color: #000000; font-size: 11pt;">●</span> Skywalker: </span><span class="fontstyle2">[&ldquo;este&rdquo;, &ldquo;&rdquo;, &ldquo;un&rdquo;, &ldquo;mensaje&rdquo;]<br /></span><span class="fontstyle0"><span class="fontstyle0" style="color: #000000; font-size: 11pt;">● </span>Sato: </span><span class="fontstyle2">[&ldquo;&rdquo;, &rdquo;&rdquo;, &rdquo;es&rdquo;, &rdquo;&rdquo;, &rdquo;mensaje&rdquo;]</span></p>
<p>&nbsp;</p>
<p id="comofunciona"><strong>Como funciona el servicio</strong></p>
<p>El servicio esta compuesto por dos endpoints:</p>
<ul>
<li>&nbsp;<strong>/topsecret</strong> Servicio POST que permite obtener la posicion y el mensaje de ser posible por medio del envio de la distancia y los mensajes parciales de los tres satelites. Ejemplo:</li>
</ul>
<p style="padding-left: 30px;"><span class="fontstyle0">POST </span><span class="fontstyle1" style="font-size: 11pt;">&rarr; </span><span class="fontstyle0">/topsecret/<br />{<br />"satellites": [<br />{<br />&ldquo;name&rdquo;: "kenobi",<br />&ldquo;distance&rdquo;: 100.0,<br />&ldquo;message&rdquo;: ["este", "", "", "mensaje", ""]<br />},<br />{<br />&ldquo;name&rdquo;: "skywalker",<br />&ldquo;distance&rdquo;: 115.5<br />&ldquo;message&rdquo;: ["", "es", "", "", "secreto"]<br />},<br />{<br />&ldquo;name&rdquo;: "sato",<br />&ldquo;distance&rdquo;: 142.7<br />&ldquo;message&rdquo;: ["este", "", "un", "", ""]<br />}<br />]<br />}</span></p>
<ul>
<li><strong>&nbsp;/topsecret_split/{satellite_name}</strong> Servicio GET y POST que permite ingresar una a una la informacion de los satelites, se requiere un minimo de tres para calcular la posicion de la nave. Si el nombre del satelite ya existe, sus datos son actualizados y, si un cuarto satelite es ingresado se realizar&aacute; un nuevo calculo con tres nuevos satelites. Ejemplo:</li>
</ul>
<p style="padding-left: 30px;"><span class="fontstyle0">POST </span><span class="fontstyle1" style="font-size: 11pt;">&rarr; </span><span class="fontstyle0">/topsecret_split/{satellite_name}<br />{<br />"distance": 100.0,<br />"message": ["este", "", "", "mensaje", ""]<br />}</span></p>
<p>&nbsp;</p>
<p id="hosting"><strong>URL hosting del servicio</strong></p>
<p>&nbsp;<strong>/topsecret</strong></p>
<p><a href="http://adrimolmeliquasar-env.eba-jpbmm7gg.us-west-2.elasticbeanstalk.com/topsecret ">http://adrimolmeliquasar-env.eba-jpbmm7gg.us-west-2.elasticbeanstalk.com/topsecret </a></p>
<p><strong>/topsecret_split/{satellite_name}</strong></p>
<p><a href="http://adrimolmeliquasar-env.eba-jpbmm7gg.us-west-2.elasticbeanstalk.com/topsecret_split/{satellite_name}">http://adrimolmeliquasar-env.eba-jpbmm7gg.us-west-2.elasticbeanstalk.com/topsecret_split/{satellite_name}</a></p>
<p>&nbsp;</p>
<p id="ejecutar"><strong>Como ejecutar el programa?</strong></p>
<p>El programa puede ser clonado desde su repositorio: <a href="https://github.com/adrimol/Quasar_Meli/">https://github.com/adrimol/Quasar_Meli/</a></p>
<p>Posteriormente puede abrirlo desde su editor de confianza, yo personalmente utilizo IntelliJ, y para ejecutarlo basta con ejecutar (Run) el metodo <strong>main</strong> de la aplicacion. Ver clase "QuasarApplication"</p>

<p id="hosting">Una vez en ejecucion, puede utilizar el comando curl o una herramienta tipo Postman para ejecutar el servicio en la url <em>localhost:8080/topsecret</em> y <em>localhost:8080/topsecret_split/sato, haciendo uso de los request de ejemplo.<br /></em></p>
<p>&nbsp;</p>
<p id="arquitectura"><strong>Arquitectura del servicio</strong></p>
<h2>&nbsp;</h2>
<p>A continuaci&ograve;n se exponen diferentes vistas de la arquitectura de software</p>
<h3>Vista de Paquetes</h3>
<p>En la vista de paquetes se definen los paquetes que tendr&aacute; el componente software. Cada Paquete es una capa de software en nuestro componente y agrupa un conjunto de clases con responsabilidades comunes, los paquetes son:</p>
<p><a href="https://github.com/JoseLuisSR/quasar/blob/master/doc/img/architecture-PackageView.png?raw=true" target="_blank" rel="noopener noreferrer"><img src="https://github.com/JoseLuisSR/quasar/raw/master/doc/img/architecture-PackageView.png?raw=true" alt="Screenshot" /></a></p>
<ul>
<li>controller: Todos las clases que reciben peticiones HTTP a los end-point defindos.</li>
<li>services: Todas las clases con l&oacute;gica de negocio para Localizar nave y construir mensaje.</li>
<li>entidades: Todas las clases que representan el negocio.</li>
<li>exceptions: Todas las excepciones para el manejo de errores controlados.</li>
<li>libraries: Dependencias con librer&iacute;as externas.</li>
</ul>
<p>La comunicaci&oacute;n entre paquetes es unidireccional y solo con la capa siguiente inferior.</p>
<h3>Vista de clases</h3>
<p>El diagrama de clases expone las clases necesarias para representar el negocio, los servicios con l&oacute;gica y reglas de negocio y Controladores para atender la petici&oacute;n HTTP a los end-points.</p>
<p><a href="https://github.com/JoseLuisSR/quasar/blob/master/doc/img/architecture-ClassView.png?raw=true" target="_blank" rel="noopener noreferrer"><img src="https://github.com/JoseLuisSR/quasar/raw/master/doc/img/architecture-ClassView.png?raw=true" alt="Screenshot" /></a></p>
<p>&nbsp;</p>
<p>Principios de programaci&oacute;n orientada a objectos para:</p>
<ul>
<li>Separar las responsabilidades de localizaci&oacute;n de nave y construir mensajes en diferentes servicios.</li>
<li>Usar caracter&iacute;sticas de herencia y polimorfismo para definir una nave espacial y extender su comportamiento en sat&eacute;lite y carguero</li>
<li>Invertir dependencias entre clases usando interfaces para la comunicaci&oacute;n entre capa controller y services.</li>
</ul>
<p>Se utiliz&oacute; Java y el framework Spring Boot para construir el componente Software, gracias a <a href="https://github.com/JoseLuisSR/quasar/commits?author=JoseLuisSR">JoseLuisSR</a> por la estructura nivel 1.</p>
<p>Tambien se usan buenas practicas de&nbsp;<a href="https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html" rel="nofollow">arquitecturas limpias</a>&nbsp;para separar las responsabilidades en capas, controlar la comunicaci&oacute;n entre capas, restringir el uso de clases de capas superiores desde una capa inferior e invertir el flujo de comunicaci&oacute;n entre capas por medio de interfaces y el principio de inversi&oacute;n de dependencias.</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
