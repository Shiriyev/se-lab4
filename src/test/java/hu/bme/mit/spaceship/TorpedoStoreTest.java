package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    // Failure rates that make the random firing predictable
    private static final double NEVER_FAILS = 0.0;
    private static final double ALWAYS_FAILS = 1.0;

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void constructor_SetsTorpedoCount() {
        // Arrange + Act
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Assert
        assertEquals(5, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void isEmpty_NoTorpedoes_ReturnsTrue() {
        // Arrange + Act
        TorpedoStore store = new TorpedoStore(0, NEVER_FAILS);

        // Assert
        assertTrue(store.isEmpty());
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_OneTorpedo_DecreasesCountByOne() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertTrue(result);
        assertEquals(4, store.getTorpedoCount());
    }

    @Test
    void fire_SeveralTorpedoes_DecreasesCountBySameNumber() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act
        boolean result = store.fire(3);

        // Assert
        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedoes_StoreBecomesEmpty() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, NEVER_FAILS);

        // Act
        boolean result = store.fire(2);

        // Assert
        assertTrue(result);
        assertTrue(store.isEmpty());
    }

    @Test
    void fire_Failure_ReturnsFalseAndKeepsTorpedoes() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, ALWAYS_FAILS);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertFalse(result);
        assertEquals(5, store.getTorpedoCount());
    }

    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1, NEVER_FAILS);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(2));
        assertEquals(1, store.getTorpedoCount());
    }

    @Test
    void fire_Zero_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_Negative_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, NEVER_FAILS);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
    }

    @Test
    void fire_EmptyStore_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(0, NEVER_FAILS);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
    }
}