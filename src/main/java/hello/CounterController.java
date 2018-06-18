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
	 List<CommitmentDto> commitment = new ArrayList<>();
	Counter commitmentAmtCounter;
	Logger LOG = LoggerFactory.getLogger(CounterController.class);
	 public CounterController(MeterRegistry registry) {
		 registry.gaugeCollectionSize("seller.commitment", Arrays.asList(Tag.of("sellernumber", "commitment.sellernumber")),commitment);
		// register a counter of questionable usefulness
			commitmentAmtCounter = registry.counter("commitment.value");
			
	 }
	@Timed(value = "smp.commitments.posted", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
	"v1" })
	@GetMapping(path = "/smpcommitment")
	public CounterDto getCounter(@RequestParam("message") String message) {
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
	    	LOG.warn("Property does not have addressline");
	    }
	    
	    @GetMapping(path ="/relationship")
	    @Timed(value = "smp.seller.relationship", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
		"v1" })
	    public void relationship() throws IOException {
	    	LOG.info("Relation does not have contacts");
	    }
	    @PostMapping("/commitment")
	    @Timed(value = "seller.commitment", histogram = true, percentiles = { 0.95, 0.99 }, extraTags = { "version",
		"v1" })
	    public List<String> putPeople(@RequestBody CommitmentDto commitment) throws InterruptedException {
	        int seconds2Sleep = SECURE_RANDOM.nextInt(1000);
	        System.out.println(seconds2Sleep);
	        commitmentAmt.add(commitment.getCommitmentValue());
	        commitmentAmtCounter.increment(commitmentAmt.doubleValue());
	        TimeUnit.MILLISECONDS.sleep(seconds2Sleep);
	        return Arrays.asList("Jim", "Tom", "Tim");
	    }
}