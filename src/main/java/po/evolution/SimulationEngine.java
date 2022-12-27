package po.evolution;

public class SimulationEngine implements Runnable {
    private Thread t;
    private boolean suspended;
    private AbstractWorldMap map;
    private boolean exportStats = false;
    private String statsFileName = "";

    public SimulationEngine(AbstractWorldMap map) {
        this.map = map;
        t = new Thread(this);
        suspended = false;
    }

    public SimulationEngine(AbstractWorldMap map, String statsFileName) {
        this.map = map;
        this.exportStats = true;
        this.statsFileName = statsFileName;
        t = new Thread(this);
        suspended = false;
        map.stats.prepareAndCreateCSV(statsFileName);
    }

    public Thread getThread() {
        return t;
    }

    private void waitForResume() {
        synchronized(this) {
            try {
                while (suspended) wait();
            } catch (InterruptedException e) {
                System.out.println("Execution thread was interrupted " + e.getMessage());
                System.exit(1);
            }
        }
    }

    public void pauseExecutution() {
        suspended = true;
    }

    public void resumeExecution() {
        t.notify();
        suspended = false;
    }

    public void run() {
        map.clearDead();

        for (Animal a : map.animals) {
            if (suspended) waitForResume();

            a.move();
        }

        map.feast();

        map.spawnPlants(map.params.plantsPerDay);

        if (exportStats) {
            map.stats.writeToCSV(statsFileName);
        }

    }
}
