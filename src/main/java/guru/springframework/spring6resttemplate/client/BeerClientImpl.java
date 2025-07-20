package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {
    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }
    @Override
    public BeerDTO saveNewBeer(BeerDTO newBeer) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        URI uri = restTemplate.postForLocation(GET_BEER_PATH, newBeer);
        return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO updateBeerById(BeerDTO savedBeer) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID_PATH, savedBeer, savedBeer.getId());
        return this.getBeerById(savedBeer.getId());
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }
    @Override
    public Page<BeerDTO> listBeers(String beerName, String beerStyle,
                                   Boolean showInventory, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
        //setting query params in uri path
            if (beerName != null) uriBuilder.queryParam("beerName", beerName);
            if (beerStyle != null) uriBuilder.queryParam("beerStyle", beerStyle);
            if (showInventory != null) uriBuilder.queryParam("showInventory", showInventory);
            if (pageNumber != null) uriBuilder.queryParam("pageNumber", pageNumber);
            if (pageSize != null) uriBuilder.queryParam("pageSize", pageSize);

            ResponseEntity<BeerDTOPageImpl> response = restTemplate.getForEntity(uriBuilder.toUriString(), BeerDTOPageImpl.class);

            return response.getBody();
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(GET_BEER_BY_ID_PATH);
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

}