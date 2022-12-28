package po.evolution;

import gui.GuiApp;
import gui.SimulationController;
import gui.SimulationVisualisation;

public class SimulationEngine implements Runnable {
    private Thread t;
    private boolean suspended;
    private AbstractWorldMap map;
    private boolean exportStats = false;
    private String statsFileName = "";

    private SimulationController app;

    public SimulationEngine(AbstractWorldMap map, SimulationController app) {
        this.map = map;
        this.app = app;
        t = new Thread(this);
        suspended = false;
        t.start();
    }

    public SimulationEngine(AbstractWorldMap map, String statsFileName) {
        this.map = map;
        this.exportStats = true;
        this.statsFileName = statsFileName;
        t = new Thread(this);
        suspended = false;
        map.stats.prepareAndCreateCSV(statsFileName);
        t.start();
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

        while (true) {
            if (suspended) waitForResume();
            map.clearDead();

            for (Animal a : map.animals) {

                a.move();
            }

            map.feast();

            map.spawnPlants(map.params.plantsPerDay);

            if (exportStats) {
                map.stats.writeToCSV(statsFileName);
            }

            int plants = 0;



            app.refreshMap();
        }

    }
}
