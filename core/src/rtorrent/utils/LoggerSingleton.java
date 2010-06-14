/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 14:50:23
 */
package rtorrent.utils;

import org.apache.log4j.*;

import java.io.*;

public class LoggerSingleton {
    private static Logger logger;
    private static File workDir;
    private static FileAppender fileAppender;
    private static PatternLayout patternLayout = new PatternLayout();
    private static String logFilePath;
    private static final Priority PRIORITY = Priority.WARN;

    static {
        patternLayout.setConversionPattern("%d %5p:%C:%M:%L - %m%n");
    }

    public static void initialize(File dir) {
        try {
            workDir = dir;
            //логируем инфо

            FileAppender appender = new FileAppender(patternLayout, dir.getAbsolutePath() + "/" + "rmanager.log");
            appender.setThreshold(Priority.INFO);
            Logger.getRootLogger().addAppender(appender);

            logFilePath = workDir + "/" + "user.log";

            appender = new FileAppender(patternLayout, logFilePath);
            appender.setThreshold(PRIORITY);
            fileAppender = appender;
            Logger.getRootLogger().addAppender(appender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger = Logger.getRootLogger();
    }

    public static void clearUserLog() {
        if (fileAppender != null) {
            logger.removeAppender(fileAppender);
            fileAppender.close();
            try {
                fileAppender = new FileAppender(patternLayout, logFilePath, false);
                fileAppender.setThreshold(PRIORITY);
                logger.addAppender(fileAppender);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readUserLogFile() {
        File file = new File(logFilePath);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text)
                        .append(System.getProperty(
                                "line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return contents.toString();
    }


    public static Logger getLogger() {
        return logger;
    }

    public static void debug() {
        ConsoleAppender appender = new ConsoleAppender(new PatternLayout());
        appender.setThreshold(Priority.DEBUG);
        LoggerSingleton.getLogger().addAppender(appender);        
    }
}
