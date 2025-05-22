# Code smells encontrados

>Duplicated code: el chequeo de monto negativo se repite en cuenta.poner() y cuenta.sacar()
- Refactor: extract method, hago un metodo llamado validar monto

>Inapropiate Intimacy y message chain en cuenta.getMontoExtraido(fecha): rompe el encapsulamiento del movimiento y
> <br> encadenando mensajes a un getter del objeto
- Refactor: move method (en realidad ya estaba creado, pero empezamos a usarlo) -> usamos el fueExtraido de la clase movimiento


