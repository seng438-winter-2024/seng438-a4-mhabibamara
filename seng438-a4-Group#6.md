**SENG 438 - Software Testing, Reliability, and Quality**

**Lab. Report \#4 – Mutation Testing and Web app testing**

| Group \#:      |  6   |
| -------------- | --- |
| Student Names: |  Mohamed Amara   |
|                |  Nour Ajami   |
|                |  Krishna Shah   |
|                |  Zuhaer Rahman   |

# Introduction
This lab comprises two parts: Mutation Testing and GUI Testing. In the first part, our group delve into Mutation Testing, learning to inject faults into a Java codebase and interpret mutation scores to improve test suite quality. The second part focused on GUI Testing, where we explored Selenium for web interface testing and compare it with alternative tools. Through hands-on exercises, our group gained practical insights into software testing methodologies and tools, preparing us for real-world testing challenges.

# Analysis of 10 Mutants of the Range class 
1. **combine** Method

   **Original Code:** `if(range1 == null)`

   **Mutated Code:** `if(false)`

   **Analysis:** This mutant was killed by the test `range1IsNullTest`, the expected range returned should have been the same as the second input range `range2`, but instead an unexpected value was received since the mutant code was using an unexpected value the max and min values.


2.  **combine** Method
    **Original Code:** `return range1`

    **Mutated Code:** `return null`

    **Analysis:** This mutant was killed by the test `range2IsNullTest`, as the expected range to be returned should have been the same as the first input range `range1` since the second one from the test is null. The return value of the mutated code was null which was unexpected and thus led to the mutant being killed.
    

3. **constrain** Method
   **Original Code:** `if (value > this.upper)`

   **Mutated Code:** `If (value >= this.upper)`

   **Analysis:** This mutant survived, as there are no tests that were used to check if the value is equal to the upper range value of the range that it is being used on.


4. **intersects** Method

   **Original Code**: `return (b0 < this.upper && b1 >= b0)`

   **Mutated Code**: `return (b0 <= this.upper && b1 >= b0`

   **Analysis:** This mutant survived. The mutant code was not detected since there are no tests for intersects that use the arguments that are equivalent to the upper value of the range.

5. **intersects** Method

   **Original Code**: `if (b0 <= this.lower)`

   **Mutated Code**: `if (b0 < this.lower)`

   **Analysis:** This mutant survived since none of the tests for this method tested the arguments that were the same as the lower value of the range in this case.
   
6. **getCentralValue** Method

   **Original Code:** `return this.lower / 2.0 + this.upper / 2.0`

   **Mutated Code:** `return this.lower / 1.0 + this.upper / 2.0`

   **Analysis:** This mutant was killed. The single test that is present for this method `centralValueBetweenRange` was able to kill this mutation since the lower bound value was behaving differently and the center value was different than the expected one.


7. **getCentralValue** Method

   **Original Code:** `return this.lower / 2.0 + this.upper / 2.0`

   **Mutated Code:** `return this.lower / 2.0 + this.upper / 1.0`

   **Analysis:** This mutant was killed. The single test that is present for this method `centralValueBetweenRange` was able to kill this mutation since the upper bound value was behaving differently and the center value was different than the expected one.


8. **getLength** Method

   **Original Code:** `return this.upper - this.lower`

   **Mutated Code** `return this.upper + this.lower`

   **Analysis:** This mutant was killed. The mutant was killed by the test `getPositiveLengthTest()` this is because the original calculation of this test is 20 - 5 = 15, but since the operation was replaced with addition then it became 20 + 5 = 25 which is the unexpected value.

9. **expand** Method

   **Original Code**: `double lower = range.getLowerBound() - length * lowerMargin`

   **Mutated Code:** `double lower = range.getLowerBound() + length * lowerMargin`

   **Analysis:** This mutation was killed. This will now compute the addition of the lower bound range with the length multiplied by the lowerMargin. The test case `expandLowerBoundToNegative()` killed this mutation because the lowerMargin is incorrect now and thus the test fails.


10. **equals** Method

    **Original Code:** `if (!(obj instanceof Range)) `

    **Mutated Code:** `if (true)`

    **Analysis:** This mutant was killed. The test `rangeObjectsAreEqual()` was able to detect and kill this mutation since an argument of type Range.class was sent to the equals method. This test should return true, but with this mutant code it will always return false which is the unexpected behavior that was detected and killed.
    
# Report all the statistics and the mutation score for each test class

