package com.coen448.robot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RobotEngineTest {

    @Test
    void testInitializeResetsState() {
        RobotEngine e = new RobotEngine();
        e.initialize(5);

        assertEquals(5, e.size());
        assertEquals(0, e.getX());
        assertEquals(0, e.getY());
        assertEquals(Direction.NORTH, e.getDirection());
        assertEquals(PenState.UP, e.getPen());
    }

    @Test
    void testPenStates() {
        RobotEngine e = new RobotEngine();
        e.initialize(5);

        e.penDown();
        assertEquals(PenState.DOWN, e.getPen());

        e.penUp();
        assertEquals(PenState.UP, e.getPen());
    }

    @Test
    void testTurnRightLeft() {
        RobotEngine e = new RobotEngine();
        e.initialize(5);

        e.turnRight();
        assertEquals(Direction.EAST, e.getDirection());

        e.turnLeft();
        assertEquals(Direction.NORTH, e.getDirection());

        e.turnLeft();
        assertEquals(Direction.WEST, e.getDirection());
    }

    @Test
    void testDrawEast() {
        RobotEngine e = new RobotEngine();
        e.initialize(5);

        e.penDown();
        e.turnRight();
        e.move(3);

        assertEquals(1, e.getCell(0,0));
        assertEquals(1, e.getCell(0,1));
        assertEquals(1, e.getCell(0,2));
        assertEquals(1, e.getCell(0,3));
        assertEquals(3, e.getX());
        assertEquals(0, e.getY());
    }

    @Test
    void testBoundaryStopsMovement() {
        RobotEngine e = new RobotEngine();
        e.initialize(3);

        e.penDown();
        e.turnRight();
        e.move(10);

        assertEquals(2, e.getX());
        assertEquals(1, e.getCell(0,0));
        assertEquals(1, e.getCell(0,1));
        assertEquals(1, e.getCell(0,2));
    }

    @Test
    void testStatusString() {
        RobotEngine e = new RobotEngine();
        e.initialize(4);

        String s = e.statusString();
        assertTrue(s.contains("Position: 0, 0"));
        assertTrue(s.contains("Pen: up"));
        assertTrue(s.contains("Facing: north"));
    }

    @Test
    void testRenderFloorContainsStars() {
        RobotEngine e = new RobotEngine();
        e.initialize(5);

        e.penDown();
        e.turnRight();
        e.move(2);

        String out = e.renderFloorWithIndices();
        assertTrue(out.contains("*"));
    }

    @Test
    void testHistoryReplay() {
        RobotEngine e = new RobotEngine();
        e.executeCommand("I 5");
        e.executeCommand("D");
        e.executeCommand("R");
        e.executeCommand("M 2");

        int x = e.getX();
        int y = e.getY();
        int c0 = e.getCell(0,0);

        e.executeCommand("H");

        assertEquals(x, e.getX());
        assertEquals(y, e.getY());
        assertEquals(c0, e.getCell(0,0));
    }

    @Test
    void testMoveBeforeInitializeThrows() {
        RobotEngine e = new RobotEngine();
        assertThrows(IllegalStateException.class, () -> e.move(1));
    }

    @Test
    void testInitializeInvalidSizeThrows() {
        RobotEngine e = new RobotEngine();
        assertThrows(IllegalArgumentException.class, () -> e.initialize(0));
    }
}
