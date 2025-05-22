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

>Long Method en cuenta.sacar(): similar al que habia en cuenta.poner()
- Refactor: extract method, la validacion entera se mueve a un metodo que se encargue de validar que no se extraiga
<br>
mas de lo que hay en cuenta ni mas del limite diario
- Nota: nuevamente el limite diario se podria inyectar como atributo, al no ser un requerimiento explicito aplico YAGNI
<br>
Tambien podria descomponerse la validacion en 2 metodos (uno para cuando queres sacar mas de lo que tenes y otro cuando superas el limite diario)
<br>
Decidi no hacerlo ya que lo considero agregar una complejidad innecesaria para 2 chequeos triviales, valore mas la simplicidad

>Inapropiate Intimacy entre Cuentas y Movimientos: la cuenta esta creando un movimiento y se pasa a si misma como objeto
<br>
y es el movimiento quien se mete en la lista de movimientos de la cuenta y le actualiza el saldo
- Refactor: move method, pasamos la responsabilidad y el comportamiento de modificar el estado de la cuenta a la cuenta en si
<br>
Asi, preservamos mejor el encapsulamiento y la cohesion de ambas clases

> Type test/temporary field en clase Movimiento: el uso de un booleano para determinar si un movimiento es extraccion o deposito
<br>
> y el uso de condicionales para determinar un comportamiento diferenciado indica que me estoy perdiendo una abstraccion y uso de polimorfismo
- Refactor: extract class y aplicar polimorfismo, creo clases Extraccion y Deposito que sigue Interfaz Movimiento
- Nota: esto tambien modifica el codigo de la clase cuenta y su forma de usar los Movimientos. 
<br>Si bien introduce un poco mas de complejidad (una nueva clase) logra mejores abstracciones y me permite aplicar
<br>polimorfismo, dandome un codigo mas legible y extensible, por lo que creo que es un cambio netamente positivo.