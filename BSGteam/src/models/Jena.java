package models;

import com.hp.hpl.jena.rdf.model.*;

public class Jena {
	public void create() {
		Model model = ModelFactory.createDefaultModel();
		String ns = new String("http://www.example.com/example#");

		// Create two Resources
		Resource john = model.createResource(ns + "John");
		Resource jane = model.createResource(ns + "Jane");

		// Create the 'hasBrother' Property declaration
		Property hasBrother = model.createProperty(ns, "hasBrother");

		// Associate jane to john through 'hasBrother'
		jane.addProperty(hasBrother, john);

		// Create the 'hasSister' Property declaration
		Property hasSister = model.createProperty(ns, "hasSister");

		// Associate john and jane through 'hasSister' with a Statement
		Statement sisterStmt = model.createStatement(john, hasSister, jane);
		model.add(sisterStmt);
		
	}

}
