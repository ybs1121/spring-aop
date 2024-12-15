package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6 {


//    @Around("hello.aop.order.aop.Pointcuts.allOrdService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            //@Before
//            log.info("[트랜잭션 시작] {} ", joinPoint.getSignature());
//            Object proceed = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[[트랜잭션 커밋] {} ", joinPoint.getSignature());
//            return proceed;
//        } catch (Exception e) {
//            //@AfterThrowing
//            log.info("[[트랜잭션 롤백] {} ", joinPoint.getSignature());
//            throw e;
//        } finally {
//            //@After
//            log.info("[리소르 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    @Before("hello.aop.order.aop.Pointcuts.allOrdService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("before {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrdService()", returning = "result")
    public void doAfter(JoinPoint joinPoint, Object result) {
        log.info("[return] {}", result);
        log.info("AfterReturning {}", joinPoint.getSignature());
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.allOrdService()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[throw] {} ", ex);
    }

    @After(value = "hello.aop.order.aop.Pointcuts.allOrdService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("after {}", joinPoint.getSignature());
    }
}
