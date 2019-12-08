package io.swagger.api;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeasuresApiAspect {
	
    private static final Logger log = LoggerFactory.getLogger(MeasuresApiAspect.class);

//	final static String MONITORIZATION_URL = "a6126b32.carbon.hostedgraphite.com";
//	final static int MONITORIZATION_PORT = 2003;
//	final static String MONITORIZATION_KEY = "46ac3511-2fa2-476b-bd5b-7dbf997c78d7";
    
    //echo "edff5a27-c65f-443e-8e69-88ff29994b57.test.testing 1" | nc 39711718.carbon.hostedgraphite.com 2003
	final static String MONITORIZATION_URL = "39711718.carbon.hostedgraphite.com";
	final static int MONITORIZATION_PORT = 2003;
	final static String MONITORIZATION_KEY = "edff5a27-c65f-443e-8e69-88ff29994b57";
	
	final static private Map<String, String> MONITOZIATION_MAPPING = new HashMap<>();
	final static String API_NAME = "measures";
	
	final static String METRIC_EXECUTION_TIME = "tiempoEjecucion";
	final static String METRIC_NEW_REQUEST = "numeroPeticiones";
	final static String METRIC_ERROR_500 = "error500";
	final static String METRIC_ERROR_400 = "error400";
	
	public MeasuresApiAspect() {
		super();
		MONITOZIATION_MAPPING.put("addMeasure", "addMeasure");
		MONITOZIATION_MAPPING.put("evaluate", "evaluate");
		MONITOZIATION_MAPPING.put("findByMagnitude", "findByMagnitude");
		MONITOZIATION_MAPPING.put("findByMagnitudeAndDateRange", "findByMagnitudeAndDateRange");
	}
	
	private void sendMetric(String method, String metric, Object value) {

		final Thread thread = new Thread() {
		    public void run() {
				final String apiMethod = MONITOZIATION_MAPPING.get(method);
				if (null != apiMethod && !apiMethod.isEmpty()) {
					try {
						final Socket conn = new Socket(MONITORIZATION_URL, MONITORIZATION_PORT);
						final DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
						final String metricString = MONITORIZATION_KEY + "." + API_NAME + "." + apiMethod  + "." + metric + " " + value.toString() + "\n";
						dos.writeBytes(metricString);
						System.out.println(metricString);
						log.info(metricString);
						conn.close();
					} catch (Exception e) {
					}
				}
	    	}
	    };
		thread.start();
		  
	}

	@Around("within(io.swagger.api.MeasuresApiController)")
	public Object aroundGraphicsApi(ProceedingJoinPoint pjp) {
		
		final String methodName = pjp.getSignature().getName();
		log.info("Ejecutando " + methodName);
		
		double x = Math.random() * 10;
		boolean simulate400 = x < 2;
		boolean simulate500 = (x >= 2 && x < 4);
		
		if (simulate400) {
			// Enviar error 500
			sendMetric(methodName, METRIC_ERROR_400, "1");
			return null;
		}
		
		// Nueva ejecución
		sendMetric(methodName, METRIC_NEW_REQUEST, "1");

		Object value = null;
		try {
			final long start = System.currentTimeMillis();
			if (simulate500) {
				throw new Throwable();
			}
			value = pjp.proceed();
			long executionTime = System.currentTimeMillis() - start;
			
			// Enviar tiempo de ejecución
			sendMetric(methodName, METRIC_EXECUTION_TIME, executionTime);
		} catch (Throwable e) {
			e.printStackTrace();
			
			// Enviar error 500
			sendMetric(methodName, METRIC_ERROR_500, "1");
		}
		
	    return value;
	}

}
