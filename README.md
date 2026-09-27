## SIMULADOR DE SISTEMA OPERATIVO

Elder Leon Perez 2023166120

Curso: Sistemas Operativos

video: https://youtu.be/25uWnqFyhg4



## Descripción general

La aplicación posee una interfaz gráfica que permite encender y apagar el sistema, seleccionar archivos ASM, visualizar instrucciones, ejecutar una instrucción o todo el proceso, consultar los registros de CPU, observar la memoria, revisar los estados de los procesos, inspeccionar el BCP seleccionado y seguir eventos por medio de una consola.

Compilación desde NetBeans

Instalar JDK 25 y Apache NetBeans.

Abrir NetBeans y seleccionar File > Open Project.

Seleccionar la carpeta JavaApplication2 del repositorio.

Verificar en Project Properties > Libraries/Java Platform que el proyecto use JDK 25.

Ejecutar Clean and Build Project.


## Estructura lógica del sistema

| Clase | Responsabilidad principal |
| --- | --- |
| JavaApplication2 | Punto de entrada del proyecto; abre la ventana principal en el Event Dispatch Thread. |
| Ventana | Interfaz Swing: interacción con el usuario, tablas, consola, selección de ASM y visualización del estado. |
| SimuladorSO | Fachada del simulador: encendido, carga de programas, preparación de procesos, despacho, ejecución y reinicio. |
| Lectorarchivos | Lee el archivo ASM, valida la gramática y reporta errores por número de línea. |
| Programa | Representa un programa y su lista de instrucciones. |
| Instruccion | Representa una instrucción ASM y genera su codificación binaria. |
| Proceso | Relaciona un Programa con su BCP. |
| BCP | Mantiene PID, estado, PC, AC, base, tamaño y registros AX/BX/CX/DX. |
| GestorProceso | Administra READY, RUNNING, BLOCKED y TERMINATED, además de asignar PID. |
| Despachador | Selecciona el siguiente proceso y carga su contexto en la CPU. |
| CPU | Simula PC, IR, AC, AX, BX, CX y DX; ejecuta MOV, LOAD, STORE, ADD y SUB. |
| Memory | Representa la memoria principal, separa zona del SO y usuario, guarda BCP e instrucciones y busca espacio contiguo. |


## Ejemplo de programa válido:

MOV AX, 10

MOV BX, 5

LOAD AX

ADD BX

STORE CX

SUB AX

STORE DX

## CPU y representación de instrucciones

La CPU simulada mantiene los registros PC, IR, AC, AX, BX, CX y DX. Al ejecutar una instrucción, esta se obtiene desde memoria usando la base del proceso y el PC. El IR recibe la representación binaria de la instrucción, se ejecuta la operación, se incrementa el PC y el contexto se copia al BCP del proceso.

- PC: contador de programa.

- IR: instrucción actual en formato binario.

- AC: acumulador de operaciones aritméticas y transferencias.

- AX, BX, CX, DX: registros de propósito general.

## Organización de memoria

La memoria se representa mediante un único arreglo. El tamaño mínimo permitido es 128 posiciones. El 20 % se reserva para estructuras del sistema operativo y el resto se utiliza para las instrucciones de los programas.

- Memoria por defecto: 128 posiciones.

- Zona del SO: ceil(tamaño × 0.20).

- Zona de usuario: posiciones restantes.

- BCP por proceso: bloque de 10 posiciones dentro de la zona del SO.

- La interfaz permite cambiar el tamaño de memoria

## Manual de ejecución de la aplicación

-  Iniciar el proyecto desde NetBeans con Run Project (F6) o ejecutar el JAR generado.

-  Presionar Encender. Mientras el sistema esté apagado, la interfaz impide cargar programas o cambiar memoria.

-  Si el programa que se desea cargar es grande, usar Opciones > Cambiar memoria e indicar un tamaño mayor 128 posiciones.

-  Seleccionar Opciones > Cargar programa.

-  Elegir un archivo .asm en el JFileChooser.

-  Si el archivo contiene errores de gramática, el sistema muestra el número de errores y la línea correspondiente.

-  Si el archivo es válido, se crea un Programa, posteriormente un Proceso con PID y BCP, y se intenta cargar en memoria.

-  Usar Ejecutar paso para avanzar una instrucción y observar los cambios de PC, IR, AC y registros.

-  Usar Ejecutar todo para completar las instrucciones restantes del proceso actual.

-  Seleccionar un proceso en la tabla para consultar su BCP.

-  Revisar la tabla de memoria y la consola para seguir la ejecución y los eventos.

-  Usar Reiniciar SO para limpiar procesos, CPU y memoria, o Apagar para detener la interacción con el simulador


## Estado de requerimientos

| Requerimiento | Estado | Observación |
| --- | --- | --- |
| Encendido apagado lógico del sistema | y CUMPLIDO | La GUI controla el estado del simulador y valida operaciones cuando está apagado. |
| Selección gráfica de archivos ASM | CUMPLIDO | Se utiliza JFileChooser con filtro *.asm. |
| Validación gramática ASM | de CUMPLIDO | Se validan operación, registro, número de parámetros y rango de MOV; se reporta línea del error. |
| Creación Programa y Proceso | de CUMPLIDO | Un ASM válido se transforma en Programa y posteriormente en Proceso. |
| PID automático por proceso | CUMPLIDO | GestorProceso asigna PID incremental. |
| BCP por proceso | CUMPLIDO | Contiene PID, estado, PC, AC, base, tamaño y AX/BX/CX/DX. |
| Estados NEW, READY, RUNNING, BLOCKED, TERMINATED | PARCIAL | Las estructuras y métodos existen, pero BLOCKED no forma parte del flujo normal de ejecución. |
| Cola READY | CUMPLIDO | READY usa Queue/ArrayDeque y el despacho toma el primer proceso. |
| CPU con PC, IR, AC, AX, BX, CX y DX | CUMPLIDO | Todos los registros están modelados y visibles en la interfaz. |
| Ejecución MOV, LOAD, STORE, ADD y SUB | CUMPLIDO | Las cinco operaciones están implementadas en CPU. |
