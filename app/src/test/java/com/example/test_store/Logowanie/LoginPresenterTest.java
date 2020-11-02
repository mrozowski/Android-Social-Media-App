package com.example.test_store.Logowanie;

import android.util.Log;

import com.example.test_store.Database.Database;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class LoginPresenterTest {
    @Mock
    private Login view;
    private LoginPresenter presenter;
    private Database mockedDatabase;

    @Before
    public void setUp(){
        mockedDatabase = Mockito.mock(Database.class);
        view = new Login();
        presenter = new LoginPresenter(view, mockedDatabase);
    }

    @Test
    public void validateEmail_emptyEmail(){
        String emptyEmail = "";

        boolean result = presenter.validateEmail(emptyEmail);
        assertFalse(result);
    }

    @Test
    public void validateEmail_incorrectEmail(){
        String emptyEmail = "com";

        boolean result = presenter.validateEmail(emptyEmail);
        assertFalse(result);
    }

    @Test
    public void validateEmail_correctEmail(){
        String emptyEmail = "test@gmail.com";

        boolean result = presenter.validateEmail(emptyEmail);
        assertTrue(result);
    }
}