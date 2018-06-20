package hello;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

/**
 * Controller for counter.
 * 
 * @author ru-rocker
 *
 */
@RestController
public class CounterController {
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private BigDecimal commitmentAmt = new BigDecimal(0.0);
	private AtomicInteger atomicInteger = new AtomicInteger();

	 List<CommitmentDto> commitmentList = new ArrayList<>();
	Counter commitmentAmtCounter;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 public CounterController(MeterRegistry registry) {
		 registry.gaugeCollectionSize("seller.commitment", Arrays.asList(Tag.of("sellernumber", "commitment.sellernumber")),commitmentList);
		// register a counter of questionable usefulness
			commitmentAmtCounter = registry.counter("seller.commitment.total");
			System.out.println("******** Counter Value "+commitmentAmtCounter.measure().iterator().next().getValue());
			
	 }
	@Timed(value = "smp.commitments.posted", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
	"v1" })
	@GetMapping(path = "/smpcommitment")
	public CounterDto getCounter(@RequestParam("message") String message) {
	CounterDto dto = new CounterDto();
	dto.setMessage(message);
	dto.setCounter(atomicInteger.incrementAndGet());
	logger.trace("This is a trace message");
	dto.setTimestamp(System.currentTimeMillis());
	return dto;
	}
	@Timed(value = "smp.servicer.allInFunding", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
	"v1" })
	@GetMapping(path = "/allinfunding")
	public CounterDto getAllInFunding(@RequestParam("message") String message) {
	CounterDto dto = new CounterDto();
	dto.setMessage(message);
	dto.setCounter(atomicInteger.incrementAndGet());
	
	dto.setTimestamp(System.currentTimeMillis());
	return dto;
	}
	@Timed(value = "smp.servicer.srpholdback", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
	"v1" })
	@GetMapping(path = "/srpHoldback")
	public CounterDto getSrpHoldBack(@RequestParam("message") String message) {
	CounterDto dto = new CounterDto();
	dto.setMessage(message);
	dto.setCounter(atomicInteger.incrementAndGet());
	
	dto.setTimestamp(System.currentTimeMillis());
	return dto;
	}
	 @GetMapping(path="/asset")
	 @Timed(value = "people.asset", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
		"v1" })
	    public void test() throws Exception {
	        throw new Exception("error!");
	    }

	    @GetMapping(path ="/property")
	    @Timed(value = "smp.party.property", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
		"v1" })
	    public void property() throws IOException {
	    	logger.warn("Property does not have addressline");
	    }
	    
	    @GetMapping(path ="/relationship")
	    @Timed(value = "smp.seller.relationship", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
		"v1" })
	    public void relationship() throws IOException {
	    	logger.info("Relation does not have contacts");
	    }
	    @PostMapping("/commitment")
	   
	    public List<String> putPeople(@RequestBody CommitmentDto commitment) throws InterruptedException {
	       
	        commitmentList.add(commitment);
	        BigDecimal result = commitmentAmt.add(commitment.getCommitmentValue());
	        
	        commitmentAmtCounter.increment(result.doubleValue());
	        
	       
	      
	        return Arrays.asList("Jim", "Tom", "Tim");
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