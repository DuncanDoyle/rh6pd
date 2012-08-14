package rh6pd.test.examples.simpleFrameworkTest;

import java.io.File;

import junit.framework.Assert;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ClassPathResource;
import org.junit.Test;

import rh6pd.businessRules.Person;
import rh6pd.businessRules.Vehicle;
import rh6pd.test.AbstractProcessTest;

public class TestSampleBpmn extends AbstractProcessTest {
	@Test
	public void testUnderageDriverCar() {
		this.createNewSession("sample.bpmn");

		Vehicle vehicle = new Vehicle("Ford", "Fiesta", 1.0);
		Person person = new Person("Alice", 10, vehicle, 800);

		this.insertVar("person", person);
		this.insertVar("vehicle", vehicle);

		this.testProcess(null, null, "com.redhat.bpmn.insuranceQuote",
				"StartProcess", "Underage");
	}

	@Test
	public void testLargeEngineSize() {
		printBpmnErrors("sample.bpmn");
		this.createNewSession("sample.bpmn");

		Vehicle vehicle = new Vehicle("ford", "Fiesta", 2.0);
		Person person = new Person("Alice", 20, vehicle, 800);

		this.insertVar("person", person);
		this.insertVar("vehicle", vehicle);

		this.testProcess(null, null, "com.redhat.bpmn.insuranceQuote",
				"StartProcess", "Engine Size Fail");
	}

	@Test
	public void testWrongCarMake() {
		this.createNewSession("sample.bpmn");
		Vehicle vehicle = new Vehicle("For", "Fiesta", 1.0);
		Person person = new Person("Alice", 20, vehicle, 800);
		this.insertVar("person", person);
		this.insertVar("vehicle", vehicle);
		this.testProcess(null, null, "com.redhat.bpmn.insuranceQuote",
				"StartProcess", "Model Fail");
	}
	
	@Test
	public void testWithPremium(){
		this.createNewSession("sample.bpmn");
		Vehicle vehicle = new Vehicle("ford", "Fiesta", 1.0);
		Person person = new Person("Alice", 20, vehicle, 800);
		this.insertVar("person", person);
		this.insertVar("vehicle", vehicle);
		this.testProcess("CalculatePremium.drl", null, "com.redhat.bpmn.insuranceQuote",
				"StartProcess", "CalculatePremium");
	}
	
	@Test
	public void testWithoutPremium(){
		this.createNewSession("sample.bpmn");
		Vehicle vehicle = new Vehicle("ford", "Fiesta", 1.0);
		Person person = new Person("Alice", 40, vehicle, 800);
		this.insertVar("person", person);
		this.insertVar("vehicle", vehicle);
		this.testProcess("CalculatePremium.drl", null, "com.redhat.bpmn.insuranceQuote",
				"StartProcess", "CalculatePremium");
	}
}
