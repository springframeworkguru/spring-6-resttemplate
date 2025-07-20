package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerClientImplTest {
    @Autowired
    BeerClientImpl beerClientImpl;
    @Test
    void listBeersNullBeerName() {
        beerClientImpl.listBeers(null, null, null, null, null);
    }
    @Test
    void listBeers() {
        beerClientImpl.listBeers("ALE", null, null, null, null);
    }
    @Test
    void getBeerById() {
        Page<BeerDTO> beerDTOS = beerClientImpl.listBeers();
        BeerDTO dto = beerDTOS.getContent().get(0);
        BeerDTO byId = beerClientImpl.getBeerById(dto.getId());

        assertNotNull(byId);
    }
    @Test
    void createBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .price(new BigDecimal("12.99"))
                .beerName("Test Beer")
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .build();
        BeerDTO savedBeer = beerClientImpl.saveNewBeer(beerDTO);
        assertNotNull(savedBeer);
    }
    @Test
    void updateBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                .price(new BigDecimal("12.99"))
                .beerName("Test Beer")
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .build();
        BeerDTO savedBeer = beerClientImpl.saveNewBeer(beerDTO);

        final String beerName = "Indian Beer";
        savedBeer.setBeerName(beerName);
        BeerDTO updatedBeer = beerClientImpl.updateBeerById(savedBeer);
        assertEquals(beerName, updatedBeer.getBeerName());

    }
    @Test
    void deleteBeer() {
        Page<BeerDTO> beerDTOS = beerClientImpl.listBeers();
        BeerDTO dto = beerDTOS.getContent().get(0);
        beerClientImpl.deleteBeer(dto.getId());
        assertThrows(HttpClientErrorException.class,
                () -> beerClientImpl.getBeerById(dto.getId())
        );

    }
}