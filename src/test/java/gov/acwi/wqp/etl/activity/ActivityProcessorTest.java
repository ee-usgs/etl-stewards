package gov.acwi.wqp.etl.activity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.result.ResultProcessorTest;
import gov.acwi.wqp.etl.stewards.result.ArsResult;

public class ActivityProcessorTest extends BaseProcessorTest {

	@Test
	public void processorTest() throws Exception {
		ArsResult arsResult = ResultProcessorTest.buildArsResult();

		ActivityProcessor processor = new ActivityProcessor(configurationService);

		Activity activity = processor.process(arsResult);

		assertActivity(activity);
	}

	public static void assertActivity(Activity activity) {
		assertEquals(TEST_DATA_SOURCE_ID, activity.getDataSourceId());
		assertEquals(TEST_DATA_SOURCE, activity.getDataSource());
		assertEquals(TEST_STATION_ID, activity.getStationId());
		assertEquals(TEST_SITE_ID, activity.getSiteId());
		assertEquals(TEST_EVENT_DATE_LOCAL_DATE, activity.getEventDate());
		assertEquals(TEST_ACTIVITY_IDENTIFIER, activity.getActivity());
		assertEquals(TEST_ACTIVITY_MEDIA_NAME, activity.getSampleMedia());
		assertEquals(TEST_ORG_ID, activity.getOrganization());
		assertEquals(TEST_SITE_TYPE, activity.getSiteType());
		assertEquals(TEST_HUC_12, activity.getHuc());
		assertEquals(TEST_GOVERNMENT_UNIT_CODE, activity.getGovernmentalUnitCode());
		assertEquals(TEST_GEOM, activity.getGeom());
		assertEquals(TEST_ORG_NAME, activity.getOrganizationName());
		assertEquals(TEST_ACTIVITY_ID, activity.getActivityId());
		assertEquals(TEST_ACTIVITY_TYPE_CODE, activity.getActivityTypeCode());
		assertEquals(TEST_ACTIVITY_START_TIME, activity.getActivityStartTime());
		assertEquals(TEST_ACTIVITY_START_TIMEZONE, activity.getActStartTimeZone());
		assertEquals(TEST_PROJECT_IDENTIFIER, activity.getProjectId());
		assertEquals(TEST_PROJECT_NAME, activity.getProjectName());
		assertEquals(TEST_ML_NAME, activity.getMonitoringLocationName());
		assertEquals(TEST_ACTIVITY_COMMENT, activity.getActivityComment());
		assertEquals(TEST_SAMPLE_COLLECTION_METHOD_ID, activity.getSampleCollectMethodId());
		assertEquals(TEST_SAMPLE_COLLECTION_CONTEXT, activity.getSampleCollectMethodCtx());
		assertEquals(TEST_SAMPLE_COLLECTION_NAME, activity.getSampleCollectMethodName());
		assertEquals(TEST_SAMPLE_COLLECTION_EQUIPMENT, activity.getSampleCollectEquipName());
	}
}
