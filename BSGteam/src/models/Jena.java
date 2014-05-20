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
	// static String filePath = "tweet.rdfs";
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

		Model m = ModelFactory.createDefaultModel();
		String xmlbase = "https://sites.google.com/site/sheffieldbash/home/result.rdf/";

		// String uri = xmlbase + "#" + id;
		// create Resource for twitter use
		Resource user = m.createResource(xmlbase + id, Ontology.twitterUser);

		// add to properties to twitterUser
		user.addProperty(FOAF.name, userName).addProperty(Ontology.USERID, id)
				.addProperty(Ontology.LOCATION, location)
				.addProperty(FOAF.img, image)
				.addProperty(Ontology.description, description)
				.addProperty(Ontology.locationVisited, locationVisited)
				.addProperty(FOAF.knows, contactPeople);

		m.setNsPrefix("intelligentWeb", Ontology.NS);
		m.setNsPrefix("foaf", FOAF.NS);
		// now write the model in XML form to a file

		FileOutputStream infor = null;
		try {
			infor = new FileOutputStream(folder+"result.rdf", true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// OutputStream out = (OutputStream) camera_File;

		m.write(infor, "TURTLE", xmlbase);

	}

	public ArrayList<TwitterUser> query(String name) {
		ArrayList<TwitterUser> users = new ArrayList<TwitterUser>();
		InputStream in;
		try {
			String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";

			in = new FileInputStream(new File(folder+"result.rdf"));

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
					+ name + "','i')" + " }";
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

	public void venue(String visitorName, String venueName, String photo,
			String category, String address, String description, String url,
			String time) {

		Model m = ModelFactory.createDefaultModel();
		String xmlbase = "https://sites.google.com/site/sheffieldbash/home/web2.rdfs/";

		// create Resource for twitter use
		Resource venue = m.createResource(Ontology.venue);

		// add to properties to twitterUser
		venue.addProperty(Ontology.nameOFVisitor, visitorName)
				.addProperty(Ontology.venueName, venueName)
				.addProperty(Ontology.venuePhoto, photo)
				.addProperty(Ontology.venueCategory, category)
				.addProperty(Ontology.venueAddress, address)
				.addProperty(Ontology.venueDescription, description)
				.addProperty(Ontology.venueUrl, url);

		m.setNsPrefix("intelligentWeb", Ontology.NS);

		// now write the model in XML form to a file
		FileOutputStream infor = null;
		try {
			infor = new FileOutputStream(folder+"VenueResult.rdf", true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		m.write(infor, "TURTLE", xmlbase);

	}

	public static void main(String args[]) {
		Jena j = new Jena("C:\\Users\\Solomon\\workspace\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\BSGteam\\Triple_store\\");
		
		// j.safeUserInfor("bash", "123", "london", "man", "com", "job",
		// "none");
		// j.safeUserInfor("mmmm", "12uitiut3", "london", "man2", "com2",
		// "job2", "none");
		j.query("a");

	}
}