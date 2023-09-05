package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void listBeers() {
        Page<BeerDTO> result = beerClient.listBeers(null, null, null, null, null);
        //assertThat(result.getContent()).isNotNull();
    }

    @Test
    void listBeersByName() {
        Page<BeerDTO> result = beerClient.listBeers("ALE", null, null, null, null);
        assertThat(result.getContent()).isNotNull();
    }

    @Test
    void listBeersByStyle() {
        Page<BeerDTO> result = beerClient.listBeers(null, BeerStyle.ALE, null, null, null);
        assertThat(result.getContent()).isNotNull();
    }

    @Test
    void listBeersWithDifferentPageSizes() {
        for (Integer pageSize = 5; pageSize <= 25; pageSize+=5) {
            Page<BeerDTO> result = beerClient.listBeers(null, null, null, null, pageSize);
            System.out.println("Testing API with page size of " + pageSize.toString());
            assertEquals(pageSize, result.getContent().size());
        }
        System.out.println("Paging works! :)");
    }

    @Test
    void getBeerById() {
        Page<BeerDTO> dtos = beerClient.listBeers();
        BeerDTO dto = dtos.getContent().get(0);
        BeerDTO byId = beerClient.getBeerById(dto.getId());
        assertNotNull(byId);
    }

    @Test
    void testCreateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal(10.99))
                .beerName("Mango Bobs - Created in the test")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("1234567")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

    @Test
    void testUpdateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal(10.99))
                .beerName("Mango Bobs - #2 - Created in the test")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("1234567")
                .build();

        BeerDTO beerDto = beerClient.createBeer(newDto);

        final String newName = "Mango Bobs - #3 - Updated";

        beerDto.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDto);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void testDeleteBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal(10.99))
                .beerName("Mango Bobs - #2 - Created in the test")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("1234567")
                .build();

        BeerDTO beerDto = beerClient.createBeer(newDto);

        beerClient.deleteBeer(beerDto.getId());

        assertThrows(HttpClientErrorException.class, () -> {
            // should error
            beerClient.getBeerById(beerDto.getId());
        });
    }
}