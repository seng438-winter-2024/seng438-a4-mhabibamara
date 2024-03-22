/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------------
 * DataUtilitiesTest.java
 * ----------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Mar-2005 : Version 1 (DG);
 * 28-Jan-2009 : Added tests for equal(double[][], double[][]) method (DG);
 * 28-Jan-2009 : Added tests for clone(double[][]) (DG);
 * 04-Feb-2009 : Added tests for new calculateColumnTotal/RowTotal methods (DG);
 *
 */

package org.jfree.data;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Some tests for the {@link DataUtilities} class.
 */
public class DataUtilitiesTest {
	
	public Mockery mockingContext;
	private Values2D values;
	private KeyedValues values2;
	
	@BeforeClass 
	public static void setUpBeforeClass() throws Exception {}
	
    @Before
    public void setUp() {
    	
    	// setup
	     mockingContext = new Mockery();
	     
	     values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() 
	     {
	         {
	             one(values).getRowCount();
	             will(returnValue(3));
	             
	             one(values).getColumnCount();
	             will(returnValue(3));
	             
	             // [ 2, -3 , -6]
	             // [ 3, 2 , -9]
	             // [ 4, 9 , -12]
	             
	             one(values).getValue(0, 0);
	             will(returnValue(2.0));
	             
	             one(values).getValue(0, 1);
	             will(returnValue(-3.0));
	             
	             one(values).getValue(0, 2);
	             will(returnValue(-6.0));

	             one(values).getValue(1, 0);
	             will(returnValue(3.0));
	             
	             one(values).getValue(1, 1);
	             will(returnValue(2.0));
	             
	             one(values).getValue(1, 2);
	             will(returnValue(-9.0));
	             
	             one(values).getValue(2, 0);
	             will(returnValue(4.0));
	             
	             one(values).getValue(2, 1);
	             will(returnValue(9.0));
	             
	             one(values).getValue(2, 2);
	             will(returnValue(-12.0));

	         }
	     }
	     );  	
	     
	     Mockery context1 = new Mockery();
			values2 = context1.mock(KeyedValues.class);
			
			List<Integer> keys = new ArrayList<>();
			Collections.addAll(keys, 0, 1, 2);
			
