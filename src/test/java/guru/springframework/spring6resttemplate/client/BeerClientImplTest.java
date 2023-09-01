package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void listBeersNoBeerName() {

        beerClient.listBeers(null, null, null, null, null);

    }

    @Test
    void listBeers() {
        beerClient.listBeers("ALE", null, null, null, null);
    }

    @Test
    void listBeersByBeerStyle() {
        beerClient.listBeers(null, BeerStyle.ALE, null, null, null);
    }

}