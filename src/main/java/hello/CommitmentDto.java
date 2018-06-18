package hello;

import java.math.BigDecimal;

/**
 * Just another DTO.
 * 
 * @author ricky
 *
 */
public class CommitmentDto {

	private long timestamp;
	private BigDecimal commitmentValue;
	private String sellerNumber;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getCommitmentValue() {
		return commitmentValue;
	}

	public void setCommitmentValue(BigDecimal commitmentValue) {
		this.commitmentValue = commitmentValue;
	}

	public String getSellerNumber() {
		return sellerNumber;
	}

	public void setSellerNumber(String sellerNumber) {
		this.sellerNumber = sellerNumber;
	}

	

}