			context1.checking(new Expectations() {
		        {
		        	//getItemCount
		        	allowing(values2).getItemCount();
		        	will(returnValue(keys));
		        	
		        	//getKeys
		        	allowing(values2).getKeys();
		        	will(returnValue(keys));
		        	
		        	//getIndex
		        	allowing(values2).getIndex(0);
		        	will(returnValue(0));
		        	allowing(values2).getIndex(1);
		        	will(returnValue(1));
		        	allowing(values2).getIndex(2);
		        	will(returnValue(2));
		        	
		        	//getKey
		        	allowing(values2).getKey(0);
		        	will(returnValue(0));
		        	allowing(values2).getKey(1);
		        	will(returnValue(1));
		        	allowing(values2).getKey(2);
		        	will(returnValue(2));
		        	
		        	//getValue
		        	allowing(values2).getValue(0);
		        	will(returnValue(5));
		        	allowing(values2).getValue(1);
		        	will(returnValue(9));
		        	allowing(values2).getValue(2);
		        	will(returnValue(2));
		        	
		        }
		    });
    }

    /**
     * Tests the createNumberArray2D() method.
     */
    @Test
    public void testCreateNumberArray2D() {
        double[][] d = new double[2][];
        d[0] = new double[] {1.1, 2.2, 3.3, 4.4};
        d[1] = new double[] {1.1, 2.2, 3.3, 4.4, 5.5};
        Number[][] n = DataUtilities.createNumberArray2D(d);
        assertEquals(2, n.length);
        assertEquals(4, n[0].length);
        assertEquals(5, n[1].length);
    }

    private static final double EPSILON = 0.000000001;

    /**
     * Some checks for the calculateColumnTotal() method.
     */
    @Test
    public void testCalculateColumnTotal() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(new Double(1.0), "R0", "C0");
        table.addValue(new Double(2.0), "R0", "C1");
        table.addValue(new Double(3.0), "R1", "C0");
        table.addValue(new Double(4.0), "R1", "C1");
        assertEquals(4.0, DataUtilities.calculateColumnTotal(table, 0), EPSILON);
        assertEquals(6.0, DataUtilities.calculateColumnTotal(table, 1), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(2.0, DataUtilities.calculateColumnTotal(table, 1), EPSILON);
    }

    /**
     * Some checks for the calculateColumnTotal() method.
     */
    @Test
    public void testCalculateColumnTotal2() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(new Double(1.0), "R0", "C0");
        table.addValue(new Double(2.0), "R0", "C1");
        table.addValue(new Double(3.0), "R1", "C0");
        table.addValue(new Double(4.0), "R1", "C1");
        assertEquals(4.0, DataUtilities.calculateColumnTotal(table, 0,
                new int[] {0, 1}), EPSILON);
        assertEquals(1.0, DataUtilities.calculateColumnTotal(table, 0,
                new int[] {0}), EPSILON);
        assertEquals(3.0, DataUtilities.calculateColumnTotal(table, 0,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtilities.calculateColumnTotal(table, 0,
                new int[] {}), EPSILON);

        assertEquals(6.0, DataUtilities.calculateColumnTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(2.0, DataUtilities.calculateColumnTotal(table, 1,
                new int[] {0}), EPSILON);
        assertEquals(4.0, DataUtilities.calculateColumnTotal(table, 1,
                new int[] {1}), EPSILON);

        table.setValue(null, "R1", "C1");
        assertEquals(2.0, DataUtilities.calculateColumnTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(0.0, DataUtilities.calculateColumnTotal(table, 1,
                new int[] {1}), EPSILON);
    }

    /**
     * Some checks for the calculateRowTotal() method.
     */
    @Test
    public void testCalculateRowTotal() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(new Double(1.0), "R0", "C0");
        table.addValue(new Double(2.0), "R0", "C1");
        table.addValue(new Double(3.0), "R1", "C0");
        table.addValue(new Double(4.0), "R1", "C1");
        assertEquals(3.0, DataUtilities.calculateRowTotal(table, 0), EPSILON);
        assertEquals(7.0, DataUtilities.calculateRowTotal(table, 1), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(3.0, DataUtilities.calculateRowTotal(table, 1), EPSILON);
    }

    /**
     * Some checks for the calculateRowTotal() method.
     */
    @Test
    public void testCalculateRowTotal2() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(new Double(1.0), "R0", "C0");
        table.addValue(new Double(2.0), "R0", "C1");
        table.addValue(new Double(3.0), "R1", "C0");
        table.addValue(new Double(4.0), "R1", "C1");
        assertEquals(3.0, DataUtilities.calculateRowTotal(table, 0,
                new int[] {0, 1}), EPSILON);
        assertEquals(1.0, DataUtilities.calculateRowTotal(table, 0,
                new int[] {0}), EPSILON);
        assertEquals(2.0, DataUtilities.calculateRowTotal(table, 0,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtilities.calculateRowTotal(table, 0,
                new int[] {}), EPSILON);

        assertEquals(7.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(3.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {0}), EPSILON);
        assertEquals(4.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {}), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(3.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(0.0, DataUtilities.calculateRowTotal(table, 1,
                new int[] {1}), EPSILON);
    }

    /**
     * Some tests for the equal(double[][], double[][]) method.
     */
    @Test
    public void testEqual() {
        assertTrue(DataUtilities.equal(null, null));
        
        double[][] a = new double[5][];
        double[][] b = new double[5][];
        assertTrue(DataUtilities.equal(a, b));

        a = new double[4][];
        assertFalse(DataUtilities.equal(a, b));
        b = new double[4][];
        assertTrue(DataUtilities.equal(a, b));

        a[0] = new double[6];
        assertFalse(DataUtilities.equal(a, b));
        b[0] = new double[6];
        assertTrue(DataUtilities.equal(a, b));

        a[0][0] = 1.0;
        assertFalse(DataUtilities.equal(a, b));
        b[0][0] = 1.0;
        assertTrue(DataUtilities.equal(a, b));

        a[0][1] = Double.NaN;
        assertFalse(DataUtilities.equal(a, b));
        b[0][1] = Double.NaN;
        assertTrue(DataUtilities.equal(a, b));

        a[0][2] = Double.NEGATIVE_INFINITY;
        assertFalse(DataUtilities.equal(a, b));
        b[0][2] = Double.NEGATIVE_INFINITY;
        assertTrue(DataUtilities.equal(a, b));

        a[0][3] = Double.POSITIVE_INFINITY;
        assertFalse(DataUtilities.equal(a, b));
        b[0][3] = Double.POSITIVE_INFINITY;
        assertTrue(DataUtilities.equal(a, b));

        a[0][4] = Double.POSITIVE_INFINITY;
        assertFalse(DataUtilities.equal(a, b));
        b[0][4] = Double.NEGATIVE_INFINITY;
        assertFalse(DataUtilities.equal(a, b));
        b[0][4] = Double.POSITIVE_INFINITY;
        assertTrue(DataUtilities.equal(a, b));
    }

    /**
     * Some tests for the clone() method.
     */
    @Test
    public void testClone() {
        double[][] a = new double[1][];
        double[][] b = DataUtilities.clone(a);
        assertTrue(DataUtilities.equal(a, b));
        a[0] = new double[] { 3.0, 4.0 };
        assertFalse(DataUtilities.equal(a, b));
        b[0] = new double[] { 3.0, 4.0 };
        assertTrue(DataUtilities.equal(a, b));

        a = new double[2][3];
        a[0][0] = 1.23;
        a[1][1] = Double.NaN;
        b = DataUtilities.clone(a);
        assertTrue(DataUtilities.equal(a, b));

        a[0][0] = 99.9;
        assertFalse(DataUtilities.equal(a, b));
        b[0][0] = 99.9;
        assertTrue(DataUtilities.equal(a, b));
    }
    
    /**
   	 * This test will pass a null object to calculateColumnTotal() with a column number of 0.
   	 * 
   	 * Expects: An IllegalArgumentException is thrown.
   	 */
       @Test (expected = IllegalArgumentException.class)
   	public void calculateColumnTotalWithNullDataTable() throws IllegalArgumentException{   
   	    DataUtilities.calculateColumnTotal(null, 0);
       }
       
        
       /**
   	 * This test will pass a valid data table object to calculateColumnTotal() with a
   	 * column number of 10, which is greater then number of columns in the data table.
   	 * 
   	 * Expects: An IllegalArgumentException is thrown.
   	 */
//   	@Test (expected = IllegalArgumentException.class)
//   	public void calculateColumnTotalForOutOfRangeColumnIndex() throws IllegalArgumentException{	
//   		
//   		double result = DataUtilities.calculateColumnTotal(values, 10);		
//   		assertEquals("Sum of a out of bounds column index should be 0", 0.0 , result, .000000001d);
//   	}
   	
       
     /**
   	 * This test will simulate passing a negative number for the column. 
   	 */
   	@Test
   	public void calculateColumnTotalForNegativeColumnIndexTest() {
   		Mockery mockingContext = new Mockery();
   		final Values2D values = mockingContext.mock(Values2D.class);

   		mockingContext.checking(new Expectations() {
   			{
   				one(values).getRowCount();
   				will(returnValue(2));

   				one(values).getValue(0, -1);
   				will(returnValue(5.0));

   				one(values).getValue(1, -1);
   				will(returnValue(10.0));
   			}
   		});

   		try {
   			DataUtilities.calculateColumnTotal(values, -1);
   		} catch (Exception e) {
   			assertEquals("The exception thrown type is IllegalArgumentException", IllegalArgumentException.class, e.getClass());
   		}
   	}
   	
   	/**
   	 * This test will pass a valid data table object to calculateColumnTotal() with a column number of -2.
   	 * 
   	 * Expects: An IllegalArgumentException is thrown.
   	 */
//   	@Test (expected = IllegalArgumentException.class)
//   	public void calculateColumnTotalForNegativeColumnIndex() throws IllegalArgumentException{
//   		double result = DataUtilities.calculateColumnTotal(values, -2);	
//   		assertEquals("Sum of a negative column index should be 0", 0.0 , result, .000000001d);
//   	}
   	
   	/**
   	 * This test will pass a valid data table object to calculateColumnTotal() with a column number of 0.
   	 * 
   	 * This will test the sum of column values, for the first column.
   	 * 
   	 * Expects: The correct sum for the given column.
   	 */
   	@Test
   	public void calculateColumnTotalForFirstIndex() {
   		double result = DataUtilities.calculateColumnTotal(values, 0);
   		assertEquals(9.0, result, .000000001d);
   	}
   	
   	/**
   	 * This test will pass a valid data table object to calculateColumnTotal() with a column number of 1.
   	 * 
   	 * This will test the sum of column values, for a column between the 2 boundary columns.
   	 * 
   	 * Expects: The correct sum for the given column.
   	 */
   	@Test
   	public void calculateColumnTotalForMiddleIndex() {
   		double result = DataUtilities.calculateColumnTotal(values, 1);
   		assertEquals(8.0, result, .000000001d);
   	}
   	
   	
   	/**
   	 * This test will pass a valid data table object to calculateColumnTotal() with a column number of 2.
   	 * 
   	 * This will test the sum of column values, for the final column.
   	 * 
   	 * Expects: The correct sum for the given column.
   	 */
   	@Test
   	public void calculateColumnTotalForFinalIndex() {
   		double result = DataUtilities.calculateColumnTotal(values, 2);
   		assertEquals(-27.0, result, .000000001d);
   	}

   	@Test
   	public void calculateColumnTotalWithValidRows() {		
   		int[] validRows = {0, 1, 2};	
   		double result = DataUtilities.calculateColumnTotal(values, 0, validRows);	
   		assertEquals(9.0, result, .000000001d);
   	}
   	
   	@Test
   	public void calculateColumnTotalWithNoValidRows() {		
   		int[] validRows = {};	
   		double result = DataUtilities.calculateColumnTotal(values, 0, validRows);	
   		assertEquals(0.0, result, .000000001d);
   	}
   	
	/**
	 * This test will pass a null object to calculateRowTotal() with a row number of
	 * 1.
	 * 
	 * Expects: An IllegalArgumentException is thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateRowTotalWithNullDataObject() {
		DataUtilities.calculateRowTotal(null, 1);
	}

	/**
	 * This test will simulate passing a null object to calculateRowTotal().
	 */
	@Test
	public void calculateRowTotalNullCheck() { 
		try {
			final Values2D value = null; 
			final int rowNumber= 0; 
			final int[] Columns = { 0 }; 

			DataUtilities.calculateRowTotal(value, rowNumber,Columns);

		} catch (Exception e) {
			assertEquals("The exception thrown type is IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}
	/**
	 * This test will pass a valid data table object to calculateRowTotal() with a
	 * row number of 5, which is greater then number of rows in the data table.
	 * 
	 * Expects: An IllegalArgumentException is thrown.
	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void calculateRowTotalForOutOfRangeRowIndex() throws IllegalArgumentException{
//
//		double result = DataUtilities.calculateRowTotal(values, 5);
//		assertEquals("Sum of a out of bounds row index should be 0", 0.0, result, .000000001d);
//	}

	/**
	 * This test will pass a valid data table object to calculateRowTotal() with a
	 * row number of -1.
	 * 
	 * Expects: An IllegalArgumentException is thrown.
	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void calculateRowTotalForNegativeRowIndex() throws IllegalArgumentException{
//
//		double result = DataUtilities.calculateRowTotal(values, -1);
//		assertEquals("Sum of a negative row index should be 0", 0.0, result, .000000001d);
//	}

	/**
	 * This test will pass a valid data table object to calculateRowTotal() with the
	 * first row index (0).
	 * 
	 * This will test the sum of the row values in the first row.
	 * 
	 * Expects: The correct sum for the given row.
	 */
	@Test
	public void calculateRowTotalForFirstRowIndex() {
		double result = DataUtilities.calculateRowTotal(values, 0);
		assertEquals("Sum of the row at the first index is", -7.0, result, .000000001d);
	}

	/**
	 * This test will pass a valid data table object to calculateRowTotal() with a
	 * in between row index (1).
	 * 
	 * This will test the sum of the row values in a middle row.
	 * 
	 * Expects: The correct sum for the given row.
	 */
	@Test
	public void calculateRowTotalForMiddleRowIndex() {
		double result = DataUtilities.calculateRowTotal(values, 1);
		assertEquals("Sum of the row at the middle index is", -4, result, .000000001d);
	}

	/**
	 * This test will pass a valid data table object to calculateRowTotal() with the
	 * final row index (2).
	 * 
	 * This will test the sum of the row values for the final row.
	 * 
	 * Expects: The correct sum for the given row.
	 */
	@Test
	public void calculateRowTotalForFinalRowIndex() {
		double result = DataUtilities.calculateRowTotal(values, 2);
		assertEquals("Sum of the row at the last index is", 1, result, .000000001d);
	}

	@Test
	public void calculateRowTotalWithValidColumns() {
		int[] validCols = { 0, 1, 2 };
		double result = DataUtilities.calculateRowTotal(values, 0, validCols);
		assertEquals(-7.0, result, .000000001d);
	}

	@Test
	public void calculateColumnTotalWithNoValidColumns() {
		int[] validCols = {};
		double result = DataUtilities.calculateRowTotal(values, 0, validCols);
		assertEquals(0.0, result, .000000001d);
	}
	
	@Test
	public void emptyArrayClone() {
		double[][] testArray = {{}}; 
		double[][] resultArray = DataUtilities.clone(testArray);
		assertArrayEquals(testArray, resultArray);
	}
	
	@Test
	public void invalidDataClone() {
		double[][] testArray = {null};
		double[][] resultArray = DataUtilities.clone(testArray); 
		assertArrayEquals(testArray, resultArray);
	}

	@Test
	public void positiveDataClone() {
		double[][] testArray = {{1, 2}, {3, 4}};
		double[][] actualArray = DataUtilities.clone(testArray);
		assertArrayEquals(testArray, actualArray);
	}

	@Test
	public void negativeDataClone() {
		double[][] testArray = {{-1, -2}, {-3, -4}};
		double[][] actualArray = DataUtilities.clone(testArray);
		assertArrayEquals(testArray, actualArray);
	}
	
	/**
     * Test with valid double array input.
     * Expects: Corresponding Number[][] array with identical values.
     */
    @Test
    public void createNumberArray2DWithValidInput() {
        double[][] data = {{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}};
        Number[][] expected = {{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}};

        Number[][] result = DataUtilities.createNumberArray2D(data);

        assertArrayEquals("The Number[][] array should match the double[][] input.", expected, result);
    }

    /**
	 * This test will simulate creating a null double array.
	 */
	@Test
	public void createNumber2DNullArray() {
		try {
			double[][] array = null;
			DataUtilities.createNumberArray2D(array);
		} catch (Exception e) {
			assertEquals("The exception thrown type is IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
			// catching the exception, asserting that an IllegalArgumentException was thrown
		}
	}
	
    /**
     * Test with null input.
     * Expects: InvalidParameterException.
     */
//    @Test(expected = InvalidParameterException.class)
//    public void createNumberArray2DWithNullInput() throws InvalidParameterException{
//        DataUtilities.createNumberArray2D(null);
//    }

    /**
     * Test with empty double array input.
     * Expects: An empty Number[][] array.
     */
    @Test
    public void createNumberArray2DWithEmptyInput() {
        double[][] data = {};
        Number[][] expected = {};

        Number[][] result = DataUtilities.createNumberArray2D(data);

        assertArrayEquals("The Number[][] array should be empty.", expected, result);
    }
    

    /**
     * Test with double array containing negative values.
     * Expects: Corresponding Number[][] array including negative values.
     */
    @Test
    public void createNumberArray2DWithNegativeValues() {
        double[][] data = {{-1.0, -2.0}, {-3.0, -4.0}};
        Number[][] expected = {{-1.0, -2.0}, {-3.0, -4.0}};

        Number[][] result = DataUtilities.createNumberArray2D(data);

        assertArrayEquals("The Number[][] array should include the negative values.", expected, result);
    }

    /**
     * Test with a double array containing NaN and Infinity values.
     * Expects: Corresponding Number[][] array containing NaN and Infinity values.
     */
    @Test
    public void createNumberArray2DWithSpecialValues() {
        double[][] data = {{Double.NaN, Double.POSITIVE_INFINITY}, {Double.NEGATIVE_INFINITY, Double.MAX_VALUE}};
        Number[][] expected = {{Double.NaN, Double.POSITIVE_INFINITY}, {Double.NEGATIVE_INFINITY, Double.MAX_VALUE}};

        Number[][] result = DataUtilities.createNumberArray2D(data);

        assertArrayEquals("The Number[][] array should handle NaN and Infinity.", expected, result);
    }
    
    /**
	 * This test will pass a null double[] array to createNumberArray()
	 * 
	 * Expects: IllegalArgumentException to be thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void createNumberArrayWithNullArray() {
		double[] inputArray = null;
		DataUtilities.createNumberArray(inputArray);
	}
	
	/**
	 * This test will pass a empty double[] array to createNumberArray()
	 * 
	 * Expects: Returns a empty Number array
	 */
	@Test
	public void createNumberArrayEmpty() {
		Number[] expectedArray = {};		
		double[] inputArray = {};		
		Number[] createdArray = DataUtilities.createNumberArray(inputArray);
		assertArrayEquals(expectedArray, createdArray);
	}
	
	/**
	 * This test will pass a double[] array with positive values to createNumberArray()
	 * 
	 * Expects: Returns a Number array with the correct values
	 */
	@Test
	public void createNumberArrayPositiveValues() {
		Number[] expectedArray = {10.0}; 	
		double[] inputArray = {10.0};
		Number[] createdArray = DataUtilities.createNumberArray(inputArray);

		assertArrayEquals(expectedArray,createdArray);
	}
	
	/**
	 * This test will pass a double[] array with negative values to createNumberArray()
	 * 
	 * Expects: Returns a Number array with the correct values
	 */
	@Test
	public void createNumberArrayNegativeValues() {
		Number[] expectedArray = {-20.0}; 	
		double[] inputArray = {-20.0};
		Number[] createdArray = DataUtilities.createNumberArray(inputArray);

		assertArrayEquals(expectedArray,createdArray);
	}
	
	@Test
	public void twoNullArraysTest() {
		double array1[][] = null;
		double array2[][] = null;
		assertTrue("The arrays should be equal", DataUtilities.equal(array1, array2));
	}
	
	@Test
    public void firstArrayNullTest() {
        double array1[][] = null;
        double array2[][] = {{1,2,3,4,5}};
        assertFalse("The arrays should not be equal", DataUtilities.equal(array1, array2));
    }
    
    @Test
    public void secondArrayNullTest() {
        double array1[][] = {{1,2,3,4,5}};
        double array2[][] = null;
        assertFalse("The arrays should not be equal", DataUtilities.equal(array1, array2));
    }
	
	@Test
	public void twoEmptyArraysTest() {
		double array1[][] = {};
		double array2[][] = {};
		assertTrue("The arrays should be equal", DataUtilities.equal(array1, array2));
	}
	
	@Test
	public void checkTwoArraysNotEqualLength() {
		double array1[][] = {{1,2,3,4,5}};
		double array2[][] = {{1,2,3}};
		assertFalse("The arrays should not be equal", DataUtilities.equal(array1, array2));
	}
	
	@Test
	public void checkTwoArraysNotEqualSize() {
		double array1[][] = {{1,2,3,4,5}};
		double array2[][] = {{1,2,3}, {1,2,3}};
		assertFalse("The arrays should not be equal", DataUtilities.equal(array1, array2));
	}
	
	@Test
	public void checkTwoValidArrays() {
		double array1[][] = {{1,2,3}};
		double array2[][] = {{1,2,3}};
		assertTrue("The arrays should be equal",DataUtilities.equal(array1, array2));
	}
	
	/*
	 * Test if null parameter will through InvalidParameterException
	*/
	@Test (expected = IllegalArgumentException.class)
	public void nullCumlativePercentageTest() {
		DataUtilities.getCumulativePercentages(null);
	}
	
	/**
	 * This test will create a mock object of the type KeyedValues.
	 */
	@Test
	public void cumulativePercentageForIndexZeroSixteen() {
		Mockery mockingContext = new Mockery(); 
		final KeyedValues data = mockingContext.mock(KeyedValues.class); 

		mockingContext.checking(new Expectations() {
			{
				atLeast(1).of(data).getItemCount();
				will(returnValue(2));

				atLeast(1).of(data).getValue(0); 
				will(returnValue(1.0)); 

				atLeast(1).of(data).getValue(1); 
				will(returnValue(5.0));

				atLeast(1).of(data).getKey(0);
				will(returnValue(25.0));

				atLeast(1).of(data).getKey(1); 
				will(returnValue(26.0));

			}
		});

		KeyedValues result = DataUtilities.getCumulativePercentages(data);
		assertEquals("The value at the index of 0 is 0.166666667", 0.166666667, result.getValue(0).doubleValue(),
				.000000001d); 
	}
	
	//This test is verifying that the cumulative average object has the same keys as the one that is being passed in
//	@Test
//	public void getCumulativePercentagesKeysTest() {
//		KeyedValues results = DataUtilities.getCumulativePercentages(values2);
//		assertEquals("The resulting keys are ", results.getKeys(), values2.getKeys());
//	}
	
	//This test is verifying that the cumulative average that is found is between 0-1 for the first value, which is expected to be 0.3125
//	@Test
//	public void getCumalativePercentagesFirstValue() {
//		KeyedValues results = DataUtilities.getCumulativePercentages(values2);
//		Number firstElement = results.getValue(0);
//		assertEquals("The cumalative percentage for the first key is 0.3125 ", firstElement);
//	}
	
	//This test is verifying that the cumulative average that is found is between 0-1 for the last value, which is expected to be 1
//	@Test
//	public void getCumulativePercentagesLastValue() {
//		KeyedValues results = DataUtilities.getCumulativePercentages(values2);
//		Number lastElement = results.getValue(2);
//		assertEquals("The cumalative percentage for the last key is 1.0 ", lastElement);
//	}
	
	/*
	 * Test to see if the correct cumulative percentage of KeyedValues pair with both positive and negative values is returned
	 */
	@Test
	public void getMixedCumulativePercentagesTest() {
		Mockery mockingContext1 = new Mockery(); 
		KeyedValues results1 = mockingContext1.mock(KeyedValues.class);
		mockingContext1.checking(new Expectations() { 
			{
				atLeast(1).of(results1).getItemCount();
				will(returnValue(3));
				
				atLeast(1).of(results1).getKey(0);
				will(returnValue(0));
				
				atLeast(1).of(results1).getKey(1);
				will(returnValue(4));
				
				atLeast(1).of(results1).getKey(2);
				will(returnValue(3));
				
				atLeast(1).of(results1).getValue(0);
				will(returnValue(-5));
				
				atLeast(1).of(results1).getValue(1);
				will(returnValue(2));
				
				atLeast(1).of(results1).getValue(2);
				will(returnValue(2));
				
			} 
		});
		
		Mockery mockingContext2 = new Mockery();
		KeyedValues results2 = mockingContext2.mock(KeyedValues.class);
		mockingContext2.checking(new Expectations() {
			{
				atLeast(1).of(results2).getItemCount(); 
				will(returnValue(3));
			
				atLeast(1).of(results2).getKey(0);
				will(returnValue(0));
				
				atLeast(1).of(results2).getKey(1);
				will(returnValue(1));
				
				atLeast(1).of(results2).getKey(2);
				will(returnValue(2));
						
				atLeast(1).of(results2).getValue(0);
				will(returnValue(-0.40));
				
				atLeast(1).of(results2).getValue(1);
				will(returnValue(0.42));
				
				atLeast(1).of(results2).getValue(2);
				will(returnValue(1.0));
			} 
		});
		
		assertFalse("The objects are equal", DataUtilities.getCumulativePercentages(results1).equals(results2));
	}
	
	/**
	 * This test is performing that the row count is equal to the valid rows in which we are trying to removing the 
	 * survived mutation from our original source code. This test is also using Mocking to create a mock object which 
	 * can be used to increase the mutation coverage. 
	 * Expected to kill mutation '1. changed conditional boundary' as our plan was to the test the boundary conditions 
	 * which may have not been covered in the previous labs. 
	 */
	@Test
	public void calculateRowTotalChangedConditionalBoundaryTest() { 
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(1));

				one(values).getValue(1, 0);
				will(returnValue(1.0));
			}
		});
		final int[] validColumns = {1}; 
		double result = DataUtilities.calculateRowTotal(values, 1, validColumns);

		assertEquals("The row total is adding up to 0.0", 0.0, result, .000000001d);

	}
	
	/**
	 * This test will simulate passing a object to calculateColumnTotal() with a
	 * column number of 0 and 4 row count and where one row has a value of n = null.
	 * Expected to kill mutation '3. removed conditional - replaced equality check
	 * with true'
	 */
	@Test
	public void calculateColumnTotalNEqualNullTest() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(4));

				one(values).getValue(0, 0);
				will(returnValue(1.0));

				one(values).getValue(1, 0);
				will(returnValue(2.0));

				one(values).getValue(2, 0);
				will(returnValue(3.0));

				one(values).getValue(3, 0);
				will(returnValue(null));
			}
		});
		final int[] validRows = { 0, 1, 2, 3 }; 
		double result = DataUtilities.calculateColumnTotal(values, 0, validRows);

		assertEquals("The column total is adding up to 6.0", 6.0, result, .000000001d);

	}
	
	/**
	 * This test will simulate passing a object to calculateColumnTotal() with a
	 * column number of 1 and row count that is equal to the valid rows. Since
	 * row = rowCount, the result should be 0. Expected to kill mutation '1. changed
	 * conditional boundary'
	 */
	@Test
	public void calculateColumnTotalRowEqualsRowTotalTest() { 
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(1));

				one(values).getValue(1, 0);
				will(returnValue(1.0));

				one(values).getValue(1, 1);
				will(returnValue(2.0));

				one(values).getValue(1, 2);
				will(returnValue(3.0));
			}
		});
		final int[] validRows = { 1 }; 
		double result = DataUtilities.calculateColumnTotal(values, 1, validRows);

		assertEquals("The column total is adding up to 0.0", 0.0, result, .000000001d);

	}
	
	/**
	 * This test will simulate passing a object to calculateColumnTotal() with a
	 * column number of 1 and row count that is smaller than the valid total rows. Since
	 * row > rowCount, the result should be 0. Killed '2. negated conditional
	 * KILLED' Mutation
	 */
	@Test
	public void calculateColumnTotalRowGreaterThanRowTotalTest() { 
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);


		mockingContext.checking(new Expectations() { 
			{
				one(values).getRowCount();
				will(returnValue(1));

				one(values).getValue(1, 0);
				will(returnValue(1.0));


				one(values).getValue(1, 1);
				will(returnValue(2.0));

				one(values).getValue(1, 2);
				will(returnValue(3.0));

				one(values).getValue(1, 3);
				will(returnValue(4.0));
			}
		});

		final int[] validRows = { 4 }; 
		double result = DataUtilities.calculateColumnTotal(values, 1, validRows);

		assertEquals("The column total is adding up to 0.0", 0.0, result, .000000001d);

	}


}
