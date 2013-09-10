package at.linuxhacker.xstreamtest;


import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.mxp1.MXParser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.binary.BinaryStreamWriter;
import com.thoughtworks.xstream.io.copy.HierarchicalStreamCopier;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.io.xml.XppReader;

public class Test {
	public static void main(String[] args) throws Exception {
		XStream xstream = new XStream( );
		
		xstream.alias( "person", Person.class );
		xstream.alias( "phonenumber", PhoneNumber.class );
		
		ArrayList<Person> personList = new ArrayList<Person>( );
		Person joe = new Person( "joe", "Walness" );
		joe.setPhone( new PhoneNumber( 123, "91919-19191" ) );
		joe.setFax( new PhoneNumber( 676, "12312-89383" ) );

		Person jim = new Person( "jim", "corner" );
		jim.setPhone( new PhoneNumber( 456, "4826-2323" ) );
		jim.setFax( new PhoneNumber( 928, "19833-02827" ) );

		personList.add( joe );
		personList.add( jim );
		String xml = xstream.toXML(  personList );
		System.out.println( xml );

		XStream xsJson = new XStream( new JsonHierarchicalStreamDriver( ) );
		System.out.println( xsJson.toXML( personList ) );

		StringWriter xmlStringWriter = new StringWriter( );
		xstream.marshal( personList, new CompactWriter( xmlStringWriter ) );
		xmlStringWriter.flush( );
		xml = xmlStringWriter.getBuffer( ).toString( );
		
		StringReader stringReader = new StringReader( xml );
		//XppReader xppReader = new XppReader( stringReader, new MXParser( ) );
		HierarchicalStreamReader hierarchicalStreamReader = new Xpp3Driver( ).createReader( stringReader );

		StringWriter stringWriter = new StringWriter( );
		HierarchicalStreamWriter hierarchicalStreamWriter = new JsonWriter( stringWriter );
		
		HierarchicalStreamCopier hierarchicalStreamCopier = new HierarchicalStreamCopier( );
		hierarchicalStreamCopier.copy( hierarchicalStreamReader, hierarchicalStreamWriter );
		hierarchicalStreamWriter.flush( );
		String test = stringWriter.getBuffer( ).toString( );
		System.out.println( test );
	}
	
}
