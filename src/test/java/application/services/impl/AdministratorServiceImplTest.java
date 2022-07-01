package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Administrator;
import data.dao.impl.MySQLAdministratorDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AdministratorServiceImplTest {

    @Test
    public void creationOfNewAdministratorWithNewAccountIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doNothing().when(administratorDAOMock).createAdministrator(eq("newLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(true, "Account was successfully created!");
        OperationResult actual = administratorService.createAdministrator("newLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }


    @Test
    public void creationOfNewAdministratorWithExistedAccountIsThrowingExceptionCausingFalseOperationResult() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doThrow(AlreadyExistException.class).when(administratorDAOMock).createAdministrator(eq("existedLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(false, "Login already exist");
        OperationResult actual = administratorService.createAdministrator("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToLoginAsAnAdministratorWithRightCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doReturn(new Administrator()).when(administratorDAOMock).findAdministrator(eq("rightLogin"), eq("rightPass"));

        ValuedOperationResult<Administrator> expected = new ValuedOperationResult<>(true, "You logged as Administrator", new Administrator());
        ValuedOperationResult<Administrator> actual = administratorService.findAdministrator("rightLogin", "rightPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsAnAdministratorWithWrongCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doThrow(NotExistException.class).when(administratorDAOMock).findAdministrator(eq("wrongLogin"), eq("wrongPass"));

        ValuedOperationResult<Administrator> expected = new ValuedOperationResult<>(false, "Administrator with login = " +
                "wrongLogin does not exist", null);
        ValuedOperationResult<Administrator> actual = administratorService.findAdministrator("wrongLogin", "wrongPass");

        assertEquals(expected, actual);
    }

    @Test
    public void AccountWasSuccessfullyDeletedWhenMethodWasUsedWithExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doNothing().when(administratorDAOMock).deleteAccount(any(Integer.class));

        OperationResult expected = new OperationResult(true, "Account was deleted");
        OperationResult actual = administratorService.deleteAccount(1);

        assertEquals(expected, actual);
    }

    @Test
    public void ExceptionWasThrownWhenTryingToDeleteAccountWithUnExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        AdministratorDAO administratorDAOMock = mock(MySQLAdministratorDAO.class);
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl(daoFactoryMock);

        doReturn(administratorDAOMock).when(daoFactoryMock).getAdministratorDAO();
        doThrow(DAOException.class).when(administratorDAOMock).deleteAccount(any(Integer.class));

        OperationResult expected = new OperationResult(false, "Account was not deleted");
        OperationResult actual = administratorService.deleteAccount(1);

        assertEquals(expected, actual);
    }


}