## Original Mutation Test Scores
### Range Original Mutation Coverage (61%)
![image](https://github.com/seng438-winter-2024/seng438-a4-mhabibamara/assets/103873879/9ad2e7cd-55fa-4a65-8b57-8348723d0465)

### DataUtilities Original Mutation Coverage (64%)
![image](https://github.com/seng438-winter-2024/seng438-a4-mhabibamara/assets/103873879/9f99226a-d020-4005-a9b3-bcb3a4cd9f26)

## Added Tests
### Range Class
**lowerBoundRangeObjectsEqual()**

Testing 2 Ranges with equal lower bound, but will still not be equal overall. This test case checks the equals() method with two Range objects that have the same lower bound but different upper bounds. It kills a mutant where the conditional if (!(this.upper == range.upper)) is replaced with false.

**testGetLength()**

**testIntersects()**

**expandRangeby0()**

Description Overall: : In order to make sure that the furture mutation were killed, these test cases were made to sepecfically target the mutants that survived. Many of them were the result of missing condition checking, and so we were able to repeat somewhat identical tests with a slight variation of some of the inputs. This is due to the fact that the mutants that survived often came from conditions and math mutations.

### DataUtilities Class
**calculateColumnTotalRowGreaterThanRowTotalTest()**

This test was made for our fourth lab assignment, and its major purpose was to check the DataUtilities.java method calculateColumnTotal's boundary condition, which is row < rowCount. This means that the method needs a rowCount that is bigger than the valid rows that are being supplied into it. This new test case is intended primarily to add to our overall mutation coverage results for this class, as we did not account for it in the prior lab. This is achieved in the test case by having row being greater with a value of 4 and rowCount only being 1. JMock, a mocking framework, is used to generate this mock object with the necessary properties.

**calculateColumnTotalNEqualNullTest()**

For this test case we were testing a different part of the method which was using JMock framework and using a mock object with the statement of if n != null this mutant had survived in our original test suite which did not account for this, hence prompting us to create a test for increasing our overall mutation coverage. This test passed a null value as n which made that row disregard this value when calculating the total sum of the values in that column which is specified.

**calculateRowTotalChangedConditionalBoundaryTest()**

In order to eliminate the mutation labeled "changed conditional boundary" of DataUtilities.java, which had withstood the initial test suite, we created this test to evaluate the boundary for the condition "if (col = colCount)". In order to accomplish this, we built a mock object Values2D object that returns 1 when getColumnCount() is called. We supplied the validRows option with the value 1. ColCount and col both receive a value of 1 in the calculateRowTotal function, covering the border condition and eliminating the mutant.

**calculateColumnTotalRowEqualsRowTotalTest()**

Because this mutant was able to withstand our original source code, we were able to eliminate it with our test case, which once more builds on the JMock framework and creates a mock object. This time, however, we were testing for the boundary condition of making the row and rowCount equal to each other, which the method does not take into account and which causes the running total of the column to equal zero because it lacks a specific path to follow. Our test case's conditional testing of equality guaranteed that the mutation was eliminated.

Note: More test cases were added to further enhance and strengthen our test suites

# Analysis drawn on the effectiveness of each of the test classes
After adding the aforementioned test cases, the following improvements were made:

## New Mutation Test Scores
### Range New Mutation Coverage (73%)
![image](https://github.com/seng438-winter-2024/seng438-a4-mhabibamara/assets/103873879/b58883ea-6b21-4db9-addf-c548a71542e4)

### DataUtilities New Mutation Coverage (75%)
![image](https://github.com/seng438-winter-2024/seng438-a4-mhabibamara/assets/103873879/930eed74-ad42-4f2e-97ad-b8dde44fa773)

# A discussion on the effect of equivalent mutants on mutation score accuracy
Equivalent mutants, by definition, do not alter the program's behavior as perceived by the test suite. Therefore, they should not be considered as true faults. Including equivalent mutants in the mutation score calculation can artificially inflate the mutation score, leading to a false sense of security about the code's robustness. If equivalent mutants are not properly identified and excluded from the analysis, testing efforts may be diluted as we tend to focus on addressing non-existent faults. Resources may be wasted on addressing perceived issues that do not actually impact the system's behavior, diverting attention from genuine defects. One way our group tried to mitigate this impact is by developing criteria to identify and classify equivalent mutants based on their behavior and impact on the program.

# A discussion of what could have been done to improve the mutation score of the test suites
Some ways we can improve the mutation score of the test suites can be to optimize test oracles and prioritize mutants. Clear and precise test oracles enable more effective detection of faults by providing a reliable baseline for comparison between the original code and mutated versions. Moreover, focusing testing efforts on high-priority mutants allows for more efficient allocation of resources and may result in a more significant improvement in mutation score. By implementing these strategies, we can enhance the fault-detection capabilities of both the Range and DataUtilities test suites, leading to a higher mutation score and ultimately improving the overall quality and reliability of the software.

# Why do we need mutation testing? Advantages and disadvantages of mutation testing
Mutation testing is needed in order to enhance the effectiveness of the testing process and increase the detection of faults.

### Advantages
**Efficiency:** Automation reduces the manual effort required to detect equivalent mutants.

**Systematic Coverage:** Automated tools can comprehensively analyze the codebase and identify potential equivalent mutants across various scenarios.

**Consistency:** Automated detection ensures consistent results and reduces the likelihood of human error.

### Disadvantages
**False Positives:** Automated tools may produce false positives, mistakenly identifying mutants as equivalent when they are not.

**Limited Scope:** Automated tools might not capture all possible equivalent mutants, especially those requiring complex reasoning or domain-specific knowledge.

**Tool Dependency:** The effectiveness of automated detection relies on the capabilities and limitations of the chosen mutation testing tool.

# Explain your SELENUIM test case design process

We started our design process by talking about possible use cases of EBay as a team.  Then for each use case, we determined different inputs and possible outputs of the application.

| Functionality | Test # 1 | Test # 2 |
| --- | --- | --- |
| Login | Successful Login | Failed Login (wrong password)|
| Watchlist | Empty Watchlist (no items inside) | Populated Watchlist|
| Searching | Search with valid query | Search with invalid query|
| Cart | Adding to Cart | Remove From Cart|
| Shipping | Valid Shipping Information | Invalid Shipping Information|
| Filtering | Adding a filter| Clearing Filter |
| Navigation | Adjust Categories| Get Information|
| Logout | Logout a previously logged in user | |
| Messages | View user messages | |
| Language Change | Change website Language | |

# Explain the use of assertions and checkpoints


| Functionality | Verification Point Type |
| --- | --- |
| Login (Test 1)| assertText |
| Login (Test 2)| assert element present |
| Watchlist (Test 1 & 2)| assert element present |
| Searching (Test 1)| assertText |
| Searching (Test 2)| assert element present |
| Cart (Test 1 & 2)| verify element present |
| Shipping (Test 1 & 2)| verify element present |
| Filtering (Test 1 & 2)| verify element present |
| Navigation (Test 1 & 2)| verify text |
| Logout (Test 1)| assert text |
| Messages (Test 1)| assert text |
| Language Change (Test 1)| assert text |

Assertions and checkpoints were used towards the end of each test to verify whether specific elements or text appear. An example of this would be the invalid login test, where we made sure that the error message and icon elements were present.


# how did you test each functionaity with different test data

We tested each functionality with different data by thinking of the possible outputs for each use case. An example of this would using valid/invalid information for a login and using a valid/invalid query for searching for a item. However, some of the functionality we tested only made sense to use 1 test case (logging out), as there were no other possible data inputs/ways to achieve the test.

# Discuss advantages and disadvantages of Selenium vs. Sikulix

- Selenium (Advantages) : Ease of access, installation and helpful features such as pausing mid-test. 
- Selenium (Disadvantages) : The IDE had a hard time replicating tests that included hover events, often leading the test to stall and then fail.
- Sikulix (Advantages) : Ability to use images allowed the tests to work more smoothly with complex events (hovering)
- Sikulix (Disadvantages) : Very slow

# How the team work/effort was divided and managed
Initially, tasks were identified based on individual strengths and interests, ensuring a balanced distribution of responsibilities. Virtual meetings were held to split the work evenly and manage individual progress. Overall, this lab was a joint effort and our teamwork in all parts of the lab ensyred the smooth progress and completion of this assignment.

# Difficulties encountered, challenges overcome, and lessons learned
Initially, understanding the intricacies of mutation testing and effectively utilizing mutation testing tools presented a significant challenge. Moreover, technical issues such as compatibility issues with the mutation testing tool, setup/configuration errors, and debugging challenges emerged during the experimentation phase. These aforementioned challenges were overcome through effective communication between the group and leveraging online resources to gain a deeper understanding of the tools and analyzing the results of these tools. In conclusion, these experiences serve as valuable lessons that will inform and enrich our future endeavors in software development and testing.

# Comments/feedback on the lab itself
Overall, our group found the lab document easy to follow and work through. In addition, we found it as practical experience in assessing the efficacy of our test suites developed in previous assignments. Moreover, this lab helped broaden our understanding of GUI testing frameworks and their respective strengths and limitations. However, we think the lab was a bit too time-consuming and required lots of effort to complete.
