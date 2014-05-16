package models;

import java.io.*;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.sparql.vocabulary.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Jena {
	public void create() {
		// String ns = new String("http://xmlns.com/foaf/0.1/");
		Model testmodel = ModelFactory.createDefaultModel();
		File log = new File("vc3.rdf");

		String filePath = "tweet.rdfs";
		OntModel myModel = ModelFactory
				.createOntologyModel(OntModelSpec.RDFS_MEM);
		try {
			myModel.read(new FileInputStream(filePath), "");
		} catch (Exception e) {
		}
		
		// myModel.write(System.out);
		 ExtendedIterator<Individual> ontologyIterator =
		 myModel.listIndividuals();
		 while (ontologyIterator.hasNext()) {
		 Individual ontology = ontologyIterator.next();
		 //System.out.println(ontology.toString());
		 }
		 ExtendedIterator<OntClass> classIterator = myModel.listClasses();
		 while (classIterator.hasNext()) {
		 OntClass ontClass = classIterator.next();
		 Resource blabla = ontClass.asResource();
		 System.out.println(blabla.toString());
		 //System.out.println(ontClass.toString());
		 //System.out.println(ontClass.listProperties().toString());
		 //ontClass.getProperty("hasName");
//		 StmtIterator iter = blabla.listProperties();
//		 while (iter.hasNext())
//		 {
//			 System.out.println(iter.next().toString());
//		 }
		 }

		 System.out.println("________________");
		Resource ll = testmodel
				.createResource("http://tomcat.dcs.shef.ac.uk:8080/stucat033/users/Soloistic");
		testmodel.add(ll, FOAF.name, "Solomon");
		testmodel.add(ll, FOAF.based_near, "London");

		String userId = "#John";
		Resource user = testmodel.createResource(FOAF.Person + userId);
		user.addProperty(FOAF.name, "Peter Parker");
		user.addProperty(FOAF.based_near, "Sheffield");
		Statement workplaceStmt = testmodel.createStatement(user,
				FOAF.workplaceHomepage, "theverge.com");
		testmodel.add(workplaceStmt);

		userId = "#Mary";
		Resource user2 = testmodel.createResource(FOAF.Person + userId);
		user2.addProperty(FOAF.name, "Mary Jane");
		user2.addProperty(FOAF.based_near, "Sheffield");
		workplaceStmt = testmodel.createStatement(user2,
				FOAF.workplaceHomepage, "theverge.com");
		testmodel.add(workplaceStmt);
		testmodel.write(System.out, "N-TRIPLE");

		try {
			testmodel.write(new FileOutputStream(log), "");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// final String inputFileName = "vc1.rdf";
		// Model model = ModelFactory.createDefaultModel();
		// InputStream in = FileManager.get().open(inputFileName);
		// if (in == null) {
		// throw new IllegalArgumentException("File: " + inputFileName
		// + " not found");
		// }
		// // read the RDF/XML file
		// model.read(in, "");
		// // write it to standard out
		// try {
		// PrintWriter out = new PrintWriter(new FileWriter(log));
		// model.write(out);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public void query() {
		InputStream in;
		try {
			in = new FileInputStream(new File("vc3.rdf"));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createMemModelMaker()
					.createDefaultModel();
			model.read(in, null); // null base URI, since model URIs are
									// absolute
			in.close();

			// Create a new query
			String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
					+ "SELECT ?person ?name ?based_near " + "WHERE {"
					+ " ?person foaf:name ?name . "
					+ "?person foaf:based_near ?based_near ." + " }";
			Query query = QueryFactory.create(queryString);
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			int i=1;
			while (results.hasNext())
			{
				System.out.println(i);
				QuerySolution res = results.next();
				Iterator<String> res1 = res.varNames();
				while (res1.hasNext())
				{
					String var = res1.next();
					if(var.equals("person"))
					{
						System.out.println(res.getResource(var));
					}
					else
					{
						System.out.println(res.getLiteral(var));
					}
				}
				i++;
			}
			
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
	}

	public void queryDBPedia() {
		String queryString = "PREFIX 	rdfs: 	<http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX 	type: 	<http://dbpedia.org/class/yago/>"
				+ "PREFIX 	prop: 	<http://dbpedia.org/property/>"
				+ "SELECT 	DISTINCT 	?country_name 	?country 	"
				+ "WHERE 	{"
				+ " 	?country 	a 	type:LandlockedCountries. 	"
				+ " 	?country 	rdfs:label 	?country_name. 	"
				+ " 	?country 	prop:populationEstimate 	?population 	."
				+ " 	FILTER 	(?population 	> 	15000000  	"
				+ " 	&& 	langMatches(lang(?country_name), 	'EN')) 	." + "}";
		JenaToEndPoint jdp = new JenaToEndPoint();
		jdp.queryEndPoint(queryString, "http://dbpedia.org/sparql");
	}

	public class JenaToEndPoint {
		public void queryEndPoint(String queryString, String endPoint) {
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.sparqlService(
					endPoint, query);
			try {
				ResultSet results = qexec.execSelect();
				ResultSetFormatter.out(System.out, results, query);
			} finally {
				qexec.close();
			}
		}
	}

	public static void main(String[] args) {
		Jena jena = new Jena();
		jena.create();
		jena.query();
		// jena.queryDBPedia();
	}

}
