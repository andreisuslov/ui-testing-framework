package helper

/**
 * Class for measuring amount of time in milliseconds for executing the code inside Closure given as a parameter
 */
class PerformanceTimer {


//  gets amount of time in milliseconds for executing the code inside the Closure given as a parameter
        static public long getExecutionTime(Closure code) {
            def startTimeMillis = System.currentTimeMillis()
            code.call()
            def endTimeMillis = System.currentTimeMillis()
            return endTimeMillis - startTimeMillis
        }

//    Prints argument to web-report and to standard output. Expects to get time in milliseconds
        static public void logTimeInSeconds(String customMessage, long timeMillis) {
           String message = "PERFORMANCE: "+ customMessage + ": Operation time: " + String.format("%.3f", timeMillis / 1000) + " seconds"
            println(message)
              def perf_file = new File('performance.log')
              if (!perf_file.exists())
                  perf_file.createNewFile()
            perf_file << message +'\n'
        }

//  gets amount of time in milliseconds for executing the code inside the Closure given as a parameter
//  and then logs it to the test report and standard output
        static public void logExecutionTime(Closure code) {
            logTimeInSeconds("", getExecutionTime(code) )
        }

        static public void logExecutionTime(String message, Closure code) {
            logTimeInSeconds(message, getExecutionTime(code) )
        }

    }

