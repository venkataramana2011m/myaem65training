package com.myaem65training.core.models;

import java.util.ArrayList;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class ResponseBodyApplication {
	  private String name;
	  ArrayList<String> topLevelDomain = new ArrayList<String>();
	  private String alpha2Code;
	  private String alpha3Code;
	  ArrayList<String> callingCodes = new ArrayList<String>();
	  private String capital;
	  ArrayList<String> altSpellings = new ArrayList<String>();
	  private String region;
	  private String subregion;
	  private float population;
	  ArrayList<String> latlng = new ArrayList<String>();
	  private String demonym;
	  private float area;
	  private float gini;
	  ArrayList<String> timezones = new ArrayList<String>();
	  ArrayList<String> borders = new ArrayList<String>();
	  private String nativeName;
	  private String numericCode;
	  ArrayList<String> currencies = new ArrayList<String>();
	  ArrayList<String> languages = new ArrayList<String>();
	  ResponseBodyApplicationTranslations TranslationsObject;
	  private String relevance;


	 // Getter Methods 

	  public String getName() {
	    return name;
	  }

	  public String getAlpha2Code() {
	    return alpha2Code;
	  }

	  public String getAlpha3Code() {
	    return alpha3Code;
	  }

	  public String getCapital() {
	    return capital;
	  }

	  public String getRegion() {
	    return region;
	  }

	  public String getSubregion() {
	    return subregion;
	  }

	  public float getPopulation() {
	    return population;
	  }

	  public String getDemonym() {
	    return demonym;
	  }

	  public float getArea() {
	    return area;
	  }

	  public float getGini() {
	    return gini;
	  }

	  public String getNativeName() {
	    return nativeName;
	  }

	  public String getNumericCode() {
	    return numericCode;
	  }

	  public ResponseBodyApplicationTranslations getTranslations() {
	    return TranslationsObject;
	  }

	  public String getRelevance() {
	    return relevance;
	  }

	 // Setter Methods 

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setAlpha2Code( String alpha2Code ) {
	    this.alpha2Code = alpha2Code;
	  }

	  public void setAlpha3Code( String alpha3Code ) {
	    this.alpha3Code = alpha3Code;
	  }

	  public void setCapital( String capital ) {
	    this.capital = capital;
	  }

	  public void setRegion( String region ) {
	    this.region = region;
	  }

	  public void setSubregion( String subregion ) {
	    this.subregion = subregion;
	  }

	  public void setPopulation( float population ) {
	    this.population = population;
	  }

	  public void setDemonym( String demonym ) {
	    this.demonym = demonym;
	  }

	  public void setArea( float area ) {
	    this.area = area;
	  }

	  public void setGini( float gini ) {
	    this.gini = gini;
	  }

	  public void setNativeName( String nativeName ) {
	    this.nativeName = nativeName;
	  }

	  public void setNumericCode( String numericCode ) {
	    this.numericCode = numericCode;
	  }

	  public void setTranslations( ResponseBodyApplicationTranslations translationsObject ) {
	    this.TranslationsObject = translationsObject;
	  }

	  public void setRelevance( String relevance ) {
	    this.relevance = relevance;
	  }
	  
	  
}
