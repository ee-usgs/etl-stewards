package gov.acwi.wqp.etl.monitoringLocation.domain;

import java.math.BigDecimal;

public class MonitoringLocation {

	private Integer dataSourceId;
	private String dataSource;
	private Integer stationId;
	private String siteId;
	private String organization;
	private String siteType;
	private String huc;
	private String governmentalUnitCode;
	private String geom;
	private String stationName;
	private String organizationName;
	private String descriptionText;
	private String stationTypeName;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String mapScale;
	private String geopositioningMethod;
	private String hdatumIdCode;
	private String elevationValue;
	private String elevationUnit;
	private String elevationMethod;
	private String vdatumIdCode;
	private BigDecimal drainAreaValue;
	private String drainAreaUnit;
	private BigDecimal contribDrainAreaValue;
	private String contribDrainAreaUnit;
	private String geopositionAccyValue;
	private String geopositionAccyUnit;
	private String verticalAccuracyValue;
	private String verticalAccuracyUnit;
	private String natAqfrName;
	private String aqfrName;
	private String aqfrTypeName;
	private String constructionDate;
	private BigDecimal wellDepthValue;
	private String wellDepthUnit;
	private BigDecimal holeDepthValue;
	private String holeDepthUnit;

	public Integer getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getHuc() {
		return huc;
	}
	public void setHuc(String huc) {
		this.huc = huc;
	}
	public String getGovernmentalUnitCode() {
		return governmentalUnitCode;
	}
	public void setGovernmentalUnitCode(String governmentalUnitCode) {
		this.governmentalUnitCode = governmentalUnitCode;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getDescriptionText() {
		return descriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}
	public String getStationTypeName() {
		return stationTypeName;
	}
	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getMapScale() {
		return mapScale;
	}
	public void setMapScale(String mapScale) {
		this.mapScale = mapScale;
	}
	public String getGeopositioningMethod() {
		return geopositioningMethod;
	}
	public void setGeopositioningMethod(String geopositioningMethod) {
		this.geopositioningMethod = geopositioningMethod;
	}
	public String getHdatumIdCode() {
		return hdatumIdCode;
	}
	public void setHdatumIdCode(String hdatumIdCode) {
		this.hdatumIdCode = hdatumIdCode;
	}
	public String getElevationValue() {
		return elevationValue;
	}
	public void setElevationValue(String elevationValue) {
		this.elevationValue = elevationValue;
	}
	public String getElevationUnit() {
		return elevationUnit;
	}
	public void setElevationUnit(String elevationUnit) {
		this.elevationUnit = elevationUnit;
	}
	public String getElevationMethod() {
		return elevationMethod;
	}
	public void setElevationMethod(String elevationMethod) {
		this.elevationMethod = elevationMethod;
	}
	public String getVdatumIdCode() {
		return vdatumIdCode;
	}
	public void setVdatumIdCode(String vdatumIdCode) {
		this.vdatumIdCode = vdatumIdCode;
	}
	public BigDecimal getDrainAreaValue() {
		return drainAreaValue;
	}
	public void setDrainAreaValue(BigDecimal drainAreaValue) {
		this.drainAreaValue = drainAreaValue;
	}
	public String getDrainAreaUnit() {
		return drainAreaUnit;
	}
	public void setDrainAreaUnit(String drainAreaUnit) {
		this.drainAreaUnit = drainAreaUnit;
	}
	public BigDecimal getContribDrainAreaValue() {
		return contribDrainAreaValue;
	}
	public void setContribDrainAreaValue(BigDecimal contribDrainAreaValue) {
		this.contribDrainAreaValue = contribDrainAreaValue;
	}
	public String getContribDrainAreaUnit() {
		return contribDrainAreaUnit;
	}
	public void setContribDrainAreaUnit(String contribDrainAreaUnit) {
		this.contribDrainAreaUnit = contribDrainAreaUnit;
	}
	public String getGeopositionAccyValue() {
		return geopositionAccyValue;
	}
	public void setGeopositionAccyValue(String geopositionAccyValue) {
		this.geopositionAccyValue = geopositionAccyValue;
	}
	public String getGeopositionAccyUnit() {
		return geopositionAccyUnit;
	}
	public void setGeopositionAccyUnit(String geopositionAccyUnit) {
		this.geopositionAccyUnit = geopositionAccyUnit;
	}
	public String getVerticalAccuracyValue() {
		return verticalAccuracyValue;
	}
	public void setVerticalAccuracyValue(String verticalAccuracyValue) {
		this.verticalAccuracyValue = verticalAccuracyValue;
	}
	public String getVerticalAccuracyUnit() {
		return verticalAccuracyUnit;
	}
	public void setVerticalAccuracyUnit(String verticalAccuracyUnit) {
		this.verticalAccuracyUnit = verticalAccuracyUnit;
	}
	public String getNatAqfrName() {
		return natAqfrName;
	}
	public void setNatAqfrName(String natAqfrName) {
		this.natAqfrName = natAqfrName;
	}
	public String getAqfrName() {
		return aqfrName;
	}
	public void setAqfrName(String aqfrName) {
		this.aqfrName = aqfrName;
	}
	public String getAqfrTypeName() {
		return aqfrTypeName;
	}
	public void setAqfrTypeName(String aqfrTypeName) {
		this.aqfrTypeName = aqfrTypeName;
	}
	public String getConstructionDate() {
		return constructionDate;
	}
	public void setConstructionDate(String constructionDate) {
		this.constructionDate = constructionDate;
	}
	public BigDecimal getWellDepthValue() {
		return wellDepthValue;
	}
	public void setWellDepthValue(BigDecimal wellDepthValue) {
		this.wellDepthValue = wellDepthValue;
	}
	public String getWellDepthUnit() {
		return wellDepthUnit;
	}
	public void setWellDepthUnit(String wellDepthUnit) {
		this.wellDepthUnit = wellDepthUnit;
	}
	public BigDecimal getHoleDepthValue() {
		return holeDepthValue;
	}
	public void setHoleDepthValue(BigDecimal holeDepthValue) {
		this.holeDepthValue = holeDepthValue;
	}
	public String getHoleDepthUnit() {
		return holeDepthUnit;
	}
	public void setHoleDepthUnit(String holeDepthUnit) {
		this.holeDepthUnit = holeDepthUnit;
	}
}
