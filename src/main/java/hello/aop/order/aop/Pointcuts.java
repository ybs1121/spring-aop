package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {
    } // 포인트컷 시그니쳐

    // 클래스 이름 패턴이 *Service 인 경우
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    } // 포인

    @Pointcut("allOrder() && allService()")
    public void allOrdService() {
    }
}
