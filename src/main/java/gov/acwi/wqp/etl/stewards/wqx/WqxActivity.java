package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Activity")
public class WqxActivity {

	private WqxActivityDescription activityDescription;
	private WqxSampleDescription sampleDescription;
	private WqxResult result;

	public WqxActivityDescription getActivityDescription() {
		return activityDescription;
	}
	@XmlElement(name = "ActivityDescription")
	public void setActivityDescription(WqxActivityDescription activityDescription) {
		this.activityDescription = activityDescription;
	}
	public WqxSampleDescription getSampleDescription() {
		return sampleDescription;
	}
	@XmlElement(name = "SampleDescription")
	public void setSampleDescription(WqxSampleDescription sampleDescription) {
		this.sampleDescription = sampleDescription;
	}
	public WqxResult getResult() {
		return result;
	}
	@XmlElement(name = "Result")
	public void setResult(WqxResult result) {
		this.result = result;
	}
}
