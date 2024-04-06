// Ramir Dalencour
// COP 4520

import java.util.*;
import java.util.concurrent.TimeUnit;

class SensorHelper extends Thread
{
    private final int sensorId;
    private final List<Double> temperatureReadings;
    private volatile boolean running;

    public SensorHelper(int sensorId)
    {
        this.sensorId = sensorId;
        this.temperatureReadings = new ArrayList<>();
        this.running = true;
    }

    public void stopSensor()
    {
        this.running = false;
    }

    public List<Double> getTemperatureReadings()
    {
        return temperatureReadings;
    }

    public void run()
    {
        Random rnd = new Random();

        while (running)
        {
            double temperature = rnd.nextDouble() * (70 - (-100)) + (-100);
            temperatureReadings.add(temperature);

            try {
                TimeUnit.SECONDS.sleep(1); // Giving it time interval...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TemperatureReadingModule
{
    private static final int NUM_SENSORS = 8;
    private static final int NUM_READINGS_PER_HOUR = 30;

    // Testing with max iterations of 5...
    private static final int MAX_ITERATIONS = 5;

    public static void main(String [] args)
    {
        List<SensorHelper> sensors = new ArrayList<>();

        for (int i = 0; i < NUM_SENSORS; i++)
        {
            SensorHelper sensor = new SensorHelper(i);
            sensor.start();
            sensors.add(sensor);
        }
        try {
            int iterations = 0;
            while (iterations < MAX_ITERATIONS)
            {
                Thread.sleep(TimeUnit.SECONDS.toMillis(NUM_READINGS_PER_HOUR)); // Wait for NUM_READINGS_PER_HOUR seconds

                // Collect all temperature readings
                List<Double> allTemps = new ArrayList<>();
                for (SensorHelper sensor : sensors)
                    allTemps.addAll(sensor.getTemperatureReadings());

                // Let's get this ready to calculate the top 5 highest and lowest temperatures
                Collections.sort(allTemps);
                List<Double> highTmp = allTemps.subList(allTemps.size() - 5, allTemps.size());
                List<Double> lowTmp = allTemps.subList(0, 5);

                System.out.println("Top 5 Highest temps: " + highTmp);
                System.out.println("Top 5 lowest temps: " + lowTmp);
                System.out.println("----------------------------------------------------------------------------------------------------------------");

                iterations++;
            }
        } catch (InterruptedException e) {

        } finally {
            // Let's now stop the sensor threads...
            for (SensorHelper sensor : sensors)
                sensor.stopSensor();
        }
    }
}