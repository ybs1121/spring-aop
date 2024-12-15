package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {
    } // 포인트컷 시그니쳐

    // 클래스 이름 패턴이 *Service 인 경우
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {
    } // 포인트컷 시그니쳐

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[doLog] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {} ", joinPoint.getSignature());
            Object proceed = joinPoint.proceed();
            log.info("[[트랜잭션 커밋] {} ", joinPoint.getSignature());
            return proceed;
        } catch (Exception e) {
            log.info("[[트랜잭션 롤백] {} ", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소르 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
