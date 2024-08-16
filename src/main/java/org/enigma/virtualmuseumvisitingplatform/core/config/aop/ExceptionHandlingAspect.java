package org.enigma.virtualmuseumvisitingplatform.core.config.aop;


import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "execution(* org.enigma.virtualmuseumvisitingplatform.service.*.*.*.*(..))", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Metod icrası zamanı istisna baş verdi: " + exception.getMessage());
    }
}