//package com.example.authorbookrest.endpoint;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.ArgumentMatchers.isA;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.mockStatic;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.example.authorbookrest.dto.AuthRequestDto;
//import com.example.authorbookrest.dto.AuthResponseDto;
//import com.example.authorbookrest.dto.CreateUserRequestDto;
//import com.example.authorbookrest.dto.UserDto;
//import com.example.authorbookrest.dto.UserType;
//import com.example.authorbookrest.entity.User;
//import com.example.authorbookrest.mapper.UserMapper;
//import com.example.authorbookrest.mapper.UserMapperImpl;
//import com.example.authorbookrest.repository.UserRepository;
//import com.example.authorbookrest.service.UserService;
//import com.example.authorbookrest.service.impl.UserServiceImpl;
//import com.example.authorbookrest.util.JwtTokenUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.OpenOption;
//import java.nio.file.Path;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.aot.DisabledInAotMode;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.multipart.MultipartFile;
//
//@ContextConfiguration(classes = {UserEndpoint.class, PasswordEncoder.class})
//@ExtendWith(SpringExtension.class)
//@DisabledInAotMode
//class UserEndpointDiffblueTest {
//    @MockBean
//    private JwtTokenUtil jwtTokenUtil;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserEndpoint userEndpoint;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @MockBean
//    private UserService userService;
//
//    /**
//     * Method under test: {@link UserEndpoint#register(CreateUserRequestDto)}
//     */
//    @Test
//    void testRegister() {
//        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//        // Arrange
//        User user = new User();
//        user.setEmail("jane.doe@example.org");
//        user.setId(1);
//        user.setImagePath("Image Path");
//        user.setName("Name");
//        user.setPassword("iloveyou");
//        user.setSurname("Doe");
//        user.setUserType(UserType.USER);
//        Optional<User> ofResult = Optional.of(user);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserMapperImpl userMapper2 = new UserMapperImpl();
//        UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, userMapper2, new JwtTokenUtil());
//
//        // Act
//        ResponseEntity<UserDto> actualRegisterResult = userEndpoint
//                .register(new CreateUserRequestDto("Name", "Doe", "jane.doe@example.org", "iloveyou"));
//
//        // Assert
//        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
//        assertNull(actualRegisterResult.getBody());
//        assertEquals(409, actualRegisterResult.getStatusCodeValue());
//        assertTrue(actualRegisterResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#register(CreateUserRequestDto)}
//     */
//    @Test
//    void testRegister2() {
//        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//        // Arrange
//        User user = new User();
//        user.setEmail("jane.doe@example.org");
//        user.setId(1);
//        user.setImagePath("Image Path");
//        user.setName("Name");
//        user.setPassword("iloveyou");
//        user.setSurname("Doe");
//        user.setUserType(UserType.USER);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
//        Optional<User> emptyResult = Optional.empty();
//        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserMapperImpl userMapper2 = new UserMapperImpl();
//        UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, userMapper2, new JwtTokenUtil());
//
//        // Act
//        ResponseEntity<UserDto> actualRegisterResult = userEndpoint
//                .register(new CreateUserRequestDto("Name", "Doe", "jane.doe@example.org", "iloveyou"));
//
//        // Assert
//        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
//        verify(userRepository).save(isA(User.class));
//        UserDto body = actualRegisterResult.getBody();
//        assertEquals("Doe", body.getSurname());
//        assertEquals("Image Path", body.getImagePath());
//        assertEquals("Name", body.getName());
//        assertEquals("jane.doe@example.org", body.getEmail());
//        assertEquals(1, body.getId());
//        assertEquals(200, actualRegisterResult.getStatusCodeValue());
//        assertEquals(UserType.USER, body.getUserType());
//        assertTrue(actualRegisterResult.hasBody());
//        assertTrue(actualRegisterResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#uploadImage(int, MultipartFile)}
//     */
//    @Test
//    void testUploadImage() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//            // Arrange
//            mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
//                    .thenReturn(new ByteArrayOutputStream(1));
//
//            User user = new User();
//            user.setEmail("jane.doe@example.org");
//            user.setId(1);
//            user.setImagePath("Image Path");
//            user.setName("Name");
//            user.setPassword("iloveyou");
//            user.setSurname("Doe");
//            user.setUserType(UserType.USER);
//            Optional<User> ofResult = Optional.of(user);
//
//            User user2 = new User();
//            user2.setEmail("jane.doe@example.org");
//            user2.setId(1);
//            user2.setImagePath("Image Path");
//            user2.setName("Name");
//            user2.setPassword("iloveyou");
//            user2.setSurname("Doe");
//            user2.setUserType(UserType.USER);
//            UserRepository userRepository = mock(UserRepository.class);
//            when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
//            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//            UserMapperImpl userMapper = new UserMapperImpl();
//            UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            UserMapperImpl userMapper2 = new UserMapperImpl();
//            UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, userMapper2, new JwtTokenUtil());
//
//            // Act
//            ResponseEntity<UserDto> actualUploadImageResult = userEndpoint.uploadImage(1,
//                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
//
//            // Assert
//            mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
//            verify(userRepository).findById(eq(1));
//            verify(userRepository).save(isA(User.class));
//            UserDto body = actualUploadImageResult.getBody();
//            assertEquals("Doe", body.getSurname());
//            assertEquals("Name", body.getName());
//            assertEquals("jane.doe@example.org", body.getEmail());
//            assertEquals(1, body.getId());
//            assertEquals(200, actualUploadImageResult.getStatusCodeValue());
//            assertEquals(UserType.USER, body.getUserType());
//            assertTrue(actualUploadImageResult.hasBody());
//            assertTrue(actualUploadImageResult.getHeaders().isEmpty());
//        }
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#getImage(String)}
//     */
//    @Test
//    void testGetImage() throws Exception {
//        // Arrange
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users/getImage")
//                .param("picName", "foo");
//
//        // Act and Assert
//        MockMvcBuilders.standaloneSetup(userEndpoint)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#login(AuthRequestDto)}
//     */
//    @Test
//    void testLogin() {
//        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//        // Arrange
//        User user = new User();
//        user.setEmail("jane.doe@example.org");
//        user.setId(1);
//        user.setImagePath("Image Path");
//        user.setName("Name");
//        user.setPassword("iloveyou");
//        user.setSurname("Doe");
//        user.setUserType(UserType.USER);
//        Optional<User> ofResult = Optional.of(user);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserMapperImpl userMapper2 = new UserMapperImpl();
//        UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, userMapper2, new JwtTokenUtil());
//
//        // Act
//        ResponseEntity<AuthResponseDto> actualLoginResult = userEndpoint
//                .login(new AuthRequestDto("jane.doe@example.org", "iloveyou"));
//
//        // Assert
//        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
//        assertNull(actualLoginResult.getBody());
//        assertEquals(401, actualLoginResult.getStatusCodeValue());
//        assertTrue(actualLoginResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#login(AuthRequestDto)}
//     */
//    @Test
//    void testLogin2() {
//        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//        // Arrange
//        UserRepository userRepository = mock(UserRepository.class);
//        Optional<User> emptyResult = Optional.empty();
//        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserMapperImpl userMapper2 = new UserMapperImpl();
//        UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, userMapper2, new JwtTokenUtil());
//
//        // Act
//        ResponseEntity<AuthResponseDto> actualLoginResult = userEndpoint
//                .login(new AuthRequestDto("jane.doe@example.org", "iloveyou"));
//
//        // Assert
//        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
//        assertNull(actualLoginResult.getBody());
//        assertEquals(401, actualLoginResult.getStatusCodeValue());
//        assertTrue(actualLoginResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#login(AuthRequestDto)}
//     */
//    @Test
//    void testLogin3() {
//        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
//
//        // Arrange
//        User user = new User();
//        user.setEmail("jane.doe@example.org");
//        user.setId(1);
//        user.setImagePath("Image Path");
//        user.setName("Name");
//        user.setPassword("iloveyou");
//        user.setSurname("Doe");
//        user.setUserType(UserType.USER);
//        Optional<User> ofResult = Optional.of(user);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
//        UserMapperImpl userMapper = new UserMapperImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new BCryptPasswordEncoder());
//
//        JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
//        when(jwtTokenUtil.generateToken(Mockito.<String>any())).thenReturn("ABC123");
//        LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
//        UserEndpoint userEndpoint = new UserEndpoint(userService, passwordEncoder, new UserMapperImpl(), jwtTokenUtil);
//
//        // Act
//        ResponseEntity<AuthResponseDto> actualLoginResult = userEndpoint
//                .login(new AuthRequestDto("jane.doe@example.org", "iloveyou"));
//
//        // Assert
//        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
//        verify(jwtTokenUtil).generateToken(eq("jane.doe@example.org"));
//        AuthResponseDto body = actualLoginResult.getBody();
//        assertEquals("ABC123", body.getToken());
//        UserDto userDto = body.getUserDto();
//        assertEquals("Doe", userDto.getSurname());
//        assertEquals("Image Path", userDto.getImagePath());
//        assertEquals("Name", userDto.getName());
//        assertEquals("jane.doe@example.org", userDto.getEmail());
//        assertEquals(1, userDto.getId());
//        assertEquals(200, actualLoginResult.getStatusCodeValue());
//        assertEquals(UserType.USER, userDto.getUserType());
//        assertTrue(actualLoginResult.hasBody());
//        assertTrue(actualLoginResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#getImage(String)}
//     */
//    @Test
//    void testGetImage2() throws Exception {
//        // Arrange
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users/getImage", "Uri Variables")
//                .param("picName", "foo");
//
//        // Act and Assert
//        MockMvcBuilders.standaloneSetup(userEndpoint)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#login(AuthRequestDto)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testLogin4() throws Exception {
//        // TODO: Diffblue Cover was only able to create a partial test for this method:
//        //   Reason: Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@53553930 testClass = com.example.authorbookrest.endpoint.DiffblueFakeClass166, locations = [], classes = [com.example.authorbookrest.endpoint.UserEndpoint, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3b574709, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@2d27dba, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@ee66d068, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6737c4b7], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        // Arrange
//        AuthRequestDto authRequestDto = new AuthRequestDto();
//        authRequestDto.setEmail("jane.doe@example.org");
//        authRequestDto.setPassword("iloveyou");
//        String content = (new ObjectMapper()).writeValueAsString(authRequestDto);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/users/auth")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//
//        // Act
//        MockMvcBuilders.standaloneSetup(userEndpoint).build().perform(requestBuilder);
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#register(CreateUserRequestDto)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRegister3() throws Exception {
//        // TODO: Diffblue Cover was only able to create a partial test for this method:
//        //   Reason: Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@7cd9aff4 testClass = com.example.authorbookrest.endpoint.DiffblueFakeClass493, locations = [], classes = [com.example.authorbookrest.endpoint.UserEndpoint, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3b574709, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@2d27dba, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@ee66d068, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6737c4b7], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        // Arrange
//        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
//        createUserRequestDto.setEmail("jane.doe@example.org");
//        createUserRequestDto.setName("Name");
//        createUserRequestDto.setPassword("iloveyou");
//        createUserRequestDto.setSurname("Doe");
//        String content = (new ObjectMapper()).writeValueAsString(createUserRequestDto);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//
//        // Act
//        MockMvcBuilders.standaloneSetup(userEndpoint).build().perform(requestBuilder);
//    }
//
//    /**
//     * Method under test: {@link UserEndpoint#uploadImage(int, MultipartFile)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testUploadImage2() throws Exception {
//        // TODO: Diffblue Cover was only able to create a partial test for this method:
//        //   Reason: Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@2104c727 testClass = com.example.authorbookrest.endpoint.DiffblueFakeClass814, locations = [], classes = [com.example.authorbookrest.endpoint.UserEndpoint, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3b574709, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@2d27dba, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@ee66d068, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6737c4b7], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        // Arrange
//        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/v1/users/image/{id}", 1);
//        MockHttpServletRequestBuilder requestBuilder = postResult.param("picture",
//                String.valueOf(new MockMultipartFile("Name", (InputStream) null)));
//
//        // Act
//        MockMvcBuilders.standaloneSetup(userEndpoint).build().perform(requestBuilder);
//    }
//}
