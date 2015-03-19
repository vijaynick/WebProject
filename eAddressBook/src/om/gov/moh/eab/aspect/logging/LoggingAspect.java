package om.gov.moh.eab.aspect.logging;

import java.io.Serializable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

public class LoggingAspect implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(LoggingAspect.class.getName());

	@Before("execution(* om.gov.moh.eab..*.*(..)) && !execution(* *.isAuthorized*(..)) && !execution(* *.set*(..)) && !execution(* *.get*()) && !execution(* *.init(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.info("Currently Running this method : " + joinPoint.getSignature().getName());
	}

}