package ensta.rentmanager.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

public class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleDao vehicleDaoMock;

    @Before
    public void setUp() {
        vehicleDaoMock = mock(VehicleDao.class);
        vehicleService = new VehicleService(vehicleDaoMock);
    }

    @Test
    public void testCreateVehicle() throws DaoException, ServiceException {
        // Arrange
        Vehicle vehicle = new Vehicle(1, "Renault", "Clio", 5);
        when(vehicleDaoMock.create(vehicle)).thenReturn(1L);

        // Act
        long result = vehicleService.create(vehicle);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    public void testDeleteVehicle() throws DaoException, ServiceException {
        // Arrange
        Vehicle vehicle = new Vehicle(1, "Renault", "Clio", 5);
        when(vehicleDaoMock.delete(vehicle)).thenReturn(1L);

        // Act
        long result = vehicleService.delete(vehicle);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    public void testFindVehicleById() throws DaoException, ServiceException {
        // Arrange
        Vehicle vehicle = new Vehicle(1, "Renault", "Clio", 5);
        when(vehicleDaoMock.findById(1)).thenReturn(vehicle);

        // Act
        Vehicle result = vehicleService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(vehicle, result);
    }

    @Test
    public void testFindAllVehicles() throws DaoException, ServiceException {
        // Arrange
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle(1, "Renault", "Clio", 5);
        Vehicle vehicle2 = new Vehicle(2, "Peugeot", "208", 5);
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        when(vehicleDaoMock.findAll()).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vehicles, result);
    }

    @Test
    public void testCountVehicles() throws DaoException, ServiceException {
        // Arrange
        when(vehicleDaoMock.count()).thenReturn(5);

        // Act
        int result = vehicleService.count();

        // Assert
        assertEquals(5, result);
    }
}

