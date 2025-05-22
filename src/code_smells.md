# Code smells encontrados

>Duplicated code: el chequeo de monto negativo se repite en cuenta.poner() y cuenta.sacar()
- Refactor: extract method, hago un metodo llamado validar monto

>Inapropiate Intimacy y message chain en cuenta.getMontoExtraido(fecha): rompe el encapsulamiento del movimiento y
> <br> encadenando mensajes a un getter del objeto
- Refactor: move method (en realidad ya estaba creado, pero empezamos a usarlo) -> usamos el fueExtraido de la clase movimiento

>Long Method en cuenta.poner(): hay una descomposicion de tareas inadecuada que hacer el codigo mas dificil de leer
- Refactor: extract method, hago un metodo que me permita ver si la cantidad de depositos de hoy supera un limite
- Nota: se podria hacer mas extensible el codigo haciendo que la fecha para la cual se chequea el deposito sea parametrizable
<br> 
al igual que el limite de depositos diario (mediante un atributo por ej). Decido no hacerlo ya que no es un requerimiento actualmente
<br>
(YAGNI) y no seria muy dificil agregarlo en el futuro, por lo que priorizo la simplicidad 