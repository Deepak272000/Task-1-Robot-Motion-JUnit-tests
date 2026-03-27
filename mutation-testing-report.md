
## Introduction
This report documents a manual mutation testing exercise performed on the RobotEngine Java project. The goal was to evaluate the effectiveness of the JUnit test suite by introducing small code changes (mutants) and observing whether the tests could detect them. Each mutant represents a potential fault that could occur during development.

## Project: RobotEngine (Java)

### Mutation Testing Assignment
- **Method:** Manual mutation (AI prompt-based)
- **Test Suite:** JUnit (RobotEngineTest.java)
- **Number of Mutants:** 5
- **Number of Test Cases Selected:** 5

## Selected Test Cases
1. `testMovePenUpFloorUnchanged`
2. `testMovePenDownFloorUpdated`
3. `testMoveAllDirections`
4. `testMoveBeyondBoundaryStops`
5. `testAppCommandSequence`

## Mutation Table

| # | Mutation Description                                 | Location (Method)         | Rationale / Fault Simulated                | Mutant Status | Killed By Test Cases                        |
|---|------------------------------------------------------|---------------------------|--------------------------------------------|---------------|---------------------------------------------|
| 1 | Reverse NORTH direction logic in move                | move(int steps)           | Simulates a logic error in direction       | Killed        | testMoveAllDirections, testMovePenDownFloorUpdated |
| 2 | inBounds always returns true                         | inBounds(int px, int py)  | Simulates missing boundary checks          | Killed        | testMoveBeyondBoundaryStops, testMoveAllDirections |
| 3 | Mark floor when pen is UP instead of DOWN            | move/internalMove         | Simulates pen state logic error            | Killed        | testMovePenDownFloorUpdated, testMovePenUpFloorUnchanged |
| 4 | Remove negative steps check in move                  | move(int steps)           | Simulates missing input validation         | Killed        | testMoveNegativeStepsThrows                 |
| 5 | Disable floor marking when pen is DOWN (internalMove)| internalMove(int steps)   | Simulates missing effect of pen state      | Killed        | testMovePenUpFloorUnchanged, testMovePenDownFloorUpdated, testAppCommandSequence |
| # | Mutation Description                                 | Mutant Status | Killed By Test Cases                        |
|---|------------------------------------------------------|---------------|---------------------------------------------|
| 1 | Reverse NORTH direction logic in move                | Killed        | testMoveAllDirections, testMovePenDownFloorUpdated |
| 2 | inBounds always returns true                         | Killed        | testMoveBeyondBoundaryStops, testMoveAllDirections |
| 3 | Mark floor when pen is UP instead of DOWN            | Killed        | testMovePenDownFloorUpdated, testMovePenUpFloorUnchanged |
| 4 | Remove negative steps check in move                  | Killed        | testMoveNegativeStepsThrows                 |
| 5 | Disable floor marking when pen is DOWN (internalMove)| Killed        | testMovePenUpFloorUnchanged, testMovePenDownFloorUpdated, testAppCommandSequence |


## Summary

- **Total Mutants:** 5
- **Killed Mutants:** 5
- **Live Mutants:** 0
- **Mutation Score:** 100%

### Mutation Process
For each mutant, the code was manually edited at the specified location, the test suite was executed, and the results were recorded. After each run, the code was reverted to its original state before introducing the next mutation. This ensured only one mutant was active at a time.


### Effectiveness
All selected test cases were effective in killing the introduced mutants. The test suite demonstrates strong coverage for the tested behaviors, especially for direction logic, boundary checks, pen state, and floor marking.

### Lessons Learned
- The test suite is robust against common logic and state errors in robot movement and pen handling.
- Mutations targeting direction, boundary, and pen logic were all detected by at least one test case.
- Manual mutation testing is a practical way to validate test effectiveness even without automated mutation tools.

---
## Dynamic Slicing Example

To further analyze the effectiveness of the test suite, we performed dynamic slicing for a failing test case and mutant. Dynamic slicing identifies all statements that actually affected the value checked by a specific assertion during a test execution.

**Example:**
Test Case: `testMovePenDownFloorUpdated`
Mutant: Disabled floor marking when pen is DOWN (internalMove)
Assertion: `assertEquals(1, e.getCell(0,0));`

**Dynamic Slice for this execution:**
1. `RobotEngine.initialize(4)` — sets up the floor array
2. `RobotEngine.penDown()` — sets pen state to DOWN
3. `RobotEngine.move(2)` — should mark floor cells as 1 when pen is DOWN
4. Assignment `floor[y][x] = 1;` inside `move`/`internalMove` — this is skipped by the mutant
5. `RobotEngine.getCell(0,0)` — returns the value checked by the assertion

**Explanation:**
The dynamic slice shows how the mutant (skipping the floor marking assignment) directly affects the value returned by `getCell(0,0)`, causing the test to fail. This demonstrates the propagation of the mutation's effect through the program state to the test output.


### Final Observation: AI Integration in Testing

During this assignment, I observed that integrating AI (such as Copilot or prompt-based mutation guidance) with the testing environment is highly beneficial. AI can:
- Suggest meaningful mutation points and test scenarios quickly.
- Automate repetitive or error-prone manual steps, reducing human oversight.
- Help visualize and document the mutation-testing process for better learning and reporting.
- Accelerate the feedback loop between code changes and test results.
- Enhance the overall quality and coverage of the test suite by proposing edge cases or overlooked logic paths.

This synergy between AI and traditional testing tools makes the process more efficient, thorough, and insightful for both students and professionals.