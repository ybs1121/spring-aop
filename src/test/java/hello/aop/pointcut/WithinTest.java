package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    Method helloMethod;

    @BeforeEach
    public void before() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(hello.aop,memberService)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


}
