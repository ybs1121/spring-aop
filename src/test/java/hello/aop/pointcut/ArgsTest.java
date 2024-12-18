package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ArgsTest {


    Method helloMethod;

    @BeforeEach
    public void before() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    // args 는 동적으로 판단한다 실제 들어 왔을 때 확인
    @Test
    void args() {
        //hello(String)
        Assertions.assertThat(pointcut("args(String)").matches(
                helloMethod, MemberServiceImpl.class
        )).isTrue();
        Assertions.assertThat(pointcut("args(Object)").matches(
                helloMethod, MemberServiceImpl.class
        )).isTrue();
        Assertions.assertThat(pointcut("args()").matches(
                helloMethod, MemberServiceImpl.class
        )).isFalse();

    }


}