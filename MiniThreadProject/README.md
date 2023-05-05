# Analysis Automation
The Open University Learning Analytics Dataset (OULAD) contains real data collected from students in online courses at the Open University in the UK.
This dataset has become a benchmark for learning analytics researchers interested in developing tools to support online learning, such as early warning systems to identify students at risk of failing a course.
This program will help automate analysis of the OULAD files
### Link to database
https://analyse.kmi.open.ac.uk/open_dataset

## How to run sequential and concurrent solution
### How to run this program in CLI / Intellij terminal
#### Run program with Intellij UI for sequential solution
- If you are using intellij, go to menu ```Run > Edit configurations``` menu setting.
- A dialog box will appear as shown below.
- Now add arguments to the ```Program arguments``` input under build and run section.

  ![EditConfigurations.png](EditConfigurations.png)

#### Run program with Intellij UI for concurrent solution
- If you are using intellij, go to menu ```Run > Edit configurations``` menu setting.
- A dialog box will appear as shown below.
- Now add two arguments to the ```Program arguments``` input under build and run section.
  
  ![concurrentConfig.png](concurrentConfig.png)

#### Run program with gradle run task for sequential solution
- Open terminal/cli and navigate to root project folder "Assignment5"
- Run the gradle run task with arguments ```gradle runSequential --args='<directory-path>'``` and program will start running successfully.
>Note : directory path of studentVle.csv file should be provided in args
```shell
gradle runSequential --args= '<directoryPath>'
```
- Example: ```gradle runSequential --args='src/test/resources/mainclass'```

#### Run program with gradle run task for concurrent solution
- Open terminal/cli and navigate to root project folder "Assignment5"
- Run the gradle run task with arguments ```gradle runConcurrent --args='<directory-path> threshold'``` and program will start running successfully.
>Note : directory path of studentVle.csv file and threshold should be provided in args and threshold should be integer
```shell
gradle runConcurrent --args= '<directoryPath> threshold'
```
- Example: ```gradle runConcurrent --args='src/test/resources/mainclass 20'```

### Changes made in build.gradle
1. Added dependency opencsv for parsing csv
    ```
    dependencies {
    ....
        implementation 'com.opencsv:opencsv:5.6'
    }
    ```
2. Add application in plugins
   ```
   plugin {
   ....
    id 'application'
   }
   ```
3. Added two java executable tasks for sequential and concurrent
   ```
   task(runSequential, type: JavaExec) {
    main = 'sequentialSolution.SequentialMain'
    classpath = sourceSets.main.runtimeClasspath
   }

   task(runConcurrent, type: JavaExec) {
   main = 'concurrentsolution.ConcurrentMain'
   classpath = sourceSets.main.runtimeClasspath
   }
   ```

## Entry point of sequential solution
Entry point of sequential program is [SequentialMain.java](src/main/java/sequentialSolution/SequentialMain.java)

## Entry point of concurrent solution
Entry point of sequential program is [ConcurrentMain.java](src/main/java/concurrentsolution/ConcurrentMain.java)

## High-level description about classes and key methods for sequential solution
1. `SequentialMain.java`
    - This class is an entry point of sequential program.
        - This class contains `main()` method.
            - To run this program successfully, you need to provide directory path of studentVle.csv files in args of main() method.

2. `CommandLineParser.java`
    - This class is used to parse argument provided in main method and start the program.
        - If directoryPath provided in parameter of method is invalid, then throws an exception `InvalidInputException`

3. `CourseModuleId.java`
    - This class represents the key with information like code module, code representation and date
    - Constructor of Grammar takes three parameters, `String codeModule, String codePresentation, Integer date`
    - `String getCodeModule()`
        - This method returns code module, represented as String
    - `String getCodePresentation()`
        - This method returns code presentation, represented as String
    - `Integer getDate()`
        - This method returns specific day within the course, relative to the course start date, represented as Integer
    - There are three overrides methods also implemented
        - boolean equals(Object obj), int hashcode(), String toString()

4. `CourseModulePresentation.java`
    - This class represents a hashMap where key is `CourseModuleID` and value is `totalClicks` 
    - Constructor of CourseModulePresentation generates empty hashMap
    - `public void add(CourseModuleId id, Long clicks)`
        - This method add clicks to the key.
    - There are three override methods as well
        - boolean equals(Object obj), int hashcode, String toString()

