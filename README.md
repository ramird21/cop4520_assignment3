# cop4520_assignment3


## On Running the Program:
- Running Problem 1:
    - To compile: javac BirthdayPresentsParty.java
    - To run the program: java BirthdayPresentsParty

- Running Problem 2:
    - To compile: javac TemperatureReadingModule.java
    - To run the program: java TemperatureReadingModule

## Report:
- Problem 1:
    - Efficiency:
    - Correctness:
    - Progress Guarantee:

- Problem 2:
    - Efficiency: multiple threads are used for the readings with a concurrent execution. Arraylists are used to store the values.
                
    - Correctness: Sensor reading is being simulated, the correct hours asked were switched a bit for testing purposes (not to end up with too long waiting times)
                    Some race conditions might occur due to the nature of this problem
    - Progress Guarantee: Each sensor will be making readings. The time asked in the assignment has been slightly modified...