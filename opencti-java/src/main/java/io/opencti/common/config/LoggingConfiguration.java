package io.opencti.common.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 重写自: opencti-graphql/src/config/conf.js (日志部分)
 * 日志配置类
 */
@Slf4j
@Component
public class LoggingConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    private final OpenCTIConfiguration configuration;

    public LoggingConfiguration(OpenCTIConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        configureLogging();
    }

    public void configureLogging() {
        AppProperties appProps = configuration.app();
        if (appProps == null || appProps.appLogs() == null) {
            return;
        }

        AppProperties.AppLogsProperties logsConfig = appProps.appLogs();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

        Level logLevel = Level.toLevel(logsConfig.logsLevel(), Level.INFO);
        rootLogger.setLevel(logLevel);

        if (logsConfig.logsConsole()) {
            configureConsoleAppender(loggerContext, rootLogger);
        }

        if (logsConfig.logsFiles()) {
            configureFileAppender(loggerContext, rootLogger, logsConfig);
        }

        log.info("Logging configured: level={}, console={}, files={}",
                logLevel, logsConfig.logsConsole(), logsConfig.logsFiles());
    }

    private void configureConsoleAppender(LoggerContext loggerContext, Logger rootLogger) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("CONSOLE");

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        rootLogger.addAppender(consoleAppender);
    }

    private void configureFileAppender(LoggerContext loggerContext, Logger rootLogger,
                                        AppProperties.AppLogsProperties logsConfig) {
        String logsDirectory = logsConfig.logsDirectory();
        File logDir = new File(logsDirectory);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("FILE");
        fileAppender.setFile(logsDirectory + "/opencti.log");

        SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new SizeAndTimeBasedRollingPolicy<>();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(logsDirectory + "/opencti.%d{yyyy-MM-dd}.%i.log");
        rollingPolicy.setMaxFileSize(FileSize.valueOf("100MB"));
        rollingPolicy.setMaxHistory(logsConfig.logsMaxFiles());
        rollingPolicy.setTotalSizeCap(FileSize.valueOf("1GB"));
        rollingPolicy.start();

        fileAppender.setRollingPolicy(rollingPolicy);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        fileAppender.setEncoder(encoder);
        fileAppender.start();

        rootLogger.addAppender(fileAppender);
    }

    public boolean isRedactedField(String fieldName) {
        AppProperties appProps = configuration.app();
        if (appProps == null || appProps.appLogs() == null) {
            return false;
        }
        List<String> redactedFields = appProps.appLogs().logsRedactedInputs();
        if (redactedFields == null) {
            return false;
        }
        return redactedFields.stream()
                .anyMatch(field -> field.equalsIgnoreCase(fieldName));
    }

    public String redactValue(String fieldName, String value) {
        if (isRedactedField(fieldName)) {
            return "***REDACTED***";
        }
        return value;
    }
}
