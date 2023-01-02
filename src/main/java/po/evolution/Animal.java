package po.evolution;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal {
    private Vector2d position;
    private MapOrientation orientation;
    private int energy;
    private int n;
    private int currGene;
    private int[] genotype;
    private boolean alive = true;
    private int children = 0;
    private int plantsEaten = 0;
    private int firstDay;
    private int lastDay;
    private SimulationParameters params;
    private AbstractWorldMap map;

    public Animal(SimulationParameters params, Vector2d position, int firstDay, AbstractWorldMap map) {
        this.params = params;
        this.energy = params.startingEnergy;
        this.n = params.genotypeLength;
        this.position = position;
        this.firstDay = firstDay;
        lastDay = firstDay;
        this.map = map;
        Random r = new Random();
        currGene = r.nextInt(n);
        orientation = MapOrientation.getMapOrientation(r.nextInt(8));
        genotype = r.ints(n, 0, 8)
            .boxed()
            .mapToInt(Integer::intValue)
            .toArray();
    }

    private Animal(int energy, SimulationParameters params, Vector2d position, int firstDay, int[] genotype, AbstractWorldMap map) {
        this.params = params;
        this.energy = energy;
        this.n = params.genotypeLength;
        this.position = position;
        this.genotype = genotype;
        this.firstDay = firstDay;
        lastDay = firstDay;
        this.map = map;
        Random r = new Random();
        currGene = r.nextInt(n);
        orientation = MapOrientation.getMapOrientation(r.nextInt(8));
    }

    public Animal procreate(Animal partner) {

        if (this.energy < params.energyNeeded || partner.energy < params.energyNeeded) return null;

        int firstEnergy = (int) (params.procreationEnergyShare * energy);
        int secondEnergy = (int) (params.procreationEnergyShare * partner.energy);
        boolean takeFromLeft = Math.random() > 0.5;
        Animal stronger = energy > partner.energy ? this : partner;
        Animal weaker = energy <= partner.energy ? this : partner;
        double ratio = ((double) stronger.energy / (double) (stronger.energy + weaker.energy));
        int genesFromStronger = (int) (n * ratio);

        int[] childGenotype = new int[n];

        if (takeFromLeft) {
            int i;
            for (i = 0; i < genesFromStronger; ++i) {
                childGenotype[i] = stronger.genotype[i];
            }

            for (int j = n - 1; j >= genesFromStronger; --j) {
                childGenotype[i++] = weaker.genotype[j];
            }

        } else {
            int i;
            for (i = 0; i < genesFromStronger; ++i) {
                childGenotype[i] = stronger.genotype[n - 1 - i];
            }

            for (int j = 0; j < n - genesFromStronger; ++j) {
                childGenotype[i++] = weaker.genotype[j];
            }
        }

        Random r = new Random();
        int genesMutated = r.nextInt(params.maxMutations - params.minMutations + 1) + params.minMutations;
        int[] genesToMutate = r.ints(genesMutated, 0, n)
            .distinct()
            .sorted()
            .boxed()
            .mapToInt(Integer::intValue)
            .toArray();


        switch (params.mutationVariant) {
            case FULL_RANDOMNESS:
                for (int i : genesToMutate) {
                    childGenotype[i] = r.nextInt(8);
                }
                break;
            case SLIGHT_CORRECTION:
                for (int i : genesToMutate) {
                    int[] diff = {-1, 1};
                    int which = r.nextInt(2);
                    int newGene = (childGenotype[i] + diff[which]) % 8;
                    if (newGene == -1) newGene = 7;
                    childGenotype[i] = newGene;
                }
                break;
        }

        energy -= firstEnergy;
        partner.energy -= secondEnergy;
        ++children;
        ++partner.children;

        ++map.animalsNum;

        return new Animal(firstEnergy + secondEnergy, params, position, lastDay, childGenotype, map);
    }

    public void move() {
        orientation = orientation.rotate(genotype[currGene]);
        Vector2d oldPosition = this.getPosition();
        Vector2d wantedPosition = oldPosition.add(orientation.toUnitVector());

        --energy;
        if (params.worldVariant == WorldVariant.INFERNAL_PORTAL && map.outOfBounds(wantedPosition)) {
            energy = (int) (1.0 - params.procreationEnergyShare) * energy;
        }
        position = map.nextPosition(wantedPosition);

        // inform observer
        map.positionChanged(this, oldPosition, position);

        map.stats.onMove();

        switch (params.geneProgressionVariant) {
            case FULL_PREDESTINATION:
                currGene = (currGene + 1) % n;
                break;
            case SOME_MADNESS:
                if (Math.random() < 0.8 || n == 1) {
                    currGene = (currGene + 1) % n;
                } else {
                    Random r = new Random();
                    int nextGene;
                    do {
                        nextGene = r.nextInt(n);
                    } while (nextGene == currGene);
                    currGene = nextGene;
                    break;
                }

        }

        ++lastDay;

    }

    public void eat() {
        energy += params.energyFromPlant;
    }

    // getters
    public List<Integer> getGenotype() {
        return Arrays.stream(genotype).boxed().toList();
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getCurrentGene() {
        return currGene;
    }

    public int getEnergy() {
        return energy;
    }

    public MapOrientation geOrientation() {
        return orientation;
    }

    public int getChildrenNumber() {
        return children;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getLifetime() {
        return lastDay - firstDay;
    }

    public int getDeathDay() {
        if (alive) return 0;
        return lastDay;
    }
    public Image getImage() throws FileNotFoundException {
        return new Image(new FileInputStream("src/main/resources/animal.png"));
    }

}
