package no.plasmid.blopp.migration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import no.plasmid.blopp.domain.EdgeClass;
import no.plasmid.blopp.domain.VertexClass;
import no.plasmid.blopp.migration.example.ExampleDataMigration;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;
import no.plasmid.blopp.orientdb.OrientDBTransactionlessWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.orientechnologies.orient.core.metadata.schema.OType;

public class MigrationRunner {

  private final static Logger LOG = LoggerFactory.getLogger(MigrationRunner.class);

  private final List<Class<? extends Migration>> classes = new ArrayList<Class<? extends Migration>>();

	public void upgrade(OrientDBTransactionFactory transactionFactory, boolean shouldCreateExampleData) {
		OrientDBTransactionlessWrapper transactionLess = transactionFactory.getTransactionLess();
		try {
			runInitialMigration(transactionFactory);
		
			findMigrationClasses();
			sortMigrationClasses();
			applyMigrationClasses(transactionFactory);
			
      if (shouldCreateExampleData) {
        LOG.debug("Creating example data");
        applyExampleDataClasses(transactionFactory);
      }
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("Could not run migrations", e);
		} finally {
			transactionLess.finish();
		}
	}

	private void runInitialMigration(OrientDBTransactionFactory transactionFactory) {
		LOG.debug("Initial migration");
    VertexClass.findOrCreate("FinishedMigration");  //Denne må først, ellers går det åt skogen
    if (!shouldRun("Initial migration")) { return; }
		
    //Sequence for vertex id
    transactionFactory.getTransactionLess().createSequence("vIdSeq");
    
    //Felter som er felles for alt
    VertexClass vClass = VertexClass.findOrCreate("V");
    vClass.addProperty("vId", OType.STRING, true, true);
    vClass.addProperty("name", OType.STRING, true, false);
    vClass.addProperty("urn", OType.STRING, false, false);

    EdgeClass eClass = EdgeClass.findOrCreate("E");

    //Register the migration as run
    new FinishedMigration(OrientDBTransactionlessWrapper.getInstance().createVertex(FinishedMigration.class, "Initial migration"));
	}

  @SuppressWarnings("unchecked")
	private void findMigrationClasses() throws ClassNotFoundException {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);

    scanner.addIncludeFilter(new AssignableTypeFilter(Migration.class));

    for (BeanDefinition bd : scanner.findCandidateComponents("no.plasmid.blopp")) {
    	classes.add((Class<? extends Migration>) Class.forName(bd.getBeanClassName()));
    }
	}

	private void sortMigrationClasses() {
    Collections.sort(classes, new Comparator<Class<? extends Migration>>() {
      @Override
      /**
       * Sorts migrations first by type (Normal migrations before ExampleDataMigrations) and then by
       * class.getSimpleName() (which amounts to ascending by date).
       */
      public int compare(Class<? extends Migration> o1, Class<? extends Migration> o2) {
//          if (ExampleDataMigration.class.isAssignableFrom(o1) && !ExampleDataMigration.class.isAssignableFrom(o2))
//              return 1;
//          if (!ExampleDataMigration.class.isAssignableFrom(o1) && ExampleDataMigration.class.isAssignableFrom(o2))
//              return -1;

          return o1.getSimpleName().compareTo(o2.getSimpleName());
      }
  });
	}

  private void applyMigrationClasses(OrientDBTransactionFactory transactionFactory) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    for (Class<? extends Migration> theClass : classes) {
        if (ExampleDataMigration.class.isAssignableFrom(theClass) || !shouldRun(theClass)) { continue; }
        applyClass(theClass, transactionFactory);
    }
  }

  private void applyExampleDataClasses(OrientDBTransactionFactory transactionFactory) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    for (Class<? extends Migration> theClass : classes) {
        if (!ExampleDataMigration.class.isAssignableFrom(theClass) || !shouldRun(theClass)) { continue; }
        applyClass(theClass, transactionFactory);
    }
  }

  private void applyClass(Class<? extends Migration> theClass, OrientDBTransactionFactory transactionFactory) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
  	//Instantiate and run the migration
    Migration migration = theClass.getConstructor(OrientDBTransactionFactory.class).newInstance(transactionFactory);
    LOG.debug(migration.getDescription());
    System.out.println(migration.getDescription());
    migration.upgrade();
    //Register the migration as run
    new FinishedMigration(OrientDBTransactionlessWrapper.getInstance().createVertex(FinishedMigration.class, theClass.getSimpleName()));
  }
  
	private boolean shouldRun(Class<? extends Migration> theClass) {
	  return shouldRun(theClass.getSimpleName());
	}
	
	private boolean shouldRun(String className) {
	  return !(OrientDBTransactionlessWrapper.getInstance().findVertexInstances(FinishedMigration.class, "name", className).iterator().hasNext());
	}

	
}
