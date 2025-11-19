# analyze.py
from pathlib import Path
import sys
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

infile = Path(sys.argv[1]) if len(sys.argv)>1 else Path("results/all_results_largo.csv")
df = pd.read_csv(infile)

df0 = df[df["mode"] == 0] # Numero maximo de evaluaciones
df1 = df[df["mode"] == 1]

tabla0_c = df0.groupby(["crossover"]).agg({
    "bestFitness": ["mean", "std"],
    "foundOptimal": "mean",
    "elapsedMillis": ["mean", "std"]
})

tabla0_m = df0.groupby(["mutation"]).agg({
    "bestFitness": ["mean", "std"],
    "foundOptimal": "mean",
    "elapsedMillis": ["mean", "std"]
})

print(tabla0_c)
print(tabla0_m)

# Guardar tabla a CSV y LaTeX
tabla0_c.to_csv("results/summary_0_c.csv")
with open("results/summary_0_c.tex", "w") as f:
    f.write(tabla0_c.to_latex(float_format="%.3f"))

tabla0_m.to_csv("results/summary_0_m.csv")
with open("results/summary_0_m.tex", "w") as f:
    f.write(tabla0_m.to_latex(float_format="%.3f"))

# fitness medio por probabilidad de cruce
plt.figure()
df0.groupby("crossover")["bestFitness"].mean().plot(marker="o")
plt.title("Fitness medio según crossover (modo 0)")
plt.ylabel("Best Fitness")
plt.savefig("results/plot_bestFitness_crossover_0.png")
plt.close()

# fitness medio por probabilidad de mutación
plt.figure()
df0.groupby("mutation")["bestFitness"].mean().plot(marker="o")
plt.title("Fitness medio según mutation (modo 0)")
plt.ylabel("Best Fitness")
plt.savefig("results/plot_bestFitness_mutation_0.png")
plt.close()

# Boxplots por configuración (crossover)
plt.figure()
sns.boxplot(data=df0, x="crossover", y="bestFitness")
plt.title("Distribución de Fitness por crossover (modo 0)")
plt.savefig("results/boxplot_bestFitness_crossover_0.png")
plt.close()

# Porcentaje de optimal alcanzado
plt.figure()
df_opt = df0.groupby(["crossover", "mutation"])["foundOptimal"].mean().unstack()
sns.heatmap(df_opt, annot=True)
plt.title("Porcentaje de runs que alcanzan el óptimo (modo 0)")
plt.savefig("results/plot_foundOptimal_0.png")
plt.close()




tabla1_c = df1.groupby(["crossover"]).agg({
    "evaluations": ["mean", "std"],
    "elapsedMillis": ["mean", "std"]
})

tabla1_m = df1.groupby(["mutation"]).agg({
    "evaluations": ["mean", "std"],
    "elapsedMillis": ["mean", "std"]
})

print(tabla1_c)
print(tabla1_m)

# Guardar tabla a CSV y LaTeX
tabla1_c.to_csv("results/summary_1_c.csv")
with open("results/summary_1_c.tex", "w") as f:
    f.write(tabla1_c.to_latex(float_format="%.3f"))

# Guardar tabla a CSV y LaTeX
tabla1_m.to_csv("results/summary_1_m.csv")
with open("results/summary_1_m.tex", "w") as f:
    f.write(tabla1_m.to_latex(float_format="%.3f"))

# # Porcentaje de éxito según cruce
# plt.figure()
# df1.groupby("crossover")["foundOptimal"].mean().plot(kind="bar")
# plt.title("Porcentaje de éxito según crossover (modo 1)")
# plt.ylabel("Proporción de runs óptimos")
# plt.xlabel("Probabilidad de crossover")
# plt.savefig("results/plot_foundOptimal_crossover_1.png")
# plt.close()

# # Porcentaje de éxito según mutacion
# plt.figure()
# df1.groupby("mutation")["foundOptimal"].mean().plot(kind="bar")
# plt.title("Porcentaje de éxito según mutation (modo 1)")
# plt.ylabel("Proporción de runs óptimos")
# plt.xlabel("Probabilidad de mutation")
# plt.savefig("results/plot_foundOptimal_mutation_1.png")
# plt.close()

plt.figure()
df1.groupby("crossover")["evaluations"].mean().plot(marker="o")
plt.title("Evaluaciones medias para lograr el óptimo (modo 1)")
plt.ylabel("Evaluaciones")
plt.xlabel("Probabilidad de crossover")
plt.savefig("results/plot_evals_crossover_1.png")
plt.close()

# Evaluaciones necesarias para encontrar el óptimo, solo para ejecuciones exitosas
plt.figure()
df1.groupby("mutation")["evaluations"].mean().plot(marker="o")
plt.title("Evaluaciones medias para lograr el óptimo (modo 1)")
plt.ylabel("Evaluaciones")
plt.xlabel("Probabilidad de mutation")
plt.savefig("results/plot_evals_mutation_1.png")
plt.close()

# Boxplot del número de evaluaciones
plt.figure()
sns.boxplot(data=df1, x="mutation", y="evaluations")
plt.title("Distribución de evaluaciones según mutation (solo éxitos, modo 1)")
plt.ylabel("Evaluaciones")
plt.xlabel("Probabilidad de mutación")
plt.savefig("results/boxplot_evaluations_mutation_1.png")
plt.close()

# Boxplot del número de evaluaciones
plt.figure()
sns.boxplot(data=df1, x="crossover", y="evaluations")
plt.title("Distribución de evaluaciones según crossover (solo éxitos, modo 1)")
plt.ylabel("Evaluaciones")
plt.xlabel("Probabilidad de mutación")
plt.savefig("results/boxplot_evaluations_crossover_1.png")
plt.close()

# Evaluaciones
plt.figure()
df_evals = df1.groupby(["crossover", "mutation"])["evaluations"].mean().unstack()
print(df_evals)
sns.heatmap(df_evals, fmt=".0f", annot=True)
plt.title("Número medio de evaluaciones (modo 1)")
plt.savefig("results/heatmap_evaluations_1.png")
plt.close()



# group_cols = ['mode', 'crossover', 'mutation']
# agg = df.groupby(group_cols).agg(
#     mean_fitness = ('bestFitness', 'mean'),
#     std_fitness = ('bestFitness', 'std'),
#     median_fitness = ('bestFitness', 'median'),
#     sucess_rate = ('foundOptimal', lambda x: 100.0 * x.sum()/len(x)),
#     mean_evals = ('evaluations', 'mean'),
#     std_evals = ('evaluations', 'std'),
#     n = ('seed', 'count')
# ).reset_index()

# # Guardar tabla a CSV y LaTeX
# agg.to_csv("results/summary_by_combo.csv", index=False)
# with open("results/summary_by_combo.tex", "w") as f:
#     f.write(agg.to_latex(index=False, float_format="%.3f"))

# # Ejemplo de boxplot para un mode concreto
# for mode in df['mode'].unique():
#     subset = df[df['mode']==mode]
#     # pivot por combo (crossover,bitflip) para hacer boxplots
#     subset['combo'] = subset['crossover'].astype(str) + "_c" + subset['mutation'].astype(str)
#     plt.figure(figsize=(12,6))
#     subset.boxplot(column='bestFitness', by='combo', rot=90)
#     plt.title(f'Best fitness boxplots - mode {mode}')
#     plt.suptitle('')
#     plt.ylabel('Best fitness')
#     plt.tight_layout()
#     plt.savefig(f"results/boxplot_bestFitness_{mode}.png")
#     plt.close()

# print("Summary and plots saved into results/")
