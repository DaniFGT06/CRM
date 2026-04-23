Para el desarrollo de este sistema CRM, se utilizó Inteligencia Artificial como asistente de código, bajo las siguientes premisas:

¿Qué se le pidió a la IA?: Se solicitó apoyo para generar el "boilerplate" o estructura base de los controladores REST y la configuración inicial de las dependencias en el pom.xml. También se consultó para la implementación de la lógica de seguridad con Spring Security 6 y la estructura de los filtros JWT.

Partes modificadas o corregidas: Se ajustaron manualmente las anotaciones de seguridad en los controladores, ya que la propuesta inicial no discriminaba correctamente entre los roles VENDEDOR y LECTOR según los requisitos del caso práctico. También se corrigieron las rutas de los endpoints para asegurar que siguieran las convenciones RESTful adecuadas.

Riesgos de copiar sin validar: El principal riesgo identificado fue la posible desincronización entre las versiones de Spring Boot (específicamente los cambios entre Spring Security 5 y 6). Copiar código sin validar podría haber resultado en métodos obsoletos (deprecated) o configuraciones de seguridad vulnerables que permitirían el acceso no autorizado a funciones críticas como la eliminación de clientes.
