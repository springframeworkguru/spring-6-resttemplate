package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BeerClient {
    Page<BeerDTO> listBeers();

    Page<BeerDTO> listBeers(String beerName, String beerStyle,
                            Boolean showInventory, Integer pageNumber, Integer pageSize);
    BeerDTO getBeerById(UUID beerId);

    void deleteBeer(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beer);

    BeerDTO updateBeerById(BeerDTO savedBeer);
}