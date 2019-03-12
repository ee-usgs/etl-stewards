package gov.acwi.wqp.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class NoopItemProcessor<D> implements ItemProcessor<D, D> {
	//Use PassThroughItemProcessor instead
	private final Logger LOG = LoggerFactory.getLogger(NoopItemProcessor.class);

	public D process(D item) {
		LOG.debug(item.toString());
		return item;
	}

}
