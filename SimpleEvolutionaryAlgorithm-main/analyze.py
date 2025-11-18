# analyze.py
from pathlib import Path
import sys
import pandas as pd
import matplotlib.pyplot as plt

infile = Path(sys.argv[1]) if len(sys.argv)>1 else Path("results/all_results.csv")
df = pd.read_csv(infile)

group_cols = ['mode', 'crossover', 'mutation']
agg = df.groupby(group_cols).agg(
    mean_fitness = ('bestFitness', 'mean'),
    std_fitness = ('bestFitness', 'std'),
    median_fitness = ('bestFitness', 'median'),
    sucess_rate = ('foundOptimal', lambda x: 100.0 * x.sum()/len(x)),
    mean_evals = ('evaluations', 'mean'),
    std_evals = ('evaluations', 'std'),
    n = ('seed', 'count')
).reset_index()

# Guardar tabla a CSV y LaTeX
agg.to_csv("results/summary_by_combo.csv", index=False)
with open("results/summary_by_combo.tex", "w") as f:
    f.write(agg.to_latex(index=False, float_format="%.3f"))

# Ejemplo de boxplot para un mode concreto
for mode in df['mode'].unique():
    subset = df[df['mode']==mode]
    # pivot por combo (crossover,bitflip) para hacer boxplots
    subset['combo'] = subset['crossover'].astype(str) + "_c" + subset['mutation'].astype(str)
    plt.figure(figsize=(12,6))
    subset.boxplot(column='bestFitness', by='combo', rot=90)
    plt.title(f'Best fitness boxplots - mode {mode}')
    plt.suptitle('')
    plt.ylabel('Best fitness')
    plt.tight_layout()
    plt.savefig(f"results/boxplot_bestFitness_{mode}.png")
    plt.close()

print("Summary and plots saved into results/")
