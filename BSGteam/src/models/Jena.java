package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

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
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

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

	public void saveUser(String userName, String id, String location,
			String image, String description, String locationVisited,
			String contactPeople) {

		if (!userExists(id)) {
			Model m = ModelFactory.createDefaultModel();
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/result.rdf/";

			// String uri = xmlbase + "#" + id;
			// create Resource for twitter use
			Resource user = m
					.createResource(xmlbase + id, Ontology.twitterUser);

			// add to properties to twitterUser
			user.addProperty(FOAF.name, userName)
					.addProperty(Ontology.USERID, id)
					.addProperty(Ontology.LOCATION, location)
					.addProperty(FOAF.img, image)
					.addProperty(Ontology.description, description)
					.addProperty(Ontology.locationVisited, locationVisited)
					.addProperty(FOAF.knows, contactPeople);

			m.setNsPrefix("intelligentWeb", Ontology.NS);
			m.setNsPrefix("foaf", FOAF.NS);
			// now write the model in XML form to a file

			FileOutputStream userRDF = null;
			try {
				userRDF = new FileOutputStream(folder + "result.rdf", true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// OutputStream out = (OutputStream) camera_File;

			m.write(userRDF, "TURTLE", xmlbase);
		}
	}

	public void saveVenue(String visitorName, String venueName, String photo,
			String category, String address, String description, String url,
			String checkinTime) {

		if (!checkinExists(visitorName, venueName, checkinTime)) {
			Model m = ModelFactory.createDefaultModel();
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/venueResult.rdf/";
			// create Resource for twitter use
			Resource venue = m.createResource(Ontology.venue);
			// add to properties to twitterUser
			venue.addProperty(Ontology.nameOFVisitor, visitorName)
					.addProperty(Ontology.venueName, venueName)
					.addProperty(Ontology.venueUrl, url)
					.addProperty(Ontology.venueAddress, address)
					.addProperty(Ontology.venueDescription, description)
					.addProperty(Ontology.venuePhoto, photo)
					.addProperty(Ontology.venueCategory, category)
					.addProperty(Ontology.checkinTime, checkinTime);
			m.setNsPrefix("intelligentWeb", Ontology.NS);
			// now write the model in XML form to a file
			FileOutputStream venueRDF = null;
			try {
				venueRDF = new FileOutputStream(folder + "venueResult.rdf",
						true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			m.write(venueRDF, "TURTLE", xmlbase);
		}
	}

	public ArrayList<TwitterUser> queryUsers(String userId) {
		ArrayList<TwitterUser> users = new ArrayList<TwitterUser>();
		InputStream in;
		try {
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";

			in = new FileInputStream(new File(folder + "result.rdf"));

			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model
												// URIs are
			// absolute
			in.close();

			// Create a new query
			String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
					+ "PREFIX intelWeb: <https://sites.google.com/site/sheffieldbash/home/web2.rdfs#> "
					+ "SELECT ?userName ?id ?location ?description "
					+ "WHERE {" + " ?x foaf:name ?userName . "
					+ " ?x intelWeb:userId ?id. "
					+ "?x intelWeb:location ?location ."
					+ "?x foaf:img ?image ."
					+ "?x intelWeb:description ?description ."
					+ "?x intelWeb:locationVisited ?locationVisited ."
					+ "?x foaf:knows ?contactPeople . " + "FILTER regex(?id,'^"
					+ userId + "','i')" + " }";
			// System.out.println(queryString);
			Query query = QueryFactory.create(queryString);
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			String userName = null;
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
					if (var.equals("x")) {
						// System.out.println(res.getResource(var));
					} else if (var.equals("userName")) {
						userName = res.getLiteral(var).toString();
					} else if (var.equals("id")) {
						id = res.getLiteral(var).toString();
					} else if (var.equals("location")) {
						location = res.getLiteral(var).toString();
					} else if (var.equals("image")) {
						image = res.getLiteral(var).toString();
					} else if (var.equals("description")) {
						description = res.getLiteral(var).toString();
					} else if (var.equals("locationVisited")) {
						locationVisited = res.getLiteral(var).toString();
					} else if (var.equals("contactPeople")) {
						contactPeople = res.getLiteral(var).toString();
					}

				}
				TwitterUser user = new TwitterUser(userName, id, location,
						image, description, locationVisited, contactPeople);
				users.add(user);
			}

			// System.out.println(users.get(0).getUserName());
			query = QueryFactory.create(queryString);
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
		try {
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";
			in = new FileInputStream(new File(folder + "result.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model //
												// URIs are
			// absolute
			in.close();
			// Create a new query
			String queryString = "PREFIX intelWeb: <https://sites.google.com/site/sheffieldbash/home/web2.rdfs#> "
					+ "SELECT ?id "
					+ "WHERE {"
					+ " ?x intelWeb:userId ?id. "
					+ "FILTER (?id='" + user + "')" + " }";
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

	public boolean checkinExists(String visitor, String venue, String time) {
		boolean exists = false;
		InputStream in;
		try {
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";
			in = new FileInputStream(new File(folder + "venueResult.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model
												// URIs are
			// absolute
			in.close();

			// Create a new query
			String queryString = "PREFIX intelWeb: <https://sites.google.com/site/sheffieldbash/home/web2.rdfs#> "
					+ "SELECT ?visitorName ?venueName ?checkinTime "
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
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";
			in = new FileInputStream(new File(folder + "venueResult.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createDefaultModel();
			model.read(in, xmlbase, "TURTLE"); // null base URI, since model
												// URIs are
			// absolute
			in.close();

			// Create a new query
			String queryString = "PREFIX intelWeb: <https://sites.google.com/site/sheffieldbash/home/web2.rdfs#> "
					+ "SELECT ?visitorName ?venueName ?checkinTime ?venueDescription "
					+ "WHERE {"
					+ "?x intelWeb:nameOFVisitor ?visitorName . "
					+ "?x intelWeb:venueName ?venueName . "
					+ "?x intelWeb:venueUrl ?venueUrl ."
					+ "?x intelWeb:venueAddress ?venueAddress ."
					+ "?x intelWeb:venueDescription ?venueDescription ."
					+ "?x intelWeb:venuePhoto ?venuePhoto ."
					+ "?x intelWeb:venueCategory ?venueCategory ."
					+ "?x intelWeb:checkinTime ?checkinTime ."
					+ "FILTER regex(?venueName,'^" + name + "','i')" + " }";
			// System.out.println(queryString);
			Query query = QueryFactory.create(queryString);
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

			while (results.hasNext()) {
				QuerySolution res = results.next();
				Iterator<String> res1 = res.varNames();
				while (res1.hasNext()) {
					String var = res1.next();
					if (var.equals("x")) {
						// System.out.println(res.getResource(var));
					} else if (var.equals("visitorName")) {
						visitorName = res.getLiteral(var).toString();
					} else if (var.equals("venueName")) {
						venueName = res.getLiteral(var).toString();
					} else if (var.equals("venueUrl")) {
						venueUrl = res.getLiteral(var).toString();
					} else if (var.equals("venueAddress")) {
						venueAddress = res.getLiteral(var).toString();
					} else if (var.equals("venueDescription")) {
						venueDescription = res.getLiteral(var).toString();
					} else if (var.equals("venuePhoto")) {
						venuePhoto = res.getLiteral(var).toString();
					} else if (var.equals("venueCategory")) {
						venueCategory = res.getLiteral(var).toString();
					} else if (var.equals("checkinTime")) {
						checkinTime = res.getLiteral(var).toString();
					}

				}
				Venue venue = new Venue(visitorName, venueName, venueUrl,
						venueAddress, venueDescription, venuePhoto,
						venueCategory, checkinTime);
				venues.add(venue);
			}

			// System.out.println(users.get(0).getUserName());
			query = QueryFactory.create(queryString);
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
		j.queryUsers("e");
		System.out.println(j.checkinExists("soloistic1",
				"St George\\'s Library", "Wed May 21 16:05:32 BST 2014"));
		j.queryVenues("S");
	}
}