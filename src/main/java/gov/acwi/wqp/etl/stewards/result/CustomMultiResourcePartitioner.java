package gov.acwi.wqp.etl.stewards.result;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class CustomMultiResourcePartitioner implements Partitioner {

	private static final String DEFAULT_KEY_NAME = "url";
	private static final String PARTITION_KEY = "partition";
	private List<URL> urls = new ArrayList<>();
	private String keyName = DEFAULT_KEY_NAME;

	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> map = new HashMap<>(gridSize);
		int i = 0;
		for (URL url : urls) {
			ExecutionContext context = new ExecutionContext();
			context.put(keyName, url);
			map.put(PARTITION_KEY + i, context);
			i++;
		}
		return map;
	}

}
