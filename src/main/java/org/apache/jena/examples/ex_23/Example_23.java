package org.apache.jena.examples.ex_23;

import java.io.InputStream;
import java.util.Iterator;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphListener;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.util.graph.GraphListenerBase;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.tdb.TDBLoader;
import com.hp.hpl.jena.tdb.base.file.Location;
import com.hp.hpl.jena.tdb.store.DatasetGraphTDB;
import com.hp.hpl.jena.util.FileManager;

public class Example_23 {

    public static void main(String[] args) {
        FileManager fm = FileManager.get();
        fm.addLocatorClassLoader(Example_23.class.getClassLoader());
        InputStream in = fm.open("org/apache/jena/examples/ex_20/data.nt");

        Location location = new Location ("tmp/TDB");
        DatasetGraphTDB dsg = TDBFactory.createDatasetGraph(location);

        GraphListener listener = new MyListener();
        dsg.getDefaultGraph().getEventManager().register(listener);
        Iterator<Node> iter = dsg.listGraphNodes();
        while ( iter.hasNext() ) {
        	Graph graph = dsg.getGraph(iter.next());
        	graph.getEventManager().register(listener);
        }

        TDBLoader.load(dsg, in, false);        

        dsg.close();
    }

    static class MyListener extends GraphListenerBase implements GraphListener {
		@Override protected void addEvent(Triple triple) { 
			System.out.println(triple); 
		}
		@Override protected void deleteEvent(Triple triple) {
			System.out.println(triple);
		}
    }

}
