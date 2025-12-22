# Trabajo Final - MH (Simple Evolutionary Algorithm)
Este repositorio contiene la implementación de un algoritmo evolutivo sencillo (Simple Evolutionary Algorithm) desarrollado con fines didácticos y experimentales, en el contexto de estudios sobre algoritmos evolutivos y optimización. El proyecto incluye código fuente en Java, scripts para el análisis de resultados y datos de ejemplo para reproducir experimentos.

---

## Descripción del proyecto

Este proyecto adapta el algoritmo evolutivo simple de la librería [SimpleEvolutionaryAlgorithm](https://github.com/NEO-Research-Group/SimpleEvolutionaryAlgorithm), adapatado al Problema de la Mochila Multidimensional.

El código principal está escrito en Java y se complementa con:

- **Shell script** (`run_experiments.sh`) para automatizar la ejecución de los experimentos.
- **Python** para generar medias y gráficos de los resultados (`analyze.py`).
- Datos y resultados de ejemplo para facilitar la reproducibilidad.

---

## Requisitos

Las siguientes herramientas son necesarias para la ejecución completa del proyecto:

- **Java 11+**.
- **Python 3.x** y bibliotecas como `matplotlib`, `pandas`, `numpy`, para ejecutar `analyze.py`.
- **Bash** para ejecutar `run_experiments.sh`.

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/mayumar/Trabajo-Final-MH.git
cd Trabajo-Final-MH
```

### 2. Ejecutar el algoritmo

```bash
java -cp java -cp target/classes es.uma.informatica.misia.ae.simpleea.Main <poblacion> <evaluaciones_maximas> <prob_cruce> <prob_mutacion> <modo> <semilla>
```

---

## Experimentos y análisis

Para ejecutar la automatización de varios experimentos:

```bash
bash run_experiments.sh
```

Luego, para generar gráficas para el análisis de resultados, se puede usar:

```bash
python analyze.py
```

Este script debería cargar los datos generados y producir gráficos o estadísticas útiles.