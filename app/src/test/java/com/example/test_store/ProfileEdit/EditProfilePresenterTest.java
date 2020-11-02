package com.example.test_store.ProfileEdit;

import com.example.test_store.Database.Database;
import com.example.test_store.Profile.Profile;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class EditProfilePresenterTest {

    @Mock
    private EditProfileView view;
    private EditProfilePresenter presenter;
    private Database mockedDatabase;

    @Before
    public void setUp(){
        mockedDatabase = Mockito.mock(Database.class);
        view = new EditProfileView();
        presenter = new EditProfilePresenter(view, mockedDatabase);
    }

    @Test
    public void validateEmail_emptyEmail(){
        String empty_email = "";
        boolean result = presenter.validateEmail(empty_email);
        assertFalse(result);
    }
}