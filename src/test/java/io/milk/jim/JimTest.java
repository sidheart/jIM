package io.milk.jim;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class JimTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JimTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JimTest.class );
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}

