package testUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modelo.Estacion;

class EstacionTest {

	@Test
	void testObtenerId() {
		Estacion estacion = new Estacion("E1", "Nombre", 0, 0);
		assertEquals("E1", estacion.getId());
	}

	@Test
	void testObtenerNombre() {
		Estacion estacion = new Estacion("E1", "Nombre", 0, 0);
		assertEquals("Nombre", estacion.getNombre());
	}

	@Test
	void testObtenerLatitud() {
		Estacion estacion = new Estacion("E1", "Nombre", -34.5, 0);
		assertEquals(-34.5, estacion.getLatitud(), 1e-6);
	}

	@Test
	void testObtenerLongitud() {
		Estacion estacion = new Estacion("E1", "Nombre", 0, -58.4);
		assertEquals(-58.4, estacion.getLongitud(), 1e-6);
	}

	@Test
	void testIgualdadMismaInstancia() {
		Estacion estacion = new Estacion("E1", "Nombre", 0, 0);
		assertEquals(estacion, estacion);
	}

	@Test
	void testDesigualdadDistintasInstancias() {
		Estacion a = new Estacion("E1", "Nombre", 0, 0);
		Estacion b = new Estacion("E1", "Nombre", 0, 0);
		assertNotEquals(a, b);
	}

	@Test
	void testHashCodeConsistentePorInstancia() {
		Estacion estacion = new Estacion("E2", "Nombre", 0, 0);
		int primerHash = estacion.hashCode();
		int segundoHash = estacion.hashCode();
		assertEquals(primerHash, segundoHash);
	}

	@Test
	void testHashCodeDiferenteParaInstanciasDistintas() {
		Estacion a = new Estacion("E1", "Nombre", 0, 0);
		Estacion b = new Estacion("E1", "Nombre", 0, 0);
		assertNotEquals(a.hashCode(), b.hashCode());
	}


   


    @Test
    void testEqualsMismaInstancia() {
        Estacion estacion = new Estacion("E1", "Nombre", 0, 0);
        assertEquals(estacion, estacion);
    }

    @Test
    void testEqualsInstanciasDistintas() {
        Estacion a = new Estacion("E1", "Nombre", 0, 0);
        Estacion b = new Estacion("E1", "Nombre", 0, 0);
        assertNotEquals(a, b);
    }

    @Test
    void testHashCodeConsistente() {
        Estacion estacion = new Estacion("E2", "Nombre", 0, 0);
        int hash1 = estacion.hashCode();
        int hash2 = estacion.hashCode();
        assertEquals(hash1, hash2);
    }

}