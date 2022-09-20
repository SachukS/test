package com.sachuk.TestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachuk.TestTask.model.Product;
import com.sachuk.TestTask.model.Shop;
import com.sachuk.TestTask.repository.ProductRepository;
import com.sachuk.TestTask.repository.ShopRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TestTaskIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static Shop shop;

    @BeforeAll
    static void setup(){
        shop = new Shop();
        shop.setName("test shop");
        shop.setAddress("Lorem ipsum transition");
        shop.setShopsProducts(new HashSet<>());
    }

    @AfterEach
    void clean() {
        shopRepository.deleteAllByName("test shop");
        productRepository.deleteAllByName("test product");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenShop_whenUserRoleIsAdnin_thenReturnCreatedShop() throws Exception{
        ResultActions response = mockMvc.perform(post("/api/v1/shops/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shop)));

        response.andDo(print()).
                andExpect(status().isCreated());

        assertThat(shopRepository.findAll(), hasItem(shop));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenShop_whenNameLess8_thenReturn400() throws Exception{
        Shop shop = new Shop();
        shop.setName("test<8");
        shop.setAddress("Lorem ipsum transition");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shop)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name is too short, minimum 8 symbols"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenShop_whenAddressLess20_thenReturn400() throws Exception{
        Shop shop = new Shop();
        shop.setName("test shop");
        shop.setAddress("less then 20");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shop)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Address is too short, minimum 20 symbols"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenShop_whenShopAlreadyExist_thenReturn400() throws Exception{
        shopRepository.save(shop);

        ResultActions response = mockMvc.perform(post("/api/v1/shops/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shop)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Shop already exist"));
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenShop_whenUserRoleIsUser_thenReturn403() throws Exception{
        ResultActions response = mockMvc.perform(post("/api/v1/shops/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shop)));

        response.andDo(print()).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void getListOfShops_thenReturnListOfShops() throws Exception{
        List<Shop> shops = shopRepository.findAll();

        ResultActions response = mockMvc.perform(get("/api/v1/shops"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(shops.size())));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenShopId_whenGetShopById_thenReturnShopObject() throws Exception{
        Shop saved = shopRepository.save(shop);

        ResultActions response = mockMvc.perform(get("/api/v1/shops/{id}", saved.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(shop.getName())))
                .andExpect(jsonPath("$.address", is(shop.getAddress())));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void givenInvalidShopId_whenGetShopById_thenReturn404() throws Exception{
        Shop saved = shopRepository.save(shop);

        ResultActions response = mockMvc.perform(get("/api/v1/shops/{id}", 0L));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().string("Shop \"0\" not found"));

    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenAllCorrect_thenReturnAddedProduct() throws Exception{
        Shop saved = shopRepository.save(shop);

        Product product = new Product();
        product.setName("test product");
        product.setDescription("test product description");
        product.setPrice("14.56");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isOk())
                .andDo(print());

        assertThat(shopRepository.findShopById(saved.getId()).get().getShopsProducts(), hasItem(product));
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenExists_thenReturn400() throws Exception{
        Product product = new Product();
        product.setName("test product");
        product.setDescription("test product description");
        product.setPrice("14.56");

        shop.getShopsProducts().add(product);

        Shop saved = shopRepository.save(shop);

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string("Product already exist in this shop"));

        shop.setShopsProducts(new HashSet<>());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenNameLess5_thenReturn400() throws Exception{
        Shop saved = shopRepository.save(shop);

        Product product = new Product();
        product.setName("test");
        product.setDescription("test product description");
        product.setPrice("14.56");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string("Name is too short, minimum 5 symbols"));

    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenDescriptionLess10_thenReturn400() throws Exception{
        Shop saved = shopRepository.save(shop);

        Product product = new Product();
        product.setName("test product");
        product.setDescription("test");
        product.setPrice("14.56");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string("Description is too short, minimum 10 symbols"));

    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenPriceWrongFormat_thenReturn400() throws Exception{
        Shop saved = shopRepository.save(shop);

        Product product = new Product();
        product.setName("test product");
        product.setDescription("test product description");
        product.setPrice("14.101");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string("must match \"^\\d+(.\\d{1,2})?$\""));

    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    public void givenProduct_whenPriceHasStr_thenReturn400() throws Exception{
        Shop saved = shopRepository.save(shop);

        Product product = new Product();
        product.setName("test product");
        product.setDescription("test product description");
        product.setPrice("14.OK");

        ResultActions response = mockMvc.perform(post("/api/v1/shops/{id}/products/add", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string("must match \"^\\d+(.\\d{1,2})?$\""));

    }

}
