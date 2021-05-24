package com.myaem65training.core.bean;

public class FetchDamListingBean {

	private String damAsset_Path;

	private String damAsset_Title;

	private String damAsset_CreatedDate;

	private String damAsset_CreatedBy;

	private String damAsset_LastModifiedDate;

	private String damAsset_dcTitle;
	
	private String damAsset_dcDescription;
	
	public String getDamAsset_Path() {
		return damAsset_Path;
	}

	public void setDamAsset_Path(String damAsset_Path) {
		this.damAsset_Path = damAsset_Path;
	}

	public String getDamAsset_Title() {
		return damAsset_Title;
	}

	public void setDamAsset_Title(String damAsset_Title) {
		this.damAsset_Title = damAsset_Title;
	}

	public String getDamAsset_CreatedDate() {
		return damAsset_CreatedDate;
	}

	public void setDamAsset_CreatedDate(String damAsset_CreatedDate) {
		this.damAsset_CreatedDate = damAsset_CreatedDate;
	}

	public String getDamAsset_CreatedBy() {
		return damAsset_CreatedBy;
	}

	public void setDamAsset_CreatedBy(String damAsset_CreatedBy) {
		this.damAsset_CreatedBy = damAsset_CreatedBy;
	}

	public String getDamAsset_LastModifiedDate() {
		return damAsset_LastModifiedDate;
	}

	public void setDamAsset_LastModifiedDate(String damAsset_LastModifiedDate) {
		this.damAsset_LastModifiedDate = damAsset_LastModifiedDate;
	}

	public String getDamAsset_dcTitle() {
		return damAsset_dcTitle;
	}

	public void setDamAsset_dcTitle(String damAsset_dcTitle) {
		this.damAsset_dcTitle = damAsset_dcTitle;
	}

	public String getDamAsset_dcDescription() {
		return damAsset_dcDescription;
	}

	public void setDamAsset_dcDescription(String damAsset_dcDescription) {
		this.damAsset_dcDescription = damAsset_dcDescription;
	}

	public FetchDamListingBean(String damAsset_Path) {

		this.damAsset_Path = damAsset_Path;

	}

	public FetchDamListingBean(String damAsset_Title, String damAsset_Path) {

		this.damAsset_Title = damAsset_Title;
		this.damAsset_Path = damAsset_Path;

	}

	public FetchDamListingBean(String damAsset_Path, String damAsset_Title, String damAsset_CreatedDate) {

		this.damAsset_CreatedDate = damAsset_CreatedDate;
		this.damAsset_Path = damAsset_Path;
		this.damAsset_Title = damAsset_Title;
	}

	public FetchDamListingBean(String damAsset_CreatedDate, String damAsset_Path, String damAsset_Title,
			String damAsset_CreatedBy) {

		this.damAsset_CreatedDate = damAsset_CreatedDate;
		this.damAsset_Path = damAsset_Path;
		this.damAsset_Title = damAsset_Title;
		this.damAsset_CreatedBy = damAsset_CreatedBy;
	}

	public FetchDamListingBean(String damAsset_CreatedDate, String damAsset_Path, String damAsset_Title,
			String damAsset_CreatedBy, String damAsset_LastModifiedDate) {

		this.damAsset_CreatedDate = damAsset_CreatedDate;
		this.damAsset_Path = damAsset_Path;
		this.damAsset_Title = damAsset_Title;
		this.damAsset_CreatedBy = damAsset_CreatedBy;
		this.damAsset_LastModifiedDate = damAsset_LastModifiedDate;
	}
	
	public FetchDamListingBean(String damAsset_CreatedDate, String damAsset_Path, String damAsset_Title,
			String damAsset_CreatedBy, String damAsset_LastModifiedDate, String damAsset_dcTitle) {

		this.damAsset_CreatedDate = damAsset_CreatedDate;
		this.damAsset_Path = damAsset_Path;
		this.damAsset_Title = damAsset_Title;
		this.damAsset_CreatedBy = damAsset_CreatedBy;
		this.damAsset_LastModifiedDate = damAsset_LastModifiedDate;
		this.damAsset_dcTitle = damAsset_dcTitle;
	}
	
	public FetchDamListingBean(String damAsset_CreatedDate, String damAsset_Path, String damAsset_Title,
			String damAsset_CreatedBy, String damAsset_LastModifiedDate, String damAsset_dcTitle, String damAsset_dcDescription) {

		this.damAsset_CreatedDate = damAsset_CreatedDate;
		this.damAsset_Path = damAsset_Path;
		this.damAsset_Title = damAsset_Title;
		this.damAsset_CreatedBy = damAsset_CreatedBy;
		this.damAsset_LastModifiedDate = damAsset_LastModifiedDate;
		this.damAsset_dcTitle = damAsset_dcTitle;
		this.damAsset_dcDescription = damAsset_dcDescription;
	}
}
