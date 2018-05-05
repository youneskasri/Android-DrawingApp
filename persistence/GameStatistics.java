package gl2.kasri.younes.paintapplication.persistence;

public class GameStatistics {

    private int nbCorrectAnswers; /* lors de operationReussie() */
    private int nbWrongAnswers; /* calculés séparement, puis ajoutés ds operatoinREUSSIE() */
    private int minTimeInSeconds; /* dans finDuJeu() */
    private int avgTimeInSeconds; /* dans finDuJeu()*/
    private Long[] tempsPourChaqueNombre = new Long[10]; /* ds operationReussie() */

    public GameStatistics() {
    }

    /* To Retrive Data From DB and Tests */
    public GameStatistics(String nombre_operation_reuss, String nombre_operation_echou, String minimum_temps_operation_sec, String moyen_temps_operation_sec) {
        this.nbCorrectAnswers = Integer.parseInt(nombre_operation_reuss);
        this.nbWrongAnswers = Integer.parseInt(nombre_operation_echou);
        this.minTimeInSeconds = Integer.parseInt(minimum_temps_operation_sec);
        this.avgTimeInSeconds = Integer.parseInt(moyen_temps_operation_sec);
    }


    /* calculer moy et avg temps */
    public void moyenneEtMinimumTemps(){
        Long sum = 0L;
        Long min = tempsPourChaqueNombre[0] == null ? 999 : tempsPourChaqueNombre[0];
        for (int i = 0; i < tempsPourChaqueNombre.length; i++){
            if (tempsPourChaqueNombre[i]==null || tempsPourChaqueNombre[i]==0) break;
            sum += tempsPourChaqueNombre[i];
            if (tempsPourChaqueNombre[i] < min)  min = tempsPourChaqueNombre[i];
        }

        avgTimeInSeconds = (int) (sum / tempsPourChaqueNombre.length / 1000);
        minTimeInSeconds = (int) (min / 1000);
    }

    public void clearResults() {
        nbCorrectAnswers = nbWrongAnswers = 0;
        tempsPourChaqueNombre = new Long[10];
    }


    public int getNbCorrectAnswers() {  return nbCorrectAnswers;  }

    public void setNbCorrectAnswers(int nbCorrectAnswers) {
        this.nbCorrectAnswers = nbCorrectAnswers;
    }

    public int getNbWrongAnswers() {
        return nbWrongAnswers;
    }

    public void setNbWrongAnswers(int nbWrongAnswers) {
        this.nbWrongAnswers = nbWrongAnswers;
    }

    public int getMinTimeInSeconds() {
        return minTimeInSeconds;
    }

    public int getAvgTimeInSeconds() {
        return avgTimeInSeconds;
    }

    public Long[] getTempsPourChaqueNombre() {
        return tempsPourChaqueNombre;
    }

}