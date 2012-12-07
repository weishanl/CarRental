package test.timingaspect;

import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * A simple aspect that wraps test methods and reports the time to execute each.
 */
@Aspect
public class TestMethodTimingAspect {
    private Logger logger;

    public TestMethodTimingAspect() {
        try {
            logger = Logger.getLogger(TestMethodTimingAspect.class);
            String outfileName = System.getProperty("user.home") + "/UnitTestTimings.txt";
            outfileName = outfileName.replace('\\', '/');
            System.err.println("Writing timings to: " + outfileName); // NOPMD
            System.err.flush();
            WriterAppender wa = new WriterAppender(new PatternLayout("%m%n"), new FileOutputStream(
                    outfileName, true));
            logger.removeAllAppenders();
            logger.addAppender(wa);
        } catch (Throwable e) { // NOPMD by brett.schuchert
            e.printStackTrace(); // NOPMD by brett.schuchert
        }
    }

    @Pointcut("target(receiver) && execution(@org.junit.Test * *())")
    public void annotatedTest(final Object receiver) {
        // empty, this is a pointcut
    }

    @Pointcut("target(receiver) && execution(void junit.framework.TestCase+.test*())")
    public void testMethod(final Object receiver) {
        // emtpty, this is a pointcut
    }

    /**
     * Record the time before and after executing and log the results.
     */
    @Around("testMethod(receiver) || annotatedTest(receiver)")
    public void around(final ProceedingJoinPoint thisJoinPoint, final Object receiver)
            throws Throwable {
        final String className = receiver.getClass().getName();
        final String methodName = thisJoinPoint.getSignature().getName();
        final String qualifiedMethodName = String.format("%s.%s", className, methodName);

        final long start = System.currentTimeMillis();
        try {
            thisJoinPoint.proceed(new Object[] { receiver });
        } finally {
            final long end = System.currentTimeMillis();
            logger.info(String.format("%10d, %s", (end - start), qualifiedMethodName));
        }
    }
}
