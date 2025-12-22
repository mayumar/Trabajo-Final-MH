#!/usr/bin/env bash
# run_experiments.sh
# Uso: ./run_experiments.sh <outdir>
OUTDIR=${1:-results}
mkdir -p "$OUTDIR"

# Parametros fijos
POP=100
MAXEVALS=2000

# Barrido de probabilidades
CROSSOVER=($(LC_NUMERIC=C seq 0.1 0.1 1.0))
MUTATION=($(LC_NUMERIC=C seq 0.01 0.01 0.1))

# seeds: 30 semillas distintas
SEEDS=($(seq 1000 1029))

# modos: 0 = max evaluations, 1 = stop at optimal
MODES=(0 1)

# Cabecera CSV
echo "seed,mode,popsize,maxEvals,crossover,mutation,bestFitness,foundOptimal,evaluations,elapsedMillis" > "$OUTDIR/all_results.csv"

for mode in "${MODES[@]}"; do
    for c in "${CROSSOVER[@]}"; do
        for m in "${MUTATION[@]}"; do
            for s in "${SEEDS[@]}"; do
                echo "Running mode=$mode crossover=$c mutation=$m seed=$s"
                # Llamada: ajustar classpath / jar
                java -cp target/classes es.uma.informatica.misia.ae.simpleea.Main $POP $MAXEVALS $c $m $mode $s >> "$OUTDIR/all_results.csv"
            done
        done
    done
done