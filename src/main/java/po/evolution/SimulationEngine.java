package po.evolution;

import gui.SimulationController;

public class SimulationEngine implements Runnable {
    private Thread t;
    private boolean suspended;
    private AbstractWorldMap map;
    private boolean running = true;
    private boolean exportStats = false;
    private String statsFileName = "";

    private SimulationController app;

    private  int refresh;

    public SimulationEngine(AbstractWorldMap map, SimulationController app) {
        this.map = map;
        this.app = app;
        t = new Thread(this);
        suspended = false;
        t.start();
    }

    public SimulationEngine(AbstractWorldMap map, SimulationController app , String statsFileName) {
        this.map = map;
        this.app = app;
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

    public synchronized void resumeExecution() {
        notify();
        suspended = false;
    }

    public void terminate() {
        running = false;
    }

    public void run() {

        while (running) {
            if (map.animals.size() == 0) {
                map.calculateFree();
                app.refreshMap();
                return;
            }
            if (suspended) waitForResume();
            map.clearDead();
            app.refreshMap();

            for (Animal a : map.animals) {

                a.move();
                try {
                    int tmp = (500 / map.animals.size());
                    refresh = Math.max(tmp, 10);
                    Thread.sleep(refresh);
                    //Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                map.calculateFree();
                app.refreshMap();
            }

            for (Vector2d pos : map.occupiedPositions) {
                map.feast(pos);
            }
            app.refreshMap();

            for (Vector2d pos : map.occupiedPositions) {
                map.procreate(pos);
            }
            app.refreshMap();

            map.spawnPlants(map.params.plantsPerDay);
            map.calculateFree();
            app.refreshMap();

            if (exportStats) {
                map.stats.writeToCSV(statsFileName);
            }

        }

    }
}
