package hello.aop.exam;

import hello.aop.exam.aop.RetryAspect;
import hello.aop.exam.aop.TraceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamConfig {

    @Bean
    public TraceAspect traceAspect() {
        return new TraceAspect();
    }

    @Bean
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }
}
