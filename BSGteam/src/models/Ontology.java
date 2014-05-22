package models;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * This class define the vocabulary of the twitter user and venue, with their properties.
 * https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs
 * 
 * @author BSGTeam on 17 May 2014 21:12
 */

public class Ontology {
	
	
	//The RDF model that holds the vocabulary terms
	private static Model m_model = ModelFactory
			.createOntologyModel(OntModelSpec.RDFS_MEM);

	
	 // The namespace of the vocabulary as a string
	
	public static final String NS = "https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#";

	
	//Return the namespace of the vocabulary as a string
	
	public static String getURI() {
		return NS;
	}

	
	 //The namespace of the vocabulary as a resource
	public static final Resource NAMESPACE = m_model.createResource(NS);

	/**
	 * <p>
	 * User name.
	 * </p>
	 */
	public static final Property USERID = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#userId");

	
	/**
	 * 
	 * This create the location of where the tweet is posted (geoTag). A property of twitter user
	 * 
	 */
	public static final Property LOCATION = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#location");
	 
	// A property of twitter user	
	public static final Property description = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#description");

	/**
	 * This create the name of the location the user had visited. A property of twitter user
	 */
	public static final Property locationVisited = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#locationVisited");

	/**
	 * 
	 * This create the name of visitor that visited the venue. A property of venue
	 * 
	 */
	public static final Property nameOFVisitor = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#nameOFVisitor");

	/**
	 * 
	 * This create the name of the venue. A property of venue
	 * 
	 */
	public static final Property venueName = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueName");

	/**
	 * 
	 * This create the location of the venue. A property of venue
	 * 
	 */
	public static final Property venueLocation = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueLocation");

	/**
	 * 
	 * This create the url the venue. A property of venue
	 * 
	 */
	public static final Property venueUrl = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueUrl");

	/**
	 * 
	 * This create the address the venue. A property of venue
	 * 
	 */
	public static final Property venueAddress = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueAddress");

	/**
	 * 
	 * This create the name of visitor that visited the venue. A property of venue
	 * 
	 */
	public static final Property venueCategory = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueCategory");

	/**
	 * 
	 * This create the time at which a user checkin in a venue. A property of venue
	 * 
	 */
	public static final Property checkinTime = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#checkinTime");

	/**
	 * 
	 * This create the description of the  venue. A property of venue
	 * 
	 */
	public static final Property venueDescription = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venueDescription");

	
	/**
	 * 
	 * This create the photos available in the venue. A property of venue
	 * 
	 */
	public static final Property venuePhoto = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venuePhoto");

	
	public static final Property nameOfPeopleVisitedVenue = m_model
			.createProperty("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#nameOfPeopleVisitedVenue");

	/**
	 * 
	 * This  create the resource twitter user.
	 * 
	 */
	public static final Resource twitterUser = m_model
			.createResource("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#twitterUser");
	/**
	 * 
	 * This  create the resource venue.
	 * 
	 */
	
	public static final Resource venue = m_model
			.createResource("https://tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#venue");

}