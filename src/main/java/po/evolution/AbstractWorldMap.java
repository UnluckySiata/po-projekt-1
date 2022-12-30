package po.evolution;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class AbstractWorldMap {
    protected Vector2d upperBound, lowerBound = new Vector2d(-1, -1);
    protected final int height, width, n;
    protected SimulationParameters params;
    protected int[] animalsDied;
    protected boolean[] plantPresent;
    protected int plants = 0;
    protected HashMap<Vector2d, LinkedList<Animal>> fields = new HashMap<>();
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected LinkedHashSet<Vector2d> occupiedPositions = new LinkedHashSet<>(); // Przez zwierzęta
    protected int animalsNum, freeFields; // Wolne od zwierząt i roślin
    protected HashMap<int[], Integer> genotypes = new HashMap<>();
    protected int currGenotypeMax = 0;
    protected int[] domimantGenotype = new int[0];
    public Statistics stats;

    public AbstractWorldMap(SimulationParameters params) {
        height = params.mapHeight;
        width = params.mapWidth;
        upperBound = new Vector2d(width + 1, height + 1);
        n = width * height;
        freeFields = n;
        this.params = params;
        animalsNum = params.initialAnimalNum;
        plantPresent = new boolean[n];
        animalsDied = new int[n];
        stats = new Statistics(animalsNum, n, (double) params.startingEnergy);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                fields.put(new Vector2d(x, y), new LinkedList<Animal>());
            }
        }

        spawnAnimals();
        spawnPlants(params.initialPlantNum);

        currGenotypeMax = Collections.max(genotypes.values());

        domimantGenotype = calculateDominantGenotype();
        stats.acquireDominantGenotype(domimantGenotype);
    }

    public abstract Vector2d nextPosition(Vector2d wantedPosition);

    public void clearDead() {
        List<Animal> dead = animals.stream()
            .filter(a -> a.getEnergy() == 0)
            .toList();

        for (Animal a: dead) {
            onDeath(a);
        }

        /*
        animals.removeIf(a -> a.getEnergy() == 0);

        for (LinkedList<Animal> field : fields.values()) {
            field.removeIf(a -> a.getEnergy() == 0);
        }
        */
    }

    public void onDeath(Animal a) {
        Vector2d pos = a.getPosition();
        int i = width * pos.y + pos.x;
        ++animalsDied[i];

        LinkedList<Animal> animalsOnField = fields.get(pos);

        if (animalsOnField.size() == 1) {
            --freeFields;
            occupiedPositions.remove(pos);
        }
        animals.remove(a);
        animalsOnField.remove(a);
        --animalsNum;

        stats.onAnimalDeath(a.getLifetime());
    }

    int[] calculateDominantGenotype() {
        for (Map.Entry<int[], Integer> entry : genotypes.entrySet()) {
            if (entry.getValue() == currGenotypeMax) return entry.getKey();
        }

        return new int[0]; //unreachable

    }

    int[] generateFavoredPermutation() {
        int[] result;

        switch (params.plantGrowthVariant) {
            case TOXIC_CORPSES:
                int quantity = (int) (0.2 * n);
                List<Integer> perm = IntStream.range(0, n)
                    .boxed()
                    .sorted((i, j) -> animalsDied[i] > animalsDied[j] ? 1
                        : animalsDied[i] == animalsDied[j] ? 0 : -1)
                    .toList();

                result = perm.stream().limit(quantity).mapToInt(Integer::intValue).toArray();
                break;

            case EQUATOR:
                int lineHeight = (int) (0.2 * (double) n / width);
                int middle = height / 2 + 1;
                int offset = lineHeight / 2;

                result = new int[lineHeight * width];
                List<Integer> rowIndexes = IntStream.range(middle - offset, middle - offset + lineHeight).boxed().toList();
                List<Integer> columns = IntStream.range(0, width).boxed().toList();

                int i = 0;
                for (int r: rowIndexes) {
                    for (int c: columns) result[i++] = width * r + c;
                }
                break;

            default: result = new int[0];
        }

        return result;

    }

    void spawnPlants(int quantity) {
        if (plants == n) return;
        boolean inPrefered, check = true;
        int[] perm = generateFavoredPermutation();
        int spawned = 0;

        for (int p = 0; p < quantity; ++p) {
            inPrefered = Math.random() < 0.8;

            if (inPrefered && check) {

                int[] free = Arrays.stream(perm)
                    .filter(x -> !plantPresent[x])
                    .toArray();

                if (free.length > 0) {
                    int i = new Random().nextInt(free.length);
                    plantPresent[free[i]] = true;
                    ++spawned;

                    Vector2d pos = new Vector2d(free[i] % width, free[i] / width);
                    if (!occupiedPositions.contains(pos)) --freeFields;
                } else check = false;

            }
            if (!inPrefered || !check) {
                int[] free = IntStream.range(0, n)
                    .filter(x -> !plantPresent[x])
                    .toArray();

                if (free.length == 0) {
                    plants = plants + spawned > n ? n : plants + spawned;
                    stats.onPlantSpawn(plants);
                    //stats.updateFree(freeFields);
                    return;
                };
                int i = new Random().nextInt(free.length);
                plantPresent[free[i]] = true;
                ++spawned;

                Vector2d pos = new Vector2d(free[i] % width, free[i] / width);
                if (!occupiedPositions.contains(pos)) --freeFields;
            }

        }

        plants = plants + spawned > n ? n : plants + spawned;

        stats.onPlantSpawn(plants);
        //stats.updateFree(freeFields);
    }

    private void spawnAnimals() {
        int x, y;
        Random r = new Random();
        for (int i = 0; i < params.initialAnimalNum; ++i) {
            x = r.nextInt(width);
            y = r.nextInt(height);
            Vector2d pos = new Vector2d(x, y);

            Animal a = new Animal(params, pos, 0, this);
            LinkedList<Animal> animalsOnField = fields.get(pos);
            if (animalsOnField.size() == 0) {
                occupiedPositions.add(pos);
                --freeFields;
            }
            //stats.updateFree(freeFields);

            animalsOnField.add(a);
            animals.push(a);

            int[] genotype = a.getGenotype();
            if (genotypes.containsKey(genotype)) {
                Integer val = genotypes.get(genotype) + 1;
                genotypes.remove(genotype);
                genotypes.put(genotype, val);

                if (val > currGenotypeMax) {
                    domimantGenotype = genotype;
                    currGenotypeMax = val;
                }
            } else {
                genotypes.put(genotype, 1);
            }
        }
    }

    void procreate(Vector2d pos) {
        LinkedList<Animal> animalsOnField = fields.get(pos);
        if (animalsOnField.size() < 2) return;

        // descending
        animalsOnField.sort((a1, a2) -> a1.getEnergy() > a2.getEnergy() ? -1 : a1.getEnergy() == a2.getEnergy() ? 0 : 1);
        Animal first = animalsOnField.get(0);
        Animal second = animalsOnField.get(1);

        Animal child = first.procreate(second);

        if (child != null) {
            animalsOnField.push(child);
            animals.push(child);

            ++animalsNum;
            stats.onProcreation();

            int[] genotype = child.getGenotype();
            if (genotypes.containsKey(genotype)) {
                Integer val = genotypes.get(genotype) + 1;
                genotypes.remove(genotype);
                genotypes.put(genotype, val);

                if (val > currGenotypeMax) {
                    domimantGenotype = genotype;
                    currGenotypeMax = val;
                    stats.acquireDominantGenotype(domimantGenotype);
                }
            } else {
                genotypes.put(genotype, 1);
            }
        }
    }

    void feast(Vector2d pos) {

        LinkedList<Animal> animalsOnField = fields.get(pos);

        if (plantPresent[pos.x + pos.y * width] && animalsOnField.size() > 0) {
            plantPresent[pos.x + pos.y * width] = false;
            --plants;
            stats.onPlantEaten(params.energyFromPlant);

            List<Animal> considered = animalsOnField.stream()
                .filter(a -> a.getEnergy() == Collections.max(animalsOnField, (a1, a2) -> a1.getEnergy() > a2.getEnergy() ? -1 :
                                                                a1.getEnergy() == a2.getEnergy() ? 0 : 1).getEnergy())
                .toList();

            if (considered.size() == 1) {
                considered.get(0).eat();
                return;
            }

            considered = considered.stream()
                .filter(a -> a.getLifetime() == Collections.max(animalsOnField, (a1, a2) -> a1.getLifetime() > a2.getLifetime() ? -1 :
                                                                a1.getLifetime() == a2.getLifetime() ? 0 : 1).getLifetime())
                .toList();

            if (considered.size() == 1) {
                considered.get(0).eat();
                return;
            }

            considered = considered.stream()
                .filter(a -> a.getChildrenNumber() == Collections.max(animalsOnField, (a1, a2) -> a1.getChildrenNumber() > a2.getChildrenNumber() ? -1 :
                                                                a1.getChildrenNumber() == a2.getChildrenNumber() ? 0 : 1).getChildrenNumber())
                .toList();

            if (considered.size() == 1) {
                considered.get(0).eat();
            } else {
                considered = animalsOnField;
                int i = new Random().nextInt(considered.size());
                considered.get(i).eat();
            }
        }

    }

    void calculateFree() {
        int counter = 0;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (!isOccupied(x, y) && !plantPresent[y * width + x]) ++counter;
            }
        }
        stats.updateFree(counter);
    }

    void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition) {
        LinkedList<Animal> oldField = fields.get(oldPosition);
        LinkedList<Animal> newField = fields.get(newPosition);

        if (oldField.size() == 1) {
            if (!plantPresent[oldPosition.y * width + oldPosition.x]) ++freeFields;

            occupiedPositions.remove(oldPosition);
        }
        if (newField.size() == 0) {
            if (!plantPresent[newPosition.y * width + newPosition.x]) --freeFields;

            occupiedPositions.add(newPosition);
        }

        oldField.remove(a);
        newField.push(a);

        //stats.updateFree(freeFields);

    }

    boolean outOfBounds(Vector2d pos) {
        return pos.precedes(lowerBound) && pos.follows(upperBound);
    }

    public boolean[] getPlants() {
        return plantPresent;
    }

    public boolean isOccupied(int x, int y) {
        return occupiedPositions.contains(new Vector2d(x, y));
    }

    public LinkedList<Animal> getAnimals() {
        return animals;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
