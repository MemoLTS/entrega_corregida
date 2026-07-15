Proyecto academico para clase de fullstack de caso de tienda hipotetica
de eco market
Desarollado por Guillermo Toledo 
Apis: 
- inventario
- catalogo
- backup
- monitoreo


puertos:
Inventario: 8090
Catalogo: 8085
backup: 8086
monitor:8089
xampp: 3306

Bases de datos: 
inventario
catalogo,
backup,
monitor

Ejemplos :
Delete: http://localhost:8083/api/v1/ecomarket/inventario/deleteprod/1

Post: http://localhost:8083/api/v1/ecomarket/inventario/addprod

PUT
{
    "nombre": "Coca Cola 1L",
    "precio": 1990,
    "stock": 25,
    "categoria": "BEBIDAS"
}

http://localhost:8085/catalogo/PorNombre/pepsi 

Swagger:
http://localhost:8085/swagger-ui/index.html#/

http://localhost:8090/swagger-ui/index.html#/


Se utilizo postman para las pruebas y utlizacion del proyecto
Se necesita la carpeta de xampp en raiz, donde se generara un backup 
en una primera ocacion y despues de forma periodica cada 2 dias

