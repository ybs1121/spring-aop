package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    Method helloMethod;

    @BeforeEach
    public void before() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }


    @Test
    void printMethod() throws NoSuchMethodException {
        // helloMethod:public java.lang.String hello.aop.member.MemberServiceImpl.hello
        log.info("helloMethod:{}", helloMethod.toString());
    }

    @Test
    void exactMatch() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(public String hello.aop.member.MemberServiceImpl.hello(String))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* *(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hel*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* *el*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* *ss*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.member.MemberServiceImpl.hello(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.member.*.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.*.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.member..*.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop..*.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* java.lang.String hello.aop.member.MemberServiceImpl.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.member.MemberService.*(..))"
        );

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello
        pointcut.setExpression(
                "execution(* hello.aop.member.MemberService.*(..))"
        );

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatch() {
        pointcut.setExpression(
                "execution(* *(String))"
        );
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression(
                "execution(* *())"
        );
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 정확하게 하나의 파라미터 (asdas)
    @Test
    void argsMatchStar() {
        pointcut.setExpression(
                "execution(* *(*))"
        );
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //() ,(xxx), (xxx,zzz)
    @Test
    void argsMatchAll() {
        pointcut.setExpression(
                "execution(* *(..))"
        );
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //(String) ,(String, xxx), (String,xxx,zzz)
    @Test
    void argsMatchComplex() {
        pointcut.setExpression(
                "execution(* *(String,..))"
        );
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


}
