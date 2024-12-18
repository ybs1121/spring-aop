package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(BeanTest.BeanAspect.class)
public class BeanTest {
    @Autowired
    OrderService orderService;


    @Test
    void success() {
        log.info("orderService Proxy = {}", orderService.getClass());
        orderService.orderItem("helloA");
    }

    @Slf4j
    @Aspect
    static class BeanAspect {
        @Around("bean(orderService) || bean(*Repository))")
        public Object methodAop(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] = {}", joinPoint.getSignature());
            Object proceed = joinPoint.proceed();

            return proceed;
        }
    }
}
