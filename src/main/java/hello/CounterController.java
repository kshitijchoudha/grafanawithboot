package hello;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

/**
 * Controller for counter.
 * 
 * @author ru-rocker
 *
 */
@RestController
public class CounterController {

	private AtomicInteger atomicInteger = new AtomicInteger();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Timed(value = "get.counter.requests", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
			"v1" })
	@GetMapping(path = "/counter")
	public CounterDto getCounter(@RequestParam("message") String message) {
		CounterDto dto = new CounterDto();
		dto.setMessage(message);
		dto.setCounter(atomicInteger.incrementAndGet());
		dto.setTimestamp(System.currentTimeMillis());
		logger.trace("This is a trace message");
		return dto;
	}

	@Timed(value = "get.log.prints", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
			"v1" })
	@GetMapping(path = "/printLogs")
	public String printSomeLogs() {
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		return "Did some logging..";
	}

}