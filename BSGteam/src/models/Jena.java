package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.util.FmtUtils;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.Photo;

public class Jena {
	private String folder = null;

	public Jena(String folder) {
		super();
		File path = new File(folder);
		if (!path.exists()) {
			path.mkdirs();
		}
		this.folder = folder;
	}

	public void saveUser(String fullame, String id, String location,
			String image, String description,
			ArrayList<String> locationVisited, Set<String> peopleContacted) {

		id = id.replace("'", "");
		if (!userExists(id)) {
			Model m = ModelFactory.createDefaultModel();
			String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/userTripleStore.rdf";

			// String uri = xmlbase + "#" + id;
			// create Resource for twitter use
			Resource user = m.createResource(xmlbase + "#" + id);

			// add to properties to twitterUser
			user.addProperty(FOAF.name, fullame)
					.addProperty(Ontology.USERID, id)
					.addProperty(Ontology.LOCATION, location)
					.addProperty(FOAF.img, image)
					.addProperty(Ontology.description, description);

			if (locationVisited != null) {
				for (String visited : locationVisited) {
					user.addProperty(Ontology.locationVisited, visited);
				}
			}
			if (peopleContacted != null) {
				for (String contact : peopleContacted) {
					user.addProperty(FOAF.knows, contact);
				}
			}

			m.setNsPrefix("intelligentWeb", Ontology.NS);
			m.setNsPrefix("foaf", FOAF.NS);

			// now write the model in TURTLE form to a file
			FileOutputStream userRDF = null;
			try {
				userRDF = new FileOutputStream(folder + "userTripleStore.rdf",
						true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			m.write(userRDF, "TURTLE", xmlbase);
		}
	}

	public void saveVenue(String visitorName, String venueName, Photo[] photos,
			Category[] categories, String address, String description,
			String url, String checkinTime) {

		venueName = venueName.replace("'", "");
		// if (!checkinExists(visitorName, venueName, checkinTime)) {
		Model m = ModelFactory.createDefaultModel();
		String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/venueTripleStore.rdf";
		// create Resource for twitter use
		Resource venue = m.createResource(xmlbase + "#"
				+ venueName.replace(" ", "_"));
		// add to properties to twitterUser
		venue.addProperty(Ontology.nameOFVisitor, visitorName)
				.addProperty(Ontology.venueName, venueName)
				.addProperty(Ontology.venueUrl, url)
				.addProperty(Ontology.venueAddress, address)
				.addProperty(Ontology.venueDescription, description)
				.addProperty(Ontology.checkinTime, checkinTime);

		System.out.println(venueName);
		for (Category category : categories) {
			venue.addProperty(Ontology.venueCategory, category.getName());
		}
		if (photos != null && photos.length > 1) {
			for (Photo photo : photos) {
				if (photo != null) {
					if (photo.getUrl() != null) {
						venue.addProperty(Ontology.venuePhoto, photo.getUrl());
					}
				}
			}
		}

		m.setNsPrefix("intelligentWeb", Ontology.NS);
		// now write the model in XML form to a file
		FileOutputStream venueRDF = null;
		try {
			venueRDF = new FileOutputStream(folder + "venueTripleStore.rdf",
					true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		m.write(venueRDF, "TURTLE", xmlbase);
		// }
	}

	public ArrayList<TwitterUser> queryUsers(String userId) {
		ArrayList<TwitterUser> users = new ArrayList<TwitterUser>();
		InputStream in;
		userId = userId.replace("'", "");
		try {
			String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/userTripleStore.rdf";
			in = new FileInputStream(new File(folder + "userTripleStore.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model //
												// URIs are
			// absolute
			in.close();
			// Create a new query
			String queryStringOne = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
					+ "PREFIX intelWeb: <tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#> "
					+ "SELECT (min(?x) as ?uri) ?id (min(?userName) as ?name) (min(?image) as ?profile) (min(?location) as ?loc) "
					+ "(min(?description) as ?des) (group_concat(?locationVisited; separator=',') as ?visited) "
					+ "(group_concat(?contactPeople; separator=',') as ?knows) "
					+ "WHERE {"
					+ " ?x foaf:name ?userName . "
					+ " ?x intelWeb:userId ?id. "
					+ "OPTIONAL {?x intelWeb:location ?location .}"
					+ "OPTIONAL {?x foaf:img ?image .}"
					+ "OPTIONAL {?x intelWeb:description ?description .}"
					+ "OPTIONAL {?x intelWeb:locationVisited ?locationVisited .}"
					+ "OPTIONAL {?x foaf:knows ?contactPeople . "
					+ "}"
					+ "FILTER regex(?id,'^";
			String queryStringTwo = "','i')" + " }" + "GROUP BY ?id";
			ParameterizedSparqlString queryString = new ParameterizedSparqlString();
			queryString.append(queryStringOne);
			queryString.append(userId);
			queryString.append(queryStringTwo);

			Query query = queryString.asQuery();
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			String userName = null;
			String uri = null;
			String id = null;
			String location = null;
			String image = null;
			String description = null;
			String locationVisited = null;
			String contactPeople = null;

			while (results.hasNext()) {
				QuerySolution res = results.next();
				Iterator<String> res1 = res.varNames();
				while (res1.hasNext()) {
					String var = res1.next();
					if (var.equals("uri")) {
						uri = res.getResource(var).toString();
					} else if (var.equals("name")) {
						userName = res.getLiteral(var).toString();
					} else if (var.equals("id")) {
						id = res.getLiteral(var).toString();
					} else if (var.equals("loc")) {
						location = res.getLiteral(var).toString();
					} else if (var.equals("profile")) {
						image = res.getLiteral(var).toString();
					} else if (var.equals("des")) {
						description = res.getLiteral(var).toString();
					} else if (var.equals("visited")) {
						locationVisited = res.getLiteral(var).toString();
					} else if (var.equals("knows")) {
						contactPeople = res.getLiteral(var).toString();
					}
				}
				TwitterUser user = new TwitterUser(userName, id, location,
						image, description, locationVisited, contactPeople);
				user.setUri(uri);
				users.add(user);
			}
			query = queryString.asQuery();
			qe = QueryExecutionFactory.create(query, model);
			results = qe.execSelect();
			ResultSetFormatter.out(System.out, results, query);

			// Important - free up resources used running the query
			qe.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	public boolean userExists(String user) {
		boolean exists = false;
		InputStream in;
		user = user.replace("'", "");

		try {
			String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/userTripleStore.rdf";
			in = new FileInputStream(new File(folder + "userTripleStore.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model //
												// URIs are
			// absolute
			in.close();
			// Create a new query
			String queryStringOne = "PREFIX intelWeb: <tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#> "
					+ "SELECT ?id "
					+ "WHERE {"
					+ " ?x intelWeb:userId ?id. "
					+ "FILTER (?id='";
			String queryStringTwo = "')" + " }";

			ParameterizedSparqlString queryString = new ParameterizedSparqlString();
			queryString.append(queryStringOne);
			queryString.append(user);
			queryString.append(queryStringTwo);
			// System.out.println(queryString);
			Query query = queryString.asQuery();
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			if (results.hasNext()) {
				exists = true;
			} else {
				exists = false;
			}
			// Important - free up resources used running the query
			qe.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exists;
	}

	public boolean checkinExists(String visitor, String venue, String time) {
		boolean exists = false;
		InputStream in;
		venue = venue.replace("'", "");
		try {
			String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/venueTripleStore.rdf";
			in = new FileInputStream(new File(folder + "venueTripleStore.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model
												// URIs are
			// absolute
			in.close();

			// Create a new query
			String queryString = "PREFIX intelWeb: <tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#> "
					+ "SELECT distinct ?visitorName ?venueName ?checkinTime "
					+ "WHERE {"
					+ "?x intelWeb:nameOFVisitor ?visitorName . "
					+ "?x intelWeb:venueName ?venueName . "
					+ "?x intelWeb:checkinTime ?checkinTime ."
					+ "FILTER (?visitorName='"
					+ visitor
					+ "' "
					+ "&& ?venueName='"
					+ venue
					+ "'"
					+ "&& ?checkinTime='"
					+ time + "')" + " }";
			// System.out.println(queryString);
			Query query = QueryFactory.create(queryString);
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			if (results.hasNext()) {
				exists = true;
			} else {
				exists = false;
			}
			// Important - free up resources used running the query
			qe.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exists;
	}

	public ArrayList<Venue> queryVenues(String name) {
		ArrayList<Venue> venues = new ArrayList<Venue>();
		InputStream in;
		try {
			String xmlbase = "tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/venueTripleStore.rdf";

			in = new FileInputStream(new File(folder + "venueTripleStore.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model
												// URIs are
			// absolute
			in.close();
			name = name.replace("'", "");
			// Create a new query
			String queryStringOne = "PREFIX intelWeb: <tomcat.dcs.shef.ac.uk:8080/stucat033/Triple_store/bsgteam.rdfs#> "
					+ "SELECT (min(?x) as ?uri) ?venueName (min(?visitorName) as ?visitor) (min(?checkinTime) as ?time) "
					+ "(min(?venueDescription) as ?description) (group_concat(?venuePhoto) as ?photos) "
					+ "(min(?venueUrl) as ?url) (min(?venueAddress) as ?address) (group_concat(distinct ?venueCategory) as ?category) "
					+ "WHERE {"
					+ "?x intelWeb:nameOFVisitor ?visitorName . "
					+ "?x intelWeb:venueName ?venueName . "
					+ "?x intelWeb:checkinTime ?checkinTime ."
					+ "OPTIONAL {?x intelWeb:venuePhoto ?venuePhoto .}"
					+ "OPTIONAL {?x intelWeb:venueUrl ?venueUrl .}"
					+ "OPTIONAL {?x intelWeb:venueAddress ?venueAddress .}"
					+ "OPTIONAL {?x intelWeb:venueDescription ?venueDescription .}"
					+ "OPTIONAL {?x intelWeb:venueCategory ?venueCategory .}"
					+ "FILTER regex(?venueName,'^";
			String queryStringTwo = "','i')" + " }" + "GROUP BY ?venueName ";

			ParameterizedSparqlString queryString = new ParameterizedSparqlString();
			queryString.append(queryStringOne);
			queryString.append(name);
			queryString.append(queryStringTwo);

			Query query = queryString.asQuery();
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			String visitorName = null;
			String venueName = null;
			String venueUrl = null;
			String venueAddress = null;
			String venueDescription = null;
			String venuePhoto = null;
			String venueCategory = null;
			String checkinTime = null;
			String uri = null;

			while (results.hasNext()) {
				QuerySolution res = results.next();
				Iterator<String> res1 = res.varNames();
				while (res1.hasNext()) {
					String var = res1.next();
					if (var.equals("uri")) {
						uri = res.getResource(var).toString();
					} else if (var.equals("visitor")) {
						visitorName = res.getLiteral(var).toString();
					} else if (var.equals("venueName")) {
						venueName = res.getLiteral(var).toString();
					} else if (var.equals("url")) {
						venueUrl = res.getLiteral(var).toString();
					} else if (var.equals("address")) {
						venueAddress = res.getLiteral(var).toString();
					} else if (var.equals("description")) {
						venueDescription = res.getLiteral(var).toString();
					} else if (var.equals("photos")) {
						venuePhoto = res.getLiteral(var).toString();
					} else if (var.equals("category")) {
						venueCategory = res.getLiteral(var).toString();
					} else if (var.equals("time")) {
						checkinTime = res.getLiteral(var).toString();
					}
				}
				Venue venue = new Venue(visitorName, venueName, venueUrl,
						venueAddress, venueDescription, venuePhoto,
						venueCategory, checkinTime);
				venue.setUri(uri);
				venues.add(venue);
			}

			// System.out.println(users.get(0).getUserName());
			query = queryString.asQuery();
			qe = QueryExecutionFactory.create(query, model);
			results = qe.execSelect();
			ResultSetFormatter.out(System.out, results, query);

			// Important - free up resources used running the query
			qe.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return venues;
	}

	public static void main(String args[]) {
		Jena j = new Jena(
				"C:\\Users\\Solomon\\workspace\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\BSGteam\\Triple_store\\");
		// j.queryUsers("m");
		// System.out.println(j.checkinExists("soloistic1",
		// "St George\\'s Library", "Wed May 21 16:05:32 BST 2014"));
		// j.saveUser("me", "you", "", "", "", null, null);
		j.queryVenues("St George's");
	}
}