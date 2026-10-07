# SIMULADOR DE SISTEMA OPERATIVO

**Elder Leon Perez**\
**Carné:** 2023166120\
**Curso:** Sistemas Operativos

## Videos del proyecto

### Video - Primera parte

[https://youtu.be/25uWnqFyhg4](https://youtu.be/25uWnqFyhg4)

### Video - PY1

[ video correspondiente a PY1.](https://youtu.be/UOp9VuSRxQE)

---

# Descripción general

Este proyecto consiste en la implementación de un **simulador de sistema operativo** desarrollado en Java utilizando una interfaz gráfica con **Swing**.

El simulador representa de forma simplificada diferentes componentes y funciones de un sistema operativo, entre ellos:

- Administración de programas y procesos.
- Lista de trabajos.
- Planificación de procesos.
- Estados de los procesos.
- Bloques de Control de Proceso (BCP).
- Memoria principal.
- Disco.
- Memoria virtual.
- CPU y registros.
- Despacho de procesos.
- Ejecución de instrucciones ASM.
- Interrupciones.
- Entrada y salida.
- Visualización del estado interno del sistema mediante una interfaz gráfica.

La aplicación permite encender y apagar el sistema, seleccionar archivos ASM, visualizar instrucciones, cargar programas en disco, admitir trabajos como procesos, ejecutar instrucciones paso a paso o automáticamente, observar los registros de la CPU, inspeccionar memoria y disco, consultar los estados de los procesos y seguir los eventos del simulador mediante una consola.

---

# Compilación desde NetBeans

1. Instalar **JDK 25** y **Apache NetBeans**.

2. Abrir NetBeans.

3. Seleccionar:

   `File > Open Project`

4. Seleccionar la carpeta:

   `JavaApplication2`

5. Verificar en:

   `Project Properties > Libraries > Java Platform`

   que el proyecto esté configurado para utilizar **JDK 25**.

6. Ejecutar:

   `Clean and Build Project`

7. Finalmente ejecutar el proyecto con:

   `Run Project (F6)`

---

# Estructura lógica del sistema

| Clase              | Responsabilidad principal                                                                                                                                      |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `JavaApplication2` | Punto de entrada del proyecto. Inicia la aplicación y abre la ventana principal utilizando el Event Dispatch Thread de Swing.                                  |
| `Ventana`          | Interfaz gráfica del simulador. Permite interactuar con programas, procesos, memoria, disco, CPU, consola y demás componentes.                                 |
| `SimuladorSO`      | Fachada principal del sistema. Coordina el encendido, apagado, carga de programas, creación de procesos, ejecución, despacho y reinicio del simulador.         |
| `Lectorarchivos`   | Lee archivos ASM, analiza las instrucciones, valida su estructura y reporta errores indicando la línea correspondiente.                                        |
| `Programa`         | Representa un programa cargado al sistema y contiene su conjunto de instrucciones.                                                                             |
| `Instruccion`      | Representa una instrucción ASM, sus operandos y su correspondiente representación binaria.                                                                     |
| `Proceso`          | Representa una instancia en ejecución de un programa y mantiene la relación con su BCP.                                                                        |
| `BCP`              | Bloque de Control de Proceso. Mantiene PID, estado, PC, AC, AX, BX, CX, DX, base, tamaño y demás información necesaria para restaurar el contexto del proceso. |
| `GestorProceso`    | Administra los procesos y sus diferentes estados, además de asignar automáticamente los PID.                                                                   |
| `Despachador`      | Selecciona el proceso que debe utilizar la CPU y restaura su contexto.                                                                                         |
| `CPU`              | Simula los registros y ejecuta las instrucciones del lenguaje ASM utilizado por el proyecto.                                                                   |
| `Memory`           | Representa la memoria RAM, la zona reservada para el sistema operativo, los BCP y las instrucciones de los procesos.                                           |
| `Disco`            | Representa el almacenamiento secundario del simulador, permitiendo guardar programas, índices y memoria virtual.                                               |
| `IndicePrograma`   | Mantiene la información necesaria para localizar un programa almacenado en disco.                                                                              |

---

# Flujo general del sistema

El flujo principal utilizado por el simulador es:

```text
Archivo ASM
    ↓
Validación de instrucciones
    ↓
Programa
    ↓
Disco
    ↓
Lista de trabajos
    ↓
Admisión
    ↓
Creación del proceso
    ↓
BCP
    ↓
READY
    ↓
Planificador
    ↓
Despachador
    ↓
RUNNING
    ↓
CPU
```

Cuando un programa se encuentra almacenado en disco puede ingresar a la **lista de trabajos**.

La lista de trabajos representa los programas que se encuentran esperando ser admitidos al sistema como procesos.

Cuando existe disponibilidad para admitir un trabajo:

1. Se obtiene el programa de la lista de trabajos.
2. Se crea su proceso.
3. Se genera un PID.
4. Se crea su BCP.
5. Se intenta cargar el proceso en memoria.
6. El proceso pasa al estado `READY`.
7. El planificador determina cuándo puede utilizar la CPU.
8. El despachador carga su contexto.
9. El proceso pasa a `RUNNING`.

Una vez que el programa fue admitido y convertido en proceso, deja de formar parte de la lista de trabajos.

---

# Estados de los procesos

El simulador trabaja con los principales estados de un proceso:

```text
NEW
READY
RUNNING
BLOCKED
TERMINATED
```

### NEW

El proceso acaba de ser creado y todavía debe ser admitido completamente por el sistema.

### READY

El proceso se encuentra preparado para ejecutarse y está esperando que la CPU quede disponible.

### RUNNING

El proceso actualmente está utilizando la CPU.

### BLOCKED

El proceso no puede continuar temporalmente porque está esperando que ocurra un evento, por ejemplo una operación de entrada o salida.

### TERMINATED

El proceso terminó su ejecución y ya no volverá a utilizar la CPU.

Una transición típica puede representarse como:

```text
NEW
 ↓
READY
 ↓
RUNNING
 ├────────→ BLOCKED
 │             ↓
 │           READY
 │
 └────────→ TERMINATED
```

---

# Planificación de procesos

El simulador utiliza actualmente planificación **FIFO / FCFS (First In, First Out / First Come, First Served)**.

Los procesos que llegan al estado `READY` se almacenan en una cola.

El primer proceso que entra a la cola será el primer proceso seleccionado para utilizar la CPU.

Ejemplo:

```text
READY

P1 → P2 → P3 → P4
```

El planificador selecciona:

```text
P1
```

Posteriormente el despachador carga el contexto de `P1` en la CPU y cambia su estado a:

```text
RUNNING
```

Cuando ese proceso termina o deja de utilizar la CPU, se selecciona el siguiente proceso disponible.

---

# Gestor de procesos

`GestorProceso` se encarga de administrar los procesos existentes en el sistema.

Entre sus responsabilidades se encuentran:

- Crear procesos.
- Asignar PID automáticamente.
- Mantener los procesos en sus estados correspondientes.
- Agregar procesos a `READY`.
- Identificar el proceso en ejecución.
- Bloquear procesos.
- Desbloquear procesos.
- Finalizar procesos.
- Mantener información de los procesos terminados.

La planificación de CPU se mantiene separada conceptualmente de la administración de los estados realizada por el gestor.

---

# Bloque de Control de Proceso - BCP

Cada proceso posee un **BCP**.

El BCP permite almacenar toda la información necesaria para detener temporalmente un proceso y posteriormente continuar su ejecución.

Entre los datos almacenados se encuentran:

- PID.
- Estado.
- PC.
- AC.
- AX.
- BX.
- CX.
- DX.
- Dirección base.
- Tamaño del proceso.

Cada BCP ocupa un bloque de **10 posiciones de memoria** dentro de la zona reservada para el sistema operativo.

Durante un cambio de contexto, los registros de la CPU se guardan en el BCP correspondiente.

Cuando el proceso vuelve a ejecutarse, su información se utiliza para restaurar el estado de la CPU y continuar desde el punto donde se había detenido.

---

# CPU

La CPU simulada mantiene los siguientes registros:

| Registro | Función                                                                      |
| -------- | ---------------------------------------------------------------------------- |
| `PC`     | Program Counter. Indica la próxima instrucción que debe ejecutar el proceso. |
| `IR`     | Instruction Register. Mantiene la representación de la instrucción actual.   |
| `AC`     | Acumulador utilizado durante operaciones aritméticas y transferencias.       |
| `AX`     | Registro de propósito general.                                               |
| `BX`     | Registro de propósito general.                                               |
| `CX`     | Registro de propósito general.                                               |
| `DX`     | Registro de propósito general.                                               |

Durante la ejecución de una instrucción:

1. Se obtiene la instrucción desde memoria.
2. Se carga la representación de la instrucción en `IR`.
3. Se interpreta la operación.
4. Se modifican los registros correspondientes.
5. Se actualiza `PC`.
6. Se guarda nuevamente el contexto del proceso en su BCP.

---

# Conjunto de instrucciones

El lenguaje ASM utilizado por el simulador ha sido ampliado durante el desarrollo del proyecto.

Entre las instrucciones manejadas se encuentran:

### Transferencia de datos

```asm
MOV
LOAD
STORE
```

### Operaciones aritméticas

```asm
ADD
SUB
INC
DEC
```

### Intercambio de datos

```asm
SWAP
```

### Comparación y saltos

```asm
CMP
JMP
JE
JNE
```

### Manejo de pila

```asm
PUSH
POP
PARAM
```

### Interrupciones

```asm
INT 09H
INT 10H
INT 20H
INT 21H
```

El comportamiento específico depende de la instrucción y de los parámetros suministrados.

---

# Ejemplo de programa ASM

```asm
MOV AX, 10
MOV BX, 5

LOAD AX
ADD BX

STORE CX

SUB AX

STORE DX
```

Este programa carga valores en los registros `AX` y `BX`, realiza operaciones utilizando el acumulador y almacena los resultados en otros registros.

---

# Validación de archivos ASM

Antes de que un programa pueda ingresar al sistema, el archivo ASM es procesado por `Lectorarchivos`.

El analizador verifica aspectos como:

- Operación válida.
- Cantidad correcta de parámetros.
- Registros válidos.
- Valores numéricos.
- Rango permitido de números.
- Estructura de cada instrucción.

Cuando se encuentra un error, el sistema muestra información sobre la línea donde se produjo.

Por ejemplo:

```text
Error en línea 5
```

Si el archivo es válido, se crea un objeto `Programa`.

---

# Disco

El simulador posee almacenamiento secundario representado mediante la clase `Disco`.

El disco se divide lógicamente en diferentes regiones:

```text
DISCO
├── Índices de programas
├── Archivos / instrucciones
└── Memoria virtual
```

El índice de un programa mantiene información como:

- Nombre.
- Dirección de inicio.
- Tamaño.

Esto permite localizar las instrucciones correspondientes a cada programa almacenado.

El disco utiliza búsqueda de espacio disponible para determinar dónde puede almacenar un nuevo programa.

---

# Lista de trabajos

La lista de trabajos contiene programas almacenados en disco que están esperando ser admitidos como procesos.

Su función es mantener separadas dos ideas importantes:

```text
Programa almacenado
        ↓
Lista de trabajos
        ↓
Proceso
```

Un programa puede existir en disco sin necesariamente tener un proceso activo.

Cuando el programa es admitido:

```text
Programa
   ↓
Proceso + PID + BCP
```

deja la lista de trabajos y pasa a ser administrado por el gestor de procesos.

Esto permite evitar confundir los programas almacenados con los procesos que actualmente forman parte del sistema.

---

# Organización de memoria

La memoria principal se representa mediante un único arreglo.

El tamaño mínimo permitido es:

```text
128 posiciones
```

La memoria se divide conceptualmente en:

```text
MEMORIA RAM
├── Zona del sistema operativo
└── Zona de usuario
```

El **20 %** de la memoria se reserva para estructuras relacionadas con el sistema operativo.

La cantidad reservada se calcula mediante:

```text
ceil(tamañoMemoria × 0.20)
```

El espacio restante se utiliza para almacenar instrucciones de los procesos.

### Configuración básica

- Memoria mínima: `128`.
- Zona del SO: `20 %`.
- Zona de usuario: espacio restante.
- BCP: `10 posiciones por proceso`.

La interfaz permite modificar el tamaño de memoria antes de cargar procesos que requieran mayor capacidad.

---

# Carga de procesos en memoria

Para ejecutar un proceso es necesario encontrar espacio suficiente dentro de la zona de usuario.

La memoria busca un bloque contiguo disponible donde puedan almacenarse las instrucciones del proceso.

Una vez cargado se establece:

```text
Base
Tamaño
```

La dirección real de una instrucción se obtiene utilizando la base del proceso y su contador de programa.

Conceptualmente:

```text
Dirección = Base + PC
```

Esto permite que cada proceso utilice un `PC` relativo a sus propias instrucciones.

---

# Cambios de contexto

Cuando la CPU deja de ejecutar un proceso, su información debe conservarse.

Para ello se realiza un cambio de contexto.

Primero se guardan los registros actuales:

```text
PC
AC
AX
BX
CX
DX
```

en el BCP.

Cuando el proceso vuelve a obtener la CPU, el despachador recupera esos valores.

De esta forma puede continuar su ejecución desde la instrucción correspondiente en lugar de comenzar nuevamente.

---

# Interrupciones

El simulador incorpora instrucciones de interrupción para representar situaciones donde la CPU necesita solicitar servicios externos o del sistema.

Las interrupciones implementadas en el proyecto incluyen:

```asm
INT 09H
INT 10H
INT 20H
INT 21H
```

Estas instrucciones permiten representar operaciones relacionadas con entrada/salida y servicios del sistema.

Una interrupción puede provocar que un proceso pase temporalmente de:

```text
RUNNING
```

a:

```text
BLOCKED
```

Mientras el proceso está bloqueado, otro proceso puede utilizar la CPU.

Cuando se completa el evento que estaba esperando, el proceso puede regresar a:

```text
READY
```

y posteriormente continuar su ejecución.

Es importante conservar correctamente su `PC` y registros para evitar que la instrucción se reinicie desde el principio al recuperar el proceso.

---

# Entrada de teclado

Las operaciones de entrada pueden provocar que el proceso deba esperar por información del usuario.

El flujo utilizado conceptualmente es:

```text
Proceso RUNNING
      ↓
Solicita entrada
      ↓
Proceso BLOCKED
      ↓
Usuario introduce valor
      ↓
Valor disponible para CPU
      ↓
Proceso READY
      ↓
Despacho
      ↓
Continúa ejecución
```

Esto permite representar el comportamiento de un proceso que debe esperar por una operación de entrada/salida antes de continuar.

---

# Interfaz gráfica

La clase `Ventana` permite visualizar e interactuar con el estado completo del simulador.

La interfaz incluye componentes para:

- Encender y apagar el sistema.
- Seleccionar archivos ASM.
- Cargar programas.
- Consultar programas almacenados.
- Observar la lista de trabajos.
- Consultar procesos.
- Ejecutar instrucciones paso a paso.
- Ejecutar procesos automáticamente.
- Observar registros de CPU.
- Visualizar memoria.
- Visualizar disco.
- Consultar el BCP.
- Observar los diferentes estados de los procesos.
- Consultar eventos mediante consola.
- Cambiar configuraciones de almacenamiento.
- Reiniciar el sistema operativo.

La interfaz se actualiza después de las operaciones importantes para reflejar el estado interno del simulador.

---

# Manual de ejecución

## 1. Iniciar la aplicación

Ejecutar el proyecto desde NetBeans mediante:

```text
Run Project (F6)
```

También puede ejecutarse el archivo JAR generado después de compilar el proyecto.

---

## 2. Encender el sistema

Presionar:

```text
Encender
```

Mientras el sistema se encuentra apagado, las operaciones que dependen del sistema operativo permanecen restringidas.

---

## 3. Configurar memoria

Si se desea modificar el tamaño de memoria utilizar:

```text
Opciones > Cambiar memoria
```

El tamaño mínimo permitido es:

```text
128
```

---

## 4. Cargar un programa

Seleccionar la opción correspondiente para cargar un archivo ASM.

Se abrirá un `JFileChooser`.

Seleccionar un archivo:

```text
*.asm
```

---

## 5. Validación

El archivo será analizado antes de ingresar al sistema.

Si existen errores se mostrará información indicando las líneas correspondientes.

Si el programa es válido podrá almacenarse y continuar dentro del flujo del simulador.

---

## 6. Lista de trabajos

Los programas preparados para ser admitidos pueden mantenerse en la lista de trabajos.

Cuando existe disponibilidad, el sistema puede admitir un trabajo y crear su proceso correspondiente.

---

## 7. Creación del proceso

Al admitir un programa:

- Se crea un `Proceso`.
- Se asigna un PID.
- Se crea su BCP.
- Se intenta cargar en memoria.
- Se coloca en `READY`.

---

## 8. Despacho

El planificador utiliza FIFO para seleccionar el primer proceso disponible en la cola `READY`.

El despachador carga su contexto en CPU y el proceso pasa a:

```text
RUNNING
```

---

## 9. Ejecución paso a paso

Utilizar:

```text
Ejecutar paso
```

para ejecutar solamente una instrucción.

Esto permite observar directamente los cambios en:

```text
PC
IR
AC
AX
BX
CX
DX
```

así como en memoria, BCP y estados de los procesos.

---

## 10. Ejecución automática

Utilizar:

```text
Ejecutar todo
```

para ejecutar las instrucciones restantes según el comportamiento establecido por el simulador.

---

## 11. Consultar un proceso

Seleccionar un proceso desde la tabla correspondiente para visualizar la información almacenada en su BCP.

---

## 12. Consultar memoria y disco

Las tablas de la interfaz permiten observar el contenido almacenado en:

```text
Memoria RAM
Disco
```

y analizar cómo cambian durante la ejecución.

---

## 13. Consultar la consola

La consola registra diferentes eventos producidos durante la ejecución del sistema, facilitando el seguimiento del comportamiento del simulador.

---

## 14. Reiniciar el sistema

La opción:

```text
Reiniciar SO
```

permite limpiar el estado de ejecución del simulador y preparar nuevamente sus componentes.

---

## 15. Apagar

La opción:

```text
Apagar
```

detiene la interacción con las funciones principales del sistema hasta volver a encenderlo.

---

# Primera parte del proyecto

La primera etapa del proyecto se concentró principalmente en construir las bases del simulador.

Entre los componentes desarrollados se encuentran:

- Interfaz gráfica.
- Encendido y apagado lógico.
- Selección de archivos ASM.
- Validación de instrucciones.
- Creación de programas.
- Creación de procesos.
- PID automático.
- BCP.
- Estados básicos de procesos.
- Cola `READY`.
- Despachador.
- CPU.
- Registros.
- Memoria principal.
- Ejecución paso a paso.
- Ejecución completa.
- Visualización del estado del sistema.

Video correspondiente:

[https://youtu.be/25uWnqFyhg4](https://youtu.be/25uWnqFyhg4)

---

# PY1 / Segunda parte

Durante la siguiente etapa se amplió la simulación del sistema operativo incorporando conceptos adicionales.

Entre los principales cambios se encuentran:

- Incorporación del disco.
- Índices para localizar programas almacenados.
- Separación entre programas y procesos.
- Lista de trabajos.
- Admisión de trabajos.
- Planificación FIFO.
- Mejor administración de estados.
- Procesos bloqueados.
- Desbloqueo de procesos.
- Cambios de contexto.
- Ampliación del conjunto de instrucciones.
- Instrucciones de comparación y salto.
- Manejo de pila.
- Interrupciones.
- Entrada y salida.
- Memoria virtual.
- Actualización de las visualizaciones de disco, memoria, procesos y CPU.

---

# Estado general de requerimientos

| Requerimiento                       | Estado                     | Observación                                                                                                         |
| ----------------------------------- | -------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| Encendido y apagado lógico          | CUMPLIDO                   | La interfaz controla el estado lógico del simulador.                                                                |
| Selección gráfica de archivos ASM   | CUMPLIDO                   | Se utiliza `JFileChooser`.                                                                                          |
| Validación de ASM                   | CUMPLIDO                   | Se validan instrucciones, operandos y parámetros.                                                                   |
| Creación de Programa                | CUMPLIDO                   | Los archivos ASM válidos generan objetos `Programa`.                                                                |
| Almacenamiento en disco             | CUMPLIDO                   | Los programas pueden representarse dentro del almacenamiento secundario.                                            |
| Lista de trabajos                   | CUMPLIDO                   | Permite mantener programas pendientes de admisión.                                                                  |
| Creación de Proceso                 | CUMPLIDO                   | Los programas admitidos generan un proceso.                                                                         |
| PID automático                      | CUMPLIDO                   | El gestor asigna PID incrementalmente.                                                                              |
| BCP por proceso                     | CUMPLIDO                   | Cada proceso posee su propio bloque de control.                                                                     |
| Estados de procesos                 | CUMPLIDO                   | Se modelan los principales estados del ciclo de vida del proceso.                                                   |
| Cola READY                          | CUMPLIDO                   | Mantiene los procesos preparados para utilizar CPU.                                                                 |
| Planificación FIFO                  | CUMPLIDO                   | Se selecciona el primer proceso disponible en READY.                                                                |
| Despacho de procesos                | CUMPLIDO                   | Se carga el contexto correspondiente en CPU.                                                                        |
| Cambios de contexto                 | CUMPLIDO                   | Los registros pueden guardarse y recuperarse desde el BCP.                                                          |
| CPU con PC, IR, AC, AX, BX, CX y DX | CUMPLIDO                   | Los registros forman parte del modelo de CPU.                                                                       |
| MOV, LOAD, STORE, ADD y SUB         | CUMPLIDO                   | Operaciones básicas implementadas.                                                                                  |
| Instrucciones adicionales           | CUMPLIDO                   | Se amplió el conjunto de instrucciones del simulador.                                                               |
| Saltos y comparaciones              | CUMPLIDO                   | Se incorporaron instrucciones como CMP, JMP, JE y JNE.                                                              |
| Manejo de pila                      | CUMPLIDO                   | Se incorporaron operaciones relacionadas con pila y parámetros.                                                     |
| Interrupciones                      | EN DESARROLLO / PARCIAL    | Se encuentran integradas al flujo y continúan ajustándose aspectos de bloqueo, entrada y recuperación del contexto. |
| BLOCKED y desbloqueo                | EN DESARROLLO / PARCIAL    | Se utiliza principalmente para eventos asociados a interrupciones y entrada/salida.                                 |
| Memoria virtual                     | IMPLEMENTADA EN ESTRUCTURA | Forma parte de la organización lógica del disco.                                                                    |
| Interfaz de monitoreo               | CUMPLIDO                   | Permite visualizar CPU, procesos, BCP, memoria, disco y consola.                                                    |
