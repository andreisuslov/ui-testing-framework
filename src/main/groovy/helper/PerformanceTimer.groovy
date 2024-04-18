package helper

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import static com.codeborne.selenide.Selenide.executeJavaScript
import static java.lang.System.*

// Class to handle performance timing and logging
class PerformanceTimer {

    /**
     * Calculates the execution time of a given closure in milliseconds.
     * @param code The closure to execute.
     * @return The execution time in milliseconds.
     */
    static long getExecutionTime(Closure code) {
        def startTime = currentTimeMillis()
        code.call()
        def endTime = currentTimeMillis()
        return endTime - startTime
    }

    /**
     * Logs a message with the operation time in seconds to the console and a log file.
     * @param customMessage The message describing the operation.
     * @param time The operation time in seconds.
     */
    static void logTimeInSeconds(String customMessage, String time) {
        String message = "PERFORMANCE: $customMessage: Operation time: $time seconds"
        println(message)
        logToTxt(message)
    }

    /**
     * Logs a message with the JavaScript memory usage in megabytes to the console and a log file.
     * @param customMessage The message describing the operation.
     * @param memoryUsage The memory usage in megabytes.
     */
    static void logJsMemoryInMb(String customMessage, String memoryUsage) {
        String message = "PERFORMANCE: $customMessage: JS Memory usage: $memoryUsage mb"
        println(message)
        logToTxt(message)
    }

    /**
     * Logs a message with the memory usage in megabytes to the console and a log file.
     * @param customMessage The message describing the operation.
     * @param memoryUsage The memory usage in megabytes.
     */
    static void logMemoryInMb(String customMessage, String memoryUsage) {
        String message = "PERFORMANCE: $customMessage: Memory usage: $memoryUsage mb"
        println(message)
        logToTxt(message)
    }

    // Logs a message to the "performance.log" file.
    private static void logToTxt(String message) {
        new File('performance.log') << "$message\n"
    }

    /**
     * Measures the execution time of a closure, logs it with additional information, and prints it to a CSV file.
     * @param message The message describing the operation.
     * @param user The user performing the operation.
     * @param code The closure to execute.
     */
    static void logExecutionTime(String message, Closure code = {}) {
        def timeMillis = getExecutionTime(code)
        def executionTime = String.format("%.3f", timeMillis / 1000)
        def jsMemoryUsage = getJsMemoryUsage()
        def currentTime = currentTimeMillis()
        logTimeInSeconds(message, executionTime)
        logJsMemoryInMb(message, jsMemoryUsage as String)
        printToCsv([currentTime, message, executionTime, jsMemoryUsage])
    }

    // Retrieves the JavaScript memory usage in megabytes.
    private static long getJsMemoryUsage() {
        long value = (long) executeJavaScript("return window.performance.memory.usedJSHeapSize")
        return value / (1024 * 1024)
    }

    // Prints performance data to a CSV file.
    static printToCsv(List data) {
        def fileName = 'performance.csv'
        def FILE_HEADER = ["Timestamp", "Operation", "Duration (seconds)", "Js Memory (Mb)"]

        def perfFile = new File(fileName)
        if (!perfFile.exists()) {
            perfFile.createNewFile()
            perfFile.withWriterAppend { writer ->
                new CSVPrinter(writer, CSVFormat.DEFAULT).printRecord(FILE_HEADER)
            }
        }

        // Convert data elements to strings before printing
        perfFile.withWriterAppend { writer ->
            new CSVPrinter(writer, CSVFormat.DEFAULT).printRecord(data.collect { it.toString() })
        }
    }
}