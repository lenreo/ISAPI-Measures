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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableScheduling
public class MeasuresApiAspect {
	
    private static final Logger log = LoggerFactory.getLogger(MeasuresApiAspect.class);

	final static String MONITORIZATION_URL = "a6126b32.carbon.hostedgraphite.com";
	final static int MONITORIZATION_PORT = 2003;
	final static String MONITORIZATION_KEY = "46ac3511-2fa2-476b-bd5b-7dbf997c78d7";
    
    //echo "edff5a27-c65f-443e-8e69-88ff29994b57.test.testing 1" | nc 39711718.carbon.hostedgraphite.com 2003
//	final static String MONITORIZATION_URL = "39711718.carbon.hostedgraphite.com";
//	final static int MONITORIZATION_PORT = 2003;
//	final static String MONITORIZATION_KEY = "edff5a27-c65f-443e-8e69-88ff29994b57";
	
	final static private Map<String, String> MONITORIZATION_MAPPING = new HashMap<>();
	final static String API_NAME = "measures";
	
	final static String METRIC_EXECUTION_TIME = "tiempoEjecucion";
	final static String METRIC_NEW_REQUEST = "numeroPeticiones";
	final static String METRIC_ERROR_500 = "error500";
	final static String METRIC_ERROR_400 = "error400";
	final static String METRIC_CPU = "cpu";
	final static String METRIC_MEMORY = "memory";
	
	final static String SERVER = "server";
	final static Double SERVER_CPU_THRESHOLD = 90D;
	final static Double SERVER_MEMORY_THRESHOLD = 90D;
	
	public MeasuresApiAspect() {
		super();
		MONITORIZATION_MAPPING.put("addMeasure", "addMeasure");
		MONITORIZATION_MAPPING.put("evaluate", "evaluate");
		MONITORIZATION_MAPPING.put("findByMagnitude", "findByMagnitude");
		MONITORIZATION_MAPPING.put("findByMagnitudeAndDateRange", "findByMagnitudeAndDateRange");
		MONITORIZATION_MAPPING.put(SERVER, SERVER);
	}
	
	private void sendMetric(String method, String metric, Object value) {

		final Thread thread = new Thread() {
		    public void run() {
				final String apiMethod = MONITORIZATION_MAPPING.get(method);
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

	private void sendAlert(String method, String metric) {

		final Thread thread = new Thread() {
		    public void run() {
		    	// TODO
				final String alertString = method + " " + metric;
				System.out.println("Alert! " + alertString);
				log.info("Alert! " + alertString);
	    	}
	    };
		thread.start();
	}

	@Around("within(io.swagger.api.MeasuresApiController)")
	public Object aroundApi(ProceedingJoinPoint pjp) {
		
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
			
			// Error grave: enviar alerta
			sendAlert(methodName, METRIC_ERROR_500);
		}
		
	    return value;
	}
	
	@Scheduled(fixedRate = 60000)
	public void checkServer() {
		final Double cpu = Math.random() * 100D;
		sendMetric(SERVER, METRIC_CPU, cpu);
		if (SERVER_CPU_THRESHOLD.compareTo(cpu) < 0) {
			// Error grave: enviar alerta
			sendAlert(SERVER, METRIC_CPU);
		}
		
		final Double memory = Math.random() * 100D;
		sendMetric(SERVER, METRIC_MEMORY, memory);
		if (SERVER_MEMORY_THRESHOLD.compareTo(memory) < 0) {
			// Error grave: enviar alerta
			sendAlert(SERVER, METRIC_MEMORY);
		}
	}

}