5. `CSVReader.java`
    - This class is used to read StudentVle.csv file at given path

6. `CSVParser.java`
    - This class parses studentVle.csv file and calculates total number of clicks
    - Constructor of JSONFileParser takes one argument, `String directoryPath` and calls the process method
      - If StudentVle.csv is not present in directory, throws exception `IOException`
    - Total number of clicks is stored in a map CourseModulePresentation where key is `CourseModuleID` and value is `Total number of clicks`
    - The `process` method then calls `Writer` class to write the data to different files.

7. `Writer.java`
   - This class writes data to multiple csv file.
   - Constructor of Writer takes two argument, `CourseModulePresentation cmp` and `String basePath`
   - For each element in the CourseModulePresentation map it will create a `<CodeModule_CodePresentation>.csv` in the basePath.
      - It adds header `Date` and `TotalClicks` and writes sum of clicks for each date

## High-level description about classes and key methods for concurrent solution
1. `ConcurrentMain.java`
   - This class is an entry point for concurrent solution
     - This class contains `main()` method.
        - To run this program successfully, you need to provide directory path and threshold(integer) of studentVle.csv files in args of main() method.

2. `AbstractConsumer.java`
   - This class is an abstract class implementing Runnable interface to consume CSV file line by line
     - Constructor of AbstractConsumer takes three arguments, `BlockingQueue<String> sharedBuffer`, `String poison`, `Integer threshold`
     - `public void run()`
       - This class is overriding run method to read CSV file line by line
     - `protected abstract void consumeCSVFile(String line)`
       - This is an abstract method defined in this class
     - There are override methods as well
        - boolean equals(Object obj), int hashcode()

3. `CLIParser.java`
   - CLIParser is a class used for command line arguments in concurrent solution
   - Constructor of CLIParser takes one argument, `String[] args` array of String of args
     - If provided in args of constructor is invalid, then throws an exception `InvalidInputException`
   - `public String getStudentVleCsv()`
     - This public method is used to get file path of studentVle.csv, represented as String
   - `public String getCoursesVleCsv()`
     - This public method is used to get file path of courses.csv, represented as String
   - `public Integer getThreshold()`
     - This public method is used to get threshold provided in args, represented as Integer
   - `public String getDirectoryPath()`
     - This public method is used to get path of directory, represented as String
   - There are override methods as well
      - boolean equals(Object obj), int hashcode(), String toString()

4. `ConcurrentConstants.java`
   - ConcurrentConstants class is used to represent all constants used in concurrent solution

5. `CSVProducer.java`
   - This class implements Runnable interface and override run method to read csv file line by line with poison pill implementation.
   - Constructor of CSVProducer takes three arguments, `BlockingQueue<String> sharedBuffer`, `String poison`, `String csvFilePath`
   - There are override methods as well
     - void run(), boolean equals(Object obj), int hashcode(), String toString()

6. `StudentVleConsumer.java`
   - StudentVleConsumer class extends AbstractConsumer runnable class for consume csv line and process clicks per course total clicks and per course per date total clicks.
   - Constructor of StudentVleConsumer takes five arguments
     - `StudentVleConsumer(BlockingQueue<String> sharedBuffer`, `String poison`, `Integer threshold`, `ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdMap`, `ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate`
   - There are override methods as well
     - boolean equals(Object obj), int hashcode(), protected void consumeCSVFile(String line), String toString()

7. `ThresholdRunnable.java`
   - ThresholdRunnable is a class implements Runnable interface used to write activity-threshold.csv in given directory
   - Constructor of this class takes three arguments, `ConcurrentMap<String, ConcurrentMap<Integer, Integer>> thresholdTtlClick`, `String directoryPath`, `Integer threshold`
   - There are override methods as well
     - void run(), boolean equals(Object obj), int hashcode(), String toString()

8. `WriterRunnable.java`
   - WriterRunnable is a class implements Runnable interface used to write each course_mode_course_presentation.csv
   - Constructor of this class takes two arguments, `String directoryPath`, `ConcurrentMap<String, ConcurrentMap<Integer, Integer>> totalClickPerDate`
   - There are override methods as well
      - void run(), boolean equals(Object obj), int hashcode(), String toString()