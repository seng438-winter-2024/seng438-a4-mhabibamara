package org.jfree.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AllRangeTests {

	private Range usedRange;
	private Range rangeInTest;
	private Range exampleRange;
	private Range expandRange;
	private Range posRange;
	private Range negRange;
	private Range sameRange;
	private Range invalidRange;
	private Range exampleRange2;
	private Range exampleRangeIntersects2;
	private Range exampleRange3;
	private Range exampleRange4;
	private Range lowerBoundRange;

	@Before
	public void setUp() throws Exception {
		usedRange = new Range(0.0, 20.0);
		lowerBoundRange = new Range(0.0, 100);
		exampleRange = new Range(-1, 1);
		expandRange = new Range(-1.0, 1.0);
		posRange = new Range(2, 4);
		negRange = new Range(-4, -2);
		sameRange = new Range(2, 2);
		invalidRange = new Range(Double.NaN, Double.NaN);
		exampleRange2 = new Range(Double.NaN, Double.NaN);
		exampleRangeIntersects2 = new Range(5, 10);
		exampleRange3 = new Range(Double.NaN, 2.0);
		exampleRange4 = new Range(-5, 10);
	}

	// ----------------------------------------------------------
	// Testing For the Contains Method
	// ----------------------------------------------------------

	// Test to verify if range contains a number that is greater than the upper
	// bound
	// Added test for mutation
	@Test
	public void numberAboveTheUpperBoundTest() {
		Range testRange = new Range(-5, 5);
		assertFalse("This should return false for 6 in the range of -5 to 5.", testRange.contains(6));
	}

	@Test
	public void numberAboveTheUpperBoundTest2() {
		Range testRange = new Range(-6, 5);
		assertFalse("This should return false for 6 in the range of -5 to 5.", testRange.contains(6));
	}

	@Test
	public void numberAboveTheUpperBoundTest3() {
		Range testRange = new Range(-5, 5);
		assertFalse("This should return false for 6 in the range of -5 to 5.", testRange.contains(7));
	}

	// Test to verify if range contains a number that is less than the lower bound
	@Test
	public void numberLessThanLowerBoundTest() {
		boolean result = usedRange.contains(-10);
		assertEquals("This number is outside the lower bound", false, result);
	}

	// Test to verify if range contains a number that is greater than the upper
	// bound
	@Test
	public void numberGreaterThanUpperBoundTest() {
		boolean result = usedRange.contains(24.0);
		assertEquals("This number is outside the upperbound", false, result);
	}

	// Test to verify if range contains a number that is on the lower bound
	@Test
	public void numberOnLowerBoundTest() {
		boolean result = usedRange.contains(0.0);
		assertEquals("This number is on the lower bound and therefore contained in the range", true, result);
	}

	// Test to verify if range contains a number that is on the upper bound
	@Test
	public void numberOnUpperBoundTest() {
		boolean result = usedRange.contains(20.0);
		assertEquals("The number is on the upper bound and therefore contained in the range", true, result);
	}

	// Test to verify if range contains a number that is in the nominal range
	// (between lower and upper bounds)
	@Test
	public void numberInNominalRangeTest() {
		boolean result = usedRange.contains(10.0);
		assertEquals("This number should be in the middle of the range", true, result);
	}

	// Test to verify if range contains a number that is below the upper bound
	@Test
	public void numberBelowUpperBoundTest() {
		boolean result = usedRange.contains(19.0);
		assertEquals("This number is just below the upper bound and contained in the range", true, result);
	}

	// Test to verify if range contains a number that is above the lower bound
	@Test
	public void numberAboveLowerBoundTest() {
		boolean result = usedRange.contains(1.0);
		assertEquals("This number is just above the lower bound and contained in the range", true, result);
	}

	@Test
	public void containsValueUnder() {
		assertFalse("The Range of -1 to 1 should not contain the number -43", exampleRange.contains(-43));
	}

	@Test
	public void containsValueUpperEdge() {
		Range testRange = new Range(0, 100);
		assertTrue("The Range of 0 to 100 should contain the number 100 - Edge case", testRange.contains(100));
	}

	@Test
	public void containsValueLowerEdge() {
		Range testRange = new Range(0, 100);
		assertTrue("The Range of 0 to 100 should contain the lower bound 0", testRange.contains(0));
	}

	@Test
	public void containsNegValue() {
		Range testRange = new Range(0, 100);
		assertFalse("The Range of 0 to 100 shoud not contain the number -12", testRange.contains(-12));
	}

	@Test
	public void containtsOnNegRange() {
		Range testRange = new Range(-50, -5);
		assertTrue("The range of -50 to -5 should contain the value -17", testRange.contains(-17));
	}

	// ----------------------------------------------------------
	// Testing Constructor Mistake Expectation
	// ----------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void expectExpectationWithReverseBoundsOnConstructors() {
		new Range(1, -1);
	}

	// ----------------------------------------------------------
	// Testing For the Combine Method
	// ----------------------------------------------------------

	/* Testing combine method when range1 is null */
	@Test
	public void range1IsNullTest() {
		assertEquals("The combined ranges of [null] and [2,4] should be [2,4] ", new Range(2, 4),
				Range.combine(null, new Range(2, 4)));
	}

