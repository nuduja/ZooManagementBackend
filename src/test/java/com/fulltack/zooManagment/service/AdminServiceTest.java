package com.fulltack.zooManagment.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import com.fulltack.zooManagment.Requests.AdminRequest;
import com.fulltack.zooManagment.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fulltack.zooManagment.repository.AdminRepository;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import com.fulltack.zooManagment.service.AdminService;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PDFGeneratorService pdfGeneratorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        // You can initialize objects or set up common mocking behavior here
    }

    @Test
    void testConvertToAdmin() {
        AdminRequest adminRequest = new AdminRequest("John", "johnDoe", "pass123", "ADMIN");
        Admin admin = adminService.convertToAdmin(adminRequest);
        assertThat(admin.getName()).isEqualTo("John");
        assertThat(admin.getUsername()).isEqualTo("johnDoe");
        assertThat(admin.getRole()).isEqualTo("ADMIN");
        assertNotNull(admin.getAdminId());  // Ensure ID is generated
    }

    @Test
    void testGetAllAdmins() {
        when(adminRepository.findAll()).thenReturn(Arrays.asList(new Admin(), new Admin()));
        assertThat(adminService.getAllAdmins()).hasSize(2);
    }

    @Test
    void testAddAdmin_AdminDoesNotExist() {
        AdminRequest adminRequest = new AdminRequest("Jane", "janeDoe", "password", "ADMIN");
        when(adminRepository.existsByUsername("janeDoe")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String response = adminService.addAdmin(adminRequest);

        assertThat(response).isEqualTo("Admin Created Successful");
        verify(adminRepository).save(any(Admin.class));  // Ensure save is called
    }

    @Test
    void testAddAdmin_AdminExists() {
        AdminRequest adminRequest = new AdminRequest("Jane", "janeDoe", "password", "ADMIN");
        when(adminRepository.existsByUsername("janeDoe")).thenReturn(true);

        String response = adminService.addAdmin(adminRequest);

        assertThat(response).isEqualTo("Admin User already exists");
        verify(adminRepository, never()).save(any(Admin.class));
    }

//    @Test
//    void testLogin_Successful() {
//        when(adminRepository.existsByUsername("johnDoe")).thenReturn(true);
//        when(adminRepository.findByUsername("johnDoe")).thenReturn(new Admin("johnDoe", "encodedPass", "ADMIN"));
//        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
//
//        boolean isLoggedIn = adminService.login("johnDoe", "password");
//
//        assertThat(isLoggedIn).isTrue();
//    }
//
//    @Test
//    void testLogin_Unsuccessful() {
//        when(adminRepository.existsByUsername("johnDoe")).thenReturn(true);
//        when(adminRepository.findByUsername("johnDoe")).thenReturn(new Admin("johnDoe", "encodedPass", "ADMIN"));
//        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(false);
//
//        boolean isLoggedIn = adminService.login("johnDoe", "password");
//
//        assertThat(isLoggedIn).isFalse();
//    }
}

