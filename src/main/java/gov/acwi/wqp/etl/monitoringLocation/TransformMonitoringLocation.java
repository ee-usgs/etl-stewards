package gov.acwi.wqp.etl.monitoringLocation;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.acwi.wqp.etl.extract.domain.ArsStation;
import gov.acwi.wqp.etl.extract.domain.ArsStationRowMapper;


@Configuration
public class TransformMonitoringLocation {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("setupMonitoringLocationSwapTableFlow")
	private Flow setupMonitoringLocationSwapTableFlow;

	@Autowired
	@Qualifier("buildMonitoringLocationIndexesFlow")
	private Flow buildMonitoringLocationIndexesFlow;

	@Bean
	public JdbcCursorItemReader<ArsStation> monitoringLocationReader() {
		return new JdbcCursorItemReaderBuilder<ArsStation>()
				.dataSource(this.dataSource)
				.name("organizationReader")
				//TODo cleanup for PostgreSQL
				.sql("select ars_station.*, ars_org_project.*, ars_site_type_to_primary.primary_site_type resolved_monitoring_location_type_name from ars_station join ars_org_project on 1=1 left join ars_site_type_to_primary on ars_station.monitoring_location_type_name = ars_site_type_to_primary.site_type")
				.rowMapper(new ArsStationRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<MonitoringLocation> monitoringLocationWriter() {
		JdbcBatchItemWriter<MonitoringLocation> itemWriter = new JdbcBatchItemWriter<MonitoringLocation>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into station_swap_stewards (data_source_id, data_source, station_id, site_id, organization, site_type, huc, governmental_unit_code,"
					+ " geom, station_name, organization_name, description_text, station_type_name, latitude, longitude, map_scale,"
					+ " geopositioning_method, hdatum_id_code, elevation_value, elevation_unit, elevation_method, vdatum_id_code,"
					+ " drain_area_value, drain_area_unit, contrib_drain_area_value, contrib_drain_area_unit,"
					+ " geoposition_accy_value, geoposition_accy_unit, vertical_accuracy_value, vertical_accuracy_unit,"
					+ " nat_aqfr_name, aqfr_name, aqfr_type_name, construction_date, well_depth_value, well_depth_unit,"
					+ " hole_depth_value, hole_depth_unit)"
				+ " values (:dataSourceId, :dataSource, :stationId, :siteId, :organization, :siteType, :huc, :governmentalUnitCode,"
					+ " :geom, :stationName, :organizationName, :descriptionText, :stationTypeName, :latitude, :longitude, :mapScale,"
					+ " :geopositioningMethod, :hdatumIdCode, :elevationValue, :elevationUnit, :elevationMethod, :vdatumIdCode,"
					+ " :drainAreaValue, :drainAreaUnit, :contribDrainAreaValue, :contribDrainAreaUnit,"
					+ " :geopositionAccyValue, :geopositionAccyUnit, :verticalAccuracyValue, :verticalAccuracyUnit,"
					+ " :natAqfrName, :aqfrName, :aqfrTypeName, :constructionDate, :wellDepthValue, :wellDepthUnit,"
					+ " :holeDepthValue, :holeDepthUnit)");
		ItemSqlParameterSourceProvider<MonitoringLocation> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformMonitoringLocationStep() {
		return stepBuilderFactory
				.get("transformMonitoringLocationStep")
				.<ArsStation, MonitoringLocation>chunk(10)
				.reader(monitoringLocationReader())
				.processor(new MonitoringLocationProcessor())
				.writer(monitoringLocationWriter())
				.build();
	}

	@Bean
	public Flow monitoringLocationFlow() {
		return new FlowBuilder<SimpleFlow>("monitoringLocationFlow")
				.start(setupMonitoringLocationSwapTableFlow)
				.next(transformMonitoringLocationStep())
				.next(buildMonitoringLocationIndexesFlow)
				.build();
	}

}