//	@Test
//	public void firstRangeNullShouldReturnRangeTwo() {
//		assertEquals("Returned range of the combine of null and 0 to 100 should be 0 to 100",
//				Range.combine(null, exampleRange2), new Range(0, 100));
//	}
//	
//	@Test
//	public void secondRangeNullShouldReturnRangeOne() {
//		assertEquals("Returned range of the combine of -1 to 1 and null should be -1 to 1",
//				Range.combine(exampleRange, null), new Range(-1, 1));
//	}

	/* Testing combine method when range2 is null */
	@Test
	public void range2IsNullTest() {
		assertEquals("The combined ranges of  [2,4] and [null] should be [2,4]", new Range(2, 4),
				Range.combine(new Range(2, 4), null));
	}

	@Test
	public void bothRangesNullTest() {
		assertEquals("The combined ranges of [null] and [null] should be [null]", null, Range.combine(null, null));
	}

	/*
	 * Testing combine method when range1 and range2 are continuous This method is
	 * not working and failing
	 */
	@Test
	public void continuousRangeTest() {
		assertEquals("The combined overlapping/continuous ranges of [0,3] and [3,4] should be [0,4]", new Range(0, 4),
				Range.combine(new Range(0, 3), new Range(3, 4)));
	}

	/*
	 * Testing combine method when range1 and range2 are discontinuous This method
	 * is not working and failing
	 */
	@Test
	public void discontinuousRangeTest() {
		assertEquals("The combines non-overlapping/discontinuous ranges of [0,5] and [8,15] should be [0,15]",
				new Range(0, 15), Range.combine(new Range(0, 5), new Range(8, 15)));
	}

	// ----------------------------------------------------------
	// Testing For the Constrain Method
	// ----------------------------------------------------------

	// Mutation test to check the constrain() method with a value that is less than
	// the lower bound amount
	@Test
	public void testConstrainLessThanLower() {
		Range range = new Range(1.0, 5.0);
		assertEquals("Failed to constrain value less than lower bound", 1.0, range.constrain(0.0), 0.0001);
	}

	// Testing a value that is above the range
	@Test
	public void constrainAboveUpperRange() {
		Range testRange = new Range(1, 10);
		double result = testRange.constrain(11.0);
		assertEquals("The value that should be returned is 10", 10, result, .000000001d);
	}

	// Testing a value that is below the range
	@Test
	public void constrainBelowLowerRange() {
		Range testRange = new Range(4, 10);
		double result = testRange.constrain(2.0);
		assertEquals("The value that should be returned is 4", 4, result, .000000001d);
	}

	// Testing a value within the range
	@Test
	public void constrainWithinRange() {
		Range testRange = new Range(5, 15);
		double result = testRange.constrain(10);
		assertEquals("The value should be returned is 10", 10, result, .000000001d);
	}

	// ----------------------------------------------------------
	// Testing For the Intersects Method
	// ----------------------------------------------------------

	// Mutation test to check the intersects() method with range from 1 to 5 and it
	// checks 2 different scenarios
	@Test
	public void testIntersects() {
		Range range = new Range(1.0, 5.0);
		assertTrue(range.intersects(0.0, 2.0));
		assertFalse(range.intersects(0.0, 0.5));
	}

	@Test
	public void testIntersectsWithRangeAsParameter() {
		Range range1 = new Range(3.0, 6.0);
		Range range2 = new Range(3.5, 4.5);
		assertTrue(range1.intersects(range2));
		assertFalse(range1.intersects(0.0, 1.0));
	}

	@Test
	public void IntersectsUpperGreaterThanLowerTest() {
		assertTrue("Parameters failed the check", exampleRange.intersects(-5, 10));
	}

	@Test
	public void IntersectsUpperGreaterThanLowerDoesntIntersectTest() {
		assertFalse("Parameters failed the check", exampleRange.intersects(5, 10));
	}

	@Test
	public void IntersectsUpperLessThanLowerTest2() {
		assertTrue("Parameters failed the check", exampleRange.intersects(5, -2) == false);
	}

	@Test
	public void IntersectsUpperGreaterThanLowerTest2() {
		assertTrue("Parameters failed the check", exampleRange.intersects(exampleRange4));
	}

	@Test
	public void IntersectsUpperGreaterThanLowerDoesntIntersectTest2() {
		assertFalse("Parameters failed the check", exampleRange.intersects(exampleRangeIntersects2));
	}

	// ----------------------------------------------------------
	// Testing the intersect OVERLOADED method
	// ----------------------------------------------------------

	@Test
	public void theRangeShouldIntersectWithOtherRangeObject() {
		Range testRange = new Range(0, 100);
		assertTrue("The range from 0 to 100 should intersect with a Range of -1 to 1",
				testRange.intersects(exampleRange));
	}

	@Test
	public void theRangeShouldNotIntersectWithOtherRangeObject() {
		Range testRange = new Range(30, 31);
		assertFalse("The range from -1 to 1 should not intersect with a Range of 30 to 31",
				exampleRange.intersects(testRange));
	}

	// ----------------------------------------------------------
	// Testing For the hashCode Method
	// ----------------------------------------------------------

	@Test
	public void hashCodeTestShouldReturn() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(-1.0);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(1.0);
		result = 29 * result + (int) (temp ^ (temp >>> 32));
		assertEquals("HASH FOR range of -1 to 1 should be" + result, exampleRange.hashCode(), result);
	}

	// ----------------------------------------------------------
	// Testing For the Equal Method
	// ----------------------------------------------------------

	// Testing 2 objects that are not equal
	@Test
	public void intObjectIsNotARangeObject() {
		Integer testInt = Integer.valueOf(5);
		Range testRange = new Range(1, 4);
		assertFalse("The Integer Object is not a Range Object and so the function should return false",
				testRange.equals(testInt));
	}

	// Testing 2 Range objects that are equal
	@Test
	public void rangeObjectsAreEqual() {
		Range testRange1 = new Range(1, 5);
		Range testRange2 = new Range(1, 5);
		assertTrue("The range objects are equal and should return true", testRange1.equals(testRange2));
	}

	// Testing 2 Ranges with equal lower bound, but will still not be equal overall
	// This test case checks the equals() method with two Range objects that have
	// the same lower bound but different upper bounds.
	// It kills a mutant where the conditional `if (!(this.upper == range.upper))`
	// is replaced with false.
	@Test
	public void lowerBoundRangeObjectsEqual() {
		Range testRange1 = new Range(1, 5);
		Range testRange2 = new Range(1, 10);
		assertFalse("The ranges are not equal", testRange1.equals(testRange2));
	}

	// Testing 2 Ranges with equal upper bound, but will still not be equal overall
	@Test
	public void upperBoundRangeObjectsEqual() {
		Range testRange1 = new Range(3, 12);
		Range testRange2 = new Range(10, 12);
		assertFalse("The ranges are not equal", testRange1.equals(testRange2));
	}

	// ----------------------------------------------------------
	// Testing For the Expand Method
	// ----------------------------------------------------------

	@Test
	public void expandRangeby0() {
		assertEquals("expand the range -1 to 1 by margins o% upper and 0% lower should show -1 to 1", new Range(-1, 1),
				Range.expand(new Range(-1, 1), 0, 0));
	}

	@Test
	public void expandLowerBoundToZero() {
		Range testRange = Range.expand(expandRange, -1.0, -1.0);
		assertEquals("Lower boundary should be 0", 0, testRange.getLowerBound(), .000000001d);
	}

	@Test
	public void expandLowerBoundToNegative() {
		Range testRange = Range.expand(expandRange, 1.0, 1.0);
		assertEquals("Lower boundary should be -3.0", -3.0, testRange.getLowerBound(), .000000001d);
	}

	// ----------------------------------------------------------
	// Testing For the ExpandToInclude Method
	// ----------------------------------------------------------

	@Test
	public void expandToIncludeUpperBound() {
		Range testRange = Range.expandToInclude(new Range(1, 4), 10);
		assertEquals("This will include 10 by expanding the range 1,4 to 1,10", new Range(1, 10), testRange);
	}

	@Test
	public void expandToIncludeLowerBound() {
		Range testRange = Range.expandToInclude(new Range(3, 10), -4);
		assertEquals("This include -4 by expanding the lower bound of the range 3,10 to -4,10", new Range(-4, 10),
				testRange);
	}

	@Test
	public void expandToIncludeNullLowerBound() {
		Range testRange = Range.expandToInclude(null, 2);
		assertEquals("The lower value will expand to 2", 2, testRange.getLowerBound(), .000000001d);
	}

	@Test
	public void expandToIncludeNominalValue() {
		Range testRange = Range.expandToInclude(new Range(-1, 1), 0);
		assertEquals("To include 0 in this range it will remain at range of -1,1", new Range(-1, 1), testRange);
	}

	@Test
	public void expandToIncludeNullRange() {
		Range testRange = Range.expandToInclude(null, 0);
		assertEquals("To include 0 in the range of null it will go to 0,0", new Range(0, 0), testRange);
	}

	// ----------------------------------------------------------
	// Testing For the GetCentralValue Method
	// ----------------------------------------------------------

	// Testing the CentralValue method to check the values between the range
	@Test
	public void centralValueBetweenRange() {
		Range testRange = new Range(4, 6);
		assertEquals("The central value of the Range between 4 and 6 should be 5", 5, testRange.getCentralValue(),
				0.000001d);
	}

	@Test
	public void centerValueShouldBeZeroTest() {
		assertEquals("The center value for range -1 to 1 should 0", exampleRange.getCentralValue(), 0, 0.000001d);
	}

	// ----------------------------------------------------------
	// Testing For the GetLength Method
	// ----------------------------------------------------------

	// Mutation test
	@Test
	public void testGetLength() {
		Range range = new Range(1.0, 5.0);
		assertEquals(4.0, range.getLength(), 0.0001);
	}

	// Test to verify that the length between 2 equal ranges is 0
	@Test
	public void getLengthZeroTest() {
		rangeInTest = new Range(10.0, 10.0);
		assertEquals("The length of this range should be 0.0", 0, rangeInTest.getLength(), .000000001d);

	}

	// Test to verify that the length between 2 different positive ranges is
	// positive
	@Test
	public void getPositiveLengthTest() {
		rangeInTest = new Range(5.0, 20.0);
		assertEquals("The length of this range should be ", 15, rangeInTest.getLength(), .000000001d);
	}

	// Test to verify that the length between 2 different negative ranges is
	// negative
	@Test
	public void getNegativeLengthTest() {
		rangeInTest = new Range(-50.0, -10);
		assertEquals("The length of this range should be ", 40, rangeInTest.getLength(), .000000001d);
	}

	// Test to verify that the length between 2 different non decimal ranges
	// (positive and negative) is a non decimal
	@Test
	public void getRangeLengthNonDecimal() {
		rangeInTest = new Range(-60, 100);
		assertEquals("The length of this range should be ", 160, rangeInTest.getLength(), .000000001d);

	}

	// Test to verify that the length between 2 different decimal ranges (positive
	// and negative) is a decimal length
	@Test
	public void getRangeLengthDecimal() {
		rangeInTest = new Range(-70.5, 10.2);
		assertEquals("The length of this range should be ", 80.7, rangeInTest.getLength(), .000000001d);
	}

	// ----------------------------------------------------------
	// Testing For the LowerBound Method
	// ----------------------------------------------------------

	@Test
	public void testLowerBoundPositive() {
		assertEquals("The lower bound of the range [2,4] should be 2", 2, posRange.getLowerBound(), .000000001d);
	}

	@Test
	public void testLowerBoundNegative() {
		assertEquals("The lower bound of the range [-4,-2] should be -4", -4, negRange.getLowerBound(), .000000001d);
	}

	@Test
	public void testLowerBoundSame() {
		assertEquals("The lower bound of the range [2,2] should be 2", 2, sameRange.getLowerBound(), .000000001d);
	}

	@Test
	public void lowerBoundShouldNotBeNegative() {
		assertFalse("The Lower bound for the range of 0 to 100 should be 0 not -12",
				-12 == lowerBoundRange.getLowerBound());
	}

	// ----------------------------------------------------------
	// Testing For the UpperBound Method
	// ----------------------------------------------------------

	@Test
	public void testUpperBoundPositive() {
		assertEquals("The upper bound of the range [2,4] should be 4", 4.0, posRange.getUpperBound(), .000000001d);
	}

	@Test
	public void testUpperBoundNegative() {
		assertEquals("The upper bound of the range [-4,-2] should be -2", -2.0, negRange.getUpperBound(), .000000001d);
	}

	@Test
	public void testUpperBoundSame() {
		assertEquals("The upper bound of the range [2,2] should be 2", 2.0, sameRange.getUpperBound(), .000000001d);
	}

