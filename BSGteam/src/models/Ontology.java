package models;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Vocabulary definitions from
 * https://sites.google.com/site/sheffieldbash/home/web2.owl
 * 
 * @author Bash on 17 May 2014 21:12
 */
public class Ontology {
	/**
	 * <p>
	 * The RDF model that holds the vocabulary terms
	 * </p>
	 */

	private static Model m_model = ModelFactory
			.createOntologyModel(OntModelSpec.RDFS_MEM);

	// m_model.read(new FileInputStream(
	// "https://sites.google.com/site/sheffieldbash/home/web2.rdfs"),
	// "");
	// private static Model m_model = ModelFactory.createDefaultModel();

	/**
	 * <p>
	 * The namespace of the vocabulary as a string
	 * </p>
	 */
	public static final String NS = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs#";

	/**
	 * <p>
	 * The namespace of the vocabulary as a string
	 * </p>
	 * 
	 * @see #NS
	 */
	public static String getURI() {
		return NS;
	}

	/**
	 * <p>
	 * The namespace of the vocabulary as a resource
	 * </p>
	 */
	public static final Resource NAMESPACE = m_model.createResource(NS);

	/**
	 * <p>
	 * User name.
	 * </p>
	 */
	public static final Property USERID = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#userId");

	/**
	 * <p>
	 * Links a Contact with a ContactGroup it belongs to.
	 * </p>
	 */
	public static final Property NAME = m_model
			.createProperty("http://xmlns.com/foaf/0.1/name");

	/**
	 * <p>
	 * Birth date of the object represented by this Contact. An equivalent of
	 * the 'BDAY' property as defined in RFC 2426 Sec. 3.1.5.
	 * </p>
	 */
	public static final Property LOCATION = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#location");

	/**
	 * <p>
	 * A Blog url.
	 * </p>
	 */
	public static final Property n_get = m_model.getProperty(NS, "location");

	public static final Property image = m_model
			.createProperty("http://xmlns.com/foaf/0.1/img");

	/**
	 * <p>
	 * The name of the contact group. This property was NOT defined in the VCARD
	 * standard. See documentation of the 'ContactGroup' class for details
	 * </p>
	 */
	public static final Property description = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#description");

	/**
	 * <p>
	 * A comment about the contact medium.
	 * </p>
	 */
	public static final Property locationVisited = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#locationVisited");

	/**
	 * <p>
	 * A value that represents a globally unique identifier corresponding to the
	 * individual or resource associated with the Contact. An equivalent of the
	 * 'UID' property defined in RFC 2426 Sec. 3.6.7
	 * </p>
	 */
	public static final Property peopleContacted = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#People_he_contacted");

	public static final Property nameOFVisitor = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#nameOFVisitor");

	public static final Property venueName = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueName");

	public static final Property venueLocation = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueLocation");

	public static final Property venueUrl = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueUrl");

	public static final Property venueAddress = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueAddress");

	public static final Property venueCategory = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueCategory");

	public static final Property checkinTime = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#checkinTime");

	public static final Property venueDescription = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venueDescription");

	public static final Property venuePhoto = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venuePhoto");

	public static final Property nameOfPeopleVisitedVenue = m_model
			.createProperty("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#nameOfPeopleVisitedVenue");

	public static final Resource twitterUser = m_model
			.createResource("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#twitterUser");

	public static final Resource venue = m_model
			.createResource("https://sites.google.com/site/sheffieldbash/home/web2.rdfs#venue");

}