//	@Test
//	public void ExceptionTest() {
//		invalidRange.getUpperBound();
//		fail("Throws Exception");
//	}

	// ----------------------------------------------------------
	// Testing For the Scale Method
	// ----------------------------------------------------------

	// Testing scaling a range with a negative factor
	@Test(expected = IllegalArgumentException.class)
	public void scalingARangeWithFactorOfNeg() {
		Range.scale(new Range(-1, 1), -1);
	}

	// Testing scaling a range with a positive factor
	@Test
	public void scalingRangeWithPositiveFactor() {
		Range testRange = new Range(1.0, 10.0);
		Range scaledRange = testRange.scale(testRange, 2);
		assertEquals("The upper bound value should be 20", 20, scaledRange.getUpperBound(), .000000001d);
	}

	// This test case checks the scale() method with a non-negative factor.
	// It kills a mutant where the conditional `if (factor < 0)` is replaced with
	// true.
	@Test
	public void testScaleNonNegativeFactor() {
		Range base = new Range(2.0, 10.0);
		double factor = 4.0;
		try {
			Range scaledRange = Range.scale(base, factor);
			assertEquals("Failed to correctly scale the lower bound", base.getLowerBound() * factor,
					scaledRange.getLowerBound(), 0.0001);
			assertEquals("Failed to correctly scale the upper bound", base.getUpperBound() * factor,
					scaledRange.getUpperBound(), 0.0001);
		} catch (IllegalArgumentException e) {
			fail("IllegalArgumentException should not be thrown for non-negative factor");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void scalingANullRange() {
		Range.scale(null, 2);
	}

	// ----------------------------------------------------------
	// Testing For the Shift Method
	// ----------------------------------------------------------

	// Test checks the shift() method with a range from 1.0 to 5.0 and with a shift
	// of 2.0.
	// It kills a mutant where the conditional at line 4 in the shift() method is
	// negated.
	@Test
	public void testShiftLowerBound() {
		Range range = new Range(1.0, 5.0);
		Range shiftedRange = Range.shift(range, 2.0, false);
		assertEquals("Failed to shift lower bound correctly", 3.0, shiftedRange.getLowerBound(), 0.0001);
	}

	// Test will check the shift() method with a range from 1.0 to 5.0 and a shift
	// of 2.0.
	// It kills the same mutant as the previous test case.
	@Test
	public void testShiftUpperBound() {
		Range range = new Range(1.0, 5.0);
		Range shiftedRange = Range.shift(range, 2.0, false);
		assertEquals("Failed to shift upper bound correctly", 7.0, shiftedRange.getUpperBound(), 0.0001);
	}

	@Test
	public void shiftRangeByThree() {
		Range testRange = new Range(2, 6);
		assertEquals("Shifting the range by 3 will result in a new range of 5 to 9", new Range(5, 9),
				Range.shift(testRange, 3));
	}

	@Test
	public void shiftRangeByTenWithAllowedZeroCrossing() {
		Range testRange = new Range(-1, 1);
		assertEquals("Shifting the range by 10 with allowed zero crossing should result in a range range of 9 to 11",
				new Range(9, 11), Range.shift(testRange, 10, true));
	}

	@Test
	public void shiftRangeByFiveWithoutAllowedZeroCrossing() {
		Range testRange = new Range(-1, 0);
		assertEquals("Shifting the range by 5 without allowed zero crossing should result in a range of 0 to 5",
				new Range(0, 5), Range.shift(testRange, 5, false));
	}

	// ----------------------------------------------------------
	// Testing For the ShiftWithNoZeroCrossing Method
	// ----------------------------------------------------------

	@Test
	public void shiftWithNoZeroCrossingValueLessThanZero() {
		Range testRange = new Range(-5, 10);
		assertEquals("The shifted value should be 20", 20, testRange.shift(testRange, 10, false).getUpperBound(),
				.000000001d);
	}

	@Test
	public void shiftWithNoZeroCrossingValueEqualZero() {
		Range testRange = new Range(0, 10);
		assertEquals("The shifted value should be 20", 20, testRange.shift(testRange, 10, false).getUpperBound(),
				.000000001d);
	}

	@Test
	public void shiftWithNoZeroCrossingValueGreatThanZero() {
		Range testRange = new Range(-5, 10);
		assertEquals("The shifted value should be 30", 30, testRange.shift(testRange, 20).getUpperBound(), .000000001d);
	}

	// ----------------------------------------------------------
	// Testing For the toString Method
	// ----------------------------------------------------------

	@Test
	public void toStringRangeTest() {
		assertEquals("The string output is wrong", "Range[-1.0,1.0]", exampleRange.toString());
	}

	@Test
	public void testToString() {
		Range range1 = new Range(2.0, 10.0);
		assertEquals("Range[2.0,10.0]", range1.toString());

		Range range2 = new Range(-5.0, 4.0);
		assertEquals("Range[-5.0,4.0]", range2.toString());

		Range range3 = new Range(0.0, 0.0);
		assertEquals("Range[0.0,0.0]", range3.toString());
	}

	// ----------------------------------------------------------
	// Testing For the combineIgnoringNaN Method
	// ----------------------------------------------------------

	@Test
	public void range1NullTest() {
		assertEquals("Range is NULL", new Range(2, 6), Range.combineIgnoringNaN(null, new Range(2, 6)));
	}

	@Test
	public void range2NullTest() {
		assertEquals("Range is Null", new Range(2, 6), Range.combineIgnoringNaN(new Range(2, 6), null));
	}

	@Test
	public void rangereturnNullTest() {
		assertNull("Range is Null", Range.combineIgnoringNaN(null, new Range(Double.NaN, Double.NaN)));
	}

	@Test
	public void combineIgnoringNaNBothRangesNullTest() {
		assertNull(Range.combineIgnoringNaN(null, null));
	}

	@Test
	public void rangereturnNullTest2() {
		assertNull("Range is Null", Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null));
	}

	@Test
	public void rangesNotNullTest() {
		assertNull("Range is Null",
				Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), new Range(Double.NaN, Double.NaN)));
	}

	@Test
	public void rangesNotNullTest2() {
		assertEquals("Range is Null", new Range(-5, 10), Range.combineIgnoringNaN(new Range(-5, 0), new Range(9, 10)));
	}

	@Test
	public void testCombineIgnoringNaN_NoNaN() {
		// none of the ranges is NaN, return new Range(l,u)
		Range range1 = new Range(1.0, 3.0);
		Range range2 = new Range(2.0, 5.0);
		Range expected = new Range(1.0, 5.0);
		assertEquals(expected, Range.combineIgnoringNaN(range1, range2));
	}

//	@Test
//	public void extraTestForMinLine() {
//		assertEquals("Range is Null", new Range(5, Double.NaN),
//				Range.combineIgnoringNaN(new Range(5, Double.NaN), new Range(Double.NaN, Double.NaN)));
//	}

//	@Test
//	public void extraTestForMaxLine() {
//		assertEquals("Range is Null", new Range(5, 5),
//				Range.combineIgnoringNaN(new Range(Double.NaN, 5), new Range(5, Double.NaN)));
//	}

	// ----------------------------------------------------------
	// Testing For the isNaNRange Method
	// ----------------------------------------------------------

	@Test
	public void isNaNRangeTestFalse() {
		assertFalse("isNaN Range", exampleRange.isNaNRange());
	}

	@Test
	public void isNaNRangeTestTrue() {
		assertTrue("isNaN Range", exampleRange2.isNaNRange());
	}

	@Test
	public void isNaNRangeTestFalse2() {
		assertFalse("isNaN Range", exampleRange3.isNaNRange());
	}

	@After
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